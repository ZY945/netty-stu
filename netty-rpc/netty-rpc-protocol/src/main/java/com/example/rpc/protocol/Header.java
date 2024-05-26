package com.example.rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dongfeng
 * 2024-01-15 21:40
 */
@AllArgsConstructor
@Data
public class Header implements Serializable {
    private short magic;
    private byte serialType;
    private byte reqType;
    private int requestId;
    private int length;
}
