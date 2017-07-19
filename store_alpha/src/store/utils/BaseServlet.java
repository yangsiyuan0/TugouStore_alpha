 package store.utils;


import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;
import store.domain.ResponseBean;
/**
 * 通用的SErvlet的编写:
 * @author admin
 *
 */
public class BaseServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 设置编码
		req.setCharacterEncoding("UTF-8");
		//resp.setContentType("text/html;charset=UTF-8");
		// 2. 获取请求URL中的参数：用于请求分发
		String methodName = req.getParameter("method");
		System.out.println("当前调用的方法:"+methodName);
		if(StringUtils.isBlank(methodName)){ // 如果请求分发参数为空，跳转到404页面
			req.getRequestDispatcher("/404.jsp").forward(req, resp);
			return;
		}
		// 3. 获得子类的Class对象：
		Class<? extends BaseServlet> clazz = this.getClass();
		try {
			// 4. 根据方法名获取方法：利用反射
			Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			// 执行方法：并获取返回的转发路径
			Object path = method.invoke(this,req,resp);
			// 如果路径为空，则为AJAX异步请求：不进行跳转
			if(path instanceof String && StringUtils.isNotBlank(path.toString())){ // 如果转发路径path为非空字符串
				req.getRequestDispatcher(path.toString()).forward(req, resp);
			}else if(path instanceof ResponseBean){ // 如果返回为一个Bean对象
				resp.getWriter().println(JSONObject.fromObject(path).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("/404.jsp").forward(req, resp);
		}
	}
}

