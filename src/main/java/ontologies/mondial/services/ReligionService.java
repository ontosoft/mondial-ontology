package ontologies.mondial.services;


import ontologies.mondial.dao.Religion;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ReligionService {

	private static ReligionService instance;

	public static ReligionService createDemoService() {

			ReligionService religionService = new ReligionService();
				
			String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
					+ "select distinct  ?co ?country ?name ?percentage where {"
					+ "  ?co :countryName ?country. ?rel :religionInCountry ?co. "
					+ "  ?rel :religionName ?name. OPTIONAL { ?rel :religionPercentage ?percentage.} "
				
					+ " }";

			QuestOWLE quest = new QuestOWLE(sparqlQuery);
			try {
				quest.runQuery();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (ArrayList<String> row : quest.getQueryResult()) {
				Religion religion = new Religion();
				religion.setCountryUri(row.get(0));
				religion.setCountry(row.get(1));
				religion.setName(row.get(2));
				if(!row.get(3).equals(""))
					religion.setPercentage(Double.parseDouble(row.get(3)));
				religionService.save(religion);
			}
			instance = religionService;

		return instance;
	}
	
	public static ReligionService reloadService(String countryUri, String countryString, String name, 
			String percentageLess, String percentageGreater) {
			ReligionService religionService = new ReligionService();
			

			String add1 = "",add2 = "", add3 = "", add4 = "", add5 ="";
			if (!countryString.isEmpty()) 
				add1 = "FILTER regex(str(?country), \""+ countryString + "\"). ";

			if (!percentageGreater.isEmpty()) 
				add2 = "FILTER (?percentage > "+ percentageGreater + "). ";
			if (!percentageLess.isEmpty()) 
				add3 = "FILTER (?percentage < "+ ""+ percentageLess + "). ";
			if (!countryUri.isEmpty()) 
				add4 = "FILTER (?co = "+ ""+ countryUri + "). ";
			if (!name.isEmpty()) 
				add5 = "FILTER regex(str(?name), \""+ name + "\"). ";
			
			String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
					+ "select distinct  ?co ?country ?name ?percentage where {"
					+ "  ?co :countryName ?country. ?rel :religionInCountry ?co. "
					+ "  ?rel :religionName ?name. OPTIONAL { ?rel :religionPercentage ?percentage.} "
					+ add1 + add2 + add3 + add4 + add5
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
				Religion religion = new Religion();
				religion.setCountryUri(row.get(0));
				religion.setCountry(row.get(1));
				religion.setName(row.get(2));
				if(!row.get(3).equals(""))
					religion.setPercentage(Double.parseDouble(row.get(3)));
				religionService.save(religion);
			}
			instance = religionService;
			return instance;
	}

	private HashMap<Long, Religion> contacts = new HashMap<>();
	private long nextId = 0;

	public synchronized List<Religion> findAll(String stringFilter) {
		ArrayList<Religion> arrayList = new ArrayList<Religion>();
		for (Religion contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter
						.isEmpty())
						|| contact.toString().toLowerCase()
								.contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(ReligionService.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Religion>() {

			@Override
			public int compare(Religion o1, Religion o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	public synchronized long count() {
		return contacts.size();
	}

	public synchronized void delete(Religion value) {
		contacts.remove(value.getId());
	}

	public synchronized void save(Religion entry) {
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Religion) BeanUtils.cloneBean(entry);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

}
