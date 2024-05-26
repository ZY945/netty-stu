/**
 * @author dongfeng
 * 2024-01-17 18:14
 * 缓冲区分为直接缓冲区和堆缓冲区
 * 每个基本数据类型都对应一个Buffer(除了boolean).
 * 含有三个长度变量
 * 1.capacity 缓冲区最大容量
 * 2.position 下一个要操作的元素的位置
 * 3.limit 缓冲区中不可操作的元素个数
 * 注意点:
 * 1.通过put进行缓冲区的写入,支持方式(单个元素,数组形式(可以指定所需下标范围),缓冲区形式)
 * 2.通过get进行缓冲区的读取,支持方式(单个读取(按顺序读取或根据下标),导出到新数组(数组长度<=缓冲区中剩余元素个数))
 * 3.flip(),来使limit=position,position=0
 * 4.array()获取缓冲区的数组,对数组进行修改会影响缓冲区
 * 5.remaining()获取缓冲区中剩余的元素个数
 */
package io.dongfeng.before.buffer;