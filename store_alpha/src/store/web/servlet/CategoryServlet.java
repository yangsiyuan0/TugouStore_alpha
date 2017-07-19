package store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import store.domain.CategoryBean;
import store.service.ICategoryService;
import store.utils.BaseServlet;
import store.utils.BeanFactory;

/**
 * 用户模块
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 查询所有的商品分类:呈现到导航栏
	 * @param request
	 * @param response
	 * @return
	 */
	public Object findAllCategory(HttpServletRequest request, HttpServletResponse response){
		try{
			ICategoryService categoryService = (ICategoryService) BeanFactory.getBean("categoryService");
			/*ICategoryService categoryService = new CategoryServiceImpl();*/
			// 调用控制层:获取所有的产品分类
			List<CategoryBean> list = categoryService.findAllCategory();
			//将产品分类列表:以json文件存储
			String categoryList = JSONArray.fromObject(list).toString();
			// 将Json文件传回页面:(页面AJAX异步获取)
			response.getWriter().println(categoryList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null; //返回原页面
	}

}
