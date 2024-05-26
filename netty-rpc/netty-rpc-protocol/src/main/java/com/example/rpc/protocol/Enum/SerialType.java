package com.example.rpc.protocol.Enum;

public enum SerialType {

    JSON_SERIAL((byte)0),
    JAVA_SERIAL((byte)1);

    private byte code;

    SerialType(byte code) {
        this.code=code;
    }

    public byte code(){
        return this.code;
    }
}
