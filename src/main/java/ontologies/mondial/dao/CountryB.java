package ontologies.mondial.dao;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;

/**
 * This class represents one of issues of this approach.
 * Namely.how to extract additional data for country without creating new DAO classes
 * We created new class CountryB for additional data about length of country borders 
 */

public class CountryB implements Serializable, Cloneable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String uri="";
    private String country = "";
    private String capital = "";
    private float area;
    private String continent = "";
    private int population;
    private Double borderLength;

    

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
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

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public Double getBorderLength() {
		return borderLength;
	}

	public void setBorderLength(Double borderLength) {
		this.borderLength = borderLength;
	}

	@Override
    public CountryB clone() throws CloneNotSupportedException {
        try {
            return (CountryB) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

	@Override
	public String toString() {
		return "Country [country=" + country + ", capital=" + capital
				+ ", area=" + area + ", continent=" + continent
				+ ", population=" + population + "]";
	}








}

