package com.hy.common.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
    * 自动生成入库单工具类
    * */
public  class DynamicNumberUtil {
    /*
    *
    * 截取前四位作为标志
    * */
    public static String   dynamicNumber(String str){
        String substring = str.substring(6, 10);
        return substring;
    }


    public static String   dynamicFileNumber(String str){
        String substring = str.substring(6, 9);
        return substring;
    }
/*
*
* 传入参数  得到后16位数字
* */
    public static String  getEight(String str) {
        Date date = new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        String timeStr=format.format(date);
        String newdate = str.substring(10, 18);
        if (newdate.equals(timeStr) ){

            String newnum = str.substring(18, 22);
            int i = Integer.parseInt(newnum);
            int tmpNum = 10000 + i + 1;
           String Orderno =  new Tools().subStr("" + tmpNum, 1);
            str = newdate+Orderno;
            return str;
        }

       else{
            String s = "0001";

            String s1 = timeStr + s;

            return s1;
        }
    }
    /*
    *
    * 传入参数方法
    * */
    public  String  getRKD(String str,String str8) {
        String s = dynamicNumber(str);
        String eight = getEight(str8);
        str =s+eight;
        return str;

    }

    public  String getDAH(String str,String fileNumber) {
        String eight = getFile(str);
        String filenumber = dynamicFileNumber(fileNumber);
        str =filenumber+eight;
        return str;

    }

    public static String  getFile(String str) {
        Date date = new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        String timeStr=format.format(date);
        String newdate = str.substring(4, 12);
        if (newdate.equals(timeStr) ){

            String newnum = str.substring(17, 21);
            int i = Integer.parseInt(newnum);
            int tmpNum = 10000 + i + 1;
            String Orderno =  new Tools().subStr("" + tmpNum, 1);
            str = newdate+Orderno;
            return str;
        }

        else{
            String s = "0001";

            String s1 = timeStr + s;

            return s1;
        }
    }
}
/*
*
* 把10002首位的1去掉的实现方法：
* */
 class Tools {
    public static String subStr(String str, int start) {
        if (str == null || str.equals("") || str.length() == 0)
            return "";
        if (start < str.length()) {
            return str.substring(start);
        } else {
            return "";
        }

    }
}