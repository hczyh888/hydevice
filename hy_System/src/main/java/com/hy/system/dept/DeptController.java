package com.hy.system.dept;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Apply;
import com.hy.common.model.Dept;
import com.hy.common.model.base.BaseApply;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;
import java.util.Map;


    public class DeptController extends BaseController {
    DeptService deptService = DeptService.me;
    public void index(){
        render("dept.html");
    }

    /**
     *
     */
    public void getList(){

        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Dept() , paraMap);
        String sWhere=paraStr[0];
        String deptId = getPara("deptId");
        if(StrKit.notBlank(deptId)){
            sWhere = "and ( id="+deptId+" or pid="+deptId+")";
        }
        Page<Dept> page= deptService.deptList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Dept>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }

    public void add(){
        render("add.html");
        }

        public void getDept(){
            int pid = getParaToInt(0,0);
            renderJson(deptService.getDept(pid));
        }
    public void edit(){
        Dept dept= deptService.edit(getParaToInt(0,0));
        setAttr("dept",dept);
        render("edit.html");
        }
    public void update(){
        Dept dept =getBean(Dept.class,"dept");
        if(deptService.update(dept)){
            CacheKit.removeAll(DEPT_CACHE);
            renderJson(Ret.ok("msg","更新成功").set("code",0).set("datas",dept));
        }else{
            Ret ret=Ret.fail("msg","更新失败！").set("code",-1);
            renderJson(ret);
        }
        }
    public void doadd(){
        Dept dept= getBean(Dept.class,"dept");
        Ret ret=null;
        if(dept.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    public void view(){
        Dept dept = deptService.getById(getParaToInt(0,0));
        setAttr("dept",dept);
        render("view.html");
        }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(deptService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
}