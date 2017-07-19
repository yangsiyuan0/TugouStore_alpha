package store.service;

import java.sql.SQLException;
import java.util.List;

import store.domain.CategoryBean;

public interface ICategoryService {
	
	// 查询所有的产品分类
	public List<CategoryBean> findAllCategory() throws SQLException;

	// 保存新增的Category
	public void save(CategoryBean categoryBean) throws SQLException;
	
	// 对category进行逻辑删除
	public void delete(String cid) throws SQLException;

	// 根据cid查询对应Category的详情
	public CategoryBean findByCid(String cid) throws SQLException;

	// 保存修改后的Category
	public void update(CategoryBean categoryBean) throws SQLException;
	
}
