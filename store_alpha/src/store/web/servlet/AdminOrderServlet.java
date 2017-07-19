package store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import store.domain.OrderBean;
import store.domain.OrderItemBean;
import store.service.IOrderService;
import store.utils.BaseServlet;
import store.utils.BeanFactory;

/**
 * 后台页面 -- 订单模块
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	IOrderService orderService = (IOrderService) BeanFactory.getBean("orderService");
	/**
	 * 查询所有订单
	 * @param req
	 * @param resp
	 * @return
	 */
	public Object findAll(HttpServletRequest request,HttpServletResponse response){
		// 接收状态:
		try{
			String state = request.getParameter("state");
			List<OrderBean> list = null;
			if(state == null){ // 若状态为空：则视为查询所有订单
				list = orderService.findAll();
			}else{ // 若状态不为空：则查询指定状态的订单
				int pstate = Integer.parseInt(state);
				list = orderService.findByState(pstate);
			}
			request.setAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/order/list.jsp";
	}
	
	/**
	 * 显示订单细节：Ajax技术
	 * @param req
	 * @param resp
	 * @return
	 */
	public Object showDetail(HttpServletRequest request,HttpServletResponse response){
		// 接收oid:
		String oid = request.getParameter("oid");
		try {
			List<OrderItemBean> list = orderService.showDetail(oid);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"orderBean"});
			JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
			System.out.println(jsonArray.toString());
			response.getWriter().println(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object send(HttpServletRequest request,HttpServletResponse response){
		
		try{
			// 接收oid:
			String oid = request.getParameter("oid");
			OrderBean order = orderService.findByOid(oid);
			order.setState(3);
			orderService.update(order);
			response.sendRedirect(request.getContextPath()+"/AdminOrderServlet?method=findAll");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
