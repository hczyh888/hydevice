package com.hy.hystock.enterstock;


import com.alibaba.fastjson.JSONArray;
import com.hy.common.constant.BillsConst;
import com.hy.common.constant.ConstCache;
import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.*;
import com.hy.common.plugin.shiro.ShiroKit;
import com.hy.common.tools.DynamicNumberUtil;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.hystock.enterstockdetail.EnterStockDetailService;
import com.hy.system.parameter.ParameterService;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;
import java.util.Map;

public class EnterStockController extends BaseController {
    EnterStockService enterStockService = EnterStockService.me;
    EnterStockDetailService enterStockDetailService= EnterStockDetailService.me;
    ParameterService parameterService=  ParameterService.me;
    public void index(){
        enterStockService.enterstockList(1, 1,"","id","desc");
        render("enterstock.html");
    }
    /**
     *获取分页同时获取到数据
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Apply(),paraMap);
        String sWhere=paraStr[0];

        String sss=getPara("copyall");
        if (StrKit.notBlank()){
        sWhere +="and status="+sss;
        }
        Page<Enterstock> page= enterStockService.enterstockList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Enterstock>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }
    /**
     * 入库新增页面
     */
    public void add(){
       String rkdh =  parameterService.getNewBillNo(BillsConst.BILL_RKDH);
       CacheKit.put(ConstCache.SYS_CACHE,BillsConst.BILL_RKDH+"-"+ ShiroKit.getUser().getId(),rkdh);
        // 将最新生成的入库单号传给页面
        setAttr("rkd",rkdh);
        render("add.html");
    }

    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before({JSONInterceptor.class,Tx.class})
    public void updateDetail() {
        int enterId = getParaToInt(0,0);
        List<EnterstockDetail> enterstockDetailsList = JSONArray.parseArray(getAttr("json").toString(), EnterstockDetail.class);
        for (EnterstockDetail detail : enterstockDetailsList) {
          /*
          * 对入库明细表的数据保存
          * */
            EnterstockDetail enterstockdetail = getModel(EnterstockDetail.class);
            enterstockdetail.setId(detail.getId());
            enterstockdetail.setWarrantyPeriod(detail.getWarrantyPeriod());
            enterstockdetail.setPrice(detail.getPrice());
            enterstockdetail.setEnternum(detail.getEnternum());
            enterstockdetail.setEnterType(detail.getEnterType());
            enterstockdetail.setDeviceId(detail.getDeviceId());
            enterstockdetail.setDeviceId(detail.getClassId());
            enterstockdetail.setCompanyId(detail.getCompanyId());
            enterstockdetail.setBeginTime(detail.getBeginTime());
            enterstockdetail.setEnterId(enterId);
            enterStockDetailService.update(enterstockdetail);
        }
        Ret ret=null;
        ret = Ret.ok("msg","更新成功！").set("code",0);
        renderJson(ret);
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before({JSONInterceptor.class,Tx.class})
    public void saveDetail(){
        int enterId = getParaToInt(0,0);
        List<EnterstockDetail> enterstockDetailsList =JSONArray.parseArray(getAttr("json").toString(),EnterstockDetail.class);
        for(EnterstockDetail enterStockDetail:enterstockDetailsList) {
          // 对入库明细表的数据保存
            EnterstockDetail enterstockdetail =getModel(EnterstockDetail.class);
            enterstockdetail.setWarrantyPeriod(enterStockDetail.getWarrantyPeriod());
            enterstockdetail.setPrice(enterStockDetail.getPrice());
            enterstockdetail.setEnternum(enterStockDetail.getEnternum());
            enterstockdetail.setEnterType(enterStockDetail.getEnterType());
            enterstockdetail.setDeviceId(enterStockDetail.getDeviceId());
            enterstockdetail.setDeviceId(enterStockDetail.getClassId());

            enterstockdetail.setCompanyId(enterStockDetail.getCompanyId());
            enterstockdetail.setBeginTime(enterStockDetail.getBeginTime());
            enterstockdetail.setEnterId(enterId);
            enterstockdetail.save();
            Stockpile stockStockpile =getModel(Stockpile.class);

            // 动态生成档案号
            Parameter value = enterStockService.value(BillsConst.BILL_RKDH);
           // Parameter parameter2 = value.getPara();
           // Parameter parameter3 = value[3];
            String s2 = String.valueOf(value.getPara());
            String s3 = String.valueOf(value.getPara());
            String dah = new  DynamicNumberUtil().getDAH(s2,s3);
            StringBuilder stringBuilder = new StringBuilder(dah);
            Integer sn =enterStockDetail.getDeviceSn();
            if(sn<1000){
                sn=1000+sn;
            }
            StringBuilder newdah = stringBuilder.insert(3, sn.toString().substring(1,3));

            // 对库存表的数据保存
            stockStockpile.setFileNumber(newdah.toString());
            stockStockpile.setNumber(enterStockDetail.getEnternum());
            stockStockpile.setPrice(enterStockDetail.getPrice());
            stockStockpile.setClassId(enterStockDetail.getClassId());
            stockStockpile.setDeviceId(enterStockDetail.getDeviceId());
            stockStockpile.setWarehouseId(enterStockDetail.getWareHouseId());
            // 回写参数表档案号
            parameterService.updateValue(dah,"7","DAH");
            stockStockpile.save();
        }
        Ret ret=null;
        ret = Ret.ok("msg","保存成功！").set("code",0);
        renderJson(ret);
    }

    /*
        * 保存入库单头部信息
        * */
    public void saveHead(){
        Enterstock enterstock =getBean(Enterstock.class,"enterstock");
        enterstock.setStatus(1);
        Ret ret;
        if(enterstock.save()){
            ret =Ret.ok("msg","保存成功").set("code",0).set("enterId",enterstock.getId());
            //删除数据库明细where enterId= enterstock.getId()

        }else{
            ret=Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }

        /*
        * 更新入库单头部信息
        * */
        public void updateHead(){
            Enterstock enterstock =getBean(Enterstock.class,"enterstock");
            enterstock.setStatus(1);
            Ret ret;
            if(enterStockService.update(enterstock)){
                ret =Ret.ok("msg","更新成功").set("code",0);
                //删除数据库明细where enterId= enterstock.getId()
                //enterStockDetailService.deleteByEnterId(enterstock.getId());
            }else{
                ret=Ret.fail("msg","更新失败！").set("code",-1);
            }
            renderJson(ret);
        }
    /*
    * 编辑
    * */
    public void edit(){
        Enterstock enterstock= enterStockService.edit(getParaToInt(0,0));
        setAttr("enterstock",enterstock);
        render("edit.html");
    }
    /*
    * 更新
    * */
    public void update(){
        Enterstock enterstock =getBean(Enterstock.class,"enterstock");
        Ret ret;
        if(enterStockService.update(enterstock)){
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
        if(enterStockService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}

