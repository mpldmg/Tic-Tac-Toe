package TicTacToe.NettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    static int port = 8080;

    public static void main(String[] args) throws Exception {
        int serverPort = args.length > 0 ? Integer.parseInt(args[0]) : port;
        System.out.println("Запуск сервера на порту " + serverPort);
        run(serverPort);
    }

    public static void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                new PlayerDecoder(),
                                new SimpleProcessingHandler(),
                                new PlayerEncoder()
                            );
                        }
                    });
            
            ChannelFuture f = serverBootstrap.bind(port).sync();
            System.out.println("Сервер успешно запущен на порту " + port);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            System.err.println("Ошибка запуска сервера: " + e.getMessage());
            throw e;
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}