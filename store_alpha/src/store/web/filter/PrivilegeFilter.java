package store.web.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import store.domain.UserBean;


public class PrivilegeFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		UserBean existUser = (UserBean) req.getSession().getAttribute("existUser");
		if(existUser == null){
			req.setAttribute("msg", "您还没有<a href=\"/store_alpha/jsp/login.jsp\">登录</a>!没有权限访问！");
			req.getRequestDispatcher("/jsp/msg.jsp").forward(req, response);
			return;
		}
		chain.doFilter(req, response);
	}

	@Override
	public void destroy() {
		
	}

}
