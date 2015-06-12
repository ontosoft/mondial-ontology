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
public class Surface implements Serializable, Cloneable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String uri="";
	private String country = "";
	private String province = "";
    private String name = "";
    private String type = "";

    

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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	@Override
	public String toString() {
		return "Water [country=" + country + ", province=" + province
				+ ", name=" + name + ", type=" + type + "]";
	}

	@Override
    public Surface clone() throws CloneNotSupportedException {
        try {
            return (Surface) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }








}

