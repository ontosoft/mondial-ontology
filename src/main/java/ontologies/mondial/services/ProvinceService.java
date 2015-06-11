package ontologies.mondial.services;


import ontologies.mondial.dao.Province;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProvinceService {

    private static ProvinceService instance;

    public static ProvinceService createDemoService() {
        if (instance == null) {

            final ProvinceService provinceService = new ProvinceService();
            
            String sparqlQuery =
					"PREFIX : <http://www.example.org/monidal.owl#> \n" +
					"select distinct ?p ?country ?province ?capital ?population ?area where {" +
                    "  ?p :provinceName ?province. ?p :provinceInCountry ?co. ?co :countryName ?country. "
                    + "?p :provinceHasCapital ?c. ?c :cityName ?capital. "
            		+ "  ?p :provinceArea ?area. ?p :provincePopulation ?population. }";
            QuestOWLE quest = new QuestOWLE(sparqlQuery);
            try {
				quest.runQuery();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            for (ArrayList<String> row : quest.getQueryResult()) {
                Province province = new Province();
                province.setUri(row.get(0));
                province.setCountry(row.get(1));
                province.setProvince(row.get(2));
                province.setCity(row.get(3));
                province.setPopulation(Integer.parseInt(row.get(4)));
    			province.setArea(Float.parseFloat(row.get(5)));  
                provinceService.save(province);
            }
            instance = provinceService;
        }

        return instance;
    }
    
	public static ProvinceService reloadService(String uri, String countryString, 
			String populationLess, String populationGreater, String areaLess, String areaGreater) {
			ProvinceService provinceService = new ProvinceService();
			

			String add1 = "",add2 = "", add3 = "", add4 = "", add5 = "", add6 = "";
			if (!countryString.isEmpty()) 
				add1 = "FILTER regex(str(?country), \""+ countryString + "\"). ";
			if (!populationLess.isEmpty()) 
				add2 = "FILTER (?population < "+ populationLess + "). ";
			if (!populationGreater.isEmpty()) 
				add3 = "FILTER (?population > "+ populationGreater + "). ";
			if (!areaLess.isEmpty()) 
				add4 = "FILTER (?area < "+ ""+ areaLess + "). ";
			if (!areaGreater.isEmpty()) 
				add5 = "FILTER (?area > "+ ""+ areaGreater + "). ";
			System.out.println(uri);
			if (!uri.isEmpty()) 
				add6 = "FILTER (?co = "+ ""+ uri + "). ";
			
			
			
			String sparqlQuery = 	"PREFIX : <http://www.example.org/monidal.owl#> \n" +
					"select distinct ?p ?country ?province ?capital ?population ?area where {" +
                    "  ?p :provinceName ?province. ?p :provinceInCountry ?co. ?co :countryName ?country. "
                    + "?p :provinceHasCapital ?c. ?c :cityName ?capital. "
					+ add1 + add2 + add3 + add4 + add5 + add6
					+ "  ?p :provinceArea ?area. ?p :provincePopulation ?population.}";

			System.out.println(sparqlQuery);
			
			QuestOWLE quest = new QuestOWLE(sparqlQuery);
			try {
				quest.runQuery();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (ArrayList<String> row : quest.getQueryResult()) {
				Province province = new Province();
	                province.setUri(row.get(0));
	                province.setCountry(row.get(1));
	                province.setProvince(row.get(2));
	                province.setCity(row.get(3));
	                province.setPopulation(Integer.parseInt(row.get(4)));
	    			province.setArea(Float.parseFloat(row.get(5)));    
	    			
				provinceService.save(province);
			}
			instance = provinceService;
			return instance;
	}

    private HashMap<Long, Province> contacts = new HashMap<>();
    private long nextId = 0;

    public synchronized List<Province> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        for (Province contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase()
                                .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ProvinceService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Province>() {

            @Override
            public int compare(Province o1, Province o2) {
            	return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    public synchronized long count() {
        return contacts.size();
    }

    public synchronized void delete(Province value) {
        contacts.remove(value.getId());
    }

    public synchronized void save(Province entry) {
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Province) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        contacts.put(entry.getId(), entry);
    }

}
