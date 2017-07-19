package store.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;import org.apache.commons.dbutils.handlers.ScalarHandler;

import store.dao.IProductDao;
import store.domain.ProductBean;
import store.utils.C3P0Utils;

public class ProductDaoImpl implements IProductDao {
	private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
	/**
	 * 查找最热商品
	 * @throws SQLException 
	 */
	@Override
	public List<ProductBean> findByHot() throws SQLException {
		// 查找标记为热门商品中的9个
		String sql = "select * from product where is_hot = ? limit ?,?";
		List<ProductBean> hostList = qr.query(sql, new BeanListHandler<ProductBean>(ProductBean.class), 1,0,9);
		return hostList;
	}
	/**
	 * 查找最新商品
	 * @throws SQLException 
	 */
	@Override
	public List<ProductBean> findByNew() throws SQLException {
		// 按照日期pdate,查询最新的9个
		String sql = "select * from product order by pdate desc limit ?,? ";
		List<ProductBean> newList = qr.query(sql, new BeanListHandler<ProductBean>(ProductBean.class), 0,9);
		return newList;
	}
	
	/**
	 * 根据商品分类(cid)查询，所以没有下架(pflag=0)的商品总数
	 * @throws SQLException 
	 */
	@Override
	public Integer findTotalItem(String cid) throws SQLException {
		String sql = "select count(*) from product where cid = ? and pflag = ?";
		Long count = (Long)qr.query(sql, new ScalarHandler(), cid,0);
		return count.intValue();
	}
	
	/**
	 * 对商品进行分页查询
	 * @throws SQLExceptio
	 */
	@Override
	public List<ProductBean> findCurrentPageByCid(String cid, int begin, Integer pageSize) throws SQLException{
		String sql = "select * from product where cid = ? and pflag = ? order by pdate desc limit ?,?";
		List<ProductBean> list = qr.query(sql, new BeanListHandler<ProductBean>(ProductBean.class), cid,0,begin,pageSize);
		return list;
	}
	
	/**
	 * 根据商品pid，查询商品详情
	 */
	@Override
	public ProductBean findByPid(String pid) throws SQLException {
		String sql = "select * from product where pid = ?";
		ProductBean productBean = qr.query(sql, new BeanHandler<ProductBean>(ProductBean.class), pid);
		return productBean;
	}
	
	/**
	 * (后台页面)查询商品总数
	 */
	@Override
	public Integer findTotalItem() throws SQLException {
		String sql = "select count(*) from product where pflag = ?";
		Long count = (Long) qr.query(sql, new ScalarHandler(),0);
		return count.intValue();
	}
	
	/**
	 *  (后台页面)：查询本页的商品数据
	 */
	@Override
	public List<ProductBean> findProductByPage(int begin,Integer pageSize) throws SQLException {
		String sql = "select * from product where pflag = ? order by cid ,pdate desc limit ?,?";
		List<ProductBean> list = qr.query(sql, new BeanListHandler<ProductBean>(ProductBean.class), 0,begin,pageSize);
		return list;
	}
	
	/**
	 * 根据商品pid对商品信息进行更新
	 */
	@Override
	public void update(ProductBean product) throws SQLException {
		// 由于cid以CategoryBean对象保存，没有被封装，故此处不予更新
		String sql = "UPDATE `product` SET `pname`=?, `market_price`=?, `shop_price`=?, `pimage`=?, `pdate`=?, `is_hot`=?, `pdesc`=?, `pflag`=? WHERE (`pid`=?)";
		Object[] params = { product.getPname(), product.getMarket_price(), product.getShop_price(),
				product.getPimage(),new Date(),product.getIs_hot(), product.getPdesc(), product.getPflag(),product.getPid()
				 };
		System.out.println("sql开始=======");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("sql结束=======");
		int update = qr.update(sql, params);
		System.out.println("sql结果:"+update);
	}
	
	/**
	 * 保存新增的商品信息
	 */
	@Override
	public void save(ProductBean product) throws SQLException {
		String sql = "insert into product values (?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { product.getPid(), product.getPname(), product.getMarket_price(), product.getShop_price(),
				product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getPflag(),
				product.getCategoryBean().getCid() };
		qr.update(sql, params);
	}
	
	/**
	 * 查询所有已下架的商品
	 * @throws SQLException 
	 */
	@Override
	public List<ProductBean> findByPushDown() throws SQLException {
		String sql = "select * from product where pflag = ? order by pdate desc";
		List<ProductBean> list = qr.query(sql, new BeanListHandler<ProductBean>(ProductBean.class), 1);
		return list;
	}
	
	/**
	 * 按照cname关键字,模糊查询商品列表
	 */
	@Override
	public List<ProductBean> search(String keyword) throws SQLException {
		String sql = "select * from product where pname like ? order by pdate desc";
		List<ProductBean> list = qr.query(sql,  new BeanListHandler<ProductBean>(ProductBean.class), "%"+keyword+"%");
		return list;
	}

}
