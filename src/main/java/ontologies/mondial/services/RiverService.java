package ontologies.mondial.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ontologies.mondial.dao.River;

import org.apache.commons.beanutils.BeanUtils;

public class RiverService {

	private static RiverService instance;

	public static RiverService createDemoService() {

		final RiverService riverService = new RiverService();

		String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
				+ "select distinct ?r ?name ?source ?estuary ?length ?saltitude where {"
				+ "  ?r :waterName ?name. ?r :riverSourceCoo ?source. ?r :riverEstuaryCoo ?estuary. "
				+ "?r :riverLength ?length. ?r :riverSourceAltitude ?saltitude. "
				+ " }";
		QuestOWLE quest = new QuestOWLE(sparqlQuery);
		try {
			quest.runQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ArrayList<String> row : quest.getQueryResult()) {
			River river = new River();
			river.setUri(row.get(0));
			river.setName(row.get(1));
			river.setSource(row.get(2));
			river.setEstuary(row.get(3));
			river.setLength(Double.parseDouble(row.get(4)));
			river.setSourceAltitude(Double.parseDouble(row.get(5)));

			riverService.save(river);
		}
		instance = riverService;

		return instance;
	}

	public static RiverService retrieveSecificRiver(String waterUri) {
		RiverService riverService = new RiverService();

		String add1 = "";
		
		if (!waterUri.isEmpty())
			add1 = "FILTER (?r = " + "" + waterUri + "). ";

		String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
				+ "select distinct ?r ?name ?source ?estuary ?length ?saltitude where {"
				+ " ?r :waterName ?name. OPTIONAL { ?r :riverSourceCoo ?source.} OPTIONAL { ?r :riverEstuaryCoo ?estuary.} "
				+ " OPTIONAL {?r :riverLength ?length.} OPTIONAL { ?r :riverSourceAltitude ?saltitude.} "
				+ add1
				+ " }";

		System.out.println(sparqlQuery);

		QuestOWLE quest = new QuestOWLE(sparqlQuery);
		try {
			quest.runQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ArrayList<String> row : quest.getQueryResult()) {
			River river = new River();
			river.setUri(row.get(0));
			river.setName(row.get(1));
			river.setSource(row.get(2));
			river.setEstuary(row.get(3));
			if(!row.get(4).equals(""))
				river.setLength(Double.parseDouble(row.get(4)));
			if(!row.get(5).equals(""))
				river.setSourceAltitude(Double.parseDouble(row.get(5)));
			riverService.save(river);
		}
		instance = riverService;
		return instance;
	}

	
	private HashMap<Long, River> contacts = new HashMap<>();
	private long nextId = 0;

	public synchronized List<River> findAll(String stringFilter) {
		ArrayList<River> arrayList = new ArrayList<River>();
		for (River contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter
						.isEmpty())
						|| contact.toString().toLowerCase()
								.contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(RiverService.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<River>() {

			@Override
			public int compare(River o1, River o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}
	
	public synchronized River getFirst() {
		if (!contacts.isEmpty()) {
			ArrayList<River> arrayList = new ArrayList<River>();
			for (River contact : contacts.values()) {
				try {
					arrayList.add(contact.clone());
				} catch (CloneNotSupportedException ex) {
					Logger.getLogger(RiverService.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
			return arrayList.get(0);
		} else{
			return null;
		}
			
	}

	public synchronized long count() {
		return contacts.size();
	}

	public synchronized void delete(River value) {
		contacts.remove(value.getId());
	}

	public synchronized void save(River entry) {
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (River) BeanUtils.cloneBean(entry);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

}
