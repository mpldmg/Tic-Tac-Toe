package TicTacToe.Client;

import java.util.Objects;

public class GameState {
    private static final int ROW_COUNT = 3;
    private static final int COL_COUNT = 3;
    private static final String CELL_STATE_EMPTY = " ";
    private static final String CELL_STATE_X = "X";
    private static final String CELL_STATE_O = "O";
    
    private String[][] board;
    private boolean playerTurn;
    private int playerScore;
    private int botScore;
    
    public GameState() {
        initializeGame();
    }
    
    public void initializeGame() {
        board = new String[ROW_COUNT][COL_COUNT];
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                board[row][col] = CELL_STATE_EMPTY;
            }
        }
        playerTurn = true;
    }
    
    public boolean makeMove(int row, int col, String symbol) {
        if (!Objects.equals(board[row][col], CELL_STATE_EMPTY)) {
            return false;
        }
        
        board[row][col] = symbol;
        playerTurn = !playerTurn;
        return true;
    }
    
    public boolean isPlayerTurn() {
        return playerTurn;
    }
    
    public String getCell(int row, int col) {
        return board[row][col];
    }
    
    public boolean isEmpty(int row, int col) {
        return Objects.equals(board[row][col], CELL_STATE_EMPTY);
    }
    
    public int getRowCount() {
        return ROW_COUNT;
    }
    
    public int getColCount() {
        return COL_COUNT;
    }
    
    public String getEmptyState() {
        return CELL_STATE_EMPTY;
    }
    
    public String getXState() {
        return CELL_STATE_X;
    }
    
    public String getOState() {
        return CELL_STATE_O;
    }
    
    public void incrementPlayerScore() {
        playerScore++;
    }
    
    public void incrementBotScore() {
        botScore++;
    }
    
    public int getPlayerScore() {
        return playerScore;
    }
    
    public int getBotScore() {
        return botScore;
    }
} 