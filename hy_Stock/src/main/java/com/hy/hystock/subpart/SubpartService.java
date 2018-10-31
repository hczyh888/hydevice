package com.hy.hystock.subpart;

import com.hy.common.model.Subpart;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class SubpartService {

    public static final SubpartService me = new SubpartService();
    private final Subpart dao=new Subpart().dao();
    private final String allSubpartCacheName = "allSubpart";

    /**
     * 获取 apply 对象
     */
    public Subpart getById(int subpartId) {
        return dao.findById(subpartId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Subpart subpart) {
        return subpart.update();
    }
    /**
     * 根据ID删除数据
     * @param subpartId
     * @return
     */
    public boolean deleteById(int subpartId){
        return dao.deleteById(subpartId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int subpartId) {
        CacheKit.remove(allSubpartCacheName, subpartId);
    }

    public Subpart edit(int subpartId){
        return dao.findFirst(Db.getSql("subpartId.select")+Db.getSql("subpartId.fromWhere")+" and id=?",subpartId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Subpart> subpartList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("subpart.select"),Db.getSql("subpart.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Subpart getSubpartAll(int id){
        Subpart subpart= dao.findById(id);
        return subpart;
    }

}



