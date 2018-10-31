package com.hy.purchase.purchaseAudit;

import com.hy.common.model.PurchaseTask;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class PurchaseAuditService {

    public static final PurchaseAuditService me = new PurchaseAuditService();
    private final PurchaseTask dao = new PurchaseTask().dao();
    private final String allAuditCacheName = "allAudit";

    /**
     * 获取 purchaseTask 对象
     */
    public PurchaseTask getById(int purchaseAuditId) {
        return dao.findById(purchaseAuditId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(PurchaseTask purchaseAudit) {
        return purchaseAudit.update();
    }
    /**
     * 根据ID删除数据
     * @param purchaseAuditId
     * @return
     */
    public boolean deleteById(int purchaseAuditId){
        return dao.deleteById(purchaseAuditId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int purchaseAuditId) {
        CacheKit.remove(allAuditCacheName, purchaseAuditId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<PurchaseTask> purchaseAuditList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("purchaseTask.select"),Db.getSql("purchaseTask.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public PurchaseTask edit(int purchaseAuditId){
        return dao.findById(purchaseAuditId);
    }

    public PurchaseTask getPurchaseTaskAll(int id){
        PurchaseTask purchaseAudit = dao.findById(id);
        return purchaseAudit;
    }
}


