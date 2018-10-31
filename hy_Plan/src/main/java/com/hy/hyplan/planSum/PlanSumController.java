package com.hy.hyplan.planSum;


import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Apply;
import com.hy.common.model.PlanTask;
import com.hy.common.model.PlanTaskdetail;
import com.hy.common.tools.Func;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.hy.hyplan.planTask.PlanTaskService;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

import java.util.List;
import java.util.Map;

public class PlanSumController extends BaseController {
   PlanSumService planSumService = PlanSumService.me;
   PlanTaskService planTaskService=PlanTaskService.me;
    public void index(){
        Page<Apply> planSumTreeData = planSumService.planSumList(1, 1,"","id","desc");
        setAttr("planSumTreeData",planSumTreeData);
        render("planSum.html");
    }

    /**
     *获取分页信息
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new Apply(),paraMap);
        String sWhere=paraStr[0];
        String status = getPara(0);
        if(StrKit.notBlank(status)) sWhere += "and status>='"+status+"'";
        Page<Apply> page= planSumService.planSumList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<Apply>  rows = page.getList();
        Integer total = page.getTotalPage();
        Integer curPage = page.getPageNumber();
        Integer count = page.getTotalRow();
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
        Apply planSum= planSumService.edit(getParaToInt(0,0));
        setAttr("planSum",planSum);
        render("edit.html");
    }
    //由计划转变成任务

    public void update(){
        Ret ret;
        //先更新apply的状态，设apply.status=2(该计划已生成任务)
        Apply planSum =getModel(Apply.class,"planSum");
        planSum.setStatus("4");
        if(planSumService.update(planSum)) {
            //后生成任务单，入库planTask
            PlanTask planTask = getModel(PlanTask.class, "planTask");
            planTask.setStatus("2");//未下达

            if (planTask.save()) {
                ret = Ret.ok("msg", "生成任务成功").set("code", 0);
            } else {
                ret = Ret.fail("msg", "生成任务失败！").set("code", -1);
            }
        }else {
            ret = Ret.fail("msg", "保存失败！").set("code", -1);
        }
        renderJson(ret);
    }

    /**
     * 保存
     */
    public void doadd(){
       Apply planSum= getBean(Apply.class,"planSum");
        Ret ret=null;
       if(planSum.save()){
            ret = Ret.ok("msg","保存成功！").set("code",0);
        }else{
            ret = Ret.fail("msg","保存失败！").set("code",-1);
        }
    }
    /**
     * 查看
     */
    public void view(){
        Apply planSum = planSumService.getApplyAll (getParaToInt(0,0));
        setAttr("planSum",planSum);
        render("prePlan.html");
    }

    /**
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(planSumService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}
