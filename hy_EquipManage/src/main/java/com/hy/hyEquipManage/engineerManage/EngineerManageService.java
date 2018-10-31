package com.hy.hyEquipManage.engineerManage;

import com.hy.common.model.EngineerManage;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class EngineerManageService {

    public static final EngineerManageService me = new EngineerManageService();
    private final EngineerManage dao = new EngineerManage().dao();
    private final String allEngineerManageCacheName = "allEngineerManage";

    /**
     * 获取 engineerManage 对象
     */
    public EngineerManage getById(int engineerManageId) {
        return dao.findById(engineerManageId);
    }
    /**
     * 修改 planTask 对象数据
     */
    public boolean update(EngineerManage engineerManage) {
        return engineerManage.update();
    }
    /**
     * 根据ID删除数据
     * @param engineerManageId
     * @return
     */
    public boolean deleteById(int engineerManageId){
        return dao.deleteById(engineerManageId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int engineerManageId) {
        CacheKit.remove(allEngineerManageCacheName, engineerManageId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<EngineerManage> engineerManageList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("planTask.select"),Db.getSql("planTask.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public EngineerManage edit(int planTaskId){
        return dao.findById(planTaskId);
    }

}



