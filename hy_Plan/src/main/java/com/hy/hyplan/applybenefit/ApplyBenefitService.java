package com.hy.hyplan.applybenefit;


import com.hy.common.model.Applybenefit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class ApplyBenefitService {

    public static final ApplyBenefitService me = new ApplyBenefitService();
    private final Applybenefit dao = new Applybenefit().dao();
    private final String allApplybenefitCacheName = "allApplybenefit";

    /**
     * 获取 User 对象
     */
    public Applybenefit getById(int applybenefitId) {
        return dao.findById(applybenefitId);
    }
    /**
     * 修改 User 对象数据
     */
    public boolean update(Applybenefit applybenefit) {
        return applybenefit.update();
    }
    /**
     * 根据ID删除数据
     * @param applybenefitId
     * @return
     */
    public boolean deleteById(int applybenefitId){
        return dao.deleteById(applybenefitId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int applybenefitId) {
        CacheKit.remove(allApplybenefitCacheName, applybenefitId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Applybenefit> applybenefitList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("applybenefit.select"),Db.getSql("applybenefit.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public Applybenefit edit(int applybenefitId){
        return dao.findById(applybenefitId);
    }
}



