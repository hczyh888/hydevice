package com.hy.hyEquipManage.departmentManage;

import com.hy.common.model.DepartmentManage;
import com.hy.common.model.Stockpile;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class DepartmentManageService {

    public static final DepartmentManageService me = new DepartmentManageService();
    private final DepartmentManage dao = new DepartmentManage().dao();
    private final String allDepartmentManageCacheName = "allDepartmentManage";

    /**
     * 获取 apply 对象
     */
    public DepartmentManage getById(int departmentManageId) {
        return dao.findById(departmentManageId);
    }
    /**
     * 修改 Audit 对象数据
     */
    public boolean update(DepartmentManage departmentManage) {
        return departmentManage.update();
    }
    /**
     * 根据ID删除数据
     * @param departmentManageId
     * @return
     */
    public boolean deleteById(int departmentManageId){
        return dao.deleteById(departmentManageId);
    }
    /**
     * 根据 deptStockId 值移除缓存
     */
    public void clearCache(int departmentManageId) {
        CacheKit.remove(allDepartmentManageCacheName, departmentManageId);
    }

    public DepartmentManage edit(int departmentManageId){
        return dao.findFirst(Db.getSql("departmentManageId.select")+Db.getSql("departmentManageId.fromWhere")+" and id=?",departmentManageId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<DepartmentManage> departmentManageList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("stockpile.select"),Db.getSql("stockpile.fromWhere")+" "+sWhere+sOrderBy);
    }
    public DepartmentManage getDepartmentManageAll(int id){
        DepartmentManage  departmentManage= dao.findById(id);
        return departmentManage;
    }

}




