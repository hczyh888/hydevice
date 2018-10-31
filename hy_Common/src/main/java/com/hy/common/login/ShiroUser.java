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
package com.hy.common.login;

import com.hy.common.constant.ConstCache;
import com.hy.common.kit.JStrKit;
import com.hy.common.tools.CollectionKit;
import com.hy.common.tools.Func;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.*;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.io.Serializable;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import com.jfinal.plugin.activerecord.Db;


public class ShiroUser implements Serializable {

	private static final long serialVersionUID = 6847303349754497231L;
	
	private Object id;// 主键
	private Object deptId;// 部门id
	private String deptName;// 部门名称
	private String loginName;// 账号
	private String name;// 姓名
	private List<String> roleList;// 角色集
	private String roles;// 角色
	private Object subDepts;// 子部门集合
	private Object subRoles;// 子角色集合
	private Object subUsers;// 子账号集合

	public ShiroUser(Object id, Object deptId, String loginName, String name, List<String> roleList) {
		this.id = id;
		this.deptId = deptId;
		this.deptName = Func.getDeptName(deptId);
		this.loginName = loginName;
		this.name = name;
		this.roleList = roleList;
		this.roles = CollectionKit.join(roleList.toArray(), ",");
		
		// 递归查找子部门id集合
		String deptSql;
		String subDepts = null;
		if (Func.isOracle()) {
			//deptSql = "select wm_concat(ID) subDepts from (select ID,PID,SIMPLENAME from tfw_dept start with ID in (#{join(deptIds)}) connect by prior ID=PID order by ID) a where a.ID not in (#{join(deptIds)})";
			//subDepts = Db.init().queryStr(deptSql, Record.create().set("deptIds", deptId.toString().split(",")));
		} else {
			String[] arr = deptId.toString().split(",");
			StringBuilder sb = new StringBuilder();
			for (String deptid : arr) {
				SpSubStr spSubStr = new SpSubStr();
				spSubStr.rootId = deptid;
				spSubStr.tableName = "sys_dept";
				Db.execute(spSubStr);
				String str = spSubStr.result;
				sb.append(str).append(",");
			}
			subDepts = JStrKit.removeSuffix(sb.toString(), ",");
		}
		this.subDepts = subDepts;
		
		// 递归查找子角色id集合
		String roleSql;
		String subRoles = null;
		if (Func.isOracle()) {
			//roleSql = "select wm_concat(ID) subRoles from (select ID,PID,NAME from tfw_role start with ID in (#{join(roleIds)}) connect by prior ID=PID order by ID) a where a.ID not in (#{join(roleIds)})";
			//subRoles = Db.init().queryStr(roleSql, Record.create().set("roleIds", roleList));
		} else {
			StringBuilder sb = new StringBuilder();
			for (String roleid : roleList) {
				SpSubStr spSubStr = new SpSubStr();
				spSubStr.rootId = roleid;
				spSubStr.tableName = "sys_roles";
				Db.execute(spSubStr);
				String str = spSubStr.result;
				sb.append(str).append(",");
			}
			subRoles = JStrKit.removeSuffix(sb.toString(), ",");
		}
		this.subRoles = subRoles;
		
		// 查找子角色对应账号id集合
		List<Record> listUser = CacheKit.get(ConstCache.USER_CACHE, "user_all_list", new IDataLoader() {
			@Override
			public Object load() {
				return Db.find(Db.getSql("user.select")+Db.getSql("user.from"));
			}
		});
		
		String[] subrolestr = Func.format(this.subRoles).split(",");
		StringBuilder sbUser = new StringBuilder();
		for (Record record : listUser) {
			for (String str : subrolestr) {
				if (Func.format(record.get("roleId")).indexOf(str) >= 0 && (("," + sbUser.toString() + ",").indexOf("," + Func.format(record.get("id")) + ",") == -1)) {
					Func.builder(sbUser, Func.format(record.get("id")) + ",");
				}
			}
		}
		
		this.subUsers = JStrKit.removeSuffix(sbUser.toString(), ",");
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Object getDeptId() {
		return deptId;
	}

	public void setDeptId(Object deptId) {
		this.deptId = deptId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Object getSubDepts() {
		return subDepts;
	}

	public void setSubDepts(Object subDepts) {
		this.subDepts = subDepts;
	}

	public Object getSubRoles() {
		return subRoles;
	}

	public void setSubRoles(Object subRoles) {
		this.subRoles = subRoles;
	}

	public Object getSubUsers() {
		return subUsers;
	}

	public void setSubUsers(Object subUsers) {
		this.subUsers = subUsers;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return loginName;
	}

	//内部类开始
	class SpSubStr implements ICallback {
		public String rootId = null;
		public String tableName=null;
		public String result=" ";
		@Override
		public Object call(Connection conn) throws SQLException {
			CallableStatement proc = null;
			try {

				proc = conn.prepareCall("{ call sp_getSub(?,?,?)}"); // borrow为mysql的存储过程名，其中有两个参数，两个返回值
				proc.setString(1, rootId);//设置参数值
				proc.setString(2, tableName);
				proc.registerOutParameter(3, Types.VARCHAR);
				proc.execute();

				result =  proc.getString(3);//得到返回值
			}catch(Exception e){
				e.printStackTrace();
			} finally {
				if(proc !=null) proc.close();
				if(conn !=null) conn.close();
			}
			return result;
		}

	}
//内部类结束
}