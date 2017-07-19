package store.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import store.domain.CategoryBean;
import store.domain.ProductBean;
import store.service.IProductService;
import store.utils.BeanFactory;
import store.utils.UUIDFilenameUtils;
import store.utils.UploadDirectoryUtils;

/**
 * 后台管理页面 -- 分类模块：修改商品功能
 */
public class AdminEditProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try{
			// 初始化ProductBean对象，用于封装新Product; 初始化Map集合，用于存放获取的参数值; 初始化fileName变量，保存上传的图片路径名称
			ProductBean productBean = new ProductBean();
			Map<String, String> map = new HashMap<String,String>();
			String fileName=null;
			// 1. 利用UpFile技术，对含有上传文件的表单进行接收
			// ① 得到工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// ② 创建解析器
			ServletFileUpload sfu = new ServletFileUpload(factory);
			sfu.setHeaderEncoding("UTF-8");// 解决中文文件名上传乱码.
			sfu.setFileSizeMax(10*1024*1024); //限制单个文件最大内存为10Mb
			//sfu.setSizeMax(1024*1024); //限制整个表单大小为1MB
			// ③ 解析request，获得FileItem
			List<FileItem> list = sfu.parseRequest(request);
			// ④ 对获取的FileItem集合进行遍历，并分类保存
			for (FileItem fileItem : list) {
				if(fileItem.isFormField()){ // 如果是普通表单项，直接保存带map集合中
					String name = fileItem.getFieldName();
					String value = fileItem.getString("UTF-8"); // 解决的是普通项的中文乱码.
					map.put(name, value);
				}else{ // 【如果是文件上传项（图片）：进行保存】
					String rootlPath = request.getServletContext().getRealPath("/products"); // 文件均放置在products文件夹下
					fileName = fileItem.getName(); // 获取fileName文件名
					String name = getfileName(fileName); // 调用方法：分浏览器获取实际文件名（去除绝对路径）
					String uuidName = UUIDFilenameUtils.getUUIDName(name); // 获取唯一文件名
					String dividePath = UploadDirectoryUtils.getDividePath(uuidName); // 设置目录分离
					File file = new File(rootlPath+File.separator+dividePath);
					if(!file.exists()){
						file.mkdirs();  //创建多层目录
					}
					fileItem.write( new File(rootlPath+File.separator+dividePath+File.separator+uuidName));
					fileName = "products/"+dividePath+uuidName;
					System.out.println("fileName:\t" + fileName);
				}
			}
			// 2.封装数据:
			BeanUtils.populate(productBean, map);
			productBean.setPdate(new Date());
			productBean.setPflag(0);
			productBean.setPimage(fileName);
			CategoryBean categoryBean = new CategoryBean();
			categoryBean.setCid(map.get("cid"));
			productBean.setCategoryBean(categoryBean);
			
			// 存入到数据库:
			IProductService productService = (IProductService) BeanFactory.getBean("productService");
			productService.update(productBean);
			
			// 页面跳转:
			response.sendRedirect(request.getContextPath()+"/AdminProductServlet?method=findProductByPage&currentPage=1");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 用于处理ie浏览器上传时:图片名为全路径的问题
	 * @param name
	 * @return
	 */
	public String getfileName(String name){ // name为filename的值
		int index = name.lastIndexOf("\\");
		if(index != -1){
			name = name.substring(index+1);
		}
		return name;
	}

}
