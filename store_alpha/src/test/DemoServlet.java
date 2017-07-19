package test;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import store.utils.BaseServlet;

/**
 * Servlet implementation class DemoServlet
 */
public class DemoServlet extends BaseServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	public Object test(HttpServletRequest req, HttpServletResponse resp){
		try {
			resp.getWriter().println("test已执行");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
