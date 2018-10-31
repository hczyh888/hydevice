package com.hy.system.parameter;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.interceptor.TokenInterceptor;
import com.hy.common.kit.DateTimeKit;
import com.hy.common.model.Parameter;
import com.hy.common.model.User;
import com.hy.common.plugin.shiro.ShiroKit;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.hy.system.user.UserValidator;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;
import java.util.Map;


public class ParameterController extends BaseController{
    ParameterService parameterService = ParameterService.me;
    public void index(){
        Page <Parameter> parameterTreeData = parameterService.parameterList(1, 1,"","id"," desc ");
        setAttr("parameterTreeData",parameterTreeData);
        render("parameter.html");
    }

    /**
     *
     */
    public void getList(){

        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Parameter() , paraMap);
        String sWhere=paraStr[0];
        Page<Parameter> page= parameterService.parameterList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Parameter>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("page",pageUtils);
        renderJson(ret);
    }

   @Before(TokenInterceptor.class)
    public void add(){
        render("add.html");
    }
    public void edit(){
        Parameter parameter= parameterService.edit(getParaToInt(0,0));
        setAttr("parameter",parameter);
        render("edit.html");
    }
    public void update(){
        Parameter user =getBean(Parameter.class,"parameter");
        Ret ret;
        if(parameterService.update(user)){
             ret =Ret.ok("msg","更新成功").set("code",0);
        }else{
            ret=Ret.fail("msg","更新失败！").set("code",-1);
        }
        renderJson(ret);
    }

    /**
     * 保存
     */
    @Before({UserValidator.class})
    public void doadd(){
        Parameter parameter= getBean(Parameter.class,"parameter");
        Ret ret=null;
        if(parameter.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    public void view(){
        Parameter parameter = parameterService.getById(getParaToInt(0,0));
        setAttr("parameter",parameter);
        render("view.html");
    }

    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(parameterService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
}
