package TicTacToe.NettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class PlayerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 8) return; // type(4) + data(4)
        
        in.markReaderIndex();
        int typeCode = in.readInt();
        int data = in.readInt();
        
        MessageType type = MessageType.values()[typeCode - 1];
        out.add(new GameMessage(type, data));
    }
} 