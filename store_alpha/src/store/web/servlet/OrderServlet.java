package store.web.servlet;


import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import store.domain.CartBean;
import store.domain.CartItemBean;
import store.domain.OrderBean;
import store.domain.OrderItemBean;
import store.domain.PageBean;
import store.domain.UserBean;
import store.service.IOrderService;
import store.utils.BaseServlet;
import store.utils.BeanFactory;
import store.utils.PaymentUtil;
import store.utils.UUIDUtils;

/**
 * 订单模块
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	IOrderService orderService = (IOrderService) BeanFactory.getBean("orderService");
	
	/**
	 * 创建订单: 订单的相关数据均来自购物车（存储在sesion中【cart】）
	 * @param request
	 * @param response
	 * @return
	 */
	public Object createOrder(HttpServletRequest request, HttpServletResponse response){
		// 1. 从session中获取登录用户的信息
		UserBean existUser = (UserBean) request.getSession().getAttribute("existUser");
		if(existUser == null){
			request.setAttribute("msg", "您还没有登录呦!");
			return "/jsp/login.jsp";
		}

		// 2. 从session中获取购物车信息
		CartBean cart = (CartBean) request.getSession().getAttribute("cart");
		if(cart == null){// 购物车为空：提醒用户(转发到信息页)
			request.setAttribute("msg", "您没有购买任何商品,立即<a href='/store_alpha/'>开始购物</a>~~");
			return "/jsp/msg.jsp";
		}
		
		// 2. 初始化Orderbean对象：①随机订单号，②获取当前时间作为下单时间,③订单状态1：未付款
		OrderBean orderBean = new OrderBean();
		orderBean.setUserBean(existUser); // 设置用户信息
		String oid = UUIDUtils.getUUID();
		Date orderTime = new Date();
		orderBean.setOrderTime(orderTime);
		orderBean.setOid(oid);
		orderBean.setState(1);
		
		// 从购物车中获取商品信息
		double total = cart.getTotal(); // 商品总金额
		orderBean.setTotal(total);
		for (CartItemBean cartItemBean: cart.getMap().values()) { // 获取商品条目信息:保存至OrderItemBean
			OrderItemBean orderItemBean = new OrderItemBean();
			orderItemBean.setCount(cartItemBean.getItemCount()); //商品数量
			orderItemBean.setSubtotal(cartItemBean.getSubtotal()); //金额小计
			orderItemBean.setItemId(UUIDUtils.getUUID()); //商品id：随机字符串
			orderItemBean.setProductBean(cartItemBean.getProductBean()); // 对应的product对象
			orderItemBean.setOrderBean(orderBean); //隶属的OrderBean对象
			// 将orderItemBean保存到OrderBean对象中
			orderBean.getOrderItems().add(orderItemBean);
		}
		
		// 3. 调用业务层：将orderBean保存到数据库
		orderService.createOrder(orderBean);
		
		// 4. 清空购物车（★★★★）
		cart.clear();
		// 5. 将页面跳转
		request.setAttribute("orderBean", orderBean);
		return "/jsp/order_info.jsp";
	}
	
	/**
	 * 查询用户的所有订单(分页查询):
	 *  - 从session中获取用户的信息
	 *  - 接收当前页面数currentPage:用于分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	public Object findOrderByUid(HttpServletRequest request, HttpServletResponse response){
		
			try {
				// 1. 接收参数:当前页面数
				Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
				// 2. 从session中获取用户信息
				UserBean existUser = (UserBean) request.getSession().getAttribute("existUser");
				if(existUser == null){ //由于session可能过期,导致用户退出登录状态,所以需要对existUser进行判空
					request.setAttribute("msg", "登录获取订单详情");
					return "/jsp/login.jsp";
					/*response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");*/
				}else{
					// 3. 调用业务层:对订单进行查询
					PageBean<OrderBean> pageBean = orderService.findOrderByUid(existUser.getUid(),currentPage);
					request.setAttribute("pageBean", pageBean);
					return "/jsp/order_list.jsp";
				}
			} catch (Exception e) {
				e.printStackTrace();
		}
		
			return null;
	}
	
	/**
	 * 根据订单id查询订单的方法:findByOid
	 */
	public String findByOid(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 接收参数:
			String oid = req.getParameter("oid");
			// 调用业务层查询:
			OrderBean orderBean = orderService.findByOid(oid);
			req.setAttribute("orderBean", orderBean);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/jsp/order_info.jsp";
	}
	
	/**
	 * 为订单付款的方法:
	 */
	public String payOrder(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 接收参数:
			String oid = req.getParameter("oid");
			String name = req.getParameter("name");
			String address = req.getParameter("address");
			String telephone = req.getParameter("telephone");
			String pd_FrpId = req.getParameter("pd_FrpId");
			
			// 修改数据库 姓名，地址，电话.
			OrderBean orderBean = orderService.findByOid(oid);
			orderBean.setAddress(address);
			orderBean.setName(name);
			orderBean.setTelephone(telephone);
			
			orderService.update(orderBean); //调用业务层：存储用户的物流信息
			// 付款:跳转到网银的界面:
			// 准备参数:
			String p0_Cmd = "Buy";
			String p1_MerId = "10001126856";
			String p2_Order = oid;
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			String p8_Url = "http://localhost:8080/store_v2.0/OrderServlet?method=callBack";
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
			
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);
			
			resp.sendRedirect(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 付款成功后执行的方法:callBack
	 */
	public String callBack(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 接收参数:
			String oid = req.getParameter("r6_Order");
			String money = req.getParameter("r3_Amt");
			
			// 修改订单状态:
			OrderBean order = orderService.findByOid(oid);
			order.setState(2); 
			orderService.update(order);
			
			/*req.setAttribute("oid", oid);
			req.setAttribute("money", money);*/
			req.setAttribute("msg", "您的订单:"+oid+"付款成功,付款的金额为:"+money);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/jsp/msg.jsp";
	}
	
	public String finish(HttpServletRequest req,HttpServletResponse resp){
		try{
			String oid = req.getParameter("oid");
			OrderBean order = orderService.findByOid(oid);
			order.setState(4);
			orderService.update(order);
			
			resp.sendRedirect(req.getContextPath()+"/OrderServlet?method=findOrderByUidfindByUid&currentPage=1");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
