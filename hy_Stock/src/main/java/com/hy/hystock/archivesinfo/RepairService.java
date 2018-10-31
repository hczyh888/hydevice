package com.hy.hystock.archivesinfo;


import com.hy.common.model.Repair;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class RepairService {

    public static final RepairService me = new RepairService();
    private final Repair dao = new Repair().dao();
    private final String allRepairCacheName = "allRepair";

    /**
     * 获取 apply 对象
     */
    public Repair getById(int repairId) {
        return dao.findById(repairId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Repair repair) {
        return repair.update();
    }
    /**
     * 根据ID删除数据
     * @param repairId
     * @return
     */
    public boolean deleteById(int repairId){
        return dao.deleteById(repairId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int repairId) {
        CacheKit.remove(allRepairCacheName, repairId);
    }

    public Repair edit(int repairId){
        return dao.findFirst(Db.getSql("repair.select")+Db.getSql("repair.fromWhere")+" and id=?",repairId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Repair> repairList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("repair.select"),Db.getSql("repair.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Repair getRepairAll(int id){
        Repair  repair= dao.findById(id);
        return repair;
    }
}

