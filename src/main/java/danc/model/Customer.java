package danc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model of the customer. A customer has a name and a list of preferences
 * @author danc
 *
 */
public class Customer {
	
	private String name;
	
	private List<Paint> preferences;
	
	public Customer() {
		preferences = new ArrayList<Paint>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Paint> getPreferences() {
		return preferences;
	}

	public void setPreferences(List<Paint> preferences) {
		this.preferences = preferences;
	}

	public void addPreference(Paint p) {
		preferences.add(p);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("name: " +name +" prefs: " +preferences);
		return sb.toString();
	}

}
