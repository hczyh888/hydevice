package com.hy.hystock.stockpileSubinfor;

import com.hy.common.model.Stockpilesubinfor;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class StockpileSubinforService {
    public static final com.hy.hystock.stockpile.StockpileSubinforService me = new com.hy.hystock.stockpile.StockpileSubinforService();
    private final Stockpilesubinfor dao = new Stockpilesubinfor().dao();
    private final String allStockpileSubinforCacheName = "allStockpileSubinfor";

    /**
     * 获取 apply 对象
     */
    public Stockpilesubinfor getById(int stockpilesubinforId) {
        return dao.findById(stockpilesubinforId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Stockpilesubinfor stockpilesubinfor) {
        return stockpilesubinfor.update();
    }
    /**
     * 根据ID删除数据
     * @param stockpilesubinforId
     * @return
     */
    public boolean deleteById(int stockpilesubinforId){
        return dao.deleteById(stockpilesubinforId);
    }
    /**
     * 根据 stockpilesubinforId 值移除缓存
     */
    public void clearCache(int stockpilesubinforId) { CacheKit.remove(allStockpileSubinforCacheName, stockpilesubinforId); }

    public Stockpilesubinfor edit(int stockpilesubinforId){
        return dao.findById(stockpilesubinforId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Stockpilesubinfor> stockpilesubinforList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("stockpilesubinfor.select"),Db.getSql("stockpilesubinfor.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Stockpilesubinfor getStockPileAll(int id){
        Stockpilesubinfor  stockpilesubinfor= dao.findById(id);
        return stockpilesubinfor;
    }

}



