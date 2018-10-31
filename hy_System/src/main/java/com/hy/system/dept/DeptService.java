

package com.hy.system.dept;

import com.hy.common.constant.ConstCache;
import com.hy.common.model.Dept;
import com.hy.common.model.Warehouse;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * 账户业务
 */
@SuppressWarnings("rawtypes")
public class DeptService implements ConstCache {

	public static final DeptService me = new DeptService();
	private final Dept dao = new Dept().dao();
	private final String allUsersCacheName = "allUsers";

    /**
     * 获取 dept 对象
     */
    public Dept getById(int deptId) {
        return dao.findById(deptId);
    }
	/**
	 * 修改 dept 对象数据
	 */
	public boolean update(Dept dept) {
		return dept.update();
	}
	/**
	 * 根据ID删除数据
	 * @param deptId
	 * @return
	 */
	public boolean deleteById(int deptId){
    	return dao.deleteById(deptId);
	}
	/**
	 * 根据 deptId 值移除缓存
	 */
	public void clearCache(int deptId) {
		CacheKit.remove(allUsersCacheName, deptId);
	}

	/**
	 * 返回分页的List数据
	 * @return
	 */
	public Page<Dept> deptList(int curPage,int pageSize,String sWhere,String orderFiled,String sort){
		String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
		return dao.paginate(curPage,pageSize,Db.getSql("dept.select"),Db.getSql("dept.fromWhere")+" "+sWhere+sOrderBy);
	}
	/**
	 * 把 dept 对象保存数据库
	 */
	public boolean Save(){
		return  dao.save();
	}
	/**
	 * 根据 deptId 编辑
	 */
	public Dept edit(int deptId){
		return dao.findById(deptId);
	}

	/**
	 * 获取库房下拉框数据
	 */
	public List<Dept> getDept(int pid) {
		return  dao.find("select id ,name text from sys_dept where pid="+pid+" order by id");
	}


}



