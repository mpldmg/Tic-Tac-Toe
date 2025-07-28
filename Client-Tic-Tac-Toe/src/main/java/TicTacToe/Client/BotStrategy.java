package TicTacToe.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotStrategy {
    private final Random random = new Random();
    
    public int[] getNextMove(GameState gameState) {
        List<int[]> emptyCells = findEmptyCells(gameState);
        
        if (emptyCells.isEmpty()) {
            return null;
        }
        
        // Простая случайная стратегия
        return emptyCells.get(random.nextInt(emptyCells.size()));
    }
    
    private List<int[]> findEmptyCells(GameState gameState) {
        List<int[]> emptyCells = new ArrayList<>();
        
        for (int row = 0; row < gameState.getRowCount(); row++) {
            for (int col = 0; col < gameState.getColCount(); col++) {
                if (gameState.isEmpty(row, col)) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }
        
        return emptyCells;
    }
} 