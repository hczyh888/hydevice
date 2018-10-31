package com.hy.common.xss;

import com.hy.common.exception.MyException;
import com.hy.common.kit.JStrKit;
import com.jfinal.kit.StrKit;

/**
 * SQL过滤
 * @author chenyi
 * @date 2018-01-01 16:16
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    public static String sqlInject(String str,boolean flag){
        if(StrKit.isBlank(str)){
            return null;
        }
        //去掉'|"|;|\字符

        str = JStrKit.replace(str, "'", "");
        str = JStrKit.replace(str, "\"", "");
        str = JStrKit.replace(str, ";", "");
        str = JStrKit.replace(str, "\\", "");

        //转换成小写
        str=flag?JStrKit.camel2Underline(str):str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            if(str.indexOf(keyword) != -1){
                throw new MyException("包含非法字符");
            }
        }

        return str;
    }
}
