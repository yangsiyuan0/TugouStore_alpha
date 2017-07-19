package store.web.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import store.domain.CategoryBean;
import store.domain.PageBean;
import store.domain.ProductBean;
import store.service.ICategoryService;
import store.service.IProductService;
import store.utils.BaseServlet;
import store.utils.BeanFactory;

/**
 * 后台管理页面 -- 分类管理
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private IProductService productService = (IProductService) BeanFactory.getBean("productService");
	ICategoryService categoryService = (ICategoryService) BeanFactory.getBean("categoryService");
	/**
	 * 分页查询商品数据
	 */
	public Object findProductByPage(HttpServletRequest request, HttpServletResponse response){
		try{
			// 接收参数：当前页面
			Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
			// 调用业务层：分页查询
			PageBean<ProductBean> pageBean = productService.findProductByPage(currentPage);
			request.setAttribute("pageBean", pageBean);
				// 调用业务层：查询所有的商品分类信息
				List<CategoryBean> list = categoryService.findAllCategory();
				request.setAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/product/list.jsp";
	}

	/**
	 * 按照cid进行分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	public Object findPageByCid(HttpServletRequest request, HttpServletResponse response){
		try{
			// 接收参数：当前页面
			Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
			String cid = request.getParameter("cid");
			String cname = request.getParameter("cname");
			if(StringUtils.isBlank(cname)){ // 如果为查询全部
				// 调用业务层：分页查询
				PageBean<ProductBean> pageBean = productService.findProductByPage(currentPage);
				request.setAttribute("pageBean", pageBean);
				// 调用业务层：查询所有的商品分类信息
				List<CategoryBean> list = categoryService.findAllCategory();
				request.setAttribute("list", list);
				request.setAttribute("cname","全部");
			}else{
				// 调用业务层：分页查询
				PageBean<ProductBean> pageBean = productService.findPageByCid(cid,currentPage);
				request.setAttribute("pageBean", pageBean);
				// 调用业务层：查询所有的商品分类信息
				List<CategoryBean> list = categoryService.findAllCategory();
				request.setAttribute("list", list);
				request.setAttribute("cname",cname);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/product/list.jsp";
	}
	
	/**
	 * 对商品进行下架操作（逻辑删除）
	 * @param request
	 * @param response
	 * @return
	 */
	public Object deleteProduct(HttpServletRequest request, HttpServletResponse response){
		try{
			// 接收参数：商品的pid
			String pid = request.getParameter("pid");
			// 调用业务层，对商品进行逻辑删除 【使用一个通用的产品数据更新方法】
			 // 先根据pid获取完整的productBean详情
			ProductBean productBean = productService.findByPid(pid);
			 // 修改pflag为1：即下架状态
			productBean.setPflag(1);
			 // 对productBean进行更新
			productService.update(productBean);
			// 转发页面
			response.sendRedirect(request.getContextPath()+"/AdminProductServlet?method=findProductByPage&currentPage=1");
		}catch(Exception e){
		}
		return null;
	}
	
	/**
	 * 跳转到商品添加页面 【商品添加操作，参见AdminAddProductServlet】
	 * @param request
	 * @param response
	 * @return
	 */
	public Object addUI(HttpServletRequest request, HttpServletResponse response){
		
		try{
			// 查询产品分类信息Category
			List<CategoryBean> list = categoryService.findAllCategory();
			request.setAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/product/add.jsp";
	}
	
	/**
	 * 对选中的多个商品进行逻辑删除：下架
	 * @param request
	 * @param response
	 * @return
	 */
	public Object deleteAll(HttpServletRequest request, HttpServletResponse response){
		try{
			String[] ids = request.getParameterValues("ids");
			if(ids != null){  //需要对ids进行判空：以防在没有选择任何删除的条目情况下，误触回车
				productService.deleteAllProduct(ids);
			}
			response.sendRedirect(request.getContextPath()+"/AdminProductServlet?method=findProductByPage&currentPage=1");
		}catch(Exception e){
			
		}
		return null;
	}
	
	/**
	 * 根据关键字+cid参数，对商品进行模糊搜索????????????
	 * @param request
	 * @param response
	 * @return
	 */
	public Object search(HttpServletRequest request, HttpServletResponse response){
		try{
			// 1. 接受参数 cid + keyword
			String keyword = request.getParameter("keyword");
			String cid = request.getParameter("cid");
			// 调用业务层：查询所有的商品分类信息
			List<CategoryBean> list = categoryService.findAllCategory();
			request.setAttribute("list", list);
			/*System.out.println("keyword:\t"+keyword);
			System.out.println("cid:\t"+cid);*/
			if(StringUtils.isBlank(cid)){ // 如果cid为空，则查询所有
				List<ProductBean> productList = productService.search(keyword);
				request.setAttribute("productList", productList);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/product/search_list.jsp";
	}
	
	
	/**
	 * 查询已下架的商品
	 */
	public Object findByPushDown(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 调用业务层
			List<ProductBean> list = productService.findByPushDown();
			req.setAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/product/pushDown_list.jsp";
	}
	
	/**
	 * 上架商品:
	 */
	public String pushUp(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 接收参数:
			String pid = req.getParameter("pid");
			// 调用业务层:
			ProductBean productBean = productService.findByPid(pid);
			productBean.setPflag(0);
			productService.update(productBean);
			resp.sendRedirect(req.getContextPath()+"/AdminProductServlet?method=findProductByPage&currentPage=1");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改商品(步骤一)：跳转到修改页面
	 */
	public Object editUI(HttpServletRequest request, HttpServletResponse response){
		
		try {
			// 接受参数：pid信息
			String pid = request.getParameter("pid");
			ProductBean productBean = productService.findByPid(pid); // 根据pid查询商品的详细信息
			request.setAttribute("productBean", productBean);
			// 查询产品分类信息Category
			List<CategoryBean> list = categoryService.findAllCategory();
			request.setAttribute("list", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/admin/product/edit.jsp";
	}
}
