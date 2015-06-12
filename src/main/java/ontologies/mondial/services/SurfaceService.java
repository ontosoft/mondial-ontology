package ontologies.mondial.services;

import ontologies.mondial.dao.Surface;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Separate Java service class. Backend implementation for the address book
 * application, with "detached entities" simulating real world DAO. Typically
 * these something that the Java EE or Spring backend services provide.
 */

public class SurfaceService {

	private static SurfaceService instance;

	public static SurfaceService createDemoService() {

		SurfaceService surfaceService = new SurfaceService();

		String sparqlQuery = "PREFIX : <http://www.example.org/monidal.owl#> \n"
				+ "select distinct ?wa ?country ?province ?name ?type  where {"
				+ " ?p :provinceName ?province. ?p :provinceInCountry ?co. ?co :countryName ?country. "
				+ " ?wa :surfaceName ?name. ?wa :surfaceInProvince ?p.   ?wa :surfaceType ?type. "
				+ " }";

		QuestOWLE quest = new QuestOWLE(sparqlQuery);
		try {
			quest.runQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ArrayList<String> row : quest.getQueryResult()) {
			Surface surface = new Surface();
			surface.setUri(row.get(0));
			surface.setCountry(row.get(1));
			surface.setProvince(row.get(2));
			surface.setName(row.get(3));
			surface.setType(row.get(4));
			surfaceService.save(surface);
		}
		instance = surfaceService;

		return instance;
	}

	public static SurfaceService reloadService(String uri, String countryUri, String provinceUri, String countryString,
			String provinceString, String nameString, String typeString) {
		SurfaceService surfaceService = new SurfaceService();

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
				+ " ?wa :surfaceName ?name. ?wa :surfaceInProvince ?p.   ?wa :surfaceType ?type. "
				+ add1 + add2 + add3 + add4 + add5 + add6 + add7 
				+ " }";
		System.out.println("************ Beginning sparql surface ********");
		System.out.println(sparqlQuery);

		QuestOWLE quest = new QuestOWLE(sparqlQuery);
		try {
			quest.runQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ArrayList<String> row : quest.getQueryResult()) {
			Surface surface = new Surface();
			surface.setUri(row.get(0));
			surface.setCountry(row.get(1));
			surface.setProvince(row.get(2));
			surface.setName(row.get(3));
			surface.setType(row.get(4));
			surfaceService.save(surface);
		}
		instance = surfaceService;
		return instance;
	}

	private HashMap<Long, Surface> contacts = new HashMap<>();
	private long nextId = 0;

	public synchronized List<Surface> findAll(String stringFilter) {
		ArrayList<Surface> arrayList = new ArrayList<Surface>();
		for (Surface contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter
						.isEmpty())
						|| contact.toString().toLowerCase()
								.contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(SurfaceService.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Surface>() {

			@Override
			public int compare(Surface o1, Surface o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	public synchronized Surface getFirst() {
		if (!contacts.isEmpty()) {
			ArrayList<Surface> arrayList = new ArrayList<Surface>();
			for (Surface contact : contacts.values()) {
				try {
					arrayList.add(contact.clone());
				} catch (CloneNotSupportedException ex) {
					Logger.getLogger(SurfaceService.class.getName()).log(
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

	public synchronized void delete(Surface value) {
		contacts.remove(value.getId());
	}

	public synchronized void save(Surface entry) {
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Surface) BeanUtils.cloneBean(entry);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

}
