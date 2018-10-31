package com.hy.common.enumresource;

import com.hy.common.utils.EnumMessage;

/**
 * Created
 * 2017/7/20.
 */
public enum ApplypartnerEnum implements EnumMessage {
    YES("1","是"),
    NO("2","否");
    private final String code;
    private final String value;
    private ApplypartnerEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
    @Override
    public String getCode() { return code;}
    @Override
    public String getValue() { return value; }
}
