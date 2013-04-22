package danc;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import danc.model.Customer;
import danc.model.Problem;
import danc.model.Solution;
import danc.util.Utils;

/**
 * Tests for specific aspects of the solver
 * 
 * @author danc
 */
public class SolverTest extends TestCase {
	
	static PaintSolver solver = new PaintSolver();
	Utils utils = Utils.getInstance();

	/**
	 * First child of single * should be G
	 */
	@Test
	public void testFirstChildOfSingleWildcardIsG() {
		Solution c = utils.buildSolution("*");
		c.setSolved(true);

		Solution first = solver.first(c);
		assertEquals("First child of single wildcard should be G", "G ", first.rawString() );
	}	
	
	@Test
	public void testFirstChildPairs() {
	
		Solution c = utils.buildSolution("* *");
		c.setSolved(true);
		
		Solution first = solver.first(c);
		assertEquals("First child of (* *) should be (G *)", "G * ", first.rawString() );
		
		c = utils.buildSolution("G *");
		first = solver.first(c);
		assertEquals("First child of (G *) should be (G G)", "G G ", first.rawString() );
			
		c = utils.buildSolution("* G");
		first = solver.first(c);
		assertEquals("First child of (* G) should be (G G)", "G G ", first.rawString() );
		
		c = utils.buildSolution("M *");
		first = solver.first(c);
		assertEquals("First child of (M *) should be (M G)", "M G ", first.rawString() );

		c = utils.buildSolution("* M");
		first = solver.first(c);
		assertEquals("First child of (* M) should be (G M)", "G M ", first.rawString() );
	}
	

	@Test
	public void testNextSiblingForTuplesWithOneWildcard() {
		Solution parent = utils.buildSolution("* *");
		
		Solution sol = solver.first(parent);
		Solution sibling = solver.next(sol);
		assertEquals("Next sibling of (G, *) should be (M, *)", "M * ", sibling.rawString() );
		
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (M, *) should be (*, G)", "* G ", sibling.rawString() );
	
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (*, G) should be (*, M)", "* M ", sibling.rawString() );
		
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (*, M) should be null", null, sibling );
	}
	
	
	@Test
	public void testNextSiblingForTuplesWithTwoWildcards1() {
		Solution parent = utils.buildSolution("G * *");
	
		Solution sol = solver.first(parent);
		
		Solution sibling = solver.next(sol);
		assertEquals("Next sibling of (G, G, *) should be (G, M, *)", "G M * ", sibling.rawString() );

		sibling = solver.next(sibling);
		assertEquals("Next sibling of (G, M, *) should be (G, *, G)", "G * G ", sibling.rawString() );
		
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (G, *, G) should be (G, *, M)", "G * M ", sibling.rawString() );
	
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (G, *, M) should be null", null, sibling);
	}
	
	
	@Test
	public void testNextSiblingForTuplesWithTwoWildcards2() {
		Solution parent = utils.buildSolution("* G *");

		Solution sol = solver.first(parent);
		Solution sibling = solver.next(sol);
		assertEquals("Next sibling of (G, G, *) should be (M, G, *)", "M G * ", sibling.rawString() );

		sibling = solver.next(sibling);
		assertEquals("Next sibling of (M, G, *) should be (*, G, G)", "* G G ", sibling.rawString() );
		
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (*, G, G) should be (*, G, M)", "* G M ", sibling.rawString() );
	
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (*, G, M) should be null", null, sibling);
	}
	
	@Test
	public void testNextSiblingForTuplesWithTwoWildcards3() {

		Solution parent = utils.buildSolution("M * *");
	
		Solution sol = solver.first(parent);
		Solution sibling = solver.next(sol);
		assertEquals("Next sibling of (M, G, *) should be (M, M, *)", "M M * ", sibling.rawString() );

		sibling = solver.next(sibling);
		assertEquals("Next sibling of (M, M, *) should be (M, *, G)", "M * G ", sibling.rawString() );
		
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (M, *, G) should be (M, *, M)", "M * M ", sibling.rawString() );
	
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (M, *, G) should be null", null, sibling);
	}

	@Test
	public void testNextSiblingForTuplesWithThreeWildcards1() {

		Solution parent = utils.buildSolution("* * *");
		
		Solution sol = solver.first(parent);
		Solution sibling = solver.next(sol);
		assertEquals("Next sibling of (G, *, *) should be (M, *, *)", "M * * ", sibling.rawString() );

		sibling = solver.next(sibling);
		assertEquals("Next sibling of (M, *, *) should be (*, G, *)", "* G * ", sibling.rawString() );
		
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (*, G, *) should be (*, M, *)", "* M * ", sibling.rawString() );
	
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (*, M, *) should be (*, *, G)", "* * G ", sibling.rawString() );
		
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (*, *, G) should be (*, *, M)", "* * M ", sibling.rawString() );
			
		sibling = solver.next(sibling);
		assertEquals("Next sibling of (*, *, M) should be null", null, sibling);
	}
	
