package com.hy.hystock.archivesinfo;

import com.hy.common.model.Upkeep;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class UpkeepService {

    public static final UpkeepService me = new UpkeepService();
    private final Upkeep dao = new Upkeep().dao();
    private final String allArchivesInfoCacheName = "allArchivesInfo";

    /**
     * 获取 apply 对象
     */
    public Upkeep getById(int upkeepId) {
        return dao.findById(upkeepId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Upkeep upkeep) {
        return upkeep.update();
    }
    /**
     * 根据ID删除数据
     * @param upkeepId
     * @return
     */
    public boolean deleteById(int upkeepId){
        return dao.deleteById(upkeepId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int upkeepId) {
        CacheKit.remove(allArchivesInfoCacheName, upkeepId);
    }

    public Upkeep edit(int upkeepId){
        return dao.findFirst(Db.getSql("upkeep.select")+Db.getSql("upkeep.fromWhere")+" and id=?",upkeepId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Upkeep> upkeepList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("upkeep.select"),Db.getSql("upkeep.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Upkeep getUpkeepAll(int id){
        Upkeep upkeep= dao.findById(id);
        return upkeep;
    }
}

