package store.dao;

import java.sql.SQLException;
import java.util.List;

import store.domain.ProductBean;

public interface IProductDao {

	//查找热门商品
	public List<ProductBean> findByHot() throws SQLException;
	//查找最新商品
	public List<ProductBean> findByNew() throws SQLException;
	// 按照cid(商品分类ID)查询商品总数
	public Integer findTotalItem(String cid) throws SQLException;
	// 分页查询
	public List<ProductBean> findCurrentPageByCid(String cid, int begin, Integer pageSize) throws SQLException;
	// 根据商品pid查询商品详情
	public ProductBean findByPid(String pid)  throws SQLException;
	// (后台页面)查询商品的总数
	public Integer findTotalItem() throws SQLException;
	// (后台页面)查询本页的商品数据
	public List<ProductBean> findProductByPage(int begin,Integer pageSize) throws SQLException;
	// 根据商品pid对商品信息进行更新
	public void update(ProductBean productBean) throws SQLException;
	// 保存新增的商品信息
	public void save(ProductBean productBean) throws SQLException;
	// 查询所有已下架的商品
	public List<ProductBean> findByPushDown()  throws SQLException ;
	// 按照cname模糊查询商品
	public List<ProductBean> search(String keyword)  throws SQLException ;
}
