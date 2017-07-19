package store.web.servlet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import store.domain.CategoryBean;
import store.service.ICategoryService;
import store.utils.BaseServlet;
import store.utils.BeanFactory;
import store.utils.UUIDUtils;

/**
 * 后台管理页面 -- 分类模块
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	ICategoryService categoryService = (ICategoryService) BeanFactory.getBean("categoryService");
	/**
	 * 查找所有的商品分类，并展示在页面
	 * @param request
	 * @param response
	 * @return
	 */
    public Object findAllCategory(HttpServletRequest request, HttpServletResponse response){
    	try {
    		// 调用业务层查找所有Category (同前台页面)
			List<CategoryBean> list = categoryService.findAllCategory();
			// 存入request域中，完成页面跳转:
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "/admin/category/list.jsp";
    }

    /**
     * 增加Category(步骤一)：跳转到添加分类页面
     * @param request
     * @param response
     * @return
     */
    public Object addCategory(HttpServletRequest request, HttpServletResponse response){
    	return "/admin/category/add.jsp";
    }
    
    /**
     * 增加Category(步骤二)：保存添加的Category
     * @param request
     * @param response
     * @return
     */
    public Object saveCategory(HttpServletRequest request, HttpServletResponse response){
    	try{
	    	// 获取参数：添加的Category的名称
	    	String cname = request.getParameter("cname");
	    	// 对新增的Category进行封装：cid，cname
	    	CategoryBean categoryBean = new CategoryBean();
	    	categoryBean.setCid(UUIDUtils.getUUID());
	    	categoryBean.setCname(cname);
	    	// 调用业务层，进行保存
	    	categoryService.save(categoryBean);
	    	response.sendRedirect(request.getContextPath()+"/AdminCategoryServlet?method=findAllCategory"); //进行重定向，以防止重复提交
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * 删除Category: 
     *  * 逻辑删除：将category的状态status更改为0(下架状态)
     * @param request
     * @param response
     * @return
     */
    public Object deleteCategory(HttpServletRequest request, HttpServletResponse response){
    	try{
    		// 接受参数：要删除的category的cid
    		String cid = request.getParameter("cid");
    		// 调用业务层，更改category的状态值
    		categoryService.delete(cid);
    		// 重定向
    		response.sendRedirect(request.getContextPath()+"/AdminCategoryServlet?method=findAllCategory");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    
    
    /**
     * 修改Category(步骤一)：实现跳转编辑页面
     *  * 实现对要编辑的category的信息展示
     * @param request
     * @param response
     * @return
     */
    public Object editCategory(HttpServletRequest request, HttpServletResponse response){
    	try{
    		// 接收参数：要修改的Category的cid
    		String cid = request.getParameter("cid");
    		// 查询对应Category的详细信息
    		CategoryBean categoryBean = categoryService.findByCid(cid);
    		request.setAttribute("categoryBean", categoryBean);
    		return "/admin/category/edit.jsp";
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * 修改Category(步骤二)：保存修改后的category
     * @param request
     * @param response
     * @return
     */
    public Object updateCategory(HttpServletRequest request, HttpServletResponse response){
    	try{
    		// 接收参数：要修改的Category的cid + 修改后的cname
    		Map<String, String[]> map = request.getParameterMap();
    		// 封装CategoryBean
    		CategoryBean categoryBean = new CategoryBean();
    		BeanUtils.populate(categoryBean, map);
    		// 调用业务层，都修改数据进行更改
    		categoryService.update(categoryBean);
    		// 页面跳转：重定向回列表页
    		response.sendRedirect(request.getContextPath()+"/AdminCategoryServlet?method=findAllCategory");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
}
