package com.hy.hyplan.applybenefit;


import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Apply;
import com.hy.common.model.Applybenefit;
import com.hy.common.tools.Func;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

import java.util.List;
import java.util.Map;

//import com.hy.common.model.Apply;

public class ApplyBenefitController extends BaseController {
   ApplyBenefitService applyBenefitService = ApplyBenefitService.me;
    public void index(){
        Page<Applybenefit> applyTreeData = applyBenefitService.applybenefitList(1, 1,"","id","desc");
        setAttr("applyTreeData",applyTreeData);
        render("applybenefit.html");
    }



    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Apply(),paraMap);
        String sWhere=paraStr[0];
        Page<Applybenefit> page= applyBenefitService.applybenefitList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Applybenefit> rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        PageUtils pageUtils=new PageUtils(rows,count,10,curPage);
        Ret ret = Ret.by("code",0).set("page",pageUtils);
        renderJson(ret);
    }
    public void add(){
        render("add.html");
    }
    public void edit(){
        Applybenefit applybenefit= applyBenefitService.edit(getParaToInt(0,0));
        setAttr("applybenefit",applybenefit);
        render("edit.html");
    }
    public void update(){
        Applybenefit applybenefit =getBean(Applybenefit.class,"applybenefit");
        Ret ret;
        if(applyBenefitService.update(applybenefit)){
            ret =Ret.ok("msg","更新成功").set("code",0);
        }else{
            ret=Ret.fail("msg","更新失败！").set("code",-1);
        }
        renderJson(ret);
    }

    /**
     * 保存
     */
    public void doadd(){
        Ret ret=null;
        Applybenefit applybenefit= getBean(Applybenefit.class,"applybenefit");
        if(applybenefit.save()) {
            ret = Ret.ok("msg", "成功").set("code", 0);
        }else{
            ret = Ret.fail("msg", "失败").set("code", -1);
        }
        renderJson(ret);
    }
    public void view(){
        Applybenefit applybenefit = applyBenefitService.getById(getParaToInt(0,0));
        setAttr("applybenefit",applybenefit);
        render("view.html");
    }
    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(applyBenefitService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }
    public void upload() {
        UploadFile file = getFile();
        if (!Func.isEmpty(file)){
            Ret ret = Ret.ok("msg","上传成功");
            ret.set("filePath","/upload/"+file.getFileName());
            renderJson(ret);
        }else {
            renderJson(Ret.fail("msg", "上传失败"));

        }
    }
}
