package store.dao;

import java.sql.SQLException;

import store.domain.UserBean;

/**
 * 面向接口编程的思想
 * @author yang
 *
 */
public interface IUserDao {
	
	/**
	 * 校验用户名重复性
	 * @param username
	 */
	UserBean findUserByUsername(String username) throws SQLException;

	/**
	 * 实现用户注册
	 * @param user
	 * @throws SQLException
	 */
	void regist(UserBean user) throws SQLException;

	/**
	 * 按照激活码来查找对应用户
	 * @param code
	 * @return
	 */
	UserBean findUserByCode(String code) throws SQLException;

	/**
	 * 对用户信息进行更新
	 * @param existUser
	 */
	void update(UserBean existUser) throws SQLException;

	/**
	 * 实现用户登录及其校验
	 * @param user
	 * @return
	 */
	UserBean login(UserBean user) throws SQLException;

}
