package com.hy.hyEquipManage.maintainManage;

import com.hy.common.model.MaintainManage;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class MaintainManageService {

    public static final MaintainManageService me = new MaintainManageService();
    private final MaintainManage dao = new MaintainManage().dao();
    private final String allMaintainManageCacheName = "allMaintainManage";

    /**
     * 获取 maintainManage 对象
     */
    public MaintainManage getById(int maintainManageId) {
        return dao.findById(maintainManageId);
    }
    /**
     * 修改 planTask 对象数据
     */
    public boolean update(MaintainManage maintainManage) {
        return maintainManage.update();
    }
    /**
     * 根据ID删除数据
     * @param maintainManageId
     * @return
     */
    public boolean deleteById(int maintainManageId){
        return dao.deleteById(maintainManageId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int maintainManageId) {
        CacheKit.remove(allMaintainManageCacheName, maintainManageId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<MaintainManage> maintainManageList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("planTask.select"),Db.getSql("planTask.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public MaintainManage edit(int planTaskId){
        return dao.findById(planTaskId);
    }

}




