package com.hy.hystock.archivesinfo;

import com.hy.common.model.Stockpile;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

public class ArchivesInfoService {

    public static final ArchivesInfoService me = new ArchivesInfoService();
    private final Stockpile dao = new Stockpile().dao();
    private final String allArchivesInfoCacheName = "allArchivesInfo";

    /**
     * 获取 apply 对象
     */
    public Stockpile getById(int archivesInfoId) {
        return dao.findById(archivesInfoId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Stockpile archivesinfo) {
        return archivesinfo.update();
    }
    /**
     * 根据ID删除数据
     * @param archivesInfoId
     * @return
     */
    public boolean deleteById(int archivesInfoId){
        return dao.deleteById(archivesInfoId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int archivesInfoId) {
        CacheKit.remove(allArchivesInfoCacheName, archivesInfoId);
    }

    public Stockpile edit(int archivesInfoId){
        return dao.findFirst(Db.getSql("stockpile.select")+Db.getSql("stockpile.fromWhere")+" and id=?",archivesInfoId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Stockpile> archivesinfoList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("stockpile.select"),Db.getSql("stockpile.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Stockpile getArchivesInfoAll(int id){
        Stockpile  stockpile= dao.findById(id);
        Record stockpileSubinfor =  Db.findFirst("select * from stock_stockpile_subinfor where stockpileId=?",id);
        Record deviceclass =  Db.findFirst("select * from sys_deviceclass where id=?",id);
        Record devicelist =  Db.findFirst("select * from sys_devicelist where id=?",id);
        Record warehouse =  Db.findFirst("select * from stock_warehouse where id=?",id);
        Record enterstock =  Db.findFirst("select * from stock_enterstock where id=?",id);
        stockpile.setStockpileSubinfor(stockpileSubinfor);
        stockpile.setDeviceclass(deviceclass);
        stockpile.setDevicelist(devicelist);
        stockpile.setWarehouse(warehouse);
        stockpile.setEnterstock(enterstock);
        return stockpile;
    }
    public List<Stockpile> getExportDatas(){
        List<Stockpile> archivesinfo = dao.find(Db.getSql("stockpile.exportSql"));
        return archivesinfo;
    }

}



