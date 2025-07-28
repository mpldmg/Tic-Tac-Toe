package TicTacToe.NettyServer;

public class GameMessage {
    private MessageType type;
    private int data;
    
    public GameMessage(MessageType type, int data) {
        this.type = type;
        this.data = data;
    }
    
    public MessageType getType() { return type; }
    public int getData() { return data; }
} 