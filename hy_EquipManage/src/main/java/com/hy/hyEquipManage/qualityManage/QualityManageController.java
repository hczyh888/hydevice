package com.hy.hyEquipManage.qualityManage;

import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.QualityManage;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class QualityManageController extends BaseController {
    QualityManageService qualityManageService = QualityManageService.me;
    public void index(){
        Page<QualityManage> qualityManageTreeData = qualityManageService.qualityManageList(1, 1,"","id","desc");
        setAttr("qualityManageTreeData",qualityManageTreeData);
        render("qualityManage.html");
    }

    /**
     *
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new QualityManage(),paraMap);
        String sWhere=paraStr[0];
        Page<QualityManage> page= qualityManageService.qualityManageList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<QualityManage> rows = page.getList();
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
        QualityManage qualityManage= qualityManageService.edit(getParaToInt(0,0));

        setAttr("qualityManage",qualityManage);
        render("edit.html");
    }
    /**
     * 更新
     */
    public void update(){
        QualityManage qualityManage =getModel(QualityManage.class,"qualityManage");
        Ret ret;
        if(qualityManageService.update(qualityManage)){
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
        QualityManage qualityManage= getBean(QualityManage.class,"qualityManage");
        Ret ret=null;
        if(qualityManage.save()){
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
        QualityManage qualityManage = qualityManageService.edit(getParaToInt(0,0));
        setAttr("qualityManage",qualityManage);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(qualityManageService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}

