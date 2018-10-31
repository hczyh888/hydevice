package com.hy.hyEquipManage.engineerManage;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.EngineerManage;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.Query;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class EngineerManageController extends BaseController {
    EngineerManageService engineerManageService = EngineerManageService.me;
    public void index(){
        Page<EngineerManage> engineerManageTreeData = engineerManageService.engineerManageList(1, 1,"","id","desc");
        setAttr("engineerManageTreeData",engineerManageTreeData);
        render("engineerManage.html");
    }

    /**
     *
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new EngineerManage(),paraMap);
        String sWhere=paraStr[0];
        Page<EngineerManage> page= engineerManageService.engineerManageList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<EngineerManage> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("page",pageUtils);
        renderJson(ret);
    }
    /**
     * 添加
     */
    public void add(){
        render("add.html");
    }
    /**
     * 编辑
     */
    public void edit(){
        EngineerManage engineerManage= engineerManageService.edit(getParaToInt(0,0));

        setAttr("engineerManage",engineerManage);
        render("edit.html");
    }
    /**
     * 更新
     */
    public void update(){
        EngineerManage engineerManage =getModel(EngineerManage.class,"engineerManage");
        Ret ret;
        if(engineerManageService.update(engineerManage)){
            ret =Ret.ok("msg","保存成功").set("code",0);
        }else{
            ret=Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }

    /**
     * 保存
     */
    public void doadd(){
        EngineerManage engineerManage= getBean(EngineerManage.class,"engineerManage");
        Ret ret=null;
        if(engineerManage.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
        renderJson(ret);
    }
    /**
     * 查看
     */
    public void view(){
        EngineerManage engineerManage = engineerManageService.edit(getParaToInt(0,0));
        setAttr("engineerManage",engineerManage);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(engineerManageService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}
