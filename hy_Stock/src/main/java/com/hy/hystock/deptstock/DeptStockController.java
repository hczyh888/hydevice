package com.hy.hystock.deptstock;


import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Apply;
import com.hy.common.model.Deptstock;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class DeptStockController extends BaseController {
    DeptStockService deptStockService = DeptStockService.me;
    public void index(){
        Page<Deptstock> deptStockTreeData =deptStockService.deptstockList(1, 1,"","id","desc");
        setAttr("deptStockTreeData",deptStockTreeData);
        render("leavestock.html");
    }

    /**
     *获取分页同时获取到数据pageGrid数据格式
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Apply(),paraMap);
        String sWhere=paraStr[0];
        Page<Deptstock> page= deptStockService.deptstockList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Deptstock> rows = page.getList();
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
        String[] paraStr = Query.makePara(new Apply(),paraMap);
        String sWhere=paraStr[0];
        Page<Deptstock> page= deptStockService.deptstockList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Deptstock> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();

        Ret ret = Ret.by("code",0).set("data",rows).set("count",total);
        renderJson(ret);
    }
    /**
     * 入库新增页面
     */
    public void add(){
        render("add.html");
    }
    /**
     * 保存
     */
    public void doadd(){
        Deptstock deptstock= getBean(Deptstock.class,"deptstock");
        Ret ret=null;
        if(deptstock.save()){
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
        Deptstock deptstock= deptStockService.edit(getParaToInt(0,0));
        setAttr("deptstock",deptstock);
        render("edit.html");
    }
    /*
    * 更新
    * */
    public void update(){
        Deptstock deptstock =getBean(Deptstock.class,"deptstock");
        Ret ret;
        if(deptStockService.update(deptstock)){
            ret =Ret.ok("msg","更新成功").set("code",0);
        }else{
            ret=Ret.fail("msg","更新失败！").set("code",-1);
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
        if(deptStockService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}
