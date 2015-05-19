package onto1.services;

import onto1.QuestOWLE;
import onto1.dao.Province;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Separate Java service class.
 * Backend implementation for the address book application, with "detached entities"
 * simulating real world DAO. Typically these something that the Java EE
 * or Spring backend services provide.
 */
// Backend service class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class ProvinceService {

    private static ProvinceService instance;

    public static ProvinceService createDemoService() {
        if (instance == null) {

            final ProvinceService provinceService = new ProvinceService();
            
            String sparqlQuery =
					"PREFIX : <http://www.example.org/monidal.owl#> \n" +
					"select distinct ?country ?province ?capital where {" +
                    "  ?p :provinceName ?province. ?p :provinceInCountry ?co. ?co :countryName ?country. "
                    + "?p :provinceHasCapital ?c. ?c :cityName ?capital}";
  
            QuestOWLE quest = new QuestOWLE(sparqlQuery);
            try {
				quest.runQuery();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            for (ArrayList<String> row : quest.getQueryResult()) {
                Province province = new Province();
                province.setCountry(row.get(0));
                province.setProvince(row.get(1));
                province.setCity(row.get(2));
            
                provinceService.save(province);
            }
            instance = provinceService;
        }

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
