

package com.hy.system.user;

import com.hy.common.kit.DateKit;
import com.hy.common.model.User;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * 账户业务
 */
@SuppressWarnings("rawtypes")
public class UserService {

	public static final UserService me = new UserService();
	private final User dao = new User().dao();
	private final String allUsersCacheName = "allUsers";

    /**
     * 获取 User 对象
     */
    public User getById(int userId) {
        return dao.findById(userId);
    }
	/**
	 * 修改 User 对象数据
	 */
	public boolean update(User user) {
		return user.update();
	}
	/**
	 * 根据ID删除数据
	 * @param userId
	 * @return
	 */
	public boolean deleteById(int userId){
    	return dao.deleteById(userId);
	}
	/**
	 * 根据 UserId 值移除缓存
	 */
	public void clearCache(int UserId) {
		CacheKit.remove(allUsersCacheName, UserId);
	}

	/**
	 * 返回分页的List数据
	 * @return
	 */
	public Page<User> userList(int curPage,int pageSize,String sWhere,String orderFiled,String sort){
		String sOrderBy=StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
		return dao.paginate(curPage,pageSize,Db.getSql("user.select"),Db.getSql("user.fromWhere")+" "+sWhere+sOrderBy);
	}
	/**
	 * 把 User 对象保存数据库
	 */
	public boolean Save(){
		return  dao.save();
	}
	/**
	 * 根据 UserId 编辑
	 */
	public User edit(int userId){
		return dao.findById(userId);
	}

	public Boolean isExitByAccount(String account){
		User user = dao.findFirst("select name from sys_user where account= ? ",account);
		return user==null?false:true;
	}
	//获取记录数
	public int getCount(){
		return dao.findFirst(Db.getSql("user.getCount")).getInt("cnt");
	}
	public static void initCheckUser(){
		if(me.getCount()==0){
			//pwd明文:123456
			User user = new User();
			user.set("account","admin").set("password","4779e4a9903dfb08f9f877791c516a73").set("salt","admin")
					.set("roleId",1).set("createtime", DateKit.getDay());
			user.save();

		}
	}
}



