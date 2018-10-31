package com.hy.common.constant;


import com.hy.common.interfaces.ILog;
import com.hy.common.interfaces.IShiro;
import com.hy.common.log.HyLogFactory;
import com.hy.common.shiro.DefaultShiroFactroy;

public class Cst {

	/**
	 * 默认shirorealm工厂类
	 */
	private IShiro defaultShiroFactory = new DefaultShiroFactroy();
	/**
	 * 默认日志处理工厂类
	 */
	private ILog defaultLogFactory = new HyLogFactory();
	/**
	 * 项目物理路径
	 */
	private String realPath ;
	/**
	 * 远程上传模式
	 */
	private boolean remoteMode = false;
	/**
	 * 上传下载路径(物理路径)
	 */
	private String remotePath = "D://jblade";

	/**
	 * 上传路径(相对路径)
	 */
	private String uploadPath = "/upload";

	/**
	 * 下载路径
	 */
	private String downloadPath = "/download";

	/**
	 * 项目相对路径
	 */
	private String contextPath ;
	
	

	/**
	 * 密码允许错误次数
	 */
	private int passErrorCount = 6;

	/**
	 * 密码锁定小时数
	 */
	private int passErrorHour = 6;
	
	/**
	 * 是否启用乐观锁
	 */
	private boolean optimisticLock = true;
	
	
	private static final Cst me = new Cst();

	private Cst() {

	}

	public static Cst me() {
		return me;
	}

	public ILog getDefaultLogFactory() {
		return defaultLogFactory;
	}

	public void setDefaultLogFactory(ILog defaultLogFactory) {
		this.defaultLogFactory = defaultLogFactory;
	}

	public int getPassErrorCount() {
		return passErrorCount;
	}

	public void setPassErrorCount(int passErrorCount) {
		this.passErrorCount = passErrorCount;
	}

	public int getPassErrorHour() {
		return passErrorHour;
	}

	public void setPassErrorHour(int passErrorHour) {
		this.passErrorHour = passErrorHour;
	}

	public boolean isOptimisticLock() {
		return optimisticLock;
	}

	public void setOptimisticLock(boolean optimisticLock) {
		this.optimisticLock = optimisticLock;
	}

	public IShiro getDefaultShiroFactory() {
		return defaultShiroFactory;
	}

	public void setDefaultShiroFactory(IShiro defaultShiroFactory) {
		this.defaultShiroFactory = defaultShiroFactory;
	}

	public boolean isRemoteMode() {
		return remoteMode;
	}
	
	
}
