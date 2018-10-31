package com.hy.hyEquipManage.installManage;


import com.hy.common.model.InstallManage;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class InstallManageService {

    public static final InstallManageService me = new InstallManageService();
    private final InstallManage dao = new InstallManage().dao();
    private final String allInstallManageCacheName = "allInstallManage";

    /**
     * 获取 installManage 对象
     */
    public InstallManage getById(int installManageId) {
        return dao.findById(installManageId);
    }
    /**
     * 修改 planTask 对象数据
     */
    public boolean update(InstallManage installManage) {
        return installManage.update();
    }
    /**
     * 根据ID删除数据
     * @param installManageId
     * @return
     */
    public boolean deleteById(int installManageId){
        return dao.deleteById(installManageId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int installManageId) {
        CacheKit.remove(allInstallManageCacheName, installManageId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<InstallManage> installManageList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("planTask.select"),Db.getSql("planTask.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public InstallManage edit(int planTaskId){
        return dao.findById(planTaskId);
    }

}



