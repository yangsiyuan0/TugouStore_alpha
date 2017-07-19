package store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import store.domain.CartBean;
import store.domain.CartItemBean;
import store.domain.ProductBean;
import store.service.IProductService;
import store.utils.BaseServlet;
import store.utils.BeanFactory;

/**
 * 购物车模块的处理:在session域中进行存取\增删操作
 *  
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 获取CartBean对象(存储在session中,名称为cart)
	 * @param name
	 * @return
	 */
	public CartBean getCart(HttpServletRequest request){
		// 从session中获取cart对象
		HttpSession session = request.getSession();
		CartBean cart = (CartBean) session.getAttribute("cart");
		// 进行判断:cart是否存在
		if(cart == null){ // 如果不存在，则新建一个存入session
			cart = new CartBean();
			session.setAttribute("cart", cart);
		}
		return cart;
	}

   /**
    * 向购物车增加商品(需要连接数据库:获取商品对象Product)
    * @param request
    * @param response
    * @return
    */
   public Object addCart(HttpServletRequest request, HttpServletResponse response){
	   try{
		   // 获取参数:商品编号 + 商品数量
		   String pid = request.getParameter("pid");
		   Integer count =  Integer.parseInt(request.getParameter("count"));
		   // 按照pid查询该商品
		   IProductService productService = (IProductService) BeanFactory.getBean("productService");
		   ProductBean productBean = productService.findByPid(pid);
		   // 封装CartBean对象
		   CartItemBean cartItemBean = new CartItemBean();
		   cartItemBean.setItemCount(count);
		   cartItemBean.setProductBean(productBean);
		   // 调用用CartBean对象中的add()方法
		   CartBean cart = getCart(request); //利用封装的自定义方法获取cart对象
		   cart.add(cartItemBean); 
		   // 页面跳转
		   response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return null;
   }

   /**
    * 删除购物车中的商品(直接在Map[存储在session中]中处理,无需操作数据库)
    * @param request
    * @param response
    * @return
    */
   public Object removeCart(HttpServletRequest request, HttpServletResponse response){
	   try{
		   // 接收参数：要删除的商品的pid
		   String pid = request.getParameter("pid");
		   // 调用方法remove()删除对应商品
		   CartBean cart = getCart(request);
		   cart.remove(pid);
		   // 页面跳转
		   response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return null;
   }
   
   /**
    * 清空购物车(直接在Map[存储在session中]中处理,无需操作数据库)
    * @param request
    * @param response
    * @return
    */
   public Object clearCart(HttpServletRequest request, HttpServletResponse response){
	   try{
		   // 调用方法clear()，清空购物车
		   CartBean cart = getCart(request);
		   cart.clear();
		  // 页面跳转
		   response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return null;
   }
}
