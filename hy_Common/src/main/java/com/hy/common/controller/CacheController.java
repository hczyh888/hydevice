package com.hy.common.controller;

import com.hy.common.kit.JStrKit;
import com.hy.common.tools.Func;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.List;
import java.util.Map;

public class CacheController extends BaseController {
    /**
     * 获取角色tree的数据
     */
    public void getRolesTree() {
        List<Record> roles = CacheKit.get(ROLES_CACHE, "role_all",
                new IDataLoader() {
                    public Object load() {
                        return Db.find("select id,pid as pId,name,case when (pid=0 or pId is null) then 'true' else 'false' end  \"open\" from sys_roles where  name is not null order by pid,id,num " );
                    }
                });
        Ret ret = Ret.create("code",0).set("data",roles);
        renderJson(ret);
    }

    /**
     * 获取部门tree的数据
     */
    public void getDeptTree() {
        List<Record> dept = CacheKit.get(DEPT_CACHE, "dept_tree_all",
                new IDataLoader() {
                    public Object load() {
                        return Db.find("select id ,pid as pId,simpleName as name,case when (pid=0 or pId is null) then 'true' else 'false' end  \"open\" from  sys_dept order by pid,id,num ");
                    }
                });
        Ret ret = Ret.create("code",0).set("data",dept);
        renderJson(ret);
    }

    /**
     * 获取字典库tree的数据
     */
    public void getDictTree() {
        List<Record> dict = CacheKit.get(DICT_CACHE, "dict_tree_all",
                new IDataLoader() {
                    public Object load() {
                        return Db.find("select id ,pid as pId,name,case when (pid=0 or pId is null) then 'true' else 'false' end  \"open\" from  sys_dict order by pid,id,code ");
                    }
                });
        Ret ret = Ret.create("code",0).set("data",dict);
        renderJson(ret);
    }

    /**
     * 获取菜单tree的数据
     */
    public void getMenuTree() {
        List<Record> menu = CacheKit.get(MENU_CACHE, "menu_tree_all",
                new IDataLoader() {
                    public Object load() {
                        return Db.find("select code id,pCode pId,name name,(case when levels=1 then 'true' else 'false' end) \"open\" from sys_menu where status=1 order by levels asc,num asc");
                    }
                });
        Ret ret = Ret.create("code",0).set("data",menu);
        renderJson(ret);
    }

    public void menuTreeListByRoleId() {
        final String roleId = getPara("roleId", "0");
        List<Record> menu = CacheKit.get(MENU_CACHE, "tree_role_" + roleId,
                new IDataLoader() {
                    public Object load() {
                        return Db.find(Db.getSqlPara("menu.menuTreeListByRoleId", Kv.by("roleId",roleId)));
                    }
                });

        renderJson(Ret.ok("code",0).set("data",menu));
    }
    /**
     * 获取设备分类tree的数据
     */
    public void getDeviceclassTree() {
        List<Record> deviceclass = CacheKit.get(DEVICECLASS_CACHE, "deviceclass_tree_all",
                new IDataLoader() {
                    public Object load() {
                        return Db.find("select id ,pid as pId,name,case when (pid=0 or pId is null) then 'true' else 'false' end  \"open\" from  sys_deviceclass order by pid,id,num ");
                    }
                });
        Ret ret = Ret.create("code",0).set("data",deviceclass);
        renderJson(ret);
    }
    /**
     * 获取合作伙伴tree的数据
     */
    public void getPartnerTree() {
        List<Record> partner = CacheKit.get(DEVICECLASS_CACHE, "partner_tree_all",
                new IDataLoader() {
                    public Object load() {
                        return Db.find("select id ,pid as pId,companyName as name,case when (pid=0 or pId is null) then 'true' else 'false' end  \"open\" from  sys_partner order by pid,id");
                    }
                });
        Ret ret = Ret.create("code",0).set("data",partner);
        renderJson(ret);
    }

    /**
     * 获取库房类型下拉框
     */
    public void getWareHourseType() {
        List<Record> wareHourseType = CacheKit.get(JSON_CACHE, "json",
                new IDataLoader() {
                    public Object load() {
                        return Db.find("select id ,type from stock_warehouse pid=0 order by id");
                    }
                });
        Ret ret = Ret.create().set("data",wareHourseType);
        renderJson(ret);
    }
    /**
     * 获取库房类型下拉框
     */
    public void getWareHourse() {
        final int pid = getParaToInt(0,0);
        List<Record> partner = CacheKit.get(JSON_CACHE, "json",
                new IDataLoader() {
                    public Object load() {
                        return Db.find("select id  ,type  from stock_warehouse where pid="+pid+" order by id");
                    }
                });
        Ret ret = Ret.create("code",0).set("data",partner);
        renderJson(ret);
    }

    /**
     * 获取设备下拉框
     */
    public void getDeviceList() {
        //final int pid = getParaToInt(0,0);
        List<Record> devicelist = CacheKit.get(DEFAULT_CACHE, "devicelist",
                new IDataLoader() {
                    public Object load() {
                        return Db.find("select id,concat_ws(' ',cnName,enName) as keyValue,cnName  value  from sys_devicelist where status=1 order by cnName");
                    }
                });
        renderJson(Ret.create("code",0).set("data",devicelist));
    }
    /**
     * 获取部门下拉框的数据
     */
    public void getDeptList() {
        List<Record> dept = CacheKit.get(DEPT_CACHE, "dept_tree_all",
                new IDataLoader() {
                    public Object load() {
                        return Db.find("select id ,simpleName as value from  sys_dept order by pid,id,num ");
                    }
                });
        Ret ret = Ret.create("code",0).set("data",dept);
        renderJson(ret);
    }
}
