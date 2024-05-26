package com.example.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dongfeng
 * 2024-01-15 21:42
 */
@Data
public class RpcRequest implements Serializable {
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
}
