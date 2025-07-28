package TicTacToe.Client;

import java.util.Objects;

public class GameLogic {
    
    public GameResult checkGameState(GameState gameState) {
        // Проверка строк
        for (int row = 0; row < gameState.getRowCount(); row++) {
            if (isWinningLine(gameState, row, 0, row, 1, row, 2)) {
                return determineWinner(gameState, row, 0);
            }
        }
        
        // Проверка столбцов
        for (int col = 0; col < gameState.getColCount(); col++) {
            if (isWinningLine(gameState, 0, col, 1, col, 2, col)) {
                return determineWinner(gameState, 0, col);
            }
        }
        
        // Проверка диагоналей
        if (isWinningLine(gameState, 0, 0, 1, 1, 2, 2)) {
            return determineWinner(gameState, 0, 0);
        }
        
        if (isWinningLine(gameState, 0, 2, 1, 1, 2, 0)) {
            return determineWinner(gameState, 0, 2);
        }
        
        // Проверка на ничью
        if (isBoardFull(gameState)) {
            return GameResult.DRAW;
        }
        
        return GameResult.CONTINUE;
    }
    
    private boolean isWinningLine(GameState gameState, int row1, int col1, int row2, int col2, int row3, int col3) {
        String cell1 = gameState.getCell(row1, col1);
        String cell2 = gameState.getCell(row2, col2);
        String cell3 = gameState.getCell(row3, col3);
        
        return Objects.equals(cell1, cell2) && 
               Objects.equals(cell2, cell3) && 
               !Objects.equals(cell1, gameState.getEmptyState());
    }
    
    private GameResult determineWinner(GameState gameState, int row, int col) {
        String cell = gameState.getCell(row, col);
        if (Objects.equals(cell, gameState.getXState())) {
            return GameResult.PLAYER_WINS;
        } else {
            return GameResult.BOT_WINS;
        }
    }
    
    private boolean isBoardFull(GameState gameState) {
        for (int row = 0; row < gameState.getRowCount(); row++) {
            for (int col = 0; col < gameState.getColCount(); col++) {
                if (gameState.isEmpty(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public enum GameResult {
        CONTINUE,
        PLAYER_WINS,
        BOT_WINS,
        DRAW
    }
} 