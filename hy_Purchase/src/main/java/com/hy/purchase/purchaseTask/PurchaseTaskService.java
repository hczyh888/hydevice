package com.hy.purchase.purchaseTask;

import com.hy.common.model.Devicelist;
import com.hy.common.model.PurchaseTask;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class PurchaseTaskService {

    public static final PurchaseTaskService me = new PurchaseTaskService();
    private final PurchaseTask dao = new PurchaseTask().dao();
    private final String allAuditCacheName = "allAudit";

    /**
     * 获取 purchaseTask 对象
     */
    public PurchaseTask getById(int purchaseTaskId) {
        return dao.findById(purchaseTaskId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(PurchaseTask purchaseTask) {
        return purchaseTask.update();
    }
    /**
     * 根据ID删除数据
     * @param purchaseTaskId
     * @return
     */
    public boolean deleteById(int purchaseTaskId){
        return dao.deleteById(purchaseTaskId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int purchaseTaskId) {
        CacheKit.remove(allAuditCacheName, purchaseTaskId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<PurchaseTask> purchaseTaskList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("purchaseTask.select"),Db.getSql("purchaseTask.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public PurchaseTask edit(int purchaseTaskId){
        return dao.findById(purchaseTaskId);
    }

    public PurchaseTask getPurchaseTaskAll(int id){
        PurchaseTask purchaseTask = dao.findById(id);
        return purchaseTask;
    }
}

