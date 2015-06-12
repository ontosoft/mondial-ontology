package ontologies.mondial.services;

import ontologies.mondial.dao.Country;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Separate Java service class. Backend implementation for the address book
 * application, with "detached entities" simulating real world DAO. Typically
 * these something that the Java EE or Spring backend services provide.
 */

public class CountryService {

	private static CountryService instance;

	public static CountryService createDemoService() {

			CountryService countryService = new CountryService();

			String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
					+ "select distinct ?co ?country ?capital ?population ?area ?continent  where {"
					+ "  ?co :countryName ?country. ?en :encompass1 ?co. ?en :encompass2 ?con.  "
					+ "  ?co :countryHasCapital ?ci. ?ci :cityName ?capital."
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
				Country country = new Country();
				country.setUri(row.get(0));
				country.setCountry(row.get(1));
				country.setCapital(row.get(2));
				country.setPopulation(Integer.parseInt(row.get(3)));
				country.setArea(Float.parseFloat(row.get(4)));
				country.setContinent(row.get(5));
				countryService.save(country);
			}
			instance = countryService;

		return instance;
	}

	public static CountryService reloadService(String uri,
			String continentString, String populationLess,
			String populationGreater, String areaLess, String areaGreater) {
		CountryService continentService = new CountryService();

		String add1 = "", add2 = "", add3 = "", add4 = "", add5 = "", add6 = "";
		if (!continentString.isEmpty())
			add1 = " FILTER regex(str(?continent), \"" + continentString
					+ "\"). ";
		if (!populationLess.isEmpty())
			add2 = " FILTER (?population < " + populationLess + "). ";
		if (!populationGreater.isEmpty())
			add3 = " FILTER (?population > " + populationGreater + "). ";
		if (!areaLess.isEmpty())
			add4 = " FILTER (?area < " + "" + areaLess + "). ";
		if (!areaGreater.isEmpty())
			add5 = " FILTER (?area > " + "" + areaGreater + "). ";
		if (!uri.isEmpty())
			add6 = " FILTER (?co = " + "" + uri + "). ";

		String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
				+ "select distinct ?co ?country ?capital ?population ?area ?continent    where {"
				+ "  ?co :countryName ?country. ?en :encompass1 ?co. ?en :encompass2 ?con.  "
				+ "  ?con :continentName ?continent. "
				+ "  ?co :countryHasCapital ?ci. ?ci :cityName ?capital. "
				+ add1
				+ add2
				+ add3
				+ add4
				+ add5
				+ add6
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
			Country country = new Country();
			country.setUri(row.get(0));
			country.setCountry(row.get(1));
			country.setCapital(row.get(2));
			country.setPopulation(Integer.parseInt(row.get(3)));
			country.setArea(Float.parseFloat(row.get(4)));
			country.setContinent(row.get(5));
			continentService.save(country);
		}
		instance = continentService;
		return instance;
	}

	private HashMap<Long, Country> contacts = new HashMap<>();
	private long nextId = 0;

	public synchronized List<Country> findAll(String stringFilter) {
		ArrayList<Country> arrayList = new ArrayList<Country>();
		for (Country contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter
						.isEmpty())
						|| contact.toString().toLowerCase()
								.contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(CountryService.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Country>() {

			@Override
			public int compare(Country o1, Country o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	public synchronized Country getFirst() {
		if (!contacts.isEmpty()) {
			ArrayList<Country> arrayList = new ArrayList<Country>();
			for (Country contact : contacts.values()) {
				try {
					arrayList.add(contact.clone());
				} catch (CloneNotSupportedException ex) {
					Logger.getLogger(CountryService.class.getName()).log(
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

	public synchronized void delete(Country value) {
		contacts.remove(value.getId());
	}

	public synchronized void save(Country entry) {
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Country) BeanUtils.cloneBean(entry);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

}
