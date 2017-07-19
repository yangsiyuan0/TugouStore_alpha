package store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import store.dao.ICategoryDao;
import store.domain.CategoryBean;
import store.utils.C3P0Utils;

public class CategoryDaoImpl implements ICategoryDao {
	private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
	/**
	 * 查询所有的产品分类
	 * @throws SQLException 
	 */
	@Override
	public List<CategoryBean> findAllCategory() throws SQLException {
		String sql = "select * from category where status = 1"; //  status=1表示状态为正常
		List<CategoryBean> list = qr.query(sql, new BeanListHandler<CategoryBean>(CategoryBean.class));
		return list;
	}
	
	/**
	 * 保存新增Category
	 */
	@Override
	public void save(CategoryBean categoryBean) throws SQLException {
		String sql = "insert into category values (?,?,?) ";
		qr.update(sql, categoryBean.getCid(),categoryBean.getCname(),1);
	}

	/**
	 * 逻辑删除Category
	 * @throws SQLException 
	 */
	@Override
	public void delete(String cid) throws SQLException {
		String sql = "update category set status = ? where cid = ?";
		qr.update(sql, 0,cid);
	}

	/**
	 * 根据cid查询对应的Category详情
	 */
	@Override
	public CategoryBean findByCid(String cid) throws SQLException {
		String sql = "select * from category where cid = ?";
		return qr.query(sql, new BeanHandler<CategoryBean>(CategoryBean.class), cid);
	}

	/**
	 * 保存修改后的Category
	 * @throws SQLException 
	 */
	@Override
	public void update(CategoryBean categoryBean) throws SQLException {
		String sql = "update category set cname = ? where cid = ?";
		qr.update(sql, categoryBean.getCname(),categoryBean.getCid());
	}

}
