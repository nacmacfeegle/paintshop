package danc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import danc.model.Customer;
import danc.model.Paint;
import danc.model.Problem;
import danc.model.Solution;
import danc.util.Utils;

/**
 * Implementation backtracking for Eric's paint batch problem 
 * @author danc
 */
public class PaintSolver extends BTSolver {

	/**
	 * 
	 * @param argv
	 * @throws Exception
	 */
	public static void main(String argv[]) throws Exception {

		Problem p = Utils.getInstance().loadProblem("src/test/resources/valid_input01.txt");
		PaintSolver ps = new PaintSolver();
		ps.setProblem(p);
		ps.solve();
		
		System.out.println("Problem 1: " +ps.getSolution());
		
		p = Utils.getInstance().loadProblem("src/test/resources/valid_input02.txt");
		ps = new PaintSolver();
		ps.setProblem(p);
		ps.solve();

		System.out.println("Problem 2: " +ps.getSolution());
	}


	public PaintSolver() {
		this.solutions = new HashSet<Solution>();
	}

	/*
	 * (non-Javadoc)
	 * @see danc.BTSolver#root()
	 */
	Solution root() {
		return new Solution(getProblem().getNumberOfPaints());
	}

	/*
	 * (non-Javadoc)
	 * @see danc.BTSolver#reject(danc.model.Solution)
	 */
	boolean reject(Solution c) {
		
		// reject a solution containing only wildcards
		if (containsOnlyWildCards(c)) return false;
		
		for (Customer cust : getProblem().getCustomers()) {
			if (notHappy(cust, c)) return true;
		}
		return false;
	}

	/**
	 * checks if a solution contains only wildcards
	 * @param c
	 * @return
	 */
	private boolean containsOnlyWildCards(Solution c) {
		for (String str: c.getSolution()) {
			if (!str.equals("*")) return false;
		}
		return true;
	}


	/**
	 * utility method to determine if a solution contains a wildcard
	 * @param c
	 * @return
	 */
	private boolean containsWildCard(Solution c) {
		for (String s: c.getSolution()) {
			if (s.equals("*")) return true;
		}
		return false;
	}

	/**
	 * Helper function for reject -- determine if a customer is NOT happy with a given solution
	 * 
	 * @param cust
	 * @param c
	 * @return
	 */
	private boolean notHappy(Customer cust, Solution c) {
	
		if (containsWildCard(c)) { // for partials -- cannot reject of solution is reachable
			
			
			if (!matchablePreference(c, cust)) {
				//System.err.println("Customer " +cust.getName() +" is not happy with solution " +c.rawString());
				return true;
			}
		
		} else { // for completed solutions -- can reject if no single preference is covered
			if (!containsAtLeastOnePreference(c, cust) && noExpansionToAtLeastOnePreference(c, cust)) return true;
		}
		
		
		return false;
	}

	/**
	 * Check if a matchable preference exists for a given customer and solution
	 * @param c
	 * @param cust
	 * @return
	 */
	private boolean matchablePreference(Solution c, Customer cust) {
		for (Paint p : cust.getPreferences()) {
			int solIndex = Integer.valueOf(p.getName()) - 1;
			//if (c.getValueAtIndex(solIndex).equals(Constants.WILDCARD)) return true;
			String sol = c.getValueAtIndex(solIndex);
			String pref = p.getType();
			if (sol.equals(Constants.WILDCARD) 
			        || (pref.equals(Constants.COLOUR_MATTE) && sol.equals(Constants.COLOUR_GLOSS)) 
					|| sol.equals(pref)) return true;
		}
		return false;
	}

	/** 
	 * handles expansions of a solution from G -> M
	 * @param c
	 * @param cust
	 * @return
	 */
	private boolean noExpansionToAtLeastOnePreference(Solution c, Customer cust) {
		for (Paint p : cust.getPreferences()) {
			int solIndex = Integer.valueOf(p.getName()) - 1;
			if (c.getValueAtIndex(solIndex).equals(Constants.COLOUR_GLOSS) && 
					p.getType().equals(Constants.COLOUR_MATTE)) return false;
		}
		return true;
	}


	/**
	 * Check if a solution satisfies at least one customer preference
	 * @param c
	 * @param cust
	 * @return
	 */
	private boolean containsAtLeastOnePreference(Solution c, Customer cust) {
		for (Paint p : cust.getPreferences()) {
			int solIndex = Integer.valueOf(p.getName()) - 1;
			if (c.getValueAtIndex(solIndex).equals(p.getType())) return true;
		}
		return false;
	}


	/*
	 * @see danc.BTSolver#accept(danc.model.Solution)
	 */
	boolean accept(Solution c) {
		if (containsOnlyWildCards(c)) return false;
	
		for (Customer cust : getProblem().getCustomers()) {
			if (!containsAtLeastOnePreference(c, cust)) return false;
		}
		
		return true;
	}

	/*
	 * @see danc.BTSolver#next(danc.model.Solution)
	 */
	Solution next(Solution s) {
		return s.getNextSibling();
	}

	/*
	 * @see danc.BTSolver#first(danc.model.Solution)
	 */
	Solution first(Solution c) {
	
		Solution ret = null;
		
		if (containsWildCard(c)) {
			return buildChildren(c);
		} 
		return ret;
	}
	
	/**
	 * From a solution that contains wildcards, construct a list of all valid children.
	 * This helps hugely with tree traversal
	 * @param parent
	 * @return
	 */
	private Solution buildChildren(Solution parent) {
		List<Solution> list = new ArrayList<Solution>();
	
		Solution g = null;
		Solution m = null;
		
		for (int i=0; i<parent.getSize(); i++ ){
			if (parent.getValueAtIndex(i).equals(Constants.WILDCARD)) {
				g = new Solution(parent.getSolution(), parent);
				g.setValueAtIndex(i, Constants.COLOUR_GLOSS);
				
				m = new Solution(parent.getSolution(), parent);
				m.setValueAtIndex(i, Constants.COLOUR_MATTE);
				
				list.add(g);
				list.add(m);
			}
		}
	
		// setup links
		for (int i=0; i<list.size()-1; i++) {
			list.get(i).setNextSibling(list.get(i+1));
		}
				
		return list.get(0);
	}

	/*
	 * @see danc.BTSolver#output(danc.model.Solution)
	 */
	void output(Solution c) {
		Solution sol = new Solution(c);
		sol.setSolved(true);
		sol.replaceAll(Constants.WILDCARD, Constants.COLOUR_GLOSS);
		addSolution(sol);
	}

}