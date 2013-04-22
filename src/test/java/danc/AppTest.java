package danc;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import danc.model.Problem;
import danc.util.Utils;

/**
 * Tests I/O and some valid and invalid problems
 * @author danc
 */
public class AppTest extends TestCase {
	
	private Utils controller = Utils.getInstance();
	
    @Test
    public void testCanLoadValidFile() {
    	checkValidFile("src/test/resources/valid_input01.txt");
    	checkValidFile("src/test/resources/valid_input02.txt");
    	checkValidFile("src/test/resources/valid_input03.txt");
    }
    
    @Test
    public void testCannotLoadInvalidFile() {
    	checkInvalidFile("src/test/resources/invalid_input01.txt");
    	checkInvalidFile("src/test/resources/invalid_input02.txt");
    	checkInvalidFile("src/test/resources/invalid_input03.txt");
    }
    

    @Test
    public void testProblemsWithSolutions() {
    	checkProblemAndSolution("src/test/resources/valid_input01.txt", "src/test/resources/expected_output01.txt");
    	checkProblemAndSolution("src/test/resources/valid_input03.txt", "src/test/resources/expected_output03.txt");
    	checkProblemAndSolution("src/test/resources/valid_input05.txt", "src/test/resources/expected_output05.txt");
    }
   
    @Test
    public void testProblemsWithNoSolutions() {
    	checkProblemAndSolution("src/test/resources/valid_input02.txt", "src/test/resources/expected_output02.txt");
    }
    
    @Test
    public void testBigProblem() {
    	checkProblemAndSolution("src/test/resources/valid_input04.txt", "src/test/resources/expected_output04.txt");
    }
    
    
	private void checkInvalidFile(String fileName) {
		try {
			Problem p = controller.loadProblem(fileName);
			fail("Should not be able to open problem: "+p);
		} catch (IOException e) {
			assertTrue("Exception did not contain expected message", 
					e.getMessage().toLowerCase().contains("invalid input"));
		}
	}
	
    
	private void checkValidFile(String fileName) {
		try {
			Problem p = controller.loadProblem(fileName);
		} catch (IOException e) {
			fail("Should be able to open problem ");
		}
	}
	
	private void checkProblemAndSolution(String input, String output) {
		try {
			Problem p = controller.loadProblem(input);
			String target = FileUtils.readLines(new File(output)).get(0);
			BTSolver solver = new PaintSolver();
			solver.setProblem(p);
			solver.solve();
			String solStr = solver.getSolution().toString().trim();
			assertEquals("problem did not have expected solution", target, solStr);
			
			
		} catch (Exception e) {
			fail("Unexpected exception when checking problem: " +input);
		}
		
	}
}
