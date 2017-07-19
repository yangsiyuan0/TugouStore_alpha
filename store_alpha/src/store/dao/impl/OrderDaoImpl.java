package store.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import store.dao.IOrderDao;
import store.domain.OrderBean;
import store.domain.OrderItemBean;
import store.domain.ProductBean;
import store.utils.C3P0Utils;

public class OrderDaoImpl implements IOrderDao {
	private QueryRunner qr = new QueryRunner(); //用于事务的连接:saveOrder + saveOrderItem
	private QueryRunner qr1 = new QueryRunner(C3P0Utils.getDataSource());
	/**
	 * 向orders表中存储订单信息（OrderBean）
	 * @throws SQLException 
	 */
	@Override
	public void saveOrder(OrderBean orderBean, Connection conn) throws SQLException {
		String sql = "INSERT INTO `orders` (`oid`, `ordertime`, `total`, `state`, `address`, `name`, `telephone`, `uid`)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		Object[] params = {orderBean.getOid(),orderBean.getOrderTime(),orderBean.getTotal(),orderBean.getState(),
				orderBean.getAddress(),orderBean.getName(),orderBean.getTelephone(),orderBean.getUserBean().getUid()};
		qr.update(conn, sql, params);
	}

	/**
	 * 向orderitem表中存储订单条目信息（OrderItemBean）
	 */
	@Override
	public void saveOrderItem(OrderBean orderBean, Connection conn) throws SQLException{
		String sql = "INSERT INTO `orderitem` (`itemid`, `count`, `subtotal`, `pid`, `oid`) VALUES (?, ?, ?, ?, ?);";
		List<OrderItemBean> list = orderBean.getOrderItems();
		for (OrderItemBean orderItemBean : list) {
			Object[] params = {orderItemBean.getItemId(),orderItemBean.getCount(),orderItemBean.getSubtotal(),
					orderItemBean.getProductBean().getPid(),orderItemBean.getOrderBean().getOid()};
			qr.update(conn, sql, params);
		}
	}

	/**
	 * 查询用户订单的总条目数
	 * @throws SQLException 
	 */
	@Override
	public Integer findTotalItems(String uid) throws SQLException {
		String sql = "select count(*) from orders where uid = ?";
		Long count = (Long)qr1.query(sql, new ScalarHandler(), uid);
		return count.intValue();
	}

	/**
	 * 查询当前页面下用户订单的详情
	 * @throws SQLException 
	 */
	@Override
	public List<OrderBean> findCurrentPageByUid(String uid, int begin, Integer pageSize) throws Exception {
		
		// 1. 先查找指定uid下的5个订单(一个页面)
		String sql = "select * from orders where uid = ? order by ordertime limit ?,?";
		// 2. 查找用户订单
		List<OrderBean> list = qr1.query(sql, new BeanListHandler<OrderBean>(OrderBean.class), uid,begin,pageSize);
		// 3. 根据订单号,查找订单内的商品详情
		for (OrderBean orderBean : list) {
	        // 对orderitem、product表进行内联查询
			sql = "select * from orderitem o,product p where o.pid = p.pid AND oid = ?";
			List<Map<String, Object>> oList = qr1.query(sql, new MapListHandler(), orderBean.getOid()); //将订单条目信息存储在Map集合中
			for (Map<String, Object> map : oList) {
				ProductBean productBean = new ProductBean();
				BeanUtils.populate(productBean,map); // 将查询到的产品信息封装到ProductBean
				OrderItemBean orderItemBean = new OrderItemBean();
				BeanUtils.populate(orderItemBean, map); // 将查询到的订单条目信息封装到OrderItemBean
				orderItemBean.setProductBean(productBean);
				orderBean.getOrderItems().add(orderItemBean);
			}
		}
		return list;
	}

	/**
	 * 根据订单号查询订单（具体方法类似 用户订单的查询）
	 */
	@Override
	public OrderBean findByOid(String oid) throws Exception {
		// 1. 查询对应订单号的订单详情
		String sql = "select * from orders where oid = ?";
		OrderBean orderBean = qr1.query(sql, new BeanHandler<OrderBean>(OrderBean.class), oid);
		// 2. 查询订单内的各个订单项详情：对Product、orderItem进行多表查询
		sql = "SELECT * FROM orderitem o,product p WHERE o.pid = p.pid AND o.oid = ?";
		List<Map<String,Object>> oList = qr1.query(sql, new MapListHandler(), oid);
		for (Map<String, Object> map : oList) {
			ProductBean product = new ProductBean();
			BeanUtils.populate(product, map); // 封装产品Product
			
			OrderItemBean orderItem = new OrderItemBean();
			BeanUtils.populate(orderItem, map); // 封装订单项
			
			orderItem.setProductBean(product);
			
			orderBean.getOrderItems().add(orderItem); // 将订单详情添加到订单对象
		}
		return orderBean;
	}

	/**
	 * 更新订单的物流信息
	 */
	@Override
	public void update(OrderBean order) throws Exception {
		String sql = "update orders set total = ?,state = ? ,address = ?,name=?,telephone = ? where oid = ?";
		Object[] params = {order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone() ,order.getOid()};
		qr1.update(sql,params);
	}

	@Override
	public List<OrderBean> findAll() throws Exception {
		String sql = "select * from orders order by ordertime desc";
		List<OrderBean> list = qr1.query(sql, new BeanListHandler<OrderBean>(OrderBean.class));
		return list;
	}

	@Override
	public List<OrderBean> findByState(int pstate) throws Exception {
		String sql = "select * from orders where state = ? order by ordertime desc";
		List<OrderBean> list = qr1.query(sql, new BeanListHandler<OrderBean>(OrderBean.class),pstate);
		return list;
	}

	@Override
	public List<OrderItemBean> showDetail(String oid) throws Exception {
		String sql = "SELECT * FROM orderitem o,product p WHERE o.pid = p.pid AND o.oid = ?";
		List<OrderItemBean> list = new ArrayList<OrderItemBean>();
		List<Map<String,Object>> oList = qr1.query(sql, new MapListHandler(),oid);
		for (Map<String, Object> map : oList) {
			ProductBean product = new ProductBean();
			BeanUtils.populate(product, map);
			
			OrderItemBean orderItem = new OrderItemBean();
			BeanUtils.populate(orderItem, map);
			
			orderItem.setProductBean(product);
			
			list.add(orderItem);
		}
		return list;
	}

}
