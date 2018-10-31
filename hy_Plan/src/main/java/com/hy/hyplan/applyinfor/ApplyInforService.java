package com.hy.hyplan.applyinfor;


import com.hy.common.model.Apply;
import com.hy.common.model.Applyinfor;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class ApplyInforService {

    public static final ApplyInforService me = new ApplyInforService();
    private final Applyinfor dao = new Applyinfor().dao();
    private final String allApplyInforCacheName = "allApplyInfor";

    /**
     * 获取 User 对象
     */
    public Applyinfor getById(int applyinforId) {
        return dao.findById(applyinforId);
    }
    /**
     * 修改 User 对象数据
     */
    public boolean update(Applyinfor applyinforId) {
        return applyinforId.update();
    }
    /**
     * 根据ID删除数据
     * @param applyinforId
     * @return
     */
    public boolean deleteById(int applyinforId){
        return dao.deleteById(applyinforId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int applyinforId) {
        CacheKit.remove(allApplyInforCacheName, applyinforId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Applyinfor> applyInforList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("apply.select"),Db.getSql("apply.fromWhere")+" "+sWhere+sOrderBy);
    }

    /**
     * 保存
     * @param applyinfor
     * @return
     */
    public boolean Save(Applyinfor applyinfor){

        return  applyinfor.save();
    }

    public Applyinfor edit(int applyinforId){
        return dao.findById(applyinforId);
    }
}



