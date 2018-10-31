package com.hy.hyplan.planTask;


import com.hy.common.model.Apply;
import com.hy.common.model.PlanTask;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

public class PlanTaskService {

    public static final PlanTaskService me = new PlanTaskService();
    private final PlanTask dao = new PlanTask().dao();
    private final String allPlanTaskCacheName = "allPlanTask";

    /**
     * 获取 PlanTask 对象
     */
    public PlanTask getById(int planTaskId) {
        return dao.findById(planTaskId);
    }
    /**
     * 修改 planTask 对象数据
     */
    public boolean update(PlanTask planTask) {
        return planTask.update();
    }
    /**
     * 根据ID删除数据
     * @param planTaskId
     * @return
     */
    public boolean deleteById(int planTaskId){
        return dao.deleteById(planTaskId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int planTaskId) {
        CacheKit.remove(allPlanTaskCacheName, planTaskId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<PlanTask> planTaskList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("planTask.select"),Db.getSql("planTask.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public PlanTask edit(int planTaskId){
        return dao.findById(planTaskId);
    }

}



