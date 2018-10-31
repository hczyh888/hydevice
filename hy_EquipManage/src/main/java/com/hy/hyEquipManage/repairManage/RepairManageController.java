package com.hy.hyEquipManage.repairManage;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.RepairManage;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class RepairManageController extends BaseController {
    RepairManageService repairManageService = RepairManageService.me;
    public void index(){
        Page<RepairManage> repairManageTreeData = repairManageService.repairManageList(1, 1,"","id","desc");
        setAttr("repairManageTreeData",repairManageTreeData);
        render("repairManage.html");
    }

    /**
     *
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new RepairManage(),paraMap);
        String sWhere=paraStr[0];
        Page<RepairManage> page= repairManageService.repairManageList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<RepairManage> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
       /* PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("page",pageUtils);*/
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
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
        RepairManage repairManage= repairManageService.edit(getParaToInt(0,0));

        setAttr("repairManage",repairManage);
        render("edit.html");
    }
    /**
     * 更新
     */
    public void update(){
        RepairManage repairManage =getModel(RepairManage.class,"repairManage");
        Ret ret;
        if(repairManageService.update(repairManage)){
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
        RepairManage repairManage= getBean(RepairManage.class,"repairManage");
        Ret ret=null;
        if(repairManage.save()){
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
        RepairManage repairManage = repairManageService.edit(getParaToInt(0,0));
        setAttr("repairManage",repairManage);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(repairManageService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}

