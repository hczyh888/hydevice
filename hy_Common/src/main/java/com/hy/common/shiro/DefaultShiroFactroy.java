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
package com.hy.common.shiro;

import com.hy.common.constant.ConstCache;
import com.hy.common.interfaces.IShiro;
import com.hy.common.login.ShiroUser;
import com.hy.common.model.User;
import com.hy.common.tools.Func;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultShiroFactroy implements IShiro {
	
	public User user(String account) {
		User user = new User().findFirst(Db.getSqlPara("user.getByAccount", Kv.by("account",account)));
		// 账号不存在
		if (null == user) {
			return null;
		}
		// 账号未审核
		if (user.getStatus() == 3 || user.getStatus() == 4) {
			return null;
		}
		// 账号被冻结
		if (user.getStatus() == 2 || user.getStatus() == 5) {
			return null;
		}
		return user;
	}

	public ShiroUser shiroUser(User user) {
		List<String> roleList = new ArrayList<>();
		String[] roles = user.getRoleId().split(",");
		for (int i = 0; i < roles.length; i++) {
			roleList.add(roles[i]);
		}
		return new ShiroUser(user.getId(), user.getDeptId(), user.getAccount(), user.getName(), roleList);
	}

	public List<Map<String, Object>> findPermissionsByRoleId(final Object userId, final String roleId) {
		Map<String, Object> userRole = CacheKit.get(ConstCache.MENU_CACHE, "role_ext_" + userId, new IDataLoader() {
			@Override
			public Object load() {
				return Db.findFirst(Db.getSqlPara("roleExt.getByUserId",Kv.by("userId",userId)));
			}
		}); 

		String roleIn = "0";
		String roleOut = "0";
		if (!Func.isEmpty(userRole)) {
			Record map = new Record().set("roleIn",userRole.get("roleIn")).set("roleOut",userRole.get("roleOut"));
			roleIn = map.getStr("roleIn");
			roleOut = map.getStr("roleOut");
		}
		final String fRoleIn = roleIn;
		final String fRoleOut = roleOut;

		List<Map<String, Object>> permissions = CacheKit.get(ConstCache.MENU_CACHE, "permissions_" + userId, new IDataLoader() {
			@Override
			public Object load() {
				return Db.find(Db.getSqlPara("menu.getByRole",Kv.by("roleId",roleId).set("roleIn",fRoleIn).set("roleOut",fRoleOut)));
			}
		}); 
		
		return permissions;
	}

	public String findRoleNameByRoleId(final String roleId) {
		Map<String, Object> map = CacheKit.get(ConstCache.ROLES_CACHE, "findRoleNameByRoleId" + roleId, new IDataLoader() {
			@Override
			public Object load() {
				return Db.findById("sys_role",roleId);
			}
		});
		return Func.format(map.get("TIPS"));
	}

	public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
		String credentials = user.getPassword();
		// 密码加盐处理
		String source = user.getSalt();
		ByteSource credentialsSalt = new Md5Hash(source);
		return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
	}

}
