package com.hy.hystock.servicerecord;

import com.hy.common.model.Servicerecord;
import com.hy.common.model.Subservice;
import com.hy.hystock.subservice.SubserviceService;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class ServicerecordService {

    public static final ServicerecordService me = new ServicerecordService();
    private final Servicerecord dao=new Servicerecord().dao();
    private final String allServicerecordCacheName = "allServicerecord";

    /**
     * 获取 apply 对象
     */
    public Servicerecord getById(int servicerecordId) {
        return dao.findById(servicerecordId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Servicerecord servicerecord) {
        return servicerecord.update();
    }
    /**
     * 根据ID删除数据
     * @param servicerecordId
     * @return
     */
    public boolean deleteById(int servicerecordId){
        return dao.deleteById(servicerecordId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int servicerecordId) {
        CacheKit.remove(allServicerecordCacheName, servicerecordId);
    }

    public Servicerecord edit(int servicerecordId){
        return dao.findFirst(Db.getSql("servicerecord.select")+Db.getSql("servicerecord.fromWhere")+" and id=?",servicerecordId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Servicerecord> servicerecordList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("servicerecord.select"),Db.getSql("servicerecord.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Servicerecord getServicerecordAll(int id){
        Servicerecord servicerecord= dao.findById(id);
        return servicerecord;
    }

}




