package store.service;


import java.util.List;

import store.domain.OrderBean;
import store.domain.OrderItemBean;
import store.domain.PageBean;

/**
 * 订单模块
 * @author yang
 *
 */
public interface IOrderService {

	// 创建新订单：保存order、保存orderItem
	void createOrder(OrderBean orderBean);
	
	// 根据用户id,进行订单的分页查询
	PageBean<OrderBean> findOrderByUid(String uid, Integer currentPage) throws Exception ;
	
	// 根据用户订单号，查询订单详情
	OrderBean findByOid(String oid)throws Exception;

	// 更新订单信息：（主要是物流信息）
	void update(OrderBean order)throws Exception;

	// 查询所有订单
	List<OrderBean> findAll()throws Exception;

	// 根据物流状态查询订单
	List<OrderBean> findByState(int pstate)throws Exception;

	//根据订单号，查询订单项的详细信息
	List<OrderItemBean> showDetail(String oid)throws Exception;

	
}
