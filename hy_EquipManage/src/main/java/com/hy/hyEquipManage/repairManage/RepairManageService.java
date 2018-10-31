package com.hy.hyEquipManage.repairManage;


import com.hy.common.model.RepairManage;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class RepairManageService {

    public static final RepairManageService me = new RepairManageService();
    private final RepairManage dao = new RepairManage().dao();
    private final String allRepairManageCacheName = "allRepairManage";

    /**
     * 获取 repairManage 对象
     */
    public RepairManage getById(int repairManageId) {
        return dao.findById(repairManageId);
    }
    /**
     * 修改 planTask 对象数据
     */
    public boolean update(RepairManage repairManage) {
        return repairManage.update();
    }
    /**
     * 根据ID删除数据
     * @param repairManageId
     * @return
     */
    public boolean deleteById(int repairManageId){
        return dao.deleteById(repairManageId);
    }
    /**
     * 根据 UserId 值移除缓存
     */
    public void clearCache(int repairManageId) {
        CacheKit.remove(allRepairManageCacheName, repairManageId);
    }

    /**
     * 返回分页的List数据
     * @return
     */
    public Page<RepairManage> repairManageList(int curPage, int pageSize, String sWhere, String orderFiled, String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize, Db.getSql("planTask.select"),Db.getSql("planTask.fromWhere")+" "+sWhere+sOrderBy);
    }

    public boolean Save(){
        return  dao.save();
    }

    public RepairManage edit(int planTaskId){
        return dao.findById(planTaskId);
    }

}



