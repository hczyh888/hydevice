package com.hy.common.enumresource;

import com.hy.common.utils.EnumMessage;

/**
 * Created
 * 2017/7/20.
 */
public enum MenuTypeEnum implements EnumMessage {
    DIR("1","目录"),
    MENU("2","菜单"),
    BUTTON("3","按钮");
    private final String code;
    private final String value;
    private MenuTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
    @Override
    public String getCode() { return code;}
    @Override
    public String getValue() { return value; }
}
