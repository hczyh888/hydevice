package com.hy.hystock.stockpile;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Stockpile;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.LayGrid;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class FixedDevicesController extends BaseController {
    FixedDevicesService fixedDevicesService = FixedDevicesService.me;
    public void index(){
        Page<Stockpile> fixedDevicesTreeData =fixedDevicesService.fixeddevicesList(1, 1,"","id","desc");
        setAttr("fixedDevicesTreeData",fixedDevicesTreeData);
        render("fixedDevices.html");
    }

    /**
     *获取分页同时获取到数据layuiGrid数据格式
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Stockpile(),paraMap);
        String sWhere=paraStr[0];
        Page<Stockpile> page= fixedDevicesService.fixeddevicesList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Stockpile> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        LayGrid layGrid=new LayGrid(rows,total,curPage,count);
        layGrid.setCode(0);
        renderJson(layGrid);
    }

    /**
     *获取分页同时获取到数据,layui.table格式
     */
    public void layuigetList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Stockpile(),paraMap);
        String sWhere=paraStr[0];
        Page<Stockpile> page= fixedDevicesService.fixeddevicesList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Stockpile> rows = page.getList();
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
        Stockpile fixeddevices= getBean(Stockpile.class,"fixeddevices");
        Ret ret=null;
        if(fixeddevices.save()){
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
        Stockpile fixeddevices= fixedDevicesService.edit(getParaToInt(0,0));
        setAttr("fixeddevices",fixeddevices);
        render("edit.html");
    }
    /*
    * 更新
    * */
    public void update(){
        Stockpile fixeddevices =getBean(Stockpile.class,"fixeddevices");
        Ret ret;
        if (fixedDevicesService.update(fixeddevices)) {
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
        if(fixedDevicesService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
    /**
     * 查看
     */
    public void view(){
        Stockpile fixeddevices = fixedDevicesService.getFixedDevicesAll (getParaToInt(0,0));
        setAttr("fixeddevices",fixeddevices);
        render("view.html");
    }
    /**
     * 医疗设备保管使用卡
     */
    public void fixedDeviceCard(){
        Stockpile fixeddevices= fixedDevicesService.edit(getParaToInt(0,0));
        setAttr("fixeddevices",fixeddevices);
        render("fixedDeviceCard.html");
    }

}
