package com.hy.hyplan.planApply;

import com.alibaba.fastjson.JSONArray;
import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.*;
import com.hy.common.tools.Func;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.hyplan.applybenefit.ApplyBenefitService;
import com.hy.hyplan.applyinfor.ApplyInforService;
import com.hy.hyplan.applypartner.ApplyParterService;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

import java.util.List;
import java.util.Map;

import static com.hy.common.tools.PinyingUtil.getGBKpy;

public class ApplyController extends BaseController {

    ApplyService applyService = ApplyService.me;
    ApplyBenefitService applyBenefitService = ApplyBenefitService.me;
    ApplyInforService applyInforService = ApplyInforService.me;
    ApplyParterService applyParterService = ApplyParterService.me;
    public void index(){
        setAttr("ApplyStateEnum",getEnumRet("ApplyStateEnum").toJson());//获取单位列表传前端页面
        render("apply.html");
    }
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Apply(),paraMap);
        String sWhere=paraStr[0];
        String status = getPara(0);
        if(StrKit.notBlank(status)) sWhere += "and status='"+status+"'";
        Page<Apply> page= applyService.applyList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Apply>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
        Ret ret = Ret.by("code",0).set("count",count).set("data",rows);
        renderJson(ret);
    }
    public void add(){
        render("add.html");
    }
    public void edit(){
        setAttr("ApplypartnerEnum",getEnumRet("ApplypartnerEnum").toJson());//获取单位列表传前端页面
        Apply apply= applyService.edit(getParaToInt(0,0));
        setAttr("apply",apply);
        Applypartner applypartner= applyParterService.edit(getParaToInt(0,0));
        setAttr("applypartner",applypartner);
        Applyinfor applyinfor= applyInforService.edit(getParaToInt(0,0));
        setAttr("applyinfor",applyinfor);
        Applybenefit applybenefit= applyBenefitService.edit(getParaToInt(0,0));
        setAttr("applybenefit",applybenefit);

        render("edit.html");
    }
    public void update(){
        Apply apply =getBean(Apply.class,"apply");
        Applybenefit applybenefit =getBean(Applybenefit.class,"applybenefit");
        Applyinfor applyinfor =getBean(Applyinfor.class,"applyinfor");
        applyBenefitService.update(applybenefit);
        applyInforService.update(applyinfor);
        Ret ret;
        if(applyService.update(apply)){
            ret =Ret.ok("msg","更新成功").set("code",0);
        }else{
            ret=Ret.fail("msg","更新失败！").set("code",-1);
        }
        renderJson(ret);
    }
        /*
        * 上传文件
        * */
    public void upload() {
        UploadFile  file = getFile();
        String fileName = file.getFileName();
        String uploadPath = file.getUploadPath();
        Applyinfor applyinfor=new Applyinfor();
        applyinfor.setFileName(fileName);
        applyinfor.setPath(uploadPath);
        applyInforService.Save(applyinfor);
        if (!Func.isEmpty(file)){
            Ret ret = Ret.ok("msg","上传成功");
            ret.set("filePath", "/upload/" + file.getFileName());

            renderJson(ret);
        }else {
            renderJson(Ret.fail("msg", "上传失败"));

        }
    }
  /*
  * 工作进度
  * */
public void Progress(){
    Apply audit = applyService.getApplyAll (getParaToInt(0,0));
    setAttr("audit",audit);
    render("Progress.html");
}

public void Export(){
    Apply apply = applyService.getApplyAll (getParaToInt(0,0));
    setAttr("apply",apply);
    render("export.html");
}
/**
 * 保存
 */
public void doadd(){
        Ret ret=null;
         /*
        * 增加的保存
        * */
        Apply apply= getBean(Apply.class,"apply");
    boolean save = apply.save();
    Integer id = apply.getId();

    if(save) {
            ret = Ret.ok("msg", "成功").set("code", 0);

        }else{
            ret = Ret.fail("msg", "失败").set("code", -1);
        }
         /*
        * 经济效益的保存
        * */
       Applybenefit applybenefit = getBean(Applybenefit.class, "applybenefit");
        applybenefit.setApplyId(id);
        if(applybenefit.save()) {
            ret = Ret.ok("msg", "成功").set("code", 0);
        }else{
            ret = Ret.fail("msg", "失败").set("code", -1);
        }
        /*
        * 报批资料保存
        * */
        Applyinfor  applyinfor = getBean(Applyinfor.class, "applyinfor");

        applyinfor.setApplyId(id);
        if(applyinfor.save()) {
            ret = Ret.ok("msg", "成功").set("code", 0);
        }else{
            ret = Ret.fail("msg", "失败").set("code", -1);
        }
        /*
        * 合作厂商的保存
        * 1、在页面获取值
        * 2、遍历数组得到值并且封装成对象保存
        * */
       Applypartner applypartner = getBean(Applypartner.class, "applypartner");

        applypartner.setApplyId(id);
        if(applypartner.save()) {
            ret = Ret.ok("msg", "成功").set("code", 0);
        }else{
            ret = Ret.fail("msg", "失败").set("code", -1);
        }
        renderJson(ret);
    }
/**
 * 按rquestJson的类方式转化接收数据
 */
@Before(JSONInterceptor.class)
public void del(){
    RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
    int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
    if(applyService.deleteById(id)){
        renderJson(Ret.ok("msg","删除成功").set("code",0));
    }else{
        renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
    }
}

public void genSimpleCode(){
        String deviceName =getPara("deviceName");
        renderJson(Ret.by("code",0).set("simpleCode", getGBKpy(deviceName)));
    }

public void tabs() {
    int activeIndex = getParaToInt(0);
    switch (activeIndex) {
        case 0: {
            render("applydraft.html");
            break;
        }
        case 1:{
            render("applyWait.html");
            break;
        }
        case 2:{
            render("applyFail.html");
            break;
        }
        case 3:{
            render("applyIssue.html");
            break;
        }
        case 4:{
            render("applyArrive.html");
            break;
        }
        default:
            renderText("waiting...");
            break;
    }
}
}
