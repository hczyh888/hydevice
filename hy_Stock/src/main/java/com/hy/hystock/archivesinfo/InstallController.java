package com.hy.hystock.archivesinfo;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Install;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class InstallController extends BaseController {
    InstallService installService = InstallService.me;
    public void index(){
        Page<Install> installTreeData =installService.installList(1, 1,"","id","desc");
        setAttr("installTreeData",installTreeData);
        render("stockInstall.html");
    }

    /**
     *获取分页同时获取到数据pageGrid数据格式
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Install(),paraMap);
        String sWhere=paraStr[0];
        Page<Install> page= installService.installList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Install> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("page",pageUtils);
        renderJson(ret);
    }

    /**
     *获取分页同时获取到数据,layui.table格式
     */
    public void layuigetList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Install(),paraMap);
        String sWhere=paraStr[0];
        Page<Install> page= installService.installList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Install> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();

        Ret ret = Ret.by("code",0).set("data",rows).set("count",total);
        renderJson(ret);
    }
    /**
     * 入库新增页面
     */
    public void add(){ render("add.html"); }
    /**
     * 保存
     */
    public void doadd(){
        Install install= getBean(Install.class,"install");
        Ret ret=null;
        if(install.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    /*
    * 编辑
    * */
    public void edit(){
        Install install= installService.edit(getParaToInt(0,0));
        setAttr("install",install);
        render("edit.html");
    }
    /*
    * 更新
    * */
    public void update(){
        Install install =getBean(Install.class,"install");
        Ret ret;
        if (installService.update(install)) {
            ret = Ret.ok("msg", "更新成功").set("code", 0);
        }else{
            ret = Ret.fail("msg", "更新失败！").set("code", -1);
        }
        renderJson(ret);
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(installService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
    /**
     * 查看
     */
    public void view(){
        Install install = installService.getInstallAll (getParaToInt(0,0));
        setAttr("install",install);
        render("view.html");
    }
}
