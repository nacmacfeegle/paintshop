package danc.model;

/**
 * Paint model. Each paint has a name and a type (Gloss or Matte)
 * @author danc
 *
 */
public class Paint {
	
	private String name;
	private String type;
	
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
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("name: " +name +", type: " +type +" ");
		return sb.toString();
	}

}
