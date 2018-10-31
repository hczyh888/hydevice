package com.hy.hyplan.applypartner;


import com.hy.common.model.Applyinfor;
import com.hy.common.model.Applypartner;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class ApplyParterService {

    public static final ApplyParterService me = new ApplyParterService();
    private final Applypartner dao = new Applypartner().dao();
    private final String allApplyPartnerCacheName = "allApplyPartner";

    /**
     * 获取 Applypartner 对象
     */
    public Applypartner getById(int applyparnerId) {
        return dao.findById(applyparnerId);
    }
    /**
     * 修改 Applypartner 对象数据
     */
    public boolean update(Applypartner applyparnerId) {
        return applyparnerId.update();
    }
    /**
     * 根据ID删除数据
     * @param applyparnerId
     * @return
     */
    public boolean deleteById(int applyparnerId){
        return dao.deleteById(applyparnerId);
    }
    /**
     * 根据 ApplypartnerId 值移除缓存
     */
    public void clearCache(int applyparnerId) {
        CacheKit.remove(allApplyPartnerCacheName, applyparnerId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Applypartner> applyInforList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("applypartner.select"),Db.getSql("applypartner.fromWhere")+" "+sWhere+sOrderBy);
    }

    /**
     * 保存
     * @param applyinfor
     * @return
     */
    public boolean Save(Applyinfor applyinfor){

        return  applyinfor.save();
    }

    public Applypartner edit(int applyinforId){
        return dao.findById(applyinforId);
    }
}



