package com.hy.hystock.initstock;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.tools.RequestJson;
import com.hy.hystock.deptstock.DeptStockService;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;

public class InitStockController extends BaseController {
    private DeptStockService deptStockService = DeptStockService.me;
    public void index(){
        render("initstock.html");
    }
    public void add(){
        render("add.html");
    }
    @Before(JSONInterceptor.class)
    public void doadd(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(), RequestJson.class);
        deptStockService.BatchSave(requestJson);
    }


}
