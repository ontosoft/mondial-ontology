package ontologies.mondial.services;

import ontologies.mondial.dao.Water;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Separate Java service class. Backend implementation for the address book
 * application, with "detached entities" simulating real world DAO. Typically
 * these something that the Java EE or Spring backend services provide.
 */

public class WaterService {

	private static WaterService instance;

	public static WaterService createDemoService() {

		WaterService waterService = new WaterService();

		String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
				+ "select distinct ?wa ?country ?province ?name ?type  where {"
				+ " ?p :provinceName ?province. ?p :provinceInCountry ?co. ?co :countryName ?country. "
				+ " ?wa :waterName ?name. ?wa :waterInProvince ?p.   ?wa :waterType ?type. "
				+ " }";

		QuestOWLE quest = new QuestOWLE(sparqlQuery);
		try {
			quest.runQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ArrayList<String> row : quest.getQueryResult()) {
			Water water = new Water();
			water.setUri(row.get(0));
			water.setCountry(row.get(1));
			water.setProvince(row.get(2));
			water.setName(row.get(3));
			water.setType(row.get(4));
			waterService.save(water);
		}
		instance = waterService;

		return instance;
	}

	public static WaterService reloadService(String uri, String countryUri, String provinceUri, String countryString,
			String provinceString, String nameString, String typeString) {
		WaterService waterService = new WaterService();

		String add1 = "", add2 = "", add3 = "", add4 = "", add5 = "", add6 = "", add7="";
		if (!countryString.isEmpty())
			add1 = " FILTER regex(str(?country), \"" + countryString + "\"). ";
		if (!provinceString.isEmpty())
			add2 = " FILTER regex(str(?province), \"" + provinceString
					+ "\"). ";
		if (!nameString.isEmpty())
			add3 = " FILTER regex(str(?name), \"" + nameString + "\"). ";
		if (!typeString.isEmpty())
			add4 = " FILTER regex(str(?type), \"" + typeString + "\"). ";
		if (!uri.isEmpty())
			add5 = " FILTER (?wa = " + "" + uri + "). ";
		if (!countryUri.isEmpty())
			add6 = " FILTER (?co = " + "" + countryUri + "). ";
		if (!provinceUri.isEmpty())
			add7 = " FILTER (?p = " + "" + provinceUri + "). ";
		
		String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
				+ "select distinct ?wa ?country ?province ?name ?type  where {"
				+ " ?p :provinceName ?province. ?p :provinceInCountry ?co. ?co :countryName ?country. "
				+ " ?wa :waterName ?name. ?wa :waterInProvince ?p.   ?wa :waterType ?type. "
				+ add1 + add2 + add3 + add4 + add5 + add6 + add7 
				+ " }";
		System.out.println("************ Beginning sparql water ********");
		System.out.println(sparqlQuery);

		QuestOWLE quest = new QuestOWLE(sparqlQuery);
		try {
			quest.runQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ArrayList<String> row : quest.getQueryResult()) {
			Water water = new Water();
			water.setUri(row.get(0));
			water.setCountry(row.get(1));
			water.setProvince(row.get(2));
			water.setName(row.get(3));
			water.setType(row.get(4));
			waterService.save(water);
		}
		instance = waterService;
		return instance;
	}
	
	public static WaterService reloadByRiverDestination(String riverFlowsTo) {
		WaterService waterService = new WaterService();

		String add1 = "";
		if (!riverFlowsTo.isEmpty())
			add1 = " FILTER (?ri = " + "" + riverFlowsTo + "). ";
		
		String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
				+ "select distinct ?wa ?country ?province ?name ?type  where {"
				+ " ?p :provinceName ?province. ?p :provinceInCountry ?co. ?co :countryName ?country. "
				+ " ?wa :waterName ?name. ?wa :waterInProvince ?p.   ?wa :waterType ?type. "
				+ " ?ri :riverFlowsTo ?wa. "
				+ add1 
				+ " }";
		System.out.println("************ Beginning sparql water ********");
		System.out.println(sparqlQuery);

		QuestOWLE quest = new QuestOWLE(sparqlQuery);
		try {
			quest.runQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ArrayList<String> row : quest.getQueryResult()) {
			Water water = new Water();
			water.setUri(row.get(0));
			water.setCountry(row.get(1));
			water.setProvince(row.get(2));
			water.setName(row.get(3));
			water.setType(row.get(4));
			waterService.save(water);
		}
		instance = waterService;
		return instance;
	}

	private HashMap<Long, Water> contacts = new HashMap<>();
	private long nextId = 0;

	public synchronized List<Water> findAll(String stringFilter) {
		ArrayList<Water> arrayList = new ArrayList<Water>();
		for (Water contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter
						.isEmpty())
						|| contact.toString().toLowerCase()
								.contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(WaterService.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Water>() {

			@Override
			public int compare(Water o1, Water o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	public synchronized Water getFirst() {
		if (!contacts.isEmpty()) {
			ArrayList<Water> arrayList = new ArrayList<Water>();
			for (Water contact : contacts.values()) {
				try {
					arrayList.add(contact.clone());
				} catch (CloneNotSupportedException ex) {
					Logger.getLogger(WaterService.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
			return arrayList.get(0);
		} else {
			return null;
		}

	}

	public synchronized long count() {
		return contacts.size();
	}

	public synchronized void delete(Water value) {
		contacts.remove(value.getId());
	}

	public synchronized void save(Water entry) {
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Water) BeanUtils.cloneBean(entry);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

}
