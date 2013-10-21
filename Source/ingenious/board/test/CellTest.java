package ingenious.board.test;

import com.google.java.contract.ContractAssertionError;

import junit.framework.TestCase;

import ingenious.board.Cell;
import ingenious.board.Direction;
import ingenious.board.StateException;

public class CellTest extends TestCase{

	private Cell cell_0_0;
	private Cell cell_1_0;
	private Cell cell_1_1;
	private Cell cell_1_2;
	private Cell cell_1_3;
	private Cell cell_1_4;
	private Cell cell_1_5;
    
	@Override
    protected void setUp() throws Exception {
        cell_0_0 = new Cell(0,0);
        cell_1_0 = new Cell(1,0);
        cell_1_1 = new Cell(1,1);
        cell_1_2 = new Cell(1,2);
        cell_1_3 = new Cell(1,3);
        cell_1_4 = new Cell(1,4);
        cell_1_5 = new Cell(1,5);
    }
	
	/**
     * Check cell connect function
     */
    public void testNSConnection() {
    	cell_0_0.connect(cell_1_0, Direction.N);    	
    	
    	assertEquals(cell_1_0.getNeighbor(Direction.S), cell_0_0);
    	assertEquals(cell_0_0.getNeighbor(Direction.N), cell_1_0);

    	    	
    }
    
    public void testNESWConnection() {
    	cell_0_0.connect(cell_1_1, Direction.NE);
 	
    	assertEquals(cell_1_1.getNeighbor(Direction.SW), cell_0_0);
    	assertEquals(cell_0_0.getNeighbor(Direction.NE), cell_1_1);	    	
    }
    
    public void testNWSEConnection() {
    	cell_0_0.connect(cell_1_5, Direction.NW);
 	
    	assertEquals(cell_1_5.getNeighbor(Direction.SE), cell_0_0);
    	assertEquals(cell_0_0.getNeighbor(Direction.NW), cell_1_5);
	    	
    }
}
