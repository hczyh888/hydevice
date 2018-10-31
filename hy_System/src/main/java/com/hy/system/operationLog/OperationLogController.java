package com.hy.system.operationLog;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.OperationLog;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;


public class OperationLogController extends BaseController{
    OperationLogService operationLogService = OperationLogService.me;
    public void index(){
        Page <OperationLog> operationLogTreeData = operationLogService.operationLogList(1, 1,"","id"," desc ");
        setAttr("operationLogTreeData",operationLogTreeData);
        render("operationLog.html");
    }

    /**
     *
     */
    public void getList(){

        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new OperationLog() , paraMap);
        String sWhere=paraStr[0];
        Page<OperationLog> page= operationLogService.operationLogList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<OperationLog>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("page",pageUtils);
        renderJson(ret);
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(operationLogService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}
