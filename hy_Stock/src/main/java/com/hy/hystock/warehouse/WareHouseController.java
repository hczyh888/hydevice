package com.hy.hystock.warehouse;


import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Apply;
import com.hy.common.model.Dept;
import com.hy.common.model.Warehouse;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class WareHouseController extends BaseController {
    WareHouseService wareHouseService = WareHouseService.me;
    public void index(){
        Page<Warehouse> wareHouseTreeData = wareHouseService.wareHouseList(1, 1,"","id","desc");
        setAttr("wareHouseTreeData",wareHouseTreeData);
        render("wareHouse.html");
    }

    /**
     *获取分页同时获取到数据
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Warehouse(),paraMap);
        String sWhere=paraStr[0];
        Page<Warehouse> page= wareHouseService.wareHouseList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Warehouse>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }
    /**
     * 库房新增页面
     */
    public void add(){
        render("add.html");
    }
    
    public void getWareHouse(){
        int pid = getParaToInt(0,0);
        renderJson(wareHouseService.getWareHouse(pid));
    }
    /**
     * 保存
     */
    public void doadd(){
        Warehouse warehouse= getBean(Warehouse.class,"warehouse");
        Ret ret=null;
        if(warehouse.save()){
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
        Warehouse warehouse= wareHouseService.edit(getParaToInt(0,0));
        setAttr("warehouse",warehouse);
        render("edit.html");
    }
    /*
    * 更新
    * */
    public void update(){
        Warehouse warehouse =getBean(Warehouse.class,"warehouse");
        Ret ret;
        if(wareHouseService.update(warehouse)){
            ret =Ret.ok("msg","更新成功").set("code",0);
        }else{
            ret=Ret.fail("msg","更新失败！").set("code",-1);
        }
        renderJson(ret);
    }
/*
* 批量删除
* */
/*public void bathDel(){
    Db.batchUpdate("",)
}*/

    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(wareHouseService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}
