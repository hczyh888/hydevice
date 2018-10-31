package com.hy.system.roles;


import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Roles;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;
import java.util.Map;

public class RolesController extends BaseController {
    RolesService rolesService= RolesService.me;
    public void index(){
        render("roles.html");
    }

    public void getList(){

        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Roles() , paraMap);
        String sWhere=paraStr[0];
        Page<Roles> page= rolesService.rolesList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Roles>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }
    public void add(){render("add.html");}
    public void edit(){
        Roles roles=rolesService.edit(getParaToInt(0,0));
        setAttr("roles",roles);
        render("edit.html");
    }
    public void update(){
        Roles roles=getBean(Roles.class,"roles");
        if(rolesService.update(roles)){
            Ret ret =Ret.ok("msg","更新成功").set("code",0);
            ret.set("datas",roles);
            renderJson(ret);
        }else{
            Ret ret=Ret.fail("msg","更新失败！").set("code",-1);
            renderJson(ret);
        }
    }
    public void doadd(){
        Roles roles= getBean(Roles.class,"roles");
        Ret ret=null;
        if(roles.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    public void view(){
        Roles roles=rolesService.getById(getParaToInt(0,0));
        setAttr("roles",roles);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(rolesService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
    public void rolesAssign(){
        Roles roles = rolesService.getById(getParaToInt(0,0));
        setAttr("roles",roles);
        render("rolesAssign.html");
    }
    public void saveAuthority() {
        String ids = getPara("ids");
        String roleId = getPara("roleId");
        String[] id = ids.split(",");
        if (id.length <= 1) {
            CacheKit.removeAll(ROLES_CACHE);
            CacheKit.removeAll(MENU_CACHE);
            renderJson(Ret.ok("msg","设置成功"));
            return;
        }
        boolean temp = rolesService.saveAuthority(ids, roleId);
        if (temp) {
            CacheKit.removeAll(ROLES_CACHE);
            CacheKit.removeAll(MENU_CACHE);
            renderJson(Ret.ok("msg","设置成功").set("code",0));
        } else {
            renderJson(Ret.fail ("msg","设置失败").set("code",-1));
        }
    }
}
