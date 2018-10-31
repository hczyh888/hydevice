package com.hy.hystock.archivesinfo;

import com.hy.common.model.Install;
import com.hy.common.model.Repair;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class InstallService {

    public static final InstallService me = new InstallService();
    private final Install dao = new Install().dao();
    private final String allInstallCacheName = "allInstall";

    /**
     * 获取 apply 对象
     */
    public Install getById(int installId) {
        return dao.findById(installId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(Install install) {
        return install.update();
    }
    /**
     * 根据ID删除数据
     * @param installId
     * @return
     */
    public boolean deleteById(int installId){
        return dao.deleteById(installId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int installId) {
        CacheKit.remove(allInstallCacheName, installId);
    }

    public Install edit(int installId){
        return dao.findFirst(Db.getSql("install.select")+Db.getSql("install.fromWhere")+" and id=?",installId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<Install> installList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("install.select"),Db.getSql("install.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Install getInstallAll(int id){
        Install  install= dao.findById(id);
        return install;
    }
}


