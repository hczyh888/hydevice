package com.hy.hyplan.planSum;


import com.hy.common.model.Apply;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

public class PlanSumService {

    public static final PlanSumService me = new PlanSumService();
    private final Apply dao = new Apply().dao();
    private final String allPlanSumCacheName = "allPlanSum";

    /**
     * 获取 apply 对象
     */
    public Apply getById(int planSumId) {
        return dao.findById(planSumId);
    }
    /**
     * 修改 planSum 对象数据
     */
    public boolean update(Apply planSum) {
        return planSum.update();
    }
    /**
     * 根据ID删除数据
     * @param planSumId
     * @return
     */
    public boolean deleteById(int planSumId){
        return dao.deleteById(planSumId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int planSumId) {
        CacheKit.remove(allPlanSumCacheName, planSumId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Apply> planSumList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("apply.select"),Db.getSql("apply.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public Apply edit(int planSumId){
        return dao.findById(planSumId);
    }

    public Apply getApplyAll(int id){
        Apply apply = dao.findById(id);
        List<Record> planpartnerLst = Db.find("select * from plan_applypartner where applyId=?",id);
        Record applybenefit =  Db.findFirst("select * from plan_applybenefit where applyId=?",id);
        Record applyinfor =  Db.findFirst("select * from plan_applyinfor where applyId=?",id);
        apply.setApplyPartnerLst(planpartnerLst);
        apply.setApplybenefit(applybenefit);
        apply.setApplyinfor(applyinfor);
        return apply;
    }
}



