package danc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the problem: we have a list of customers (each with their own set of preferences)
 * and a total number of paints
 * @author danc
 */
public class Problem {
	
	private List<Customer> customers;
	private Integer numberOfPaints;

	public Problem() {
		customers = new ArrayList<Customer>();
	}
	
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	public Integer getNumberOfPaints() {
		return numberOfPaints;
	}
	public void setNumberOfPaints(Integer numberOfPaints) {
		this.numberOfPaints = numberOfPaints;
	}
	public void addCustomer(Customer customer) {
		this.customers.add(customer);
		
	}
	

}
