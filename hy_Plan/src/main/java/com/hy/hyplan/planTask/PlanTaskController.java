package com.hy.hyplan.planTask;


import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.JSONInterceptor;
import com.hy.common.model.Apply;
import com.hy.common.model.PlanTask;
import com.hy.common.model.PurchaseTask;
import com.hy.common.tools.Query;
import com.hy.common.tools.RequestJson;
import com.hy.common.tools.grid.PageUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;
import java.util.Map;

public class PlanTaskController extends BaseController {
   PlanTaskService planTaskService = PlanTaskService.me;
    public void index(){
        Page<PlanTask> planTaskTreeData = planTaskService.planTaskList(1, 1,"","id","desc");
        setAttr("planTaskTreeData",planTaskTreeData);
        render("planTask.html");
    }

    /**
     *
     */
    public void getList(){
        Map<String,String[]> paraMap = getParaMap();
        Query query = new Query(paraMap);
        String[] paraStr = Query.makePara(new PlanTask(),paraMap);
        String sWhere=paraStr[0];
        Page<PlanTask> page= planTaskService.planTaskList(query.getPage(),query.getLimit(),sWhere,query.getSidx(),query.getOrder());

        List<PlanTask>  rows = page.getList();
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
        PlanTask planTask= planTaskService.edit(getParaToInt(0,0));

        setAttr("planTask",planTask);
        render("edit.html");
    }
    /**
     * 更新
     */
    public void update(){
        PlanTask planTask =getModel(PlanTask.class,"planTask");
        Ret ret;
        if(planTaskService.update(planTask)){
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
        PlanTask planTask= getBean(PlanTask.class,"planTask");
        Ret ret=null;
        if(planTask.save()){
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
        PlanTask planTask = planTaskService.edit(getParaToInt(0,0));
        setAttr("planTask",planTask);
        render("view.html");
    }
    //任务TO采购任务
    public void taskToPur(){
        Ret ret;
        //先更新apply的状态，设apply.status=2(该计划已生成任务)
        PlanTask planTask =getModel(PlanTask.class,"planTask");
        String id = getPara(0);
        Integer integer = Integer.valueOf(id);
        PlanTask planTask1 = planTaskService.getById(integer);
        planTask.setId(integer);
        planTask.setStatus("1");
        if(planTask.update()) {
            //后生成任务单，入库planTask
            PurchaseTask purchaseTask = getModel(PurchaseTask.class, "purchaseTask");
            purchaseTask.setTaskName(planTask1.getTaskName());
            purchaseTask.setTaskType(planTask1.getTaskType());
            purchaseTask.setSumBuget(planTask1.getPlanBuget());
            purchaseTask.setTaskNum(planTask1.getPlanNum());
            purchaseTask.setTime(planTask1.getTaskTime());
            purchaseTask.setStatus(1);//状态为默认为未审核
            if (purchaseTask.save()) {
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
     * 按rquestJson的类方式转化接收数据
     */
    @Before(JSONInterceptor.class)
    public void del(){
        RequestJson requestJson =  FastJson.getJson().parse(getAttr("json").toString(),RequestJson.class);
        int id=requestJson.getDataArray().length==1?Integer.parseInt(requestJson.getDataArray()[0]):0;
        if(planTaskService.deleteById(id)){
            renderJson(Ret.ok("msg","删除成功").set("code",0));
        }else{
            renderJson(Ret.fail("msg","删除失败！id="+id).set("code",-1));
        }
    }

}
