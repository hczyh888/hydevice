package com.hy.system.menu;

import com.hy.common.constant.ConstCache;
import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Dept;
import com.hy.common.model.Menu;
import com.hy.common.plugin.shiro.ShiroKit;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.TreeNode;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MenuController extends BaseController{
    MenuService menuService = MenuService.me;
    private List<TreeNode> nodeLst=new ArrayList<TreeNode>();
    private List<TreeNode> sideLst=new ArrayList<TreeNode>();
    public void index(){
        Page <Menu> userTreeData = menuService.menuList(1, 1,"","num"," desc ");
        setAttr("menuTreeData",userTreeData);
        render("menu.html");
    }

    /**
     *
     */
    public void getList(){

        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Menu() , paraMap);
        String sWhere=paraStr[0];
        String menuCode = getPara("code");
        if(StrKit.notBlank(menuCode)){
            sWhere = "and ( code='"+menuCode+"' or pcode='"+menuCode+"')";
        }
        Page<Menu> page= menuService.menuList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Menu>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        /*Ret ret = Ret.by("code",0).set("page",pageUtils);*/
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }
    public void add(){
        render("add.html");
    }
    public void edit(){
        Menu menu= menuService.edit(getParaToInt(0,0));
        setAttr("menu",menu);
        render("edit.html");
    }
    public void update(){
        Menu menu =getBean(Menu.class,"menu");
        if(menuService.update(menu)){
            CacheKit.removeAll(MENU_CACHE);
            renderJson(Ret.ok("msg","更新成功").set("code",0).set("datas",menu));
        }else{
            renderJson(Ret.fail("msg","更新失败！").set("code",-1));
        }
    }
    public void doadd(){
        Menu menu= getBean(Menu.class,"menu");
        Ret ret=null;
        if(menu.save()){
            CacheKit.removeAll(MENU_CACHE);
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    public void view(){
        Menu menu = menuService.getById(getParaToInt(0,0));
        setAttr("menu",menu);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(menuService.deleteById(id)){
            CacheKit.removeAll(MENU_CACHE);
            CacheKit.removeAll(ROLES_CACHE);
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }


    public void getMenuSide(){
        String MENU_CACHE = ConstCache.MENU_CACHE;
        final String roleId =ShiroKit.getUser().getRoles();
        String userId = ShiroKit.getUser().getId().toString();
        List<Record> sideBar = CacheKit.get(MENU_CACHE, "sideBar_" + userId, new IDataLoader() {
            public Object load() {
                return Db.find(Db.getSqlPara("menu.getMenuSide", Kv.by("roleId",roleId)));
            }
        });
        for(Record menu :sideBar){
            TreeNode treeNode = new TreeNode();
            treeNode.setId(menu.getStr("code"));
            treeNode.setParentId(menu.getStr("pcode"));
            treeNode.setName(menu.getStr("name"));
            treeNode.setType(menu.getInt("menuType"));
            treeNode.setUrl(menu.getStr("url"));
            treeNode.setIcon(menu.getStr("icon"));
            nodeLst.add(treeNode);
        }
        new TreeNode().buildNodes(nodeLst);
        for(TreeNode treeNode:nodeLst){
            if("0".equals(treeNode.getParentId())){
                sideLst.add(treeNode);
            }
        }
        renderJson(Ret.by("code",0).set("menuList",sideLst));
    }

    /**
     * 导航菜单
     */
    public void nav(){
        List<Record> userMenuList = menuService.getUserMenuList(Long.valueOf(ShiroKit.getUser().getId().toString()));
        renderJson(Ret.by("code",0).set("menuList",userMenuList));
    }

}
