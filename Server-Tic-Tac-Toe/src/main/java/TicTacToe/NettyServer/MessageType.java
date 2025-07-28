package TicTacToe.NettyServer;

public enum MessageType {
    CONNECT(1), MOVE(2), GAME_STATE(3), ERROR(4);
    
    private final int code;
    MessageType(int code) { this.code = code; }
    public int getCode() { return code; }
} 