package io.dongfeng.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.charset.StandardCharsets;

/**
 * @author dongfeng
 * 2024-01-05 22:19
 */
public class NettyServer {
    ServerBootstrap serverBootstrap;

    /**
     * 服务端口
     */
    private int port = 9999;

    // NioEventLoopGroup
    //  1. NioEventLoopGroup 接受传入的连接。
    NioEventLoopGroup acceptor;
    // 2. NioEventLoopGroup 是一个事件循环组，它包含多个事件循环，用于处理多个客户端连接和I/O
    NioEventLoopGroup worker;

    public NettyServer(int port) {
        this.serverBootstrap = new ServerBootstrap();

        this.acceptor = new NioEventLoopGroup();
        this.worker = new NioEventLoopGroup();
        this.port = port;
        try {
            this.start();
        } catch (InterruptedException e) {
            System.out.println("启动服务端失败");
            throw new RuntimeException(e);
        }
    }

    public void start() throws InterruptedException {
        serverBootstrap
                .group(acceptor, worker) // 线程组
                .channel(NioServerSocketChannel.class) // 构建socketChannel工厂
                .option(ChannelOption.SO_KEEPALIVE, true)  //配置 option 参数(key,value)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 传入自定义客户端Handle（服务端在这里搞事情）
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        NettyServer.this.initChannel(socketChannel);
                    }
                });
        //绑定端口号，启动服务端
        ChannelFuture channelFuture = serverBootstrap.bind(this.port).sync();
        //对关闭通道进行监听
        channelFuture.channel().closeFuture().sync();

    }

    private void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 注册handler
        pipeline
                .addLast(new MyServerHandler());
    }

    /**
     * 该方法用于读取客户端消息
     */
    @ChannelHandler.Sharable
    class MyServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            ByteBuf result = (ByteBuf) msg;
            byte[] bytes = new byte[result.readableBytes()];
            result.readBytes(bytes);
            String resultStr = new String(bytes, StandardCharsets.UTF_8);
            System.out.println("接收到客户端消息:" + resultStr);


            // 返回客户端消息
            String response = "访问成功";
            ByteBuf buffer = ctx.alloc().buffer(4 * response.length());
            buffer.writeBytes(response.getBytes(StandardCharsets.UTF_8));
            ctx.writeAndFlush(buffer);


        }

        /**
         * 处理异常
         *
         * @param ctx
         * @param cause
         * @throws Exception
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
            ctx.close();
        }

        /**
         * 处理完消息后，调用该方法
         *
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            super.channelReadComplete(ctx);
        }
    }
}
