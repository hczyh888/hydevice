

package com.hy.system.loginLog;

import com.hy.common.model.LoginLog;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

/**
 * 账户业务
 */
@SuppressWarnings("rawtypes")
public class LoginLogService {

	public static final LoginLogService me = new LoginLogService();
	private final LoginLog dao = new LoginLog().dao();
	private final String allUsersCacheName = "allUsers";
	/**
	 * 根据ID删除数据
	 * @param loginLogId
	 * @return
	 */
	public boolean deleteById(int loginLogId){
		return dao.deleteById(loginLogId);
	}
	/**
	 * 根据 loginLogId 值移除缓存
	 */
	public void clearCache(int loginLogId) {
		CacheKit.remove(allUsersCacheName, loginLogId);
	}

	/**
	 * 返回分页的List数据
	 * @return
	 */
	public Page<LoginLog> loginLogList(int curPage,int pageSize,String sWhere,String orderFiled,String sort){
		String sOrderBy=StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
		return dao.paginate(curPage,pageSize,Db.getSql("loginLog.select"),Db.getSql("loginLog.fromWhere")+" "+sWhere+sOrderBy);
	}
	public List<LoginLog> getExportDatas(){
		List<LoginLog> loginLogs = dao.find(Db.getSql("loginLog.exportSql"));
		return loginLogs;
	}
}