	@Test
	public void testNextSiblingForTuplesWithoutWildcards() {
	
		Solution parent = utils.buildSolution("G *");
		Solution sol = solver.first(parent);
		
		Solution sibling = solver.next(sol);
		assertEquals("Next sibling of (G, G) should be (G, M)", "G M ", sibling.rawString() );

		sibling = solver.next(sibling);
		assertEquals("Next sibling of (G, M) should be null", null, sibling);
		
		parent = utils.buildSolution("* G");
		sol = solver.first(parent);
		
		sibling = solver.next(sol);
		assertEquals("Next sibling of (G, G) should be (M, G)", "M G ", sibling.rawString() );

		sibling = solver.next(sibling);
		assertEquals("Next sibling of (M, G) should be null", null, sibling);

		parent = utils.buildSolution("* M");
		sol = solver.first(parent);
		sibling = solver.next(sol);
		assertEquals("Next sibling of (G, M) should be (M, M)", "M M ", sibling.rawString() );

		sibling = solver.next(sibling);
		assertEquals("Next sibling of (M, M) should be null", null, sibling);
		
		parent = utils.buildSolution("M *");
		sol = solver.first(parent);
		sibling = solver.next(sol);
		assertEquals("Next sibling of (M, G) should be (M, M)", "M M ", sibling.rawString() );

		sibling = solver.next(sibling);
		assertEquals("Next sibling of (M, M) should be null", null, sibling);
	}
	

	@Test
	public void testCannotRejectSolution1() {

		String custPrefs = "1 M";
		Customer customer = Utils.getInstance().buildCustomer("test1", custPrefs);

		Problem problem = new Problem();
		problem.setNumberOfPaints(1);
		problem.addCustomer(customer);
	
		Solution s1 = new Solution(1);
		s1.setValueAtIndex(0, "G");

		solver.setProblem(problem);
		
		assertFalse("Solution should not have been rejected since M is reachable from G", solver.reject(s1));
	
		// however: just because it is reachable, doesn't mean solution is acceptable
		assertFalse("Solution should not have been accepted since at least one preference must be matched", solver.accept(s1));
		
	}
	
	@Test
	public void testCannotRejectSolution2() {
		Customer c1 = Utils.getInstance().buildCustomer("a", "1 M 3 G 5 G");
		Customer c2 = Utils.getInstance().buildCustomer("b", "2 G 3 M 4 G");
		Customer c3 = Utils.getInstance().buildCustomer("c", "5 M");
		
		Problem problem = new Problem();
		problem.setNumberOfPaints(5);
		problem.addCustomer(c1); 
		problem.addCustomer(c2); 
		problem.addCustomer(c3); 
		
		solver.setProblem(problem);
	
		Solution s1 = new Solution(5);
		s1.setValueAtIndex(0, "G");
		s1.setValueAtIndex(1, "G");
		s1.setValueAtIndex(2, "G");
		s1.setValueAtIndex(3, "G");
		s1.setValueAtIndex(4, "M");
		
		assertFalse("Solution should not be rejected", solver.reject(s1));
		assertTrue("Solution should be accepted", solver.accept(s1));
	}

	@Test
	public void testCannotRejectSolution3() {
		Customer c1 = Utils.getInstance().buildCustomer("a", "1 M 2 G 4 G 7 G");
		Customer c2 = Utils.getInstance().buildCustomer("b", "1 G 2 M 5 G 9 G");
		Customer c3 = Utils.getInstance().buildCustomer("c", "7 M");
		Customer c4 = Utils.getInstance().buildCustomer("d", "5 G 9 M");
		
		Problem problem = new Problem();
		problem.setNumberOfPaints(9);
		problem.addCustomer(c1); 
		problem.addCustomer(c2); 
		problem.addCustomer(c3); 
		problem.addCustomer(c4); 
		
		solver.setProblem(problem);
	
		Solution s1 = new Solution(9);
		s1.setValueAtIndex(0, "G");
		s1.setValueAtIndex(1, "G");
		s1.setValueAtIndex(2, "G");
		s1.setValueAtIndex(3, "G");
		s1.setValueAtIndex(4, "G");
		s1.setValueAtIndex(5, "G");
		s1.setValueAtIndex(6, "G");
		s1.setValueAtIndex(7, "*");
		s1.setValueAtIndex(8, "*");
		
		assertFalse("Solution should not be rejected", solver.reject(s1));
	}
	

	private boolean listsEqual(List<Solution> solutions, List<Solution> acceptableSolutions) {
		if (solutions.size() != acceptableSolutions.size()) {
			return false;
		}
		
		for (int i=0; i<solutions.size(); i++) {
			if (!solutions.get(i).equals(acceptableSolutions.get(i))) {
				return false;
			}
		}
		return true;
	}

}
