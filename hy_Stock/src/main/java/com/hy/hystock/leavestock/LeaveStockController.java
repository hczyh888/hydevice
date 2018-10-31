package com.hy.hystock.leavestock;


import com.alibaba.fastjson.JSONArray;
import com.hy.common.constant.BillsConst;
import com.hy.common.constant.ConstCache;
import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.kit.DateKit;
import com.hy.common.model.*;
import com.hy.common.tools.DynamicNumberUtil;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.hy.hystock.leavestockdetail.LeaveStockDetailService;
import com.hy.system.parameter.ParameterService;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class LeaveStockController extends BaseController {
    LeaveStockService leaveStockService = LeaveStockService.me;
    LeaveStockDetailService leaveStockDetailService = LeaveStockDetailService.me;
    ParameterService parameterService = ParameterService.me;
    public void index(){
        Page<LeaveStock> leaveStockTreeData = leaveStockService.leavestockList(1, 1,"","id","desc");
        setAttr("leaveStockTreeData",leaveStockTreeData);
        render("leavestock.html");
    }

    /**
     *获取分页同时获取到数据
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Apply(),paraMap);
        String sWhere=paraStr[0];
        Page<LeaveStock> page= leaveStockService.leavestockList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<LeaveStock>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }
    /**
     * 出库新增页面
     */
    public void add(){
        String ckdh = parameterService.getNewBillNo(BillsConst.BILL_CKDH);
        setAttr("ckd",ckdh);
        render("add.html");
    }
    /**
     * 保存
     */
    public void doadd(){
        LeaveStock leavestock= getBean(LeaveStock.class,"leavestock");
        Ret ret=null;
        if(leavestock.save()){
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
        LeaveStock leavestock= leaveStockService.edit(getParaToInt(0,0));
        setAttr("leavestock",leavestock);
        render("edit.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before({JSONInterceptor.class,Tx.class})

    public void saveDetail(){
        int leaveId=getParaToInt(0,0);
        List<LeaveStockDetail> leaveStockDetailList =JSONArray.parseArray(getAttr("json").toString(),LeaveStockDetail.class);
        for(LeaveStockDetail leaveStockDetail:leaveStockDetailList) {
          /*
          * 对入库明细表的数据保存
          * */
            LeaveStockDetail leaveStockDetail1 =getModel(LeaveStockDetail.class);
            leaveStockDetail1.setDeviceClassName(leaveStockDetail.getDeviceClassName());
            leaveStockDetail1.setDeviceName(leaveStockDetail.getDeviceName());
            leaveStockDetail1.setFileNumber(leaveStockDetail.getFileNumber());
            leaveStockDetail1.setLeaveDate(leaveStockDetail.getLeaveDate());
            leaveStockDetail1.setLeavenum(leaveStockDetail.getLeavenum());
            leaveStockDetail1.setLeaveId(leaveId);
            leaveStockDetail1.save();

            Deptstock deptstock = getModel(Deptstock.class);
            deptstock.setFileNumber(leaveStockDetail1.getFileNumber());
            deptstock.setDeviceClassName(leaveStockDetail1.getDeviceClassName());
            deptstock.setDeviceName(leaveStockDetail1.getDeviceName());
            deptstock.setNumber(leaveStockDetail1.getLeavenum());
            deptstock.save();
        }
        Ret ret=null;
        ret = Ret.ok("msg","保存成功！").set("code",0);
        renderJson(ret);
    }

    /*
     * 保存主表信息
     *
    * */
    public void saveHead(){
        LeaveStock leavestock= getBean(LeaveStock.class,"leavestock");
        Ret ret=null;
        if(leavestock.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0).set("leaveId",leavestock.getId());
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);

    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before({JSONInterceptor.class,Tx.class})
    public void updateDetail(){
        int leaveId=getParaToInt(0,0);
        List<LeaveStockDetail> leaveStockDetailList =JSONArray.parseArray(getAttr("json").toString(),LeaveStockDetail.class);
        for(LeaveStockDetail detail:leaveStockDetailList) {
          /*
          * 对出库明细表的数据保存
          * */
            LeaveStockDetail leaveStockDetail1 =getModel(LeaveStockDetail.class);
            leaveStockDetail1.setId(detail.getId());
            leaveStockDetail1.setDeviceClassName(detail.getDeviceClassName());
            leaveStockDetail1.setDeviceName(detail.getDeviceName());
            leaveStockDetail1.setFileNumber(detail.getFileNumber());
            leaveStockDetail1.setLeaveDate(detail.getLeaveDate());
            leaveStockDetail1.setLeavenum(detail.getLeavenum());
            leaveStockDetail1.setLeaveId(leaveId);
            leaveStockDetailService.update(leaveStockDetail1);

        }
        Ret ret=null;
        ret = Ret.ok("msg","保存成功！").set("code",0);
        renderJson(ret);
    }
    /*
    * 更新主表信息
    *
   * */
    public void updateHead(){
        LeaveStock leavestock= getBean(LeaveStock.class,"leavestock");
        Ret ret=null;
        if(leaveStockService.update(leavestock)){
            ret = Ret.ok("msg","保存成功！").set("code",0);
            //删除数据库明细where enterId= enterstock.getId()
            //leaveStockDetailService.deleteByLeaveId(leavestock.getId());
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);

    }
    /*
    * 更新
    * */
    public void update(){
        LeaveStock leavestock =getBean(LeaveStock.class,"leavestock");
        Ret ret;
        if(leaveStockService.update(leavestock)){
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
        if(leaveStockService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}
