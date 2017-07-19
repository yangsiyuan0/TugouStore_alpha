package test;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Demo1Servlet
 */
public class Demo1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String method = request.getParameter("method");
		System.out.println("get:\t"+method);*/
		Map<String, String[]> map = request.getParameterMap();
		for (String method : map.keySet()) {
			System.out.println(method);
			String[] strings = map.get(method);
			System.out.println(strings[0]);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		System.out.println("post:\t"+method);
	}

}
