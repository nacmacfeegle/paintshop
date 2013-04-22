package danc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import danc.model.Problem;
import danc.model.Solution;

/*
 * Backtracking pseudo-code:
 * 
 * (from wikipedia)
 * 
 *  procedure bt(c)
 *  	if reject(P,c) then return 
 *  	if accept(P,c) then output(P,c) 
 *  	s <- first(P,c) 
 *  	while s != null do 
 *  		bt(s) 
 *  		s <- next(P,s)
 *  
 */
public abstract class BTSolver {
	
	/**
	 * Problem being solverd
	 */
	protected Problem problem;
	
	/**
	 * Set of solutions
	 */
	protected Set<Solution> solutions;

	/**
	 * Return the partial candidate at the root of the search tree.
	 * @return
	 */
	abstract Solution root();

	/**
	 * Rule out a candidate solution.
	 * @param c		Candidate solution
	 * @return true only if the partial candidate c is not worth completing.
	 */
	abstract boolean reject(Solution c);

	/**
	 * Test if a solution is acceptable
	 * @param c		Candidate solution
	 * @return true if c is a solution of current problem, and false otherwise.
	 */
	abstract boolean accept(Solution c);

	/**
	 * generate the first extension (child) of candidate c.
	 * @param c		Candidate solution
	 * @return first child of c
	 */
	abstract Solution first(Solution c);

	/**
	 * Generate the next alternative extension (sibling) of a candidate, after the extension s.
	 * @param s		Candidate solution
	 * @return next sibling of s
	 */
	abstract Solution next(Solution s);

	/**
	 * Use the solution c of current problem, as appropriate to the application.
	 * @param s		Candidate solution
	 * @return		Solved problem
	 */
	abstract void output(Solution c);
	
	/**
	 * Backtracking implementation (based on Wikipedia pseudo-code)
	 * 
	 * @param c	candidate solution
	 */
	public void backtrack(Solution c) {
	
		// I'm setting this to 1. The hope is that if I've implemented things correctly,
		// the first solution found will actually be in a part of the tree that minimises
		// the number of Ms
		if (solutions.size() > 0)
			return;
		
		//System.err.println(c.rawString());
		
		if (reject(c)) 
			return; 
		
		if (accept(c)) 
			output(c);
		
		Solution s = first(c);
		while (s != null) {
			backtrack(s);
			s = next(s);
		}
	}
	
	
	public void solve() {
		backtrack(root());
	}

	public void setProblem(Problem p) {
		this.problem = p;
	}
	
	public Problem getProblem() {
		return this.problem;
	}
	
	public void addSolution(Solution s) {
		this.solutions.add(s);
	}

	/**
	 * Get the first solution found by backtracking. Note that the backtracking approach implemented here
	 * actually traverses the while tree and outputs all solutions found (duplicates can exist).
	 * 
	 * @return
	 */
	public Solution getSolution() {
		if (solutions.size() > 0) {
			List<Solution> solutionList = new ArrayList<Solution>(solutions);
			Collections.sort(solutionList);
			return solutionList.get(0);
		} else {
			Solution noSol = new Solution();
			noSol.setSolved(false);
			return noSol;
		}
	}
}
