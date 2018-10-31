package com.hy.hyEquipManage.badManage;

import com.hy.common.model.BadManage;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class BadManageService {

    public static final BadManageService me = new BadManageService();
    private final BadManage dao = new BadManage().dao();
    private final String allBadManageCacheName = "allBadManage";

    /**
     * 获取 badManageId 对象
     */
    public BadManage getById(int badManageId) {
        return dao.findById(badManageId);
    }
    /**
     * 修改 planTask 对象数据
     */
    public boolean update(BadManage badManage) {
        return badManage.update();
    }
    /**
     * 根据ID删除数据
     * @param badManageId
     * @return
     */
    public boolean deleteById(int badManageId){
        return dao.deleteById(badManageId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int badManageId) {
        CacheKit.remove(allBadManageCacheName, badManageId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<BadManage> badManageList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("planTask.select"),Db.getSql("planTask.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public BadManage edit(int planTaskId){
        return dao.findById(planTaskId);
    }

}




