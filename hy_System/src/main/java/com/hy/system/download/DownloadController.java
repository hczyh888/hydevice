
package com.hy.system.download;

import com.jfinal.aop.Before;
import com.hy.common.controller.BaseController;
import com.hy.common.interceptor.AdminAuthInterceptor;
import com.hy.common.interceptor.FrontAuthInterceptor;
import com.hy.common.kit.IpKit;
import com.hy.common.model.Account;
import com.jfinal.kit.Ret;

/**
 * 下载控制器
 */
@Before(FrontAuthInterceptor.class)
public class DownloadController extends BaseController {

	private static final DownloadService srv = DownloadService.me;

	/**
	 * 下载
	 */
	public void index() {
		Account loginAccount = getLoginAccount();
		String ip = IpKit.getRealIp(getRequest());
		Ret ret = srv.download(loginAccount, getPara("file"), ip);
		if (ret.isOk()) {
			String fullFileName = ret.getAs("fullFileName");
			renderFile(fullFileName);
		} else {
			renderError(404);
		}
	}

	/**
	 * 清缓存
	 */
	@Before(AdminAuthInterceptor.class)
	public void clear() {
		srv.clearCache();
		redirect("/");
	}
}
