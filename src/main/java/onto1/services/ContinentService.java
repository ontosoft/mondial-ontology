package onto1.services;

import onto1.QuestOWLE;
import onto1.dao.Continent;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Separate Java service class. Backend implementation for the address book
 * application, with "detached entities" simulating real world DAO. Typically
 * these something that the Java EE or Spring backend services provide.
 */

public class ContinentService {

	private static ContinentService instance;

	public static ContinentService createDemoService() {
		if (instance == null) {

			ContinentService continentService = new ContinentService();
				
			String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
					+ "select distinct ?country ?population ?area ?continent    where {"
					+ "  ?co :countryName ?country. ?en :encompass1 ?co. ?en :encompass2 ?con.  "
					+ "  ?con :continentName ?continent. "
					+ "  ?co :countryArea ?area. ?co :countryPopulation ?population.}";

			QuestOWLE quest = new QuestOWLE(sparqlQuery);
			try {
				quest.runQuery();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (ArrayList<String> row : quest.getQueryResult()) {
				Continent continent = new Continent();
				continent.setCountry(row.get(0));
				continent.setPopulation(Integer.parseInt(row.get(1)));
				continent.setArea(Float.parseFloat(row.get(2)));
				continent.setContinent(row.get(3));
				continentService.save(continent);
			}
			instance = continentService;
		}

		return instance;
	}
	
	public static ContinentService reloadService(String continentString, 
			String populationLess, String populationGreater, String areaLess, String areaGreater) {
			ContinentService continentService = new ContinentService();
			

			String add1 = "",add2 = "", add3 = "", add4 = "", add5 = "";
			if (!continentString.isEmpty()) 
				add1 = "FILTER regex(str(?continent), \""+ continentString + "\"). ";
			if (!populationLess.isEmpty()) 
				add2 = "FILTER (?population < "+ populationLess + "). ";
			if (!populationGreater.isEmpty()) 
				add3 = "FILTER (?population > "+ populationGreater + "). ";
			if (!areaLess.isEmpty()) 
				add4 = "FILTER (?area < "+ ""+ areaLess + "). ";
			if (!areaGreater.isEmpty()) 
				add5 = "FILTER (?area > "+ ""+ areaGreater + "). ";
			
			String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
					+ "select distinct ?country ?population ?area ?continent    where {"
					+ "  ?co :countryName ?country. ?en :encompass1 ?co. ?en :encompass2 ?con.  "
					+ "  ?con :continentName ?continent. "
					+ add1 + add2 + add3 + add4 + add5
					+ "  ?co :countryArea ?area. ?co :countryPopulation ?population.}";
			System.out.println("************Beginning********");
			System.out.println(sparqlQuery);
			
			QuestOWLE quest = new QuestOWLE(sparqlQuery);
			try {
				quest.runQuery();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (ArrayList<String> row : quest.getQueryResult()) {
				Continent continent = new Continent();
				continent.setCountry(row.get(0));
				continent.setPopulation(Integer.parseInt(row.get(1)));
				continent.setArea(Float.parseFloat(row.get(2)));
				continent.setContinent(row.get(3));
				continentService.save(continent);
			}
			instance = continentService;
			return instance;
	}

	private HashMap<Long, Continent> contacts = new HashMap<>();
	private long nextId = 0;

	public synchronized List<Continent> findAll(String stringFilter) {
		ArrayList arrayList = new ArrayList();
		for (Continent contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter
						.isEmpty())
						|| contact.toString().toLowerCase()
								.contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(ContinentService.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Continent>() {

			@Override
			public int compare(Continent o1, Continent o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	public synchronized long count() {
		return contacts.size();
	}

	public synchronized void delete(Continent value) {
		contacts.remove(value.getId());
	}

	public synchronized void save(Continent entry) {
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Continent) BeanUtils.cloneBean(entry);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

}
