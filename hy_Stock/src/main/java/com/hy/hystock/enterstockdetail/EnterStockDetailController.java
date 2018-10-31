package com.hy.hystock.enterstockdetail;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Enterstock;
import com.hy.common.model.EnterstockDetail;
import com.hy.common.model.LeaveStockDetail;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.hy.system.parameter.ParameterService;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.Map;

public class EnterStockDetailController extends BaseController {
    EnterStockDetailService enterstockDetailService = EnterStockDetailService.me;
    ParameterService parameterService=  ParameterService.me;
    Enterstock enterstock=null;
    public void index(){
        enterstockDetailService.enterstockdetailList(1, 1,"","id","desc");
        render("enterstock.html");
    }
    /**
     *获取分页同时获取到数据
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new EnterstockDetail(),paraMap);
        String sWhere=paraStr[0];

        String sss=getPara("copyall");
        if (StrKit.notBlank()){
            sWhere +="and status="+sss;
        }
        Page<EnterstockDetail> page=enterstockDetailService.enterstockdetailList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<EnterstockDetail> rows = page.getList();
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
        String[] paraStr = Query.makePara(new EnterstockDetail(),paraMap);
        String sWhere=paraStr[0];
        Page<EnterstockDetail> page= enterstockDetailService.enterstockdetailList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<EnterstockDetail> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();

        Ret ret = Ret.by("code",0).set("data",rows).set("count",total);
        renderJson(ret);
    }
    /*
    * 编辑
    * */
    public void edit(){
        EnterstockDetail enterstockDetail= enterstockDetailService.edit(getParaToInt(0,0));
        setAttr("enterstockDetail",enterstockDetail);
        render("edit.html");
    }
    /*
    * 更新
    * */
    public void update(){
        EnterstockDetail enterstockDetail =getBean(EnterstockDetail.class,"enterStockDetail");
        Ret ret;
        if(enterstockDetailService.update(enterstockDetail)){
            ret =Ret.ok("msg","更新成功").set("code",0);
        }else{
            ret=Ret.fail("msg","更新失败！").set("code",-1);
        }


        renderJson(ret);
    }
    //通过主表的ID获取明细
    public void getByEnterId(){
        int enterId=getParaToInt(0,0);
        List<Record> Lst = enterstockDetailService.getByEnterId(enterId);
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
        if(enterstockDetailService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}

