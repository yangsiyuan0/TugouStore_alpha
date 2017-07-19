package store.service;

import java.sql.SQLException;
import java.util.List;

import store.domain.PageBean;
import store.domain.ProductBean;

public interface IProductService {

	// 查找热门商品
	public List<ProductBean> findByHot() throws SQLException;
	// 查找最新商品
	public List<ProductBean> findByNew() throws SQLException;
	// 根据分类cid查找分页数据
	public PageBean<ProductBean> findPageByCid(String cid,Integer currentPage) throws SQLException;
	// 根据pid查询商品详情
	public ProductBean findByPid(String pid) throws SQLException;
	// 分页查找商品（不区分Category）
	public PageBean<ProductBean> findProductByPage(Integer currentPage) throws SQLException;
	// (后台页面)通用的产品信息更新方法
	public void update(ProductBean productBean) throws SQLException;
	// 保存新增的商品信息
	public void save(ProductBean productBean) throws SQLException;
	// 对选中的多个商品进行下架
	public void deleteAllProduct(String[] ids)  throws SQLException;
	// 查询所有已下架的商品
	public List<ProductBean> findByPushDown()  throws SQLException ;
	// 按照cname关键字,对商品进行模糊查询
	public List<ProductBean> search(String keyword)throws SQLException ;
}
