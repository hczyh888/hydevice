

package com.hy.system.index;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hy.common.controller.BaseController;
import com.hy.common.log.LogManager;
import com.hy.common.login.LoginService;
import com.hy.common.loginlog.LoginLog;
import com.hy.common.plugin.shiro.ShiroKit;
import com.hy.common.tools.Func;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import org.apache.poi.ss.examples.html.ToHtml;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 首页控制器
 */
@Api(tag = "index", description = "测试输出")
public class IndexController extends BaseController {
	/**
	 * 无权限地址
	 */
	final static String noPermissionPath = "/error/permission.html";


	public void index() {

		render("index.html");
	}
	public void main(){
		render("main.html");
	}
	@ApiOperation(url = "/deviceLst", tag = "index", httpMethod = "get", description = "测试json")
	@Params({
			@Param(name = "id", description = "编号", required = false, dataType = "long"),
			@Param(name = "name", description = "名称", required = false, dataType = "string")
	})
	public void deviceLst(){
		render("deviceLst.html");
	}
	@Before(GET.class)
	public void login(){
		if(ShiroKit.isAuthenticated()){
			index();
			return;
		}
		render("login.html");
	}
	@Before(POST.class)
	public void doLogin(){
		String account = getPara("account");
		String password = getPara("password");
		if (!validateCaptcha("imgCode")) {
			renderJson( Ret.fail("msg","验证码错误"));
			return;
		}
		Subject currentUser = ShiroKit.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(account, password.toCharArray());
		token.setRememberMe(true);
		try {
			currentUser.login(token);
			Session session = ShiroKit.getSession();
			System.out.println(Func.format("sessionID	: {} ", session.getId()));
			System.out.println(Func.format("sessionHost	: {}", session.getHost()));
			System.out.println(Func.format("sessionTimeOut	: {}", session.getTimeout()));
		} catch (UnknownAccountException e) {
			LogKit.error(Func.format("账号不存在：{}", e));
			renderJson(Ret.fail("msg","账号不存在"));
			return;
		} catch (DisabledAccountException e) {
			LogKit.error(Func.format("账号未启用：{}", e));
			renderJson(Ret.fail("msg","账号未启用"));
			return;
		} catch (IncorrectCredentialsException e) {
			LogKit.error(Func.format("密码错误：{}", e));
			renderJson(Ret.fail("msg","密码错误"));
			return;
		} catch (RuntimeException e) {
			LogKit.error(Func.format("账号不存在或密码错：{}", e));
			renderJson(Ret.fail("msg","账号不存在或密码错"));
			return;
		}
		doLog(ShiroKit.getSession(), "登录");
		renderJson(Ret.ok("msg","登录成功"));
		return;
	}



	public void logout() {
		doLog(ShiroKit.getSession(), "登出");
		Subject currentUser = ShiroKit.getSubject();
		currentUser.logout();
		redirect("/login");
	}

	public void unauth() {
		if (ShiroKit.notAuthenticated()) {
			redirect("login");
		}
		render(noPermissionPath);
	}

	public void doLog(Session session, String type){
		if(!LogManager.isDoLog()){
			return;
		}
		try{
			LoginLog log = new LoginLog();
			String msg = Func.format("[sessionID]: {} [sessionHost]: {} [sessionHost]: {}", session.getId(), session.getHost(), session.getTimeout());
			log.set("logName",type);
			log.set("method",msg);
			log.set("createtime",new Date());
			log.set("succeed","1");
			log.set("userId",Func.format(ShiroKit.getUser().getId()));
			log.save();
		}catch(Exception ex){
			LogKit.logNothing(ex);
		}
	}
	public void captcha(){
		renderCaptcha();
	}
	public void deviceCard(){
		render("deviceCard.html");
	}
	public void getQRCode() {
		HttpServletResponse response=getResponse();
		String content = "固定资产ID，档案号：DAH2018";
		LoginService.me.encoderQRCoder(content,response);
	}
	public void exportToWord(){
		/*String rst="fail";
		try {
			DocumentHandler.downloadWord("aa");
			rst="OK";
		}catch (Exception ex){
			rst=ex.getMessage();
		}finally {
			renderText(rst);
		}*/
		String localFile = "D:\\tools\\aaa.xlsx";
		HttpServletResponse response = this.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter printWriter = response.getWriter();
			ToHtml toHtml = ToHtml.create(localFile, printWriter);
			toHtml.setCompleteHTML(true);
			toHtml.printPage();
		}catch (Exception ex) {
		}finally {
		}
		renderNull();
	}
}
