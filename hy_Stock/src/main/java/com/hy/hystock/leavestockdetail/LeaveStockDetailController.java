package com.hy.hystock.leavestockdetail;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.LeaveStock;
import com.hy.common.model.LeaveStockDetail;
import com.hy.common.model.Stockpile;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.Map;

public class LeaveStockDetailController extends BaseController {
    LeaveStockDetailService leaveStockDetailService = LeaveStockDetailService.me;
    public void index(){
        Page<LeaveStockDetail> leaveStockDetailTreeData = leaveStockDetailService.leavestockdetailList(1, 1,"","id","desc");
        setAttr("leaveStockDetailTreeData",leaveStockDetailTreeData);
        render("leaveStockDetail.html");
    }

    /**
     *获取分页同时获取到数据
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new LeaveStockDetail(),paraMap);
        String sWhere=paraStr[0];
        Page<LeaveStockDetail> page= leaveStockDetailService.leavestockdetailList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<LeaveStockDetail> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("page",pageUtils);
        renderJson(ret);
    }
    public void layuigetList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new LeaveStockDetail(),paraMap);
        String sWhere=paraStr[0];
        Page<LeaveStockDetail> page= leaveStockDetailService.leavestockdetailList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<LeaveStockDetail> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();

        Ret ret = Ret.by("code",0).set("data",rows).set("count",total);
        renderJson(ret);
    }
    /**
     * 保存
     */
    public void doadd(){
        LeaveStockDetail leaveStockDetail= getBean(LeaveStockDetail.class,"leaveStockDetail");
        Ret ret=null;
        if(leaveStockDetail.save()){
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
        LeaveStockDetail leaveStockDetail= leaveStockDetailService.edit(getParaToInt(0,0));
        setAttr("leaveStockDetail",leaveStockDetail);
        render("edit.html");
    }
    /*
    * 更新
    * */
    public void update(){
        LeaveStockDetail leaveStockDetail =getBean(LeaveStockDetail.class,"leaveStockDetail");
        Ret ret;
        if(leaveStockDetailService.update(leaveStockDetail)){
            ret =Ret.ok("msg","更新成功").set("code",0);
        }else{
            ret=Ret.fail("msg","更新失败！").set("code",-1);
        }
        renderJson(ret);
    }
    //通过主表的ID获取明细
   public void getByLeaveId(){
        int leaveId=getParaToInt(0,0);
       List<Record> Lst = leaveStockDetailService.getByLeaveId(leaveId);
        Ret ret=Ret.by("data",Lst).set("code",0);
        renderJson(ret);
   }

    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(leaveStockDetailService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}
