package ontologies.mondial.dao;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;

public class River implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String uri="";
	private String name;
	private String source; 
	private String estuary; 
    private Double length;
    private Double sourceAltitude;
    

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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getSourceAltitude() {
		return sourceAltitude;
	}

	public void setSourceAltitude(Double sourceAltitude) {
		this.sourceAltitude = sourceAltitude;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getEstuary() {
		return estuary;
	}

	public void setEstuary(String estuary) {
		this.estuary = estuary;
	}



	@Override
	public String toString() {
		return "River [source=" + source + ", estuary=" + estuary + ", length="
				+ length + ", sourceAltitude=" + sourceAltitude + "]";
	}

	@Override
    public River clone() throws CloneNotSupportedException {
        try {
            return (River) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }


}

