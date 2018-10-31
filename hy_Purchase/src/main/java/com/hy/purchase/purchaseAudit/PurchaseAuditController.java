package com.hy.purchase.purchaseAudit;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.PurchaseTask;
import com.hy.common.model.PurchaseTaskDetail;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.hy.purchase.purchaseTask.PurchaseTaskService;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class PurchaseAuditController extends BaseController {
    PurchaseAuditService purchaseAuditService = PurchaseAuditService.me;
    public void index(){
        Page<PurchaseTask> purchaseAuditTreeData = purchaseAuditService.purchaseAuditList(1, 1,"","id","desc");
        setAttr("purchaseAuditTreeData",purchaseAuditTreeData);
        render("purchaseAudit.html");
    }

    /**
     *
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new PurchaseTask(),paraMap);
        String sWhere=paraStr[0];
        Page<PurchaseTask> page= purchaseAuditService.purchaseAuditList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

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
        PurchaseTask purchaseAudit= purchaseAuditService.edit(getParaToInt(0,0));
        setAttr("purchaseAudit",purchaseAudit);
        render("edit.html");
    }
    /**
     * 更新
     */
    public void update(){
        PurchaseTask purchaseAudit=getModel(PurchaseTask.class, "purchaseAudit");
        Ret ret;
        if(purchaseAuditService.update(purchaseAudit)){
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
        PurchaseTask purchaseAudit= getBean(PurchaseTask.class, "purchaseAudit");
        boolean save=purchaseAudit.save();
        Integer id = purchaseAudit.getId();
        if(save){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        //保存明细表
        PurchaseTaskDetail purchaseAuditDetail=getBean(PurchaseTaskDetail.class,"purchaseAuditDetail");
        purchaseAuditDetail.setPursureId(id);
        if(purchaseAuditDetail.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    /**
     * 查看
     */
    public void audit(){
        PurchaseTask purchaseAudit = purchaseAuditService.getPurchaseTaskAll (getParaToInt(0,0));
        setAttr("purchaseAudit",purchaseAudit);
        render("audit.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(purchaseAuditService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}


