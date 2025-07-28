package TicTacToe.Client;

public class GameController {
    private final GameState gameState;
    private final GameLogic gameLogic;
    private final BotStrategy botStrategy;
    private GameView gameView; // Изменим на не final
    
    public GameController() {
        this.gameState = new GameState();
        this.gameLogic = new GameLogic();
        this.botStrategy = new BotStrategy();
    }
    
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
    
    public void startNewGame() {
        gameState.initializeGame();
        gameView.updateGameBoard();
        gameView.updateStatus("Ваш ход");
        gameView.enableAllButtons();
    }
    
    public void handlePlayerMove(int row, int col) {
        if (!gameState.isPlayerTurn() || !gameState.isEmpty(row, col)) {
            return;
        }
        
        makeMove(row, col, gameState.getXState());
        gameView.updateButton(row, col, gameState.getXState());
        
        if (checkGameEnd()) {
            return;
        }
        
        // Ход бота
        gameState.makeMove(0, 0, gameState.getOState()); // Временный ход
        gameView.updateStatus("Ход бота...");
        
        // Имитация задержки
        new Thread(() -> {
            try {
                Thread.sleep(50);
                makeBotMove();
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
            gameView.updateButton(row, col, gameState.getOState());
            checkGameEnd();
        }
    }
    
    private void makeMove(int row, int col, String symbol) {
        gameState.makeMove(row, col, symbol);
        gameView.updateStatus(gameState.isPlayerTurn() ? "Ваш ход" : "Ход бота...");
    }
    
    private boolean checkGameEnd() {
        GameLogic.GameResult result = gameLogic.checkGameState(gameState);
        
        switch (result) {
            case PLAYER_WINS:
                gameState.incrementPlayerScore();
                gameView.updateStatus("Х победили!");
                break;
            case BOT_WINS:
                gameState.incrementBotScore();
                gameView.updateStatus("O победили!");
                break;
            case DRAW:
                gameView.updateStatus("Ничья");
                break;
            case CONTINUE:
                return false;
        }
        
        gameView.updateScore(gameState.getPlayerScore(), gameState.getBotScore());
        gameView.disableAllButtons();
        return true;
    }
    
    public GameState getGameState() {
        return gameState;
    }
} 