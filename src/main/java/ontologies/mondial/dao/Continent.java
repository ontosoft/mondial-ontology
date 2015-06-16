package ontologies.mondial.dao;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;

public class Continent implements Serializable, Cloneable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String name = "";
    private Double area;


    

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	@Override
    public Continent clone() throws CloneNotSupportedException {
        try {
            return (Continent) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }



}

