package com.hy;

import static com.hy.system.parameter.ParameterService.initCheckAllBillNo;
import static com.hy.system.user.UserService.initCheckUser;

public class AppStartDo {
    /**
     * 检查user表是否为空，如果为空自动加入一条记录，用户admin
     */
    public static void CheckUser(){
        initCheckUser();
    }

    /**
     * 检查参数表的所有单号，如果没有设置，增加默认设置
     */
    public static void CheckAllBillNo(){
        initCheckAllBillNo();

    }

}
