package store.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import store.domain.OrderBean;
import store.domain.OrderItemBean;

/**
 * 订单模块
 * @author yang
 *
 */
public interface IOrderDao {

	// 保存订单信息到OrderBean
	void saveOrder(OrderBean orderBean, Connection conn) throws SQLException;
	// 保存订单条目到OrderItemBean
	void saveOrderItem(OrderBean orderBean, Connection conn) throws SQLException;
	// 获取用户订单的总条目数
	Integer findTotalItems(String uid)  throws SQLException;
	// 获取当前分页中的用户订单详情
	List<OrderBean> findCurrentPageByUid(String uid ,int begin, Integer pageSize)  throws Exception;
	// 根据订单号查询订单详情
	OrderBean findByOid(String oid)throws Exception;
	// 修改订单的详细信息
	void update(OrderBean order)throws Exception;
	// 查询所有订单
	List<OrderBean> findAll()throws Exception;
	// 根据物流状态查询订单
	List<OrderBean> findByState(int pstate)throws Exception;
	// 根据订单号，查询订单详情
	List<OrderItemBean> showDetail(String oid)throws Exception;
	

}
