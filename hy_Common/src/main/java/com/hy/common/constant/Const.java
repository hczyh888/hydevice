package com.hy.common.constant;

public interface Const {
	/**
	 * 字典库的几个类别
	 */
	static String unitList = "unitList";  //单位
	static String brandList = "brandList"; //品牌
	static String partnerType = "partnerType"; //或作伙伴
	static String countryList = "countryList"; //国别
	/**
	 * 登陆地址(路径)
	 */
	final static String loginRealPath = "/login.html";
	/**
	 * 首页面地址(路径)
	 */
	final static String indexRealPath = "/index.html";
	/**
	 * 400页面地址
	 */
	final static String error400Path = "/error/400.html";
	/**
	 * 401页面地址
	 */
	final static String error401Path = "/error/401.html";
	/**
	 * 404页面地址
	 */
	final static String error404Path = "/error/404.html";
	/**
	 * 403页面地址
	 */
	final static String error403Path = "/error/403.html";
	/**
	 * 500页面地址
	 */
	final static String error500Path = "/error/500.html";
	/**
	 * 无权限地址
	 */
	final static String noPermissionPath = "/error/permission.html";
	/**
	 * 下载地址
	 */
	final static String downloadPath = "/download";

	/**
	 * 定义用户sessionkey
	 */
	final static String USER_SESSION_KEY = "user";

	/**
	 * 定义日志参数
	 */
	final static String PARA_LOG_CODE = "101";

	/**
	 * 定义乐观锁字段
	 */
	final static String OPTIMISTIC_LOCK = "version";
	
	/**
	 * 定义mybatis分页插件的排序字段
	 */
	final static String ORDER_BY_STR = "orderBy";
	/**包路径**/
	public final static String PACKAGE_NAME = "com.hy.common.enumresource";
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
	/**
	 * 菜单类型
	 *
	 * @author chenyi
	 * @email 228112142@qq.com
	 * @date 2016年11月15日 下午1:24:29
	 */
	public enum MenuType {
		/**
		 * 目录
		 */
		CATALOG(1),
		/**
		 * 菜单
		 */
		MENU(2),
		/**
		 * 按钮
		 */
		BUTTON(3);

		private int value;

		private MenuType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 定时任务状态
	 *
	 * @author chenyi
	 * @email 228112142@qq.com
	 * @date 2016年12月3日 上午12:07:22
	 */
	public enum ScheduleStatus {
		/**
		 * 正常
		 */
		NORMAL(0),
		/**
		 * 暂停
		 */
		PAUSE(1);

		private int value;

		private ScheduleStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
}
