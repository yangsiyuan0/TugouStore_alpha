package store.web.servlet;


import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import store.domain.PageBean;
import store.domain.ProductBean;
import store.service.IProductService;
import store.utils.BaseServlet;
import store.utils.BeanFactory;
import store.utils.ConvertTypeInCookie;
import store.utils.CookieUtils;

/**
 * 商品模块:主要用于展示
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private IProductService productService = (IProductService) BeanFactory.getBean("productService");
	/*private IProductService productService = new ProductServiceImpl();*/
	/**
	 * 按照商品的分类id(cid)查询对应的商品详情
	 * @param request
	 * @param response
	 * @return
	 */
	public Object findPageByCid(HttpServletRequest request, HttpServletResponse response){
		try{
			// 1. 获取参数:商品的分类ID
			String cid = request.getParameter("cid");
			Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
			// 2. 调用控制层,查询对应的商品:结果封装在Pagebean类中
			PageBean<ProductBean> pageBean = productService.findPageByCid(cid,currentPage);
			request.setAttribute("pageBean", pageBean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/jsp/product_list.jsp";
	}
	
	/**
	 * 根据商品pid查询商品详情，并展示在页面
	 * 记录用户的浏览记录
	 * @param request
	 * @param response
	 * @return
	 */
	
	public Object findByPid(HttpServletRequest request, HttpServletResponse response){
		try{
			//1. 获取参数：商品pid，并调用控制层查询商品详情
			String pid = request.getParameter("pid");
			ProductBean productBean = productService.findByPid(pid);
			request.setAttribute("productBean", productBean);
			String pim = pid+"###"+productBean.getPimage(); //将产品id和图片路径连接
			//2.记录浏览记录：利用cookie
			Cookie[] cookies = request.getCookies();
			Cookie cookie = CookieUtils.findCookie(cookies, "historyLog"); //浏览记录cookie的名称为historyLog
			
			// 2.1 若cookie为空：即用户第一次浏览（还未创建cookie）
			if(cookie == null){
				Cookie history = new Cookie("historyLog", pim);
				history.setPath("/store_alpha");
				history.setMaxAge(60*60*24);
				response.addCookie(history);
				return "/jsp/product_info.jsp";
			}
			
			// 2.2 若cookie不为空：分类分析--是否已经浏览过该商品？   总浏览数是否超出限制长度？
			String value = cookie.getValue();
			LinkedList<String> list = ConvertTypeInCookie.getList(value); //转换为List格式，便于存取数据(利用自定义工具类)
			 // 2.2.1 已经浏览过该商品
			if(list.contains(pim)){ // 已经浏览过该商品
				list.remove(pim);
				list.addFirst(pim);
			} else{
				// 2.2.2 已经浏览过该商品 + 超过最大长度4
				if(list.size() >=4 ){ 
					list.removeLast();
				}
				list.addFirst(pim);
			}
			String historyLog = ConvertTypeInCookie.getString(list);
			Cookie history = new Cookie("historyLog",historyLog);
			history.setPath("/store_alpha");
			history.setMaxAge(24 * 60 * 60);
			response.addCookie(history);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/jsp/product_info.jsp";
	}
	
	/**
	 * 清空浏览记录
	 * @param request
	 * @param response
	 * @return
	 */
	public void clearHistory(HttpServletRequest request, HttpServletResponse response){
		
		try {
			String currentPage = request.getParameter("currentPage");
			String cid = request.getParameter("cid");
			System.out.println("cid:\t"+cid+"currentPage:\t"+currentPage);
			Cookie cookie = new Cookie("historyLog", null);
			cookie.setPath("/store_alpha");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			response.sendRedirect(request.getContextPath()+"/ProductServlet?method=findPageByCid&currentPage="+ currentPage +"&cid="+cid);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}



	