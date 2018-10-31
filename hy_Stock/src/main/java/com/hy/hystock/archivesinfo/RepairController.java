package com.hy.hystock.archivesinfo;


import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Repair;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class RepairController extends BaseController {
    RepairService repairService = RepairService.me;
    public void index(){
        Page<Repair> repairTreeData =repairService.repairList(1, 1,"","id","desc");
        setAttr("repairTreeData",repairTreeData);
        render("stockRepair.html");
    }

    /**
     *获取分页同时获取到数据pageGrid数据格式
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Repair(),paraMap);
        String sWhere=paraStr[0];
        Page<Repair> page= repairService.repairList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Repair> rows = page.getList();
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
        String[] paraStr = Query.makePara(new Repair(),paraMap);
        String sWhere=paraStr[0];
        Page<Repair> page= repairService.repairList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Repair> rows = page.getList();
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
        Repair repair= getBean(Repair.class,"repair");
        Ret ret=null;
        if(repair.save()){
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
        Repair repair= repairService.edit(getParaToInt(0,0));
        setAttr("repair",repair);
        render("edit.html");
    }
    /*
    * 更新
    * */
    public void update(){
        Repair repair =getBean(Repair.class,"repair");
        Ret ret;
        if (repairService.update(repair)) {
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
        if(repairService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
    /**
     * 查看
     */
    public void view(){
        Repair repair = repairService.getRepairAll (getParaToInt(0,0));
        setAttr("repair",repair);
        render("view.html");
    }
}
