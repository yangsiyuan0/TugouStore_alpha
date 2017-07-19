package store.service.impl;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import store.dao.ICategoryDao;
import store.domain.CategoryBean;
import store.service.ICategoryService;
import store.utils.BeanFactory;

public class CategoryServiceImpl implements ICategoryService {
	private ICategoryDao categoryDao = (ICategoryDao) BeanFactory.getBean("categoryDao");
	/*private ICategoryDao categoryDao = new CategoryDaoImpl();*/
	/**
	 * 查询所有的产品分类
	 * 1. 由于每个页面导航条均要呈现商品分类,每次都访问数据库,较慢,且占用网络资源
	 * 2. 使用cache技术(Ehcache),在缓存中存取category信息
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryBean> findAllCategory() throws SQLException {
		//1. 初始化缓存对象
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("ehcache.xml"); //读取src目录下的cache配置文件
		CacheManager cacheManager = CacheManager.create(in); 
		Cache cache = cacheManager.getCache("categoryCache"); //获取cache对象
		
		//2. 判断缓存中是否已经有了category信息(名称为catogoryList)
		Element element = cache.get("catogoryList");
		List<CategoryBean> catogoryList = null; //用于存储查询到的分类信息
		if(element == null){//缓存中没有分类信息:  查询数据库
			System.out.println("为查到缓存数据，查询数据库");
			catogoryList = categoryDao.findAllCategory();
			element = new Element("catogoryList", catogoryList);  //将category信息存储到cache
			cache.put(element);
		} else{ // 缓存中已有分类信息:  直接读取缓存信息
			System.out.println("缓存中已有数据，本次未查询数据库");
			catogoryList = (List<CategoryBean>)element.getObjectValue();
		}
		
		return catogoryList;
	}
	
	/**
	 * 保存新增的Category
	 */
	@Override
	public void save(CategoryBean categoryBean) throws SQLException {
		// 1. 调用数据控制层，保存Category
		categoryDao.save(categoryBean);
		// 2. 清除缓存，促使页面更新category清单
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("ehcache.xml");
		CacheManager cacheManager = CacheManager.create(in);
			// 从配置文件中获取名称为categoryCache缓存区
		Cache categoryCache = cacheManager.getCache("categoryCache");
			// 移除名为catogoryList的cache
		categoryCache.remove("catogoryList");
	}

	/**
	 * 对category进行逻辑删除
	 */
	@Override
	public void delete(String cid) throws SQLException {
		// 1. 调用数据控制层，逻辑删除Category
		categoryDao.delete(cid);
		// 2. 清除缓存，促使页面更新category清单
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("ehcache.xml");
		CacheManager cacheManager = CacheManager.create(in);
			// 从配置文件中获取名称为categoryCache缓存区
		Cache categoryCache = cacheManager.getCache("categoryCache");
			// 移除名为catogoryList的cache
		categoryCache.remove("catogoryList");
	}

	/**
	 * 根据cid查询对应的Category详情
	 */
	@Override
	public CategoryBean findByCid(String cid) throws SQLException {
		
		return categoryDao.findByCid(cid);
	}

	/**
	 * 保存修改后的Category数据
	 */
	@Override
	public void update(CategoryBean categoryBean) throws SQLException {
		// 1. 调用数据控制层，保存修改的Category
		categoryDao.update(categoryBean);
		// 2. 清除缓存，促使页面更新category清单
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("ehcache.xml");
		CacheManager cacheManager = CacheManager.create(in);
			// 从配置文件中获取名称为categoryCache缓存区
		Cache categoryCache = cacheManager.getCache("categoryCache");
			// 移除名为catogoryList的cache
		categoryCache.remove("catogoryList");
		
	}

}
