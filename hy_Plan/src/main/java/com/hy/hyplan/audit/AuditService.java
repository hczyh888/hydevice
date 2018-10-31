package com.hy.hyplan.audit;


import com.hy.common.model.Apply;
import com.hy.common.model.Applybenefit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

public class AuditService {

    public static final AuditService me = new AuditService();
    private final Apply dao = new Apply().dao();
    private final String allAuditCacheName = "allAudit";

    /**
     * 获取 apply 对象
     */
    public Apply getById(int auditId) {
        return dao.findById(auditId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Apply audit) {
        return audit.update();
    }
    /**
     * 根据ID删除数据
     * @param auditId
     * @return
     */
    public boolean deleteById(int auditId){
        return dao.deleteById(auditId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int auditId) {
        CacheKit.remove(allAuditCacheName, auditId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Apply> auditList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("apply.select"),Db.getSql("apply.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public Apply edit(int auditId){
        return dao.findById(auditId);
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



