

package com.hy.system.parameter;

import com.hy.common.constant.BillsConst;
import com.hy.common.constant.ConstCache;
import com.hy.common.kit.DateKit;
import com.hy.common.model.Parameter;
import com.hy.common.model.User;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

/**
 * 参数设置业务
 */
@SuppressWarnings("rawtypes")
public class ParameterService {

	public static final ParameterService me = new ParameterService();
	private static Parameter dao = new Parameter().dao();
	private final String allParametersCacheName = "allParameters";

    /**
     * 获取 Parameter 对象
     */
    public Parameter getById(int parameterId) {
        return dao.findById(parameterId);
    }
	/**
	 * 修改 Parameter 对象数据
	 */
	public boolean update(Parameter parameter) {
		return parameter.update();
	}
	/**
	 * 根据ID删除数据
	 * @param parameterId
	 * @return
	 */
	public boolean deleteById(int parameterId){
		return dao.deleteById(parameterId);
	}
	/**
	 * 把 Parameter 对象保存数据库
	 */
	public boolean Save(Parameter parameter){
		return  parameter.save();
	}
	/**
	 * 根据 ParameterId 编辑
	 */
	public Parameter edit(int parameterId){
		return dao.findById(parameterId);
	}

	public int updateValue(String value,String num,String code){
			 SqlPara sql = Db.getSqlPara("parameter.updateValue", Kv.create().set("v",value).set("num",num).set("code",code));
		return Db.update(sql);
	}

	/**
	 * 根据 ParameterId 值移除缓存
	 */
	public void clearCache(int parameterId) {
		CacheKit.remove(allParametersCacheName, parameterId);
	}

	/**
	 * 返回分页的List数据
	 * @return
	 */
	public Page<Parameter> parameterList(int curPage,int pageSize,String sWhere,String orderFiled,String sort){
		String sOrderBy=StrKit.notBlank(orderFiled)?" order by "+orderFiled +" "+sort:"";
		return dao.paginate(curPage,pageSize,Db.getSql("parameter.select"),Db.getSql("parameter.fromWhere")+" "+sWhere+sOrderBy);
	}
	public static void initCheckAllBillNo(){
		//yyyymmdd001
		String no = DateKit.getDays()+"000";
		//入库单号
		if(findFirstByCode(BillsConst.BILL_RKDH)==false){
			insertInitBillNo(BillsConst.BILL_RKDH,no,BillsConst.RKDH_NAME);
			CacheKit.put(ConstCache.SYS_CACHE,BillsConst.BILL_RKDH,BillsConst.BILL_RKDH+no);
		}
		//出库单号
		if(findFirstByCode(BillsConst.BILL_CKDH)==false){
			insertInitBillNo(BillsConst.BILL_CKDH,no,BillsConst.CKDH_NAME);
			CacheKit.put(ConstCache.SYS_CACHE,BillsConst.BILL_CKDH,BillsConst.BILL_CKDH+no);
		}
		//调拨单号
		if(findFirstByCode(BillsConst.BILL_DBDH)==false){
			insertInitBillNo(BillsConst.BILL_DBDH,no,BillsConst.DBDH_NAME);
			CacheKit.put(ConstCache.SYS_CACHE,BillsConst.BILL_DBDH,BillsConst.BILL_DBDH+no);
		}

	}
	private static Boolean findFirstByCode(String billType){
		String countSQL = "select count(*) as cnt from sys_parameter where code= ?";
		Parameter parameter= dao.findFirst(countSQL,billType);
		return parameter.getInt("cnt")>0?true:false;
	}
	private static void insertInitBillNo(String code,String para,String billName){
		Parameter parameter = new Parameter();
		parameter.setCode(code);
		parameter.setName(billName);
		parameter.setNum(1);
		parameter.setPara(code+para);
		parameter.setStatus(1);
		parameter.save();
	}
	public String getNewBillNo(String code){
		//比较日期，看今天是否是第一单
		String d = DateKit.getDays();
		String cacheDH = CacheKit.get(ConstCache.SYS_CACHE,code);
		String d2 = StrKit.notBlank(cacheDH)?cacheDH.substring(4,12):d;//取出日期部分
		String bh3 = "001";//后三位序号
		if(d.equals(d2)){ //说明今天已经有入库单号产生 ，取后三位+1得到新的单号
			bh3 =StrKit.notBlank(cacheDH)?cacheDH.substring(cacheDH.length()-3):"001";
			bh3 = String.valueOf(1000 +Integer.parseInt(bh3)+1);
			bh3 = bh3.substring(bh3.length()-3);

		}else{  //否则这是第一单
			//no dosomething
		}
		return code+d2+bh3;
	}


}



