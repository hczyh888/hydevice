package com.hy.hystock.enterstockdetail;


import com.hy.common.model.Enterstock;
import com.hy.common.model.EnterstockDetail;
import com.hy.common.model.Parameter;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

public class EnterStockDetailService {

    public static final EnterStockDetailService me = new EnterStockDetailService();
    private final EnterstockDetail dao = new EnterstockDetail().dao();
    private final String allEnterStockDetailCacheName = "allEnterStockDetail";

    /**
     * 获取 apply 对象
     */
    public EnterstockDetail getById(int enterStockDetailId) {
        return dao.findById(enterStockDetailId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(EnterstockDetail enterstockDetail) {
        return enterstockDetail.update();
    }
    /**
     * 根据ID删除数据
     * @param enterStockDetailId
     * @return
     */
    public boolean deleteById(int enterStockDetailId){
        return dao.deleteById(enterStockDetailId);
    }

    /**
     * 通过主表ID删除对应的明细记录
     * @param enterId
     * @return
     */
    public int deleteByEnterId(String enterId){
         return Db.delete("delete from stock_enterstock_detail where enterId ="+enterId);
    }
    /**
     * 根据 EnterstockId 值移除缓存
     */
    public void clearCache(int enterStockDetailId) {
        CacheKit.remove(allEnterStockDetailCacheName, enterStockDetailId);
    }

    public EnterstockDetail edit(int enterStockDetailId){
        return dao.findById(enterStockDetailId);
    }

    public List<Record> getByEnterId(int enterId){
        //拿对应的明细
        return  Db.find(Db.getSqlPara("enterstockdetail.selectByEnterId", Kv.by("enterId",enterId)));
    }
    /**
     * 返回分页的List数据
     * @return
     */
    public Page<EnterstockDetail> enterstockdetailList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("enterstockdetail.select"),Db.getSql("enterstockdetail.fromWhere")+" "+sWhere+sOrderBy);
    }


}



