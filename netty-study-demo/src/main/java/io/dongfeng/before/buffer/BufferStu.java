package io.dongfeng.before.buffer;

import java.nio.IntBuffer;

/**
 * @author dongfeng
 * 2024-01-17 18:08
 */
public class BufferStu {
    public static void main(String[] args) {
        mark();
    }

    void putBuffer() {
        // 方式1:申请指定容量的缓冲区
        int[] array = new int[]{0, 1, 2, 3};
        IntBuffer wrap = IntBuffer.wrap(array);
        System.out.println(wrap.get(1)); // 1
        // 方式2: 创建一个指定大小的缓冲区
        IntBuffer allocate = IntBuffer.allocate(10);
        allocate.put(1);

        allocate.flip();

    }

    static void mark() {
        IntBuffer wrap = IntBuffer.wrap(new int[]{0, 1, 2, 3});
        System.out.println(wrap.get()); // 1
        System.out.println(wrap.get()); // 1
        System.out.println(wrap.get()); // 1
        System.out.println(wrap.get()); // 1
        System.out.println(wrap.get()); // 1
        System.out.println(wrap.get()); // 1

    }
}
