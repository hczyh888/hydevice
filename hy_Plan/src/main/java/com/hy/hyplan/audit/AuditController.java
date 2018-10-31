package com.hy.hyplan.audit;


import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Apply;
import com.hy.common.model.Applypartner;
import com.hy.common.model.Dept;
import com.hy.common.tools.Func;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import java.util.List;
import java.util.Map;

public class AuditController extends BaseController {
   AuditService auditService = AuditService.me;
    public void index(){
        Page<Apply> auditTreeData = auditService.auditList(1, 1,"","id","desc");
        setAttr("auditTreeData",auditTreeData);
        render("audit.html");
    }

    /**
     *
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Apply(),paraMap);
        String sWhere=paraStr[0];
        String status = getPara(0);
        if(StrKit.notBlank(status)) sWhere += "and status='"+status+"'"+"or status='3"+"'";
        Page<Apply> page= auditService.auditList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Apply>  rows = page.getList();
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
     * 审核
     */
    public void edit(){
        Apply audit= auditService.edit(getParaToInt(0,0));

        setAttr("audit",audit);
        render("check.html");
    }
    /**
     * 更新
     */
    public void update(){
        Apply audit =getModel(Apply.class,"audit");
        Ret ret;
        if(auditService.update(audit)){
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
        Apply audit= getBean(Apply.class,"audit");
        Ret ret=null;
        if(audit.save()){
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
        Apply audit = auditService.getApplyAll (getParaToInt(0,0));
        setAttr("audit",audit);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(auditService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}
