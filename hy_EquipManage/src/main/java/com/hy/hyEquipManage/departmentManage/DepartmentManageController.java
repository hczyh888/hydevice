package com.hy.hyEquipManage.departmentManage;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Apply;
import com.hy.common.model.DepartmentManage;
import com.hy.common.model.Stockpile;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class DepartmentManageController extends BaseController {
    DepartmentManageService departmentManageService = DepartmentManageService.me;
    public void index(){
        Page<DepartmentManage> departmentManageTreeData =departmentManageService.departmentManageList(1, 1,"","id","desc");
        setAttr("departmentManageTreeData",departmentManageTreeData);
        render("departmentManage.html");
    }

    /**
     *获取分页同时获取到数据pageGrid数据格式
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new DepartmentManage(),paraMap);
        String sWhere=paraStr[0];
        Page<DepartmentManage> page= departmentManageService.departmentManageList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<DepartmentManage> rows = page.getList();
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
        Page<DepartmentManage> page= departmentManageService.departmentManageList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<DepartmentManage> rows = page.getList();
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
        DepartmentManage departmentManage= getBean(DepartmentManage.class,"departmentManage");
        Ret ret=null;
        if(departmentManage.save()){
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
        DepartmentManage departmentManage= departmentManageService.edit(getParaToInt(0,0));
        setAttr("departmentManage",departmentManage);
        render("edit.html");
    }
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(departmentManageService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
    /**
     * 查看
     */
    public void view(){
        DepartmentManage departmentManage = departmentManageService.getDepartmentManageAll (getParaToInt(0,0));
        setAttr("departmentManage",departmentManage);
        render("view.html");
    }

    /**
     * do报修
     */
    public void repair(){
        render("repair.html");
    }
    /**
     * do保养
     */
    public void maintain(){
        render("maintain.html");
    }
    /**
     * do设备计量与质控记录
     */
    public void quality(){
        render("quality.html");
    }
    /**
     * do不良事件
     */
    public void badthing(){
        render("badthing.html");
    }

}
