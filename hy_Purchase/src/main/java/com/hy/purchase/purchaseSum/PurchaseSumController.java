package com.hy.purchase.purchaseSum;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.PurchaseTaskDetail;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class PurchaseSumController extends BaseController {
    PurchaseSumService purchaseSumService = PurchaseSumService.me;
    public void index(){
        render("purchasesum.html");
    }

    /**
     *
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new PurchaseTaskDetail(),paraMap);
        String sWhere=paraStr[0];
        Page<PurchaseTaskDetail> page= purchaseSumService.purchaseTaskDetailList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<PurchaseTaskDetail>  rows = page.getList();
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
        PurchaseTaskDetail purchaseTaskDetail= purchaseSumService.edit(getParaToInt(0,0));

        setAttr("purchaseTaskDetail",purchaseTaskDetail);
        render("edit.html");
    }
    /**
     * 更新
     */
    public void update(){
        PurchaseTaskDetail purchaseTaskDetail =getModel(PurchaseTaskDetail.class, "purchaseTaskDetail");
        Ret ret;
        if(purchaseSumService.update(purchaseTaskDetail)){
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
        PurchaseTaskDetail purchaseTaskDetail= getBean(PurchaseTaskDetail.class, "purchaseTaskDetail");
        Ret ret=null;
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
        PurchaseTaskDetail purchaseTaskDetail = purchaseSumService.getPurchaseTaskDetailAll (getParaToInt(0,0));
        setAttr("purchaseTaskDetail",purchaseTaskDetail);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(purchaseSumService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}



