package com.hy.purchase.purchaseSum;

import com.hy.common.model.PurchaseTaskDetail;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class PurchaseSumService {

    public static final PurchaseSumService me = new PurchaseSumService();
    private final PurchaseTaskDetail dao = new PurchaseTaskDetail().dao();
    private final String allAuditCacheName = "allAudit";

    /**
     * 获取 purchaseTaskDetail 对象
     */
    public PurchaseTaskDetail getById(int purchaseTaskDetailId) {
        return dao.findById(purchaseTaskDetailId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(PurchaseTaskDetail purchaseTaskDetail) {
        return purchaseTaskDetail.update();
    }
    /**
     * 根据ID删除数据
     * @param purchaseTaskDetailId
     * @return
     */
    public boolean deleteById(int purchaseTaskDetailId){
        return dao.deleteById(purchaseTaskDetailId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int purchaseTaskDetailId) {
        CacheKit.remove(allAuditCacheName, purchaseTaskDetailId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<PurchaseTaskDetail> purchaseTaskDetailList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("purchaseTaskDetail.select"),Db.getSql("purchaseTaskDetail.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public PurchaseTaskDetail edit(int purchaseTaskDetailId){
        return dao.findById(purchaseTaskDetailId);
    }

    public PurchaseTaskDetail getPurchaseTaskDetailAll(int id){
        PurchaseTaskDetail purchaseTaskDetail = dao.findById(id);
        return purchaseTaskDetail;
    }
}

