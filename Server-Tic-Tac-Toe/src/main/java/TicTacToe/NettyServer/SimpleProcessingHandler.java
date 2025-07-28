package TicTacToe.NettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleProcessingHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент подключен");
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент отключился");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof GameMessage) {
            GameMessage gameMsg = (GameMessage) msg;
            System.out.println("Получено сообщение: " + gameMsg.getType() + " с данными: " + gameMsg.getData());
            
            // Эхо-ответ
            ctx.writeAndFlush(new GameMessage(MessageType.GAME_STATE, gameMsg.getData()));
        } else {
            System.out.println("Неизвестный тип сообщения: " + msg.getClass());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("Ошибка: " + cause.getMessage());
        ctx.close();
    }
}
