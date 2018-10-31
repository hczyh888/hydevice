package com.hy.hystock.stockpile;


import com.hy.common.model.Roles;
import com.hy.common.model.Stockpile;
import com.hy.hystock.deptstock.DeptStockService;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

public class StockPileService {

    public static final StockPileService me = new StockPileService();
    private final Stockpile dao = new Stockpile().dao();
    private final String allStockPileCacheName = "allStockPile";

    /**
     * 获取 apply 对象
     */
    public Stockpile getById(int stockPileId) {
        return dao.findById(stockPileId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Stockpile stockpile) {
        return stockpile.update();
    }
    /**
     * 根据ID删除数据
     * @param stockPileId
     * @return
     */
    public boolean deleteById(int stockPileId){
        return dao.deleteById(stockPileId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int stockPileId) {
        CacheKit.remove(allStockPileCacheName, stockPileId);
    }

    public Stockpile edit(int stockPileId){
        return dao.findFirst(Db.getSql("stockpile.select")+Db.getSql("stockpile.fromWhere")+" and id=?",stockPileId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Stockpile> stockpileList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("stockpile.select"),Db.getSql("stockpile.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Stockpile getStockPileAll(int id){
        Stockpile  stockpile= dao.findById(id);
        return stockpile;
    }

}



