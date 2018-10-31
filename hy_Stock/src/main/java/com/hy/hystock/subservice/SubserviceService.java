package com.hy.hystock.subservice;


import com.hy.common.model.Subservice;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class SubserviceService {

    public static final SubserviceService me = new SubserviceService();
    private final Subservice dao=new Subservice().dao();
    private final String allSubserviceCacheName = "allSubservice";

    /**
     * 获取 apply 对象
     */
    public Subservice getById(int subserviceId) {
        return dao.findById(subserviceId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Subservice subservice) {
        return subservice.update();
    }
    /**
     * 根据ID删除数据
     * @param subserviceId
     * @return
     */
    public boolean deleteById(int subserviceId){
        return dao.deleteById(subserviceId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int subserviceId) {
        CacheKit.remove(allSubserviceCacheName, subserviceId);
    }

    public Subservice edit(int subserviceId){
        return dao.findFirst(Db.getSql("subservice.select")+Db.getSql("subservice.fromWhere")+" and id=?",subserviceId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Subservice> subserviceList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("subservice.select"),Db.getSql("subservice.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Subservice getSubserviceAll(int id){
        Subservice subservice= dao.findById(id);
        return subservice;
    }

}



