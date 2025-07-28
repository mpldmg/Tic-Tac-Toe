package TicTacToe.Client;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Создаем контроллер и представление в правильном порядке
            GameController controller = new GameController();
            GameView gameView = new GameView(controller);
            controller.setGameView(gameView); // Устанавливаем связь
            
            gameView.show();
            controller.startNewGame();
        });
    }
}