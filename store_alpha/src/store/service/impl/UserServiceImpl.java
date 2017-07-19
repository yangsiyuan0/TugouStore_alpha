package store.service.impl;

import java.sql.SQLException;

import store.dao.IUserDao;
import store.domain.UserBean;
import store.service.IUserService;
import store.utils.ActiveMailUtils;
import store.utils.BeanFactory;
import store.utils.UUIDUtils;

public class UserServiceImpl implements IUserService{
	private IUserDao  userDao = (IUserDao) BeanFactory.getBean("userDao");
	/*private IUserDao  userDao = new UserDaoImpl(); */
	/**
	 * 实现用户名的重复性校验
	 * @throws SQLException 
	 */
	@Override
	public UserBean findUserByUsername(String username) throws SQLException {
		UserBean existUser = userDao.findUserByUsername(username);
		return existUser;
	}
	
	/**
	 * 实现用户的注册
	 */
	@Override
	public void regist(UserBean user) throws SQLException {
		user.setUid(UUIDUtils.getUUID());
		user.setState(1);// 1代表未激活, 2:代表已经激活.
		String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code); // 设置激活码
		// 发送激活邮件
		ActiveMailUtils.sendMail(user.getEmail(), code);
		userDao.regist(user);
	}

	/**
	 * 按照激活邮件中的信息，查找用户信息是否符合
	 */
	@Override
	public UserBean findUserByCode(String code) throws SQLException {
		
		return userDao.findUserByCode(code);
	}

	/**
	 * 对用户的信息进行更新：
	 * 主要是用于更改用户的激活状态
	 */
	@Override
	public void update(UserBean existUser) throws SQLException {
		userDao.update(existUser);
	}

	/**
	 * 实现用户登录的校验
	 */
	@Override
	public UserBean login(UserBean user) throws SQLException {
		return userDao.login(user);
	}

}
