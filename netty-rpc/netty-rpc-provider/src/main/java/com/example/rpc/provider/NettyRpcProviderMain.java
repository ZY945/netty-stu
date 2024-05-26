package com.example.rpc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dongfeng
 * 2024-01-15 21:38
 */
@SpringBootApplication
public class NettyRpcProviderMain {
    public static void main(String[] args) {
        SpringApplication.run(NettyRpcProviderMain.class, args);
//        new NettyServer("127.0.0.1",8080).startNettyServer();   //case2(后续再加上)
    }
}
