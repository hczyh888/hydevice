package com.hy.hyEquipManage.dispatchManage;

import com.hy.common.model.DispatchManage;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class DispatchManageService {

    public static final DispatchManageService me = new DispatchManageService();
    private final DispatchManage dao = new DispatchManage().dao();
    private final String allDispatchManageCacheName = "allDispatchManage";

    /**
     * 获取 dispatchManage 对象
     */
    public DispatchManage getById(int dispatchManageId) {
        return dao.findById(dispatchManageId);
    }
    /**
     * 修改 planTask 对象数据
     */
    public boolean update(DispatchManage dispatchManage) {
        return dispatchManage.update();
    }
    /**
     * 根据ID删除数据
     * @param dispatchManageId
     * @return
     */
    public boolean deleteById(int dispatchManageId){
        return dao.deleteById(dispatchManageId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int dispatchManageId) {
        CacheKit.remove(allDispatchManageCacheName, dispatchManageId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<DispatchManage> dispatchManageList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("planTask.select"),Db.getSql("planTask.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public DispatchManage edit(int planTaskId){
        return dao.findById(planTaskId);
    }

}



