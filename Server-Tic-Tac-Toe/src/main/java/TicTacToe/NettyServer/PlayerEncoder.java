package TicTacToe.NettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PlayerEncoder extends MessageToByteEncoder<GameMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, GameMessage msg, ByteBuf out) {
        out.writeInt(msg.getType().getCode());
        out.writeInt(msg.getData());
    }
}