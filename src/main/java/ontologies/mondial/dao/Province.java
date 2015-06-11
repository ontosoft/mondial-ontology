package ontologies.mondial.dao;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;

/**
 * A simple DTO for the address book example.
 *
 * Serializable and cloneable Java Object that are typically persisted
 * in the database and can also be easily converted to different formats like JSON.
 */
// Backend DTO class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class Province implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String uri="";
    private String city = "";
    private String province = "";
    private String country = "";
    private int population;
    private float area;



    

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


    public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "Province [id=" + id + ", city=" + city + ", province="
				+ province + ", country=" + country + "]";
	}

	@Override
    public Province clone() throws CloneNotSupportedException {
        try {
            return (Province) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }


}

