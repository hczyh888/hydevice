package com.hy.common.enumresource;

import com.hy.common.utils.EnumMessage;

/**
 * Created
 * 2017/7/20.
 */
public enum ApplyStateEnum implements EnumMessage {
    DRAFT("1","草稿"),
    WAITCHECK("2","待审核"),
    CHENECKED("3","已审核");
    private final String code;
    private final String value;
    private ApplyStateEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
    @Override
    public String getCode() { return code;}
    @Override
    public String getValue() { return value; }
}
