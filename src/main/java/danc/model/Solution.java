package danc.model;

import java.util.ArrayList;
import java.util.List;

import danc.Constants;

/**
 * Representation of a solution to the paint shop batch problem.
 * 
 * @author danc
 *
 */
public class Solution implements Comparable {

	/**
	 * default message to print out in case there is no solution
	 */
	private static final String NO_SOLUTION = "No solution exists";
	
	private List<String> solution;
	private boolean solved;

	private Solution parent;
	private Solution nextSibling;
	
	public Solution() {
		solution = new ArrayList<String>();
	}
	
	public Solution(Integer size) {
		solution = new ArrayList<String>(size);
		for (int i=0; i<size; i++) {
			solution.add("*");
		}
		solved = false;
	}

	public Solution(List<String> list) {
		this.solution = list;
	}
	
	public Solution(Solution c) {
		this.solution = new ArrayList(c.getSolution());
		this.solved = c.isSolved();
	}
	
	public Solution(List<String> list, Solution parent) {
		this.solution = new ArrayList(list);
		this.parent = parent;
	}

	public void setParent(Solution p) {
		this.parent = p;
	}
	
	public Solution getParent() {
		return this.parent;
	}

	public void setNextSibling(Solution s) {
		this.nextSibling = s;
	}
	
	public Solution getNextSibling() {
		return this.nextSibling;
	}
	
	public List<String> getSolution() {
		return solution;
	}

	public void setSolution(List<String> solution) {
		this.solution = solution;
	}
	
	public void setValueAtIndex(int idx, String value) {
		solution.set(idx, value);
	}
	
	public String getValueAtIndex(int idx) {
		return solution.get(idx);
	}
	
	public void setSolved(boolean b) {
		solved = b;
	}
	
	public boolean isSolved() {
		return solved;
	}
	
	public int getSize() {
		return solution.size();
	}
	
	
	public String toString() {
		if (!isSolved()) return NO_SOLUTION;
		return rawString();
	}
			
	
	public String rawString() {
		StringBuffer sb = new StringBuffer();
		for (String s : solution) {
			sb.append(s +" ");
		}
		return sb.toString();
	}
	
	public boolean equals(Solution other) {
		if (getSize() != other.getSize()) return false;
		
		for (int i=0; i< getSize(); i++) {
			if(!this.getValueAtIndex(i).equals(other.getValueAtIndex(i))) {
				return false;
			}
		}
		return true;
	}

	public int compareTo(Object o) {
		Solution other = (Solution) o;
		if (countOccurrences(this, Constants.COLOUR_MATTE) < countOccurrences(other, Constants.COLOUR_MATTE)) {
			return -1;
		}
		if (countOccurrences(this, Constants.COLOUR_MATTE) > countOccurrences(other, Constants.COLOUR_MATTE)) {
			return 1;
		}
		return 0;
	}

	private int countOccurrences(Solution sol, String colour) {
		int count = 0;
		for (String s : sol.getSolution()) {
			if (s.equals(colour)) count++;
		}
		return count;
	}

	public void replaceAll(String src, String dst) {
		for (int i=0; i<getSize(); i++) {
			if (getValueAtIndex(i).equals(src)) {
				setValueAtIndex(i, dst);
			}
		}
	}
	
	public boolean equals(Object other) {
		Solution s = (Solution)other;

		for (int i=0; i<getSize(); i++) {
			if (!getValueAtIndex(i).equals(s.getValueAtIndex(i))) return false;
		}
		return true;
	}
	
}
