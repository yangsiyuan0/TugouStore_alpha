package store.web.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import store.domain.UserBean;
import store.service.IUserService;
import store.service.impl.UserServiceImpl;
import store.utils.CookieUtils;


/**
 * 实现自动登录方法
 */
public class AutoLoginFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 对request进行类型强转：可以操作session、cookie
		HttpServletRequest req = (HttpServletRequest) request;
		// 获取cookie
		Cookie[] cookies = req.getCookies();
		Cookie autoLogin = CookieUtils.findCookie(cookies, "autoLogin");
		// 获取session中的登录信息：如果存在，则直接放行
		HttpSession session = req.getSession();
		Object existUser = session.getAttribute("existUser");
		if(existUser == null && autoLogin != null && StringUtils.isNotBlank(autoLogin.toString())){
			//当cookie不为空，且session不存在时，对cookie转存
			String value = autoLogin.getValue();
			String username = value.split("#&%&#")[0];
			String password = value.split("#&%&#")[1];
			UserBean user = new UserBean();
			user.setUsername(username);
			user.setPassword(password);
			// 检查cookie是否被篡改：通过数据库查询实现
			IUserService userService = new UserServiceImpl();
			try {
				existUser = userService.login(user);
				if(existUser != null){ //如果cookie未被篡改
					session.setAttribute("existUser", existUser);
				}
				chain.doFilter(req, response);
				return ;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
