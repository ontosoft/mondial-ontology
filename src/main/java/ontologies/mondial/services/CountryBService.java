package ontologies.mondial.services;

import ontologies.mondial.dao.CountryB;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CountryBService {

	private static CountryBService instance;

	public static CountryBService createDemoService() {

			CountryBService countryBService = new CountryBService();

			String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
					+ "select distinct ?co ?country ?capital ?population ?area ?continent ?border where {"
					+ "  ?co :countryName ?country. ?en :encompass1 ?co. ?en :encompass2 ?con.  "
					+ "  ?co :countryHasCapital ?ci. ?ci :cityName ?capital."
					+ "  ?b :border1 ?co.  ?b :border2 ?c2.  ?b :borderLength ?border. "
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
				CountryB countryB = new CountryB();
				countryB.setUri(row.get(0));
				countryB.setCountry(row.get(1));
				countryB.setCapital(row.get(2));
				countryB.setPopulation(Integer.parseInt(row.get(3)));
				countryB.setArea(Float.parseFloat(row.get(4)));
				countryB.setContinent(row.get(5));
				countryB.setBorderLength(Double.parseDouble(row.get(6)));
				countryBService.save(countryB);
			}
			instance = countryBService;

		return instance;
	}

	//Extract bordering countries of country with uri
	public static CountryBService reloadService(String uri,
			String continentString, String populationLess,
			String populationGreater, String areaLess, String areaGreater) {
		CountryBService continentService = new CountryBService();

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
			add6 = " FILTER (?c1 = " + "" + uri + "). ";

		String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
				+ "select * where { {"
				+ "select distinct ?c2 ?country ?capital ?population ?area ?continent ?border where {"
				+ "  ?c2 :countryName ?country. ?en :encompass1 ?c2. ?en :encompass2 ?con.  "
				+ "  ?con :continentName ?continent. "
				+ "  ?c2 :countryHasCapital ?ci. ?ci :cityName ?capital. "
				+ "  ?b :border1 ?c1.  ?b :border2 ?c2.  ?b :borderLength ?border. "
				+ add1
				+ add2
				+ add3
				+ add4
				+ add5
				+ add6
				+ "  ?c2 :countryArea ?area. ?c2 :countryPopulation ?population.}  }"
			    + " UNION { "
			    + "select distinct ?c2 ?country ?capital ?population ?area ?continent ?border where {"
				+ "  ?c2 :countryName ?country. ?en :encompass1 ?c2. ?en :encompass2 ?con.  "
				+ "  ?con :continentName ?continent. "
				+ "  ?c2 :countryHasCapital ?ci. ?ci :cityName ?capital. "
				+ "  ?b :border1 ?c2.  ?b :border2 ?c1.  ?b :borderLength ?border. "
				+ add1
				+ add2
				+ add3
				+ add4
				+ add5
				+ add6
				+ "  ?c2 :countryArea ?area. ?c2 :countryPopulation ?population.} } }";
		
		System.out.println(sparqlQuery);

		QuestOWLE quest = new QuestOWLE(sparqlQuery);
		try {
			quest.runQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ArrayList<String> row : quest.getQueryResult()) {
			CountryB countryB = new CountryB();
			countryB.setUri(row.get(0));
			countryB.setCountry(row.get(1));
			countryB.setCapital(row.get(2));
			countryB.setPopulation(Integer.parseInt(row.get(3)));
			countryB.setArea(Float.parseFloat(row.get(4)));
			countryB.setContinent(row.get(5));
			countryB.setBorderLength(Double.parseDouble(row.get(6)));
			continentService.save(countryB);
			
		}
		instance = continentService;
		return instance;
	}

	private HashMap<Long, CountryB> contacts = new HashMap<>();
	private long nextId = 0;

	public synchronized List<CountryB> findAll(String stringFilter) {
		ArrayList<CountryB> arrayList = new ArrayList<CountryB>();
		for (CountryB contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter
						.isEmpty())
						|| contact.toString().toLowerCase()
								.contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(CountryBService.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<CountryB>() {

			@Override
			public int compare(CountryB o1, CountryB o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	public synchronized CountryB getFirst() {
		if (!contacts.isEmpty()) {
			ArrayList<CountryB> arrayList = new ArrayList<CountryB>();
			for (CountryB contact : contacts.values()) {
				try {
					arrayList.add(contact.clone());
				} catch (CloneNotSupportedException ex) {
					Logger.getLogger(CountryBService.class.getName()).log(
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

	public synchronized void delete(CountryB value) {
		contacts.remove(value.getId());
	}

	public synchronized void save(CountryB entry) {
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (CountryB) BeanUtils.cloneBean(entry);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

}
