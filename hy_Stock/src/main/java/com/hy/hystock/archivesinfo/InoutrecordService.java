package com.hy.hystock.archivesinfo;

import com.hy.common.model.Inoutrecord;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class InoutrecordService {

    public static final InoutrecordService me = new InoutrecordService();
    private final Inoutrecord dao = new Inoutrecord().dao();
    private final String allInoutrecordCacheName = "allInoutrecord";

    /**
     * 获取 apply 对象
     */
    public Inoutrecord getById(int inoutrecordId) {
        return dao.findById(inoutrecordId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Inoutrecord inoutrecord) {
        return inoutrecord.update();
    }
    /**
     * 根据ID删除数据
     * @param inoutrecordId
     * @return
     */
    public boolean deleteById(int inoutrecordId){
        return dao.deleteById(inoutrecordId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int inoutrecordId) {
        CacheKit.remove(allInoutrecordCacheName, inoutrecordId);
    }

    public Inoutrecord edit(int inoutrecordId){
        return dao.findFirst(Db.getSql("inoutrecord.select")+Db.getSql("inoutrecord.fromWhere")+" and id=?",inoutrecordId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Inoutrecord> inoutrecordList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("inoutrecord.select"),Db.getSql("inoutrecord.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Inoutrecord getInoutrecordAll(int id){
        Inoutrecord inoutrecord= dao.findById(id);
        return inoutrecord;
    }
}


