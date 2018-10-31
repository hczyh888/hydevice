package com.hy.hyEquipManage.qualityManage;

import com.hy.common.model.QualityManage;
import com.hy.common.model.RepairManage;
import com.hy.hyEquipManage.repairManage.RepairManageService;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class QualityManageService {

    public static final QualityManageService me = new QualityManageService();
    private final QualityManage dao = new QualityManage().dao();
    private final String allQualityManageCacheName = "allQualityManage";

    /**
     * 获取 qualityManage 对象
     */
    public QualityManage getById(int qualityManageId) {
        return dao.findById(qualityManageId);
    }
    /**
     * 修改 planTask 对象数据
     */
    public boolean update(QualityManage qualityManage) {
        return qualityManage.update();
    }
    /**
     * 根据ID删除数据
     * @param qualityManageId
     * @return
     */
    public boolean deleteById(int qualityManageId){
        return dao.deleteById(qualityManageId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int qualityManageId) {
        CacheKit.remove(allQualityManageCacheName, qualityManageId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<QualityManage> qualityManageList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("planTask.select"),Db.getSql("planTask.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public QualityManage edit(int planTaskId){
        return dao.findById(planTaskId);
    }

}




