package store.domain;

import java.io.Serializable;

// 利用到缓存技术，可能需要保存到硬盘，所以要实现序列化
public class CategoryBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private String cid;
	private String cname;
	private int status;  // Category的状态：1表示正常；0表示已下架
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
}
