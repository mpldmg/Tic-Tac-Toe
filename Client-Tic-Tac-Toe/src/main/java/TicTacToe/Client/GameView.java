package TicTacToe.Client;

import javax.swing.*;
import java.awt.*;

public class GameView {
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private final GameState gameState;
    private final GameLogic gameLogic;
    private final BotStrategy botStrategy;
    
    public GameView(GameController controller) {
        this.gameState = controller.getGameState();
        this.gameLogic = new GameLogic();
        this.botStrategy = new BotStrategy();
        createGUI();
    }
    
    private void createGUI() {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Заголовок
        JLabel titleLabel = new JLabel("Tic-Tac-Toe");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Счет
        scoreLabel = new JLabel("Игрок: 0 | Бот: 0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Статус
        statusLabel = new JLabel("Ваш ход");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Игровое поле
        JPanel gamePanel = createGamePanel();
        
        // Кнопка новой игры
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(e -> startNewGame());
        
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(scoreLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(statusLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(gamePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(newGameButton);
        
        frame.add(mainPanel);
    }
    
    private JPanel createGamePanel() {
        JPanel gamePanel = new JPanel(new GridLayout(3, 3, 5, 5));
        gamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttons = new JButton[3][3];
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.BOLD, 24));
                button.setPreferredSize(new Dimension(80, 80));
                
                final int finalRow = row;
                final int finalCol = col;
                button.addActionListener(e -> handlePlayerMove(finalRow, finalCol));
                
                buttons[row][col] = button;
                gamePanel.add(button);
            }
        }
        
        return gamePanel;
    }
    
    private void handlePlayerMove(int row, int col) {
        if (!gameState.isPlayerTurn() || !gameState.isEmpty(row, col)) {
            return;
        }
        
        // Ход игрока
        makeMove(row, col, gameState.getXState());
        updateButton(row, col, gameState.getXState());
        
        if (checkGameEnd()) {
            return;
        }
        
        // Ход бота
        updateStatus("Ход бота...");
        
        // Имитация задержки
        new Thread(() -> {
            try {
                Thread.sleep(500);
                SwingUtilities.invokeLater(this::makeBotMove);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    private void makeBotMove() {
        int[] coordinates = botStrategy.getNextMove(gameState);
        if (coordinates != null) {
            int row = coordinates[0];
            int col = coordinates[1];
            
            makeMove(row, col, gameState.getOState());
            updateButton(row, col, gameState.getOState());
            checkGameEnd();
        }
    }
    
    private void makeMove(int row, int col, String symbol) {
        gameState.makeMove(row, col, symbol);
        updateStatus(gameState.isPlayerTurn() ? "Ваш ход" : "Ход бота...");
    }
    
    private boolean checkGameEnd() {
        GameLogic.GameResult result = gameLogic.checkGameState(gameState);
        
        switch (result) {
            case PLAYER_WINS:
                gameState.incrementPlayerScore();
                updateStatus("Х победили!");
                break;
            case BOT_WINS:
                gameState.incrementBotScore();
                updateStatus("O победили!");
                break;
            case DRAW:
                updateStatus("Ничья");
                break;
            case CONTINUE:
                return false;
        }
        
        updateScore(gameState.getPlayerScore(), gameState.getBotScore());
        disableAllButtons();
        return true;
    }
    
    public void show() {
        frame.setVisible(true);
    }
    
    public void startNewGame() {
        gameState.initializeGame();
        updateGameBoard();
        updateStatus("Ваш ход");
        enableAllButtons();
    }
    
    public void updateButton(int row, int col, String text) {
        buttons[row][col].setText(text);
    }
    
    public void updateStatus(String status) {
        statusLabel.setText(status);
    }
    
    public void updateScore(int playerScore, int botScore) {
        scoreLabel.setText("Игрок: " + playerScore + " | Бот: " + botScore);
    }
    
    public void enableAllButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(true);
            }
        }
    }
    
    public void disableAllButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }
    
    public void updateGameBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(gameState.getCell(row, col));
            }
        }
    }
} 