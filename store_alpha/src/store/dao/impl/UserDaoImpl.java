package store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import store.dao.IUserDao;
import store.domain.UserBean;
import store.utils.C3P0Utils;

public class UserDaoImpl implements IUserDao {
	private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
	/**
	 * 校验用户名是否重复（可注册）
	 * @throws SQLException 
	 */
	@Override
	public UserBean findUserByUsername(String username) throws SQLException {
		
		String sql = "select * from user where username = ? ";
		UserBean existUser = qr.query(sql, new BeanHandler<UserBean>(UserBean.class), username);
		return existUser;
	}
	/**
	 * 实现用户注册功能
	 */
	@Override
	public void regist(UserBean user) throws SQLException {
		String sql = "insert into user values (?,?,?,?,?,?,?,?,?,?)";
		System.out.println(user.getSex());
		Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode() };
		qr.update(sql, params);
	}
	
	/**
	 * 按照激活码来查找对应用户
	 * @param code
	 * @return
	 */
	@Override
	public UserBean findUserByCode(String code) throws SQLException {
		String sql = "select * from user where code = ?";
		UserBean existUser = qr.query(sql, new BeanHandler<UserBean>(UserBean.class), code);
		return existUser;
	}
	
	/**
	 *  对用户的信息进行更新：
	 * 主要是用于更改用户的激活状态
	 */
	@Override
	public void update(UserBean existUser) throws SQLException {
		String sql = "update user set username = ?,password = ?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid = ?";
		Object[] params = {  existUser.getUsername(), existUser.getPassword(), existUser.getName(), existUser.getEmail(),
				existUser.getTelephone(), existUser.getBirthday(), existUser.getSex(), existUser.getState(), existUser.getCode(),existUser.getUid() };
		qr.update(sql, params);
	}
	
	/**
	 * 实现用户登录功能
	 */
	@Override
	public UserBean login(UserBean user) throws SQLException {
		String sql = "select * from user where username = ? and password = ? and state = ?";
		UserBean existUser = qr.query(sql, new BeanHandler<UserBean>(UserBean.class), user.getUsername(),user.getPassword(),2);
		return existUser;
	}

}
