package store.domain;

import java.util.List;

/**
 * 用于存储分页展示中的数据
 * @author yang
 *
 */
public class PageBean<T> {

	private Integer currentPage; //当前页数
	private Integer pageSize; //每页条目数
	private Integer totalPage;//总页数
	private Integer totalItem;//总条目数
	private List<T> list; //每页中条目的详情
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
