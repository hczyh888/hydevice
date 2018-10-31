

package com.hy.system.operationLog;

import com.hy.common.model.OperationLog;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * 账户业务
 */
@SuppressWarnings("rawtypes")
public class OperationLogService {

	public static final OperationLogService me = new OperationLogService();
	private final OperationLog dao = new OperationLog().dao();
	private final String allUsersCacheName = "allUsers";

	/**
	 * 根据ID删除数据
	 * @param operationLogId
	 * @return
	 */
	public boolean deleteById(int operationLogId){
    	return dao.deleteById(operationLogId);
	}
	/**
	 * 根据 operationLogId 值移除缓存
	 */
	public void clearCache(int operationLogId) {
		CacheKit.remove(allUsersCacheName, operationLogId);
	}

	/**
	 * 返回分页的List数据
	 * @return
	 */
	public Page<OperationLog> operationLogList(int curPage,int pageSize,String sWhere,String orderFiled,String sort){
		String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
		return dao.paginate(curPage,pageSize,Db.getSql("operationLog.select"),Db.getSql("operationLog.fromWhere")+" "+sWhere+sOrderBy);
	}

}



