package io.dongfeng.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author dongfeng
 * 2024-03-04 13:59
 */
public class Buffer_Channel_Demo {
    public static void main(String[] args) throws IOException {
        String path = Buffer_Channel_Demo.class.getResource("/file.txt").getFile();
        FileChannel channel = new FileInputStream(path).getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        while (true) {
            int read = channel.read(byteBuffer);
            byteBuffer.flip();
            if(read==-1)break;
            while (byteBuffer.hasRemaining()) {
                byte b = byteBuffer.get();
                System.out.println("b = " + (char) b);

            }
            byteBuffer.clear();
        }
    }
}
