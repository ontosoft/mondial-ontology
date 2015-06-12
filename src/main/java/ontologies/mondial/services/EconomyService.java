package ontologies.mondial.services;


import ontologies.mondial.dao.Economy;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Separate Java service class. Backend implementation for the address book
 * application, with "detached entities" simulating real world DAO. Typically
 * these something that the Java EE or Spring backend services provide.
 */

public class EconomyService {

	private static EconomyService instance;

	public static EconomyService createDemoService() {

			EconomyService economyService = new EconomyService();
				
			String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
					+ "select distinct ?country ?gdp ?industry ?services  ?inflation ?continent ?co where {"
					+ "  ?co :countryName ?country. ?en :encompass1 ?co. ?en :encompass2 ?con.  "
					+ "  ?con :continentName ?continent. "
					+ "  ?ec :economyInCountry ?co. "
					+ "  ?ec :economyGdp ?gdp. ?ec :economyIndustry ?industry. "
					+ "  ?ec :economyService ?services. ?ec :economyInflation ?inflation }";

			QuestOWLE quest = new QuestOWLE(sparqlQuery);
			try {
				quest.runQuery();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (ArrayList<String> row : quest.getQueryResult()) {
				Economy economy = new Economy();
				economy.setCountry(row.get(0));
				economy.setGdp(Float.parseFloat(row.get(1)));
				economy.setAgriculture(Float.parseFloat(row.get(2)));
				economy.setService(Float.parseFloat(row.get(3)));
				economy.setIndustry(Float.parseFloat(row.get(4)));
				economy.setContinent(row.get(5));
				economy.setCountryuri(row.get(6));
				economyService.save(economy);
			}
			instance = economyService;

		return instance;
	}
	
	public static EconomyService reloadService(String uri, String continentString, String gdpLess, 
			String gdpGreater, String agricultureLess, String agricultureGreater,String industryLess, 
			String industryGreater, String serviceLess, String serviceGreater, String inflationLess, 
			String inflationGreater) {
			EconomyService economyService = new EconomyService();
			

			String add1 = "",add2 = "", add3 = "", add4 = "", add5 = "",
					add6 = "", add7 = "", add8 = "", add9 = "", add10 = "";
			if (!continentString.isEmpty()) 
				add1 = "FILTER regex(str(?continent), \""+ continentString + "\"). ";
			if (!gdpLess.isEmpty()) 
				add2 = "FILTER (?gdp < "+ gdpLess + "). ";
			if (!gdpGreater.isEmpty()) 
				add3 = "FILTER (?gdp > "+ gdpGreater + "). ";
			if (!industryLess.isEmpty()) 
				add4 = "FILTER (?industry < "+ ""+ industryLess + "). ";
			if (!industryGreater.isEmpty()) 
				add5 = "FILTER (?industry > "+ ""+ industryGreater + "). ";
			if (!serviceLess.isEmpty()) 
				add6 = "FILTER (?services < "+ serviceLess + "). ";
			if (!serviceGreater.isEmpty()) 
				add7 = "FILTER (?services > "+ serviceGreater + "). ";
			if (!inflationLess.isEmpty()) 
				add8 = "FILTER (?inflation < "+ ""+ inflationLess + "). ";
			if (!inflationGreater.isEmpty()) 
				add9 = "FILTER (?inflation > "+ ""+ inflationGreater + "). ";
			if (!uri.isEmpty()) 
				add10 = "FILTER (?co = "+ ""+ uri + "). ";
			
			String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
					+ "select distinct ?country ?gdp ?industry ?services  ?inflation ?continent ?co where {"
					+ "  ?co :countryName ?country. ?en :encompass1 ?co. ?en :encompass2 ?con.  "
					+ "  ?con :continentName ?continent. "
					+ "  ?ec :economyInCountry ?co. "
					+ "  ?ec :economyGdp ?gdp. ?ec :economyIndustry ?industry. "
					+ "  ?ec :economyService ?services. ?ec :economyInflation ?inflation. " 
					+ add1 + add2 + add3 + add4 + add5 + add6 + add7 + add8 + add9 + add10 + " }";
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
				Economy economy = new Economy();
				economy.setCountry(row.get(0));
				economy.setGdp(Float.parseFloat(row.get(1)));
				economy.setAgriculture(Float.parseFloat(row.get(2)));
				economy.setService(Float.parseFloat(row.get(3)));
				economy.setIndustry(Float.parseFloat(row.get(4)));
				economy.setContinent(row.get(5));
				economy.setCountryuri(row.get(6));
				economyService.save(economy);
			}
			instance = economyService;
			return instance;
	}

	private HashMap<Long, Economy> contacts = new HashMap<>();
	private long nextId = 0;

	public synchronized List<Economy> findAll(String stringFilter) {
		ArrayList<Economy> arrayList = new ArrayList<Economy>();
		for (Economy contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter
						.isEmpty())
						|| contact.toString().toLowerCase()
								.contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(EconomyService.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Economy>() {

			@Override
			public int compare(Economy o1, Economy o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	public synchronized long count() {
		return contacts.size();
	}

	public synchronized void delete(Economy value) {
		contacts.remove(value.getId());
	}

	public synchronized void save(Economy entry) {
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Economy) BeanUtils.cloneBean(entry);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

}
