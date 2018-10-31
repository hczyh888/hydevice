package com.hy.hystock.stockpile;

import com.hy.common.model.Stockpile;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class FixedDevicesService {

    public static final FixedDevicesService me = new FixedDevicesService();
    private final Stockpile dao = new Stockpile().dao();
    private final String allFixedDevicesCacheName = "allFixedDevices";

    /**
     * 获取 apply 对象
     */
    public Stockpile getById(int fixedDevicesId) {
        return dao.findById(fixedDevicesId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Stockpile fixeddevices) {
        return fixeddevices.update();
    }
    /**
     * 根据ID删除数据
     * @param fixedDevicesId
     * @return
     */
    public boolean deleteById(int fixedDevicesId){
        return dao.deleteById(fixedDevicesId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int fixedDevicesId) {
        CacheKit.remove(allFixedDevicesCacheName, fixedDevicesId);
    }

    public Stockpile edit(int fixedDevicesId){
        return dao.findFirst(Db.getSql("stockpile.select")+Db.getSql("stockpile.fromWhere")+" and id=?",fixedDevicesId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Stockpile> fixeddevicesList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("stockpile.select"),Db.getSql("stockpile.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Stockpile getFixedDevicesAll(int id){
        Stockpile  fixeddevices= dao.findById(id);
        return fixeddevices;
    }

}