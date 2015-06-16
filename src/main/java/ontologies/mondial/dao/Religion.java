package ontologies.mondial.dao;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;


public class Religion implements Serializable, Cloneable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String countryUri = "";
	private String country = "";
	private String name = "";
    private Double percentage;

    

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

	public String getCountryUri() {
		return countryUri;
	}

	public void setCountryUri(String countryUri) {
		this.countryUri = countryUri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}


	@Override
    public Religion clone() throws CloneNotSupportedException {
        try {
            return (Religion) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }



}

