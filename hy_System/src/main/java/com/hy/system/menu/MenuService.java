

package com.hy.system.menu;

import com.hy.common.constant.Const;
import com.hy.common.constant.ConstCache;
import com.hy.common.model.Menu;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单业务
 */
@SuppressWarnings("rawtypes")
public class MenuService implements ConstCache,Const {

	public static final MenuService me = new MenuService();
	private final Menu dao = new Menu().dao();
	private final String allUsersCacheName = "allUsers";

    /**
     * 获取 menu 对象
     */
    public Menu getById(int menuId) {
        return dao.findById(menuId);
    }
	/**
	 * 修改 menu 对象数据
	 */
	public boolean update(Menu menu) {
		return menu.update();
	}
	/**
	 * 根据ID删除数据
	 * @param menuId
	 * @return
	 */
	public boolean deleteById(int menuId){
    	return dao.deleteById(menuId);
	}
	/**
	 * 根据 menuId 值移除缓存
	 */
	public void clearCache(int menuId) {
		CacheKit.remove(allUsersCacheName, menuId);
	}

	/**
	 * 返回分页的List数据
	 * @return
	 */
	public Page<Menu> menuList(int curPage,int pageSize,String sWhere,String orderFiled,String sort){
		String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
		return dao.paginate(curPage,pageSize,Db.getSql("menu.select"),Db.getSql("menu.fromWhere")+" "+sWhere+sOrderBy);
	}
	/**
	 *
	 * 把 menu对象保存数据库
	 */
	public boolean Save(){
		return  dao.save();
	}
	/**
	 * 根据 menuId 编辑
	 */
	public Menu edit(int menuId){
		return dao.findById(menuId);
	}

	/**
	 * 提供给Ztree使用
	 * @return
	 */
	public String menuTreeList(){
			List<Map<String, Object>> menu = CacheKit.get(MENU_CACHE, "menu_tree_all",
					new IDataLoader() {
						public Object load() {
							return Db.find("select code \"id\",pCode \"pId\",name \"name\",(case when levels=1 then 'true' else 'false' end) \"open\" from sys_menu where status=1 order by levels asc,num asc");
						}
					});

			return FastJson.getJson().toJson(menu);
		}

	/**
	 * 得到用户菜单
	 * @param userId
	 * @return
	 */
	public List<Record> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Const.SUPER_ADMIN){
			return getAllMenuList(null);
		}

		//用户菜单列表
		List<Long> menuIdList = queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}
	public List<Long> queryAllMenuId(Long userId) {
		List<Record> lst = Db.find(Db.getSqlPara("menu.queryAllMenuId",Kv.by("userId",userId)));
		List<Long> ls=new ArrayList<Long>();
			for(Record rd :lst){
				ls.add(Long.valueOf (rd.get("menuId").toString()));
		}
		return ls;

	}
	/**
	 * 获取所有菜单列表
	 */
	private List<Record> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<Record> menuList = queryListParentId("0", menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);

		return menuList;
	}

	/**
	 * 递归
	 */
	private List<Record> getMenuTreeList(List<Record> menuList, List<Long> menuIdList){
		List<Record> subMenuList = new ArrayList<Record>();

		for(Record entity : menuList){
			//目录
			if(entity.getInt("menuType") == Const.MenuType.CATALOG.getValue()){
				entity.set("sublist",getMenuTreeList(queryListParentId(entity.getStr("code"), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}

		return subMenuList;
	}

	public List<Record> queryListParentId(String pcode, List<Long> menuIdList) {
		List<Record> menuList = queryListParentId(pcode);
		if(menuIdList == null){
			return menuList;
		}

		List<Record> userMenuList = new ArrayList<>();
		for(Record menu : menuList){
			if(menuIdList.contains(menu.get("id"))){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	public List<Record> queryListParentId(String pcode) {
		return Db.find (Db.getSqlPara("menu.queryListParentId",Kv.by("pcode",pcode)));
	}

}



