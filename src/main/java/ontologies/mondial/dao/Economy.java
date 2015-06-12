package ontologies.mondial.dao;

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
public class Economy implements Serializable, Cloneable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String country = "";
    private float gdp;
    private float agriculture;
    private float service;
    private float industry;
    private float inflation;
    private String continent = "";
	private String countryuri="";


    

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}



	public float getGdp() {
		return gdp;
	}

	public void setGdp(float gdp) {
		this.gdp = gdp;
	}

	public float getAgriculture() {
		return agriculture;
	}

	public void setAgriculture(float agriculture) {
		this.agriculture = agriculture;
	}

	public float getService() {
		return service;
	}

	public void setService(float service) {
		this.service = service;
	}

	public float getIndustry() {
		return industry;
	}

	public void setIndustry(float industry) {
		this.industry = industry;
	}

	public float getInflation() {
		return inflation;
	}

	public void setInflation(float inflation) {
		this.inflation = inflation;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getCountryuri() {
		return countryuri;
	}

	public void setCountryuri(String countryuri) {
		this.countryuri = countryuri;
	}

	@Override
    public Economy clone() throws CloneNotSupportedException {
        try {
            return (Economy) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

	@Override
	public String toString() {
		return "Economy [id=" + id + ", country=" + country + ", gdp=" + gdp
				+ ", agriculture=" + agriculture + ", service=" + service
				+ ", industry=" + industry + ", inflation=" + inflation
				+ ", continent=" + continent + "]";
	}


}

