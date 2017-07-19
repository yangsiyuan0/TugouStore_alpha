package store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import store.domain.ProductBean;
import store.service.IProductService;
import store.utils.BaseServlet;
import store.utils.BeanFactory;

/**
 * 绑定在项目根目录下的空白index页面
 *  1. 对真实的/jsp/index.jsp首页进行初始化:显示最新\热门商品
 *  2. 向真实首页跳转
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String index(HttpServletRequest request, HttpServletResponse response){
		
		try {
			IProductService productService = (IProductService) BeanFactory.getBean("productService");
			/*IProductService productService = new ProductServiceImpl();*/
			// 调用控制层:查询热门商品+最新商品
			List<ProductBean> hotList = productService.findByHot();
			List<ProductBean> newList = productService.findByNew();
			// 将商品列表保存到request域
			request.setAttribute("hotList", hotList);
			request.setAttribute("newList", newList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/index.jsp";
	}
}
