package com.hy.purchase.purchaseTask;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.PlanTask;
import com.hy.common.model.PlanTaskdetail;
import com.hy.common.model.PurchaseTask;
import com.hy.common.model.PurchaseTaskDetail;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;

import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class PurchaseTaskController extends BaseController {
    PurchaseTaskService purchaseTaskService = PurchaseTaskService.me;
    public void index(){
        render("purchaseTask.html");
    }

    /**
     *
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new PurchaseTask(),paraMap);
        String sWhere=paraStr[0];
        Page<PurchaseTask> page= purchaseTaskService.purchaseTaskList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<PurchaseTask>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }
    /**
     * 添加
     */
    public void add(){
        render("add.html");
    }
    /**
     * 编辑
     */
    public void edit(){
        PurchaseTask purchaseTask= purchaseTaskService.edit(getParaToInt(0,0));
        setAttr("purchaseTask",purchaseTask);
        render("edit.html");
    }
    /**
     * 更新
     */
    public void update(){
        PurchaseTask purchaseTask =getModel(PurchaseTask.class, "purchaseTask");
        Ret ret;
        if(purchaseTaskService.update(purchaseTask)){
            ret =Ret.ok("msg","保存成功").set("code",0);
        }else{
            ret=Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }

    /**
     * 保存
     */
    public void doadd(){
        Ret ret=null;
        PurchaseTask purchaseTask= getBean(PurchaseTask.class, "purchaseTask");
        boolean save=purchaseTask.save();
        Integer id = purchaseTask.getId();
        if(save){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        //保存明细表
        PurchaseTaskDetail purchaseTaskDetail=getBean(PurchaseTaskDetail.class,"purchaseTaskDetail");
        purchaseTaskDetail.setPursureId(id);
        if(purchaseTaskDetail.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    /**
     * 查看
     */
    public void view(){
        PurchaseTask purchaseTask = purchaseTaskService.getPurchaseTaskAll (getParaToInt(0,0));
        setAttr("purchaseTask",purchaseTask);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(purchaseTaskService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}


