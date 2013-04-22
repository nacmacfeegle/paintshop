package danc.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import danc.Constants;
import danc.model.Customer;
import danc.model.Paint;
import danc.model.Problem;
import danc.model.Solution;

public class Utils {
	
	private static Utils instance;
	
	public static Utils getInstance() {
		if(instance == null) instance = new Utils();
		return instance;
	}
	
	private Utils() { }
	

	public Problem loadProblem(String fileName) throws IOException {
		List<String> lines = FileUtils.readLines(new File(fileName));
	
		if (!inputOk(lines)) {
			throw new IOException("Unable to parse problem file: invalid input");
		}
		
		Problem p = new Problem();
		p.setNumberOfPaints(Integer.parseInt(lines.get(0)));
		p.setCustomers(buildCustomerList(lines.subList(1, lines.size())));
		
		return p;
	}

	public List<Customer> buildCustomerList(List<String> list) {
		List<Customer> retval = new ArrayList<Customer>();
		int custIdx = 1;
		for (String str: list) {
			retval.add(buildCustomer(new Integer(custIdx).toString(), str));
			custIdx++;
		}
		return retval;
	}
	
	public Customer buildCustomer(String name, String str) {
		Customer c = new Customer();
		c.setName(name);
		
		String[] tokens = str.split("\\s+");
		int i=0;
		while (i < tokens.length -1) {
			Paint p = new Paint();
			p.setName(new Integer(tokens[i]).toString());
			p.setType(tokens[i+1]);
			c.addPreference(p);
			i+=2;
		}
		return c;
	}
	
	public Solution buildSolution(String sol) {
		List<String> list = new ArrayList<String>();
		String[] tokens = sol.split("\\s+");
		for (int i=0; i<tokens.length; i++) {
			list.add(tokens[i]);
		}
		return new Solution(list);
	}

	/**
	 * Input file checks. Helps with the construction of valid problem instances 
	 * by filtering out the invalids
	 * 
	 * @param lines
	 * @return
	 */
	private boolean inputOk(List<String> lines) {
		// file checks: must make it all the way to the end of this chain
	
		// input must contain more than one line
		if (lines.size() <= 1) return false;
		
		// first line must parse to an int value
		int numColours = -1;
		try {
			numColours = Integer.valueOf(lines.get(0));
		} catch (NumberFormatException nfe) {
			return false;
		}
		
		// make sure numColours is positive
		if (numColours < 0) return false;
	
		// each line formatted to colour, number, colour, number...
		List<String> customerLines = lines.subList(1, lines.size()-1);
		if (!customerLinesFormattedOk(customerLines, numColours)) return false;
		
		return true;
	}

	private boolean customerLinesFormattedOk(List<String> lines, int numColours) {
		
		for(String str: lines) {
		
			// make sure each line has an even number of tokens
			String[] tokens = str.split("\\s+");
			if (tokens.length % 2 != 0)  return false;
	
			// contains too many mattes
			if (countInstances(tokens, Constants.COLOUR_MATTE) > 1) return false;
			
			// even values are all numbers and less than or equal to numColours
			if (!lineFormattedOk(tokens, numColours)) return false;
			
		}
		return true;
	}

	private boolean lineFormattedOk(String[] tokens, int numColours) {
	
		for (int i=0; i<tokens.length; i++) {
			// for even indices, we make sure that the value is a number and 
			// is less than or equal to the number of colours available
			if (i % 2 == 0) {
				int n = -1;
				try {n = Integer.valueOf(tokens[i]);} catch (NumberFormatException e) { return false; }
				if  (n > numColours || n < 0) return false;
			} else {
				if (!(tokens[i].equals(Constants.COLOUR_GLOSS) 
						|| tokens[i].equals(Constants.COLOUR_MATTE))) return false;
			}
		}
		return true;
	}

	private int countInstances(String[] tokens, String str) {
		int count = 0;
		for (int i=0; i<tokens.length; i++) {
			if (tokens[i].equals(str)) count++;
		}
		return count;
	}
}
