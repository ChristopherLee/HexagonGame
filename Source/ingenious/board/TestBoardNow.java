package ingenious.board;

public class TestBoardNow {
	public static void main(String[] args) {
		Board board = new Board(2);
        //GUIGameState.displayBoard(board);
        
		Cell cell0 = board.getCell(0, 0);
		System.out.println("Cell: " + cell0);
		for (Direction dir : cell0.getNeighbors().keySet()) {
			System.out.println("\t" + dir + ": " + cell0.getNeighbors().get(dir));
		}
		System.out.println();
		
        for (int i = 1; i < 4; i++) {
        	for (int j = 0; j < i*6; j++) {
        		Cell cell = board.getCell(i, j);
        		System.out.println("Cell: " + cell);
        		for (Direction dir : cell.getNeighbors().keySet()) {
        			System.out.println("\t" + dir + ": " + cell.getNeighbors().get(dir));
        		}
        		System.out.println();
        	}
        }
	}
}
