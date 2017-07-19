package store.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import store.dao.IProductDao;
import store.domain.PageBean;
import store.domain.ProductBean;
import store.service.IProductService;
import store.utils.BeanFactory;
import store.utils.C3P0Utils;

public class ProductServiceImpl implements IProductService {
	private IProductDao productDao = (IProductDao) BeanFactory.getBean("productDao");
	/*private IProductDao productDao = new ProductDaoImpl();*/
	/**
	 * 查找热门商品
	 */
	@Override
	public List<ProductBean> findByHot() throws SQLException{
		return productDao.findByHot();
	}

	/**
	 * 查找最新商品
	 */
	@Override
	public List<ProductBean> findByNew() throws SQLException{
		return productDao.findByNew();
	}

	/**
	 * 按照商品分类ID查找商品
	 */
	@Override
	public PageBean<ProductBean> findPageByCid(String cid,Integer currentPage) throws SQLException {
		//初始化PageBean的参数
		PageBean<ProductBean> pageBean = new PageBean<ProductBean>();
		// 1. 当前页数
		pageBean.setCurrentPage(currentPage);
		// 2. 每页条目数
		Integer pageSize = 12;
		pageBean.setPageSize(pageSize);
		// 3. 总条目数:向数据库中查询
		Integer totalItem = productDao.findTotalItem(cid); //查询对应商品分类的商品总数
		double totalTemp = totalItem;
		pageBean.setTotalItem(totalItem);
		// 4. 总页数
		Double totalPage = Math.ceil(totalTemp/pageSize);
		pageBean.setTotalPage(totalPage.intValue());
		// 5. 当前页面详情:通过起始数,条目数向数据库查询
		int begin = (currentPage-1)*pageSize;
		List<ProductBean> list = productDao.findCurrentPageByCid(cid,begin,pageSize);
		pageBean.setList(list);
		
		return pageBean;
	}

	/**
	 * 根据商品pid查询商品详情
	 */
	@Override
	public ProductBean findByPid(String pid) throws SQLException {
		return productDao.findByPid(pid);
	}

	/**
	 * (后台页面)：分页查询商品（不区分cid）
	 *  * 封装PageBean<ProductBean>对象
	 */
	@Override
	public PageBean<ProductBean> findProductByPage(Integer currentPage) throws SQLException {
		// 初始化PageBean
		PageBean<ProductBean> pageBean = new PageBean<ProductBean>();
		// 1. 设置当前页 + 每页item数量 
		pageBean.setCurrentPage(currentPage);
		Integer pageSize = 12;
		pageBean.setPageSize(pageSize);
		// 2. 查询并设置总items数量totalItem + 总页数
		Integer totalItem = productDao.findTotalItem();
		pageBean.setTotalItem(totalItem);
		double total = totalItem;
		Double totalPage = Math.ceil(total/pageSize);
		pageBean.setTotalPage(totalPage.intValue());
		// 3. 查询本页数据，并设置List<ProductBean>
		int begin = (currentPage-1)*pageSize;
		List<ProductBean> list = productDao.findProductByPage(begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * 根据商品pid对商品信息进行更新
	 */
	@Override
	public void update(ProductBean productBean) throws SQLException {
		productDao.update(productBean);
	}

	@Override
	public void save(ProductBean productBean) throws SQLException {
		productDao.save(productBean);
	}

	/**
	 * 对选中的多个商品进行下架(利用事务)
	 */
	@Override
	public void deleteAllProduct(String[] ids) throws SQLException {
		Connection conn = null;
		try {
			conn = C3P0Utils.getConnection();
			conn.setAutoCommit(false);
			for (String id : ids) { //逐个更改pflag为1
				ProductBean productBean = productDao.findByPid(id);
				productBean.setPflag(1);
				productDao.update(productBean);
			}
			DbUtils.commitAndCloseQuietly(conn);
		} catch (Exception e) {
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(conn);
		}
	}

	/**
	 * 查询所有已下架的商品
	 */
	@Override
	public List<ProductBean> findByPushDown()  throws SQLException {
		
		return productDao.findByPushDown();
	}

	/**
	 * 按照cname关键字,对商品进行模糊查询
	 */
	@Override
	public List<ProductBean> search(String keyword) throws SQLException {
		return productDao.search(keyword);
	}

}
