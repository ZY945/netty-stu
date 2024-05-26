package com.example.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dongfeng
 * 2024-01-15 21:43
 */
@Data
public class RpcResponse implements Serializable {
    private String requestId;
    private Throwable error;
    private Object data;
    private String msg;
    private Integer code;
}
