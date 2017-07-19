package store.web.filter;


import java.io.IOException;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import store.utils.MyEncodingInvocationHandler;


/**
 * 全站编码过滤
 */
public class EncodingFilter implements Filter {
	
	private String encoding = "UTF-8";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request = (ServletRequest)Proxy.newProxyInstance(request.getClass().getClassLoader(), request.getClass().getInterfaces(), new MyEncodingInvocationHandler((HttpServletRequest)request,encoding));
		response.setContentType("text/html;charset="+encoding);
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter("encoding");
	}
	
	public void destroy() {
	}

}
