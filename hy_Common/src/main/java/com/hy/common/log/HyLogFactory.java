/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hy.common.log;


import com.hy.common.constant.Const;
import com.hy.common.constant.ConstCache;
import com.hy.common.interfaces.ILog;
import com.hy.common.kit.JStrKit;
import com.hy.common.login.ShiroUser;
import com.hy.common.model.OperationLog;
import com.hy.common.tools.Func;
import com.hy.common.tools.HyRecord;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 系统默认日志记录
 * 
 */
public class HyLogFactory implements ILog {

	public String[] logPatten() {
		String[] patten = { "login", "logout", "grant", "save", "update", "remove", "del", "delete", "restore" };
		return patten;
	}

	public HyRecord logMaps() {
		HyRecord maps = HyRecord.create()
				.set("login", "登录")
				.set("logout", "登出")
				.set("grant", "授权")
				.set("save", "新增")
				.set("update", "修改")
				.set("remove", "删除")
				.set("del", "删除")
				.set("delete", "删除")
				.set("restore", "还原");
		return maps;
	}

	public boolean isDoLog() {
		Record map = CacheKit.get(ConstCache.SYS_CACHE, "parameter_log", new IDataLoader() {
			@Override
			public Object load() {
				return Db.findFirst(Db.getSqlPara("parameter.getByCode", Kv.by("code",Const.PARA_LOG_CODE)));
			}
		});
		if(map != null && JStrKit.equals(map.getStr("para"),"1")){
			return true;
		}
		return false;
	}
	
	public boolean doLog(ShiroUser user, String msg, String logName, HttpServletRequest request, boolean succeed) {
		if (null == user) {
			return true;
		}
		try {
			OperationLog log = new OperationLog();
			log.setMethod(msg);
			log.setCreatetime(new Date());
			log.setSucceed((succeed)?"1":"0");
			log.setUserId(Func.format(user.getId()));
			log.setLogName(logName);
			boolean temp = log.save();
			LogKit.info(msg);
			return temp;
		} catch (Exception ex) {
			return false;
		}
	}

}
