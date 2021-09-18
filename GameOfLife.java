import java.util.*;


/*
1. Any live cell with fewer than two live neighbors dies, as if by loneliness.
2. Any live cell with more than three live neighbors dies, as if by overcrowding.
3. Any live cell with two or three live neighbors lives, unchanged, to the next generation.
4. Any dead cell with exactly three live neighbors comes to life.

 */
public final class GameOfLife
 {

    // The value representing a dead cell
    public final static int DEAD    = 0x00;

    // The value representing a live cell
    public final static int LIVE    = 0x01;

    /* Entrypoint function */
    public final static void main(String[] args)
	{

        // test the game of life implementation
        GameOfLife gof = new GameOfLife();
        gof.test(4);
    }


    /**
     * Test the gameoflife implementation, change the array 
     * values to test each condition in the game of life.
     *
     * @param nrIterations      the number of times the board should be played
     */
    private void test(int nrIterations) 
	{

        // the starting playing board with life and dead cells
        int[][] board = {{DEAD, DEAD, DEAD, DEAD, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD},
                         {DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, DEAD, DEAD}}; 
        
        System.out.println("Conway's GameOfLife");
        printBoard(board);
		
		int i=0;
		int j=0;
		
        for (i = 0 ; i < nrIterations ; i++) {
            System.out.println();
            board = getNextBoard(board);
            printBoard(board);
        }
    }

    
    private void printBoard(int[][] board)
	{
		int i,j=0;
		int e,f;
        for (i = 0, e = board.length ; i < e ; i++)
			{

            for (j = 0, f = board[i].length ; j < f ; j++) 
			{
                System.out.print(Integer.toString(board[i][j]) + ",");
            } 
            System.out.println();
        }
    }

    /*
     * get the next game board, this will calculate if cells live on or die or new
     * ones should be created by reproduction.
     * 
     * The current board field
     * A newly created game buffer
     */
    public int[][] getNextBoard(int[][] board)
	{

        // The board does not have any values so return the newly created
        // playing field.
        if (board.length == 0 || board[0].length == 0)
		{
            throw new IllegalArgumentException("Board must have a positive amount of rows and/or columns");
        }

        int nrRows = board.length;
        int nrCols = board[0].length;
		
		int rows,cols;
		
        // temporary board to store new values
        int[][] buf = new int[nrRows][nrCols];

        for (rows = 0 ; rows< nrRows ; rows++)
		{
	
            for (cols = 0 ; cols < nrCols ; cols++)
			{
                buf[rows][cols] = getNewCellState(board[rows][cols], getLiveNeighbours(rows, cols, board));
            }
        }   
        return buf;
    }

    /*
     * Get the number of the live neighbours given the cell position
     * 
     * cellRow       the column position of the cell
     * cellCol       the row position of the cell
     *  the number of live neighbours given the position in the array
     */
    private int getLiveNeighbours(int cellRow, int cellCol, int[][] board)
	{

        int liveNeighbours = 0;
        int rowEnd = Math.min(board.length , cellRow + 2);
        int colEnd = Math.min(board[0].length, cellCol + 2);
		
		int rows,cols;
        for (rows = Math.max(0, cellRow - 1) ; rows < rowEnd ; rows++)
		{
            
			for (cols = Math.max(0, cellCol - 1) ; cols < colEnd ; cols++)
			{
                
                // make sure to exclude the cell itself from calculation
                if ((rows != cellRow || cols != cellCol) && board[rows][cols] == LIVE)
				{
                    liveNeighbours++;
                }
            }
        }
        return liveNeighbours;
    }


    /* 
     * Get the new state of the cell given the current state and
     * the number of live neighbours of the cell.
     * 
     *  curState          The current state of the cell, either DEAD or ALIVE
     * liveNeighbours    The number of live neighbours of the given cell.
     * 
     *  The new state of the cell given the number of live neighbours 
     *         and the current state
     */
    private int getNewCellState(int curState, int liveNeighbours)
	{
		
		
        int newState = curState;
		
		
		
        switch (curState)
		{
			case LIVE:
			//case 1:
			// Any live cell with fewer than two live neighbors dies, as if by loneliness.
            
            if (liveNeighbours < 2)
			{
				System.out.println("Cell status is dead");
                newState = DEAD;
				
            }
			
            //Any live cell with two or three live neighbors lives, unchanged, to the next generation.
			
            if (liveNeighbours == 2 || liveNeighbours == 3) 
			{
				System.out.println("Cell status is live");
                newState = LIVE;
				
            }

            //Any live cell with more than three live neighbors dies, as if by overcrowding.
            if (liveNeighbours > 3)
			{
				System.out.println("Cell status is dead");
                newState = DEAD;
				
				
            }
            break;

			case DEAD:
			
           // Any dead cell with exactly three live neighbors comes to life.
		   
            if (liveNeighbours == 3)
			{
				System.out.println("Cell status is live");
                newState = LIVE;
				
            }
			
            break;

			default:
            throw new IllegalArgumentException("State of cell must be either LIVE or DEAD");
        }			
        return newState;
    }
}