package com.hy.hystock.deviceallot;


import com.hy.common.constant.BillsConst;
import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.kit.DateKit;
import com.hy.common.model.*;
import com.hy.common.tools.DynamicNumberUtil;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.hy.hystock.deptstock.DeptStockService;
import com.hy.hystock.stockpile.StockPileService;
import com.hy.hystock.warehouse.WareHouseService;
import com.hy.system.parameter.ParameterService;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;
import java.util.Map;

public class DeviceAllotController extends BaseController {
    DeviceAllotService deviceAllotService= DeviceAllotService.me;
    DeptStockService deptStockService= DeptStockService.me;
    StockPileService stockPileService=StockPileService.me;
    ParameterService parameterService=  ParameterService.me;
    WareHouseService wareHouseService=WareHouseService.me;
    public void index(){
        Page <Allot> allotTreeData = deviceAllotService.allotList(1, 1,"","id"," desc ");
        setAttr("allotTreeData",allotTreeData);
        render("deviceallot.html");
    }

    public void getList(){

        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Allot() , paraMap);
        String sWhere=paraStr[0];
        Page<Allot> page= deviceAllotService.allotList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Allot>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("page",pageUtils);
        renderJson(ret);
    }
    public void getDeptStockList(){

        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Deptstock() , paraMap);
        String sWhere=paraStr[0];

        Page<Deptstock> page= deptStockService.deptstockList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Deptstock>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("page",pageUtils);
        renderJson(ret);
    }
    public void getStockPileList(){

        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Stockpile() , paraMap);
        String sWhere=paraStr[0];
        Page<Stockpile> page= stockPileService.stockpileList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Stockpile>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("page",pageUtils);
        renderJson(ret);
    }
    //增加
    public void add(){
         // 在service写sql语句获取para和name值
        Parameter value = deviceAllotService.value(BillsConst.BILL_DBDH);
        String d= DateKit.getDays();
        String oldDbdh = value.getPara();
        String dv = StrKit.notBlank(oldDbdh)?oldDbdh.substring(4,12):d;
        String dbdh=BillsConst.BILL_CKDH+d;  //得到前12位
        String bh3 = "001";
        if(d.equals(dv)){ //说明今天已经有入库单号产生 ，取后三位+1得到新的单号
            bh3 =StrKit.notBlank(oldDbdh)?oldDbdh.substring(oldDbdh.length()-3):"001";
            bh3 = String.valueOf(1000 +Integer.parseInt(bh3)+1);
            bh3 = bh3.substring(bh3.length()-3);

        }else{  //否则这是第一单
            //no dosomething
        }
        dbdh = dbdh+bh3;
        //回写到parameter
        parameterService.updateValue(dbdh,"2","DBDH");
        //库房类型
        List<Warehouse> lst =  wareHouseService.getWareHouseType();
        setAttr("wareHourses",lst);
       // 将最新生成的档案号传给页面
        setAttr("DBDH",dbdh);
        render("add.html");
    }
    public void edit(){
        Allot allot=deviceAllotService.edit(getParaToInt(0,0));
        setAttr("allot",allot);
        render("edit.html");
    }
    public void update(){
        Allot allot=getBean(Allot.class,"allot");
        if(deviceAllotService.update(allot)){
            Ret ret =Ret.ok("msg","更新成功").set("code",0);
            ret.set("allot",allot);
            renderJson(ret);
        }else{
            Ret ret=Ret.fail("msg","更新失败！").set("code",-1);
            renderJson(ret);
        }
    }
    public void doadd(){
        Allot allot= getBean(Allot.class,"allot");
        Ret ret=null;
        if(allot.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    public void view(){
        Allot allot=deviceAllotService.getById(getParaToInt(0,0));
        setAttr("allot",allot);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(deviceAllotService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}
