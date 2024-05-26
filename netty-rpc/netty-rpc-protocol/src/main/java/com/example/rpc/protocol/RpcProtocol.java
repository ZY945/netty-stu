package com.example.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dongfeng
 * 2024-01-15 21:44
 */
@Data
public class RpcProtocol<T> implements Serializable {
    private Header header;
    private T body;
}
