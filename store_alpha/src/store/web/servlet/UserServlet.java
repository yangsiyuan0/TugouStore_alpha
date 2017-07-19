	package store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import store.domain.ResponseBean;
import store.domain.UserBean;
import store.service.IUserService;
import store.utils.BaseServlet;
import store.utils.BeanFactory;
import store.utils.MyDateConverter;

/**
 * 用户模块的servlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private IUserService userService = (IUserService) BeanFactory.getBean("userService");
	/*private IUserService userService = new UserServiceImpl();*/
	
	/**
	 * 校验用户名是否被占用：AJAX
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Object checkUsername(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{ 
		String username = request.getParameter("username");
		UserBean existUser = userService.findUserByUsername(username);
		// 用户不存在：可以注册
		if(existUser==null){
			ResponseBean<UserBean> bean = new ResponseBean<UserBean>("yes");
			//response.getWriter().print("yes"); 
			return bean;
		} 
		//用户名已被占用
		ResponseBean<UserBean> bean = new ResponseBean<UserBean>("no");
		return bean ;
		//return null;
	}
	
	/**
	 * 校验验证码是否正确：利用AJAX技术
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Object checkVerifyCode(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		//校验验证码是否正确
		String code1 = request.getParameter("verifycode");
		String code2 = (String)request.getSession().getAttribute("verifycode");
		if(!code2.equalsIgnoreCase(code1)){ //如果验证码错误
			ResponseBean<UserBean> bean = new ResponseBean<UserBean>("no","验证码错误");
			return bean;
		}else{ ////如果验证码正确
			ResponseBean<UserBean> bean = new ResponseBean<UserBean>("yes");
			return bean;
		}
	}
	
	/**
	 * 用户注册信息保存
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Object regist(HttpServletRequest request, HttpServletResponse response){
		
		Map<String, String[]> map = request.getParameterMap();
		UserBean user = new UserBean();
		// 利用令牌判断是否为重复提交
		String token1 = (String)request.getSession().getAttribute("token");  // 获取sessio中的令牌
		request.getSession().removeAttribute("token");  //获取session中的令牌后，立马销毁
		String token2 = request.getParameter("token");
		System.out.println("token1：\t"+token1);
		System.out.println("token2：\t"+token2);
		if(!token2.equals(token1)){ //如果令牌不相等  (字符串比较，equals前面的不能为空)
			request.setAttribute("msg", "您已经提交过数据,请勿刷新");
			return "/jsp/msg.jsp";
		}else{
			try { 
				// 对日期进行转换
				ConvertUtils.register(new MyDateConverter(), Date.class);
				// 封装参数
				BeanUtils.populate(user, map);
				// 调用service层:进行注册
				userService.regist(user);
				// 页面跳转
				request.setAttribute("msg", "注册成功！请去邮箱激活!<br/><small>(已激活？请点击<a href=\"/store_alpha/jsp/login.jsp\">登录</a>)</small>");
				return "/jsp/msg.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			return null;
		
	}
	
	/**
	 * 对用户进行激活
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Object active(HttpServletRequest request, HttpServletResponse response){
		try{
			// 获取激活链接中传递过来的激活码code
			String code = request.getParameter("code");
			UserBean existUser = userService.findUserByCode(code);
			if(existUser == null){ // 如果激活码不存在
				request.setAttribute("msg", "激活码错误，进重新验证");
			} else{
				existUser.setCode(null);
				existUser.setState(2);
				request.setAttribute("msg", "账户已激活，即将为您跳转&emsp;<a href='/store_alpha/jsp/login.jsp'>等不及?请猛击</a>");
				userService.update(existUser);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 跳转到登陆页面
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Object loginUI(HttpServletRequest request, HttpServletResponse response){
		
		return "/jsp/login.jsp";
	}
	
	/**
	 * 跳转到注册页面
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Object registUI(HttpServletRequest request, HttpServletResponse response){
		
		return "/jsp/register.jsp";
	}
	
	/**
	 * 进行用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Object login(HttpServletRequest request, HttpServletResponse response){
		try {
			// 接收数据并封装
			UserBean user = new UserBean();
			Map<String, String[]> map = request.getParameterMap();
			BeanUtils.populate(user, map);
			// 调用业务层
			UserBean existUser = userService.login(user);
			// 处理结果
			if(existUser == null){ // 登录失败:用户名+密码匹配错误,或者用户未激活
				request.setAttribute("msg", "用户名密码错误,或用户未激活");
				return "/jsp/login.jsp";
			}else{ //登录成功
			// 判断是否勾选了自动登录
				String autoLogin = request.getParameter("autoLogin");
				if("true".equals(autoLogin)){ //如果选择了自动登录：将用户信息存储到cookie中
					Cookie cookie = new Cookie("autoLogin",existUser.getUsername()+"#&%&#"+existUser.getPassword());
					cookie.setPath("/store_alpha");
					cookie.setMaxAge(60*60*24); //设置有效期一天
					response.addCookie(cookie);
				}
				
				// 判断是否记住用户名
				String remember = request.getParameter("remember");
				if("true".equals(remember)){//如果勾选了记住用户名
					Cookie cookie = new Cookie("username",existUser.getUsername());
					cookie.setPath("/store_alpha");
					cookie.setMaxAge(60*60*24); //设置有效期一天
					response.addCookie(cookie);
				}
				request.getSession().setAttribute("existUser", existUser);
				response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 退出登陆
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Object exit(HttpServletRequest request, HttpServletResponse response){
		try{
			// 删除session
			request.getSession().removeAttribute("existUser");
			// 删除cookie
			Cookie cookie = new Cookie("autoLogin","");
			cookie.setPath("/store_alpha");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			// 重定向到主页
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
