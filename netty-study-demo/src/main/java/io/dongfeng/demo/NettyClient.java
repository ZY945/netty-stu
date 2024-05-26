package io.dongfeng.demo;

import io.netty.bootstrap.Bootstrap;
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
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @author dongfeng
 * 2024-01-08 17:45
 */
public class NettyClient {
    private String host;
    private int port;
    private Bootstrap bootstrap;

    private NioEventLoopGroup work;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;

        this.bootstrap = new Bootstrap();
        this.work = new NioEventLoopGroup();
        try {
            this.start();
        } catch (InterruptedException e) {
            System.out.println("客户端启动失败");
            throw new RuntimeException(e);
        }
    }

    public void start() throws InterruptedException {
        bootstrap
                .group(work)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        NettyClient.this.initChannel(socketChannel);
                    }
                });
        ChannelFuture sync = bootstrap.connect(host, port).sync();
        sync.channel().closeFuture().sync();
    }

    private void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new StringDecoder())
                .addLast(new StringEncoder())
                .addLast(new MyClientHandler());
    }

    /**
     * 该方法用于读取客户端消息
     */
    @ChannelHandler.Sharable
    class MyClientHandler extends ChannelInboundHandlerAdapter {
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
