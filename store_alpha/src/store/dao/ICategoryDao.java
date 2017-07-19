package store.dao;

import java.sql.SQLException;
import java.util.List;

import store.domain.CategoryBean;

public interface ICategoryDao {

	// 查询所有的产品分类
	List<CategoryBean> findAllCategory()  throws SQLException;
	// 保存新增的Category
	void save(CategoryBean categoryBean)  throws SQLException;
	// 逻辑删除一个category
	void delete(String cid) throws SQLException;
	// 根据cid查询对应的Category详情
	CategoryBean findByCid(String cid) throws SQLException;
	// 保存修改后的Category
	void update(CategoryBean categoryBean)  throws SQLException;

}
