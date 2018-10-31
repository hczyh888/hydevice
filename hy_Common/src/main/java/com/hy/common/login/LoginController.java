
package com.hy.common.login;

import com.hy.common.log.LogManager;
import com.hy.common.loginlog.LoginLog;
import com.hy.common.plugin.shiro.ShiroKit;
import com.hy.common.tools.Func;
import com.jfinal.aop.Before;
import com.jfinal.core.Const;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


import java.util.Date;

/**
 * 登录控制器
 */
public class LoginController extends Controller {

	static final LoginService srv = LoginService.me;

	public void index(){


	}




}

