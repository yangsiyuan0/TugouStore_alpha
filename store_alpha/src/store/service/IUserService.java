package store.service;

import java.sql.SQLException;

import store.domain.UserBean;

/**
 * 面向接口编程思想
 * @author yang
 *
 */
public interface IUserService {

	// 校验用户名是否已存在
	UserBean findUserByUsername(String username)  throws SQLException;

	// 对用户进行注册
	void regist(UserBean user) throws SQLException;

	// 按照激活码来查找对应用户
	UserBean findUserByCode(String code) throws SQLException;

	// 对用户信息进行更新
	void update(UserBean existUser) throws SQLException;

	// 用户登录
	UserBean login(UserBean user) throws SQLException;

}
