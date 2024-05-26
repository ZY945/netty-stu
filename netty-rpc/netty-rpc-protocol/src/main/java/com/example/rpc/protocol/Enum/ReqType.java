package com.example.rpc.protocol.Enum;

/**
 * @author dongfeng
 * 2024-01-15 21:45
 */
public enum ReqType {
    REQUEST((byte)1),
    RESPONSE((byte)2),
    HEARTBEAT((byte)3);

    private byte code;

    private ReqType(byte code) {
        this.code = code;
    }


    public byte getCode() {
        return this.code;
    }

    public static ReqType findByCode(int code) {
        for (ReqType msgType : ReqType.values()) {
            if(msgType.getCode()==code){
                return msgType;
            }
        }
        return null;
    }
}
