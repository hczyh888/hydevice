

package com.hy.common.dict;

import com.hy.common.model.Dict;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.List;

import static com.hy.common.constant.ConstCache.DICT_CACHE;

/**
 * 账户业务
 */
@SuppressWarnings("rawtypes")
public class DictService {

	public static final DictService me = new DictService();
	private final Dict dao = new Dict().dao();
	private final String allUsersCacheName = "allUsers";

    /**
     * 获取 Dict 对象
     */
    public Dict getById(int dictId) {
        return dao.findById(dictId);
    }
	/**
	 * 修改 Dict 对象数据
	 */
	public boolean update(Dict dict) {
		return dict.update();
	}
	/**
	 * 根据ID删除数据
	 * @param dictId
	 * @return
	 */
	public boolean deleteById(int dictId){
    	return dao.deleteById(dictId);
	}
	/**
	 * 根据 DictId 值移除缓存
	 */
	public void clearCache(int UserId) {
		CacheKit.remove(allUsersCacheName, UserId);
	}

	/**
	 * 返回分页的List数据
	 * @return
	 */
	public Page<Dict> dictList(int curPage,int pageSize,String sWhere,String orderFiled,String sort){
		String sOrderBy= StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
		return dao.paginate(curPage,pageSize,Db.getSql("dict.select"),Db.getSql("dict.fromWhere")+" "+sWhere+sOrderBy);
	}
	/**
	 * 把 Dict 对象保存数据库
	 */
	public boolean Save(){
		return  dao.save();
	}
	/**
	 * 根据 DictId 编辑
	 */
	public Dict edit(int dictId){
		return dao.findFirst(Db.getSqlPara("dict.selectFirst", Kv.by("dictId",dictId)));
	}
	public List<Record> getDictTreeByCache(){
		List<Record> dict = CacheKit.get(DICT_CACHE, "dict_all",
				new IDataLoader() {
					public Object load() {
						return Db.find("select id ,pid as pId,name,case when (pid=0 or pId is null) then 'true' else 'false' end  'open' from  sys_dict order by pid,id,code ");
					}
				});
		return dict;
	}
	public List<Record> getDictListByCache(String pcode){
		final String _pcode = pcode;
		List<Record> dict = CacheKit.get(DICT_CACHE, pcode,
				new IDataLoader() {
					public Object load() {
						return Db.find("select ID,name as TEXT from  sys_dict where pcode= ? and code>0",_pcode);
					}
				});
		return dict;
	}
}



