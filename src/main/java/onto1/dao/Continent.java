package onto1.dao;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;

/**
 * A simple DTO for the address book example.
 *
 * Serializable and cloneable Java Object that are typically persisted
 * in the database and can also be easily converted to different formats like JSON.
 */
// Backend DTO class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class Continent implements Serializable, Cloneable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String capital = "";
    private String country = "";
    private float area;
    private int population;


    

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	
    public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}


	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	@Override
    public Continent clone() throws CloneNotSupportedException {
        try {
            return (Continent) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

	@Override
	public String toString() {
		return "Country [id=" + id + ", capital=" + capital + ", country="
				+ country + ", area=" + area + ", population=" + population
				+ "]";
	}




}

