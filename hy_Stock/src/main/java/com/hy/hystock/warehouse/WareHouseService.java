package com.hy.hystock.warehouse;


import com.hy.common.model.Warehouse;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.List;

public class WareHouseService {

    public static final WareHouseService me = new WareHouseService();
    private final Warehouse dao = new Warehouse().dao();
    private final String allWareHouseCacheName = "allWareHouse";

    /**
     * 获取 apply 对象
     */
    public Warehouse getById(int warehouseId) {
        return dao.findById(warehouseId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Warehouse warehouse) {
        return warehouse.update();
    }
    /**
     * 根据ID删除数据
     * @param warehouseId
     * @return
     */
    public boolean deleteById(int warehouseId){
        return dao.deleteById(warehouseId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int warehouseId) {
        CacheKit.remove(allWareHouseCacheName, warehouseId);
    }

    public Warehouse edit(int warehouseId){
        return dao.findById(warehouseId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Warehouse> wareHouseList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("warehouse.select"),Db.getSql("warehouse.fromWhere")+" "+sWhere+sOrderBy);
    }

    /**
     * 获取库房类型下拉框数据
     */
    public List<Warehouse> getWareHouseType() {
        return  dao.find("select id ,type from stock_warehouse where pid=0 order by id");
    }
    /**
     * 获取库房下拉框数据
     */
    public List<Warehouse> getWareHouse(int pid) {
        return  dao.find("select id ,name text from stock_warehouse where pid="+pid+" order by id");
    }


}



