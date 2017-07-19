package store.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import store.dao.IOrderDao;
import store.domain.OrderBean;
import store.domain.OrderItemBean;
import store.domain.PageBean;
import store.service.IOrderService;
import store.utils.BeanFactory;
import store.utils.C3P0Utils;

public class OrderServiceImpl implements IOrderService {
	private IOrderDao orderDao = (IOrderDao) BeanFactory.getBean("orderDao");
	/**
	 * 新建订单：利用事务管理
	 *  1. 往orders表中，存入OrderBean的信息
	 *  2. 往orderItem表中，存入OrderIttemBean的信息
	 */
	@Override
	public void createOrder(OrderBean orderBean) {
		// 1. 开启事务
		Connection conn = C3P0Utils.getConnection(); //保证dao层中的同一事务内的操作使用统一connection
		try {
			conn.setAutoCommit(false);
			// 2. 保存Order
			orderDao.saveOrder(orderBean,conn);
			// 2. 保存OrdeItem
			orderDao.saveOrderItem(orderBean,conn);
			// 3. 提交事务
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			// 3. 如果保存失败，进行回滚
			DbUtils.rollbackAndCloseQuietly(conn);
		}
	}
	
	/**
	 * 根据用户id查询用户的订单
	 * - 封装到pageBean:
	 * @throws SQLException 
	 */
	@Override
	public PageBean<OrderBean> findOrderByUid(String uid, Integer currentPage) throws Exception {
		// 初始化PageBean的参数
		PageBean<OrderBean> pageBean = new PageBean<OrderBean>();
		// ①.设置每页显示记录数 + 当前页数
		Integer pageSize = 5;
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize); 
		// ②.设置总记录数
		Integer totalItem = orderDao.findTotalItems(uid);
		pageBean.setTotalItem(totalItem);
		// ③.设置总页数
		double  total = totalItem;
		Double totalPage = Math.ceil(total/pageSize); // 计算总页数
		pageBean.setTotalPage(totalPage.intValue());
		// ④.设置当前页面的详情:调用数据层
		int begin = (currentPage-1)*pageSize; //起始条目数
		List<OrderBean> list = orderDao.findCurrentPageByUid(uid,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * 根据用户订单号，查询订单详情
	 */
	@Override
	public OrderBean findByOid(String oid) throws Exception {
		return orderDao.findByOid(oid);
	}

	/**
	 * 更新订单信息：（主要是物流信息）
	 */
	@Override
	public void update(OrderBean order) throws Exception {
		orderDao.update(order);
	}

	/**
	 * 查询所有订单
	 */
	@Override
	public List<OrderBean> findAll() throws Exception {
		return orderDao.findAll();
	}

	/**
	 * 根据物流状态查询订单
	 */
	@Override
	public List<OrderBean> findByState(int pstate) throws Exception {
		return orderDao.findByState(pstate);
	}

	/**
	 * 根据订单号，查询订单项的详细信息
	 */
	@Override
	public List<OrderItemBean> showDetail(String oid) throws Exception {
		return orderDao.showDetail(oid);
	}



}
