package com.hy.system.roles;

import com.hy.common.model.Roles;
import com.hy.common.tools.Func;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.hy.common.constant.ConstCache;

import java.util.List;
import java.util.Map;


public class RolesService implements ConstCache{
    public static final RolesService me = new RolesService();
    private final Roles dao = new Roles().dao();
    private final String allUsersCacheName = "allUsers";
    /**
     * 返回分页的List数据
     * @return
     */
   /* public Page<Roles> rolesList(int curPage, int pageSize,String sWhere){
        return dao.paginate(curPage,pageSize, Db.getSql("roles.select"),Db.getSql("roles.fromWhere")+" "+sWhere);
    }*/
    public Page<Roles> rolesList(int curPage,int pageSize,String sWhere,String orderFiled,String sort){
        String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
        return dao.paginate(curPage,pageSize,Db.getSql("roles.select"),Db.getSql("roles.fromWhere")+" "+sWhere+sOrderBy);
    }
    public Roles edit(int rolesId) {

        return dao.findById(rolesId);
    }
    public boolean update(Roles roles){
        return roles.update();
    }
    public boolean save(){
        return dao.save();
    }
    public boolean deleteById(int rolesId){
        return dao.deleteById(rolesId);
    }
    public Roles getById(int rolesId){
        return dao.findById(rolesId);
    }
    public String rolesTreeList() {
        List<Map<String, Object>> roles = CacheKit.get(ROLES_CACHE, "roles_tree_all_" +"001",
                new IDataLoader() {
                    public Object load() {
                        return Db.find("select id \"id\",pid \"pId\",name as \"name\",(case when (pId=0 or pId is null) then 'true' else 'false' end) \"open\" from  sys_roles ");
                    }
                });

        return FastJson.getJson().toJson(roles);
    }
    @Before(Tx.class)
    public boolean saveAuthority(String ids, String roleId) {
        Db.update ("delete from sys_role_menu where roleId = ?",roleId);

        String sql = "";
        String insertSql = "";
        String union_all = "";
        String[] id = ids.split(",");
        String dual = (Func.isOracle()) ? " from dual " : "";
        for (int i = 0; i < id.length; i++) {
            union_all = (i < id.length - 1) ? " union all " : "";
            sql += " (select " + id[i] + " menuId," + roleId + " roleId "
                    + dual + ")" + union_all;
        }

        if (Func.isOracle()) {
            sql = "select SEQ_RELATION.nextval,i.* from (" + sql + ") i";
            insertSql = "insert into sys_role_menu(id,menuId,roleId) ";
        } else {
            sql = "select i.* from (" + sql + ") i";
            insertSql = "insert into sys_role_menu(menuId,roleId) ";
        }
        int cnt = Db.update(insertSql + sql);
        return cnt > 0;
    }
}
