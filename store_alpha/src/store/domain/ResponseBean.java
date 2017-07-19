package store.domain;

import java.util.List;

/**
 * 用于封装servlet的响应数据
 * @author yang
 *
 */
public class ResponseBean<T> {
	private String status;  //请求的处理状态:yes/no
	private String message; //处理的反馈信息
	private T result ; // 响应对象
	private List<T> results; // 多个响应
	
	
	public ResponseBean(String status) {
		super();
		this.status = status;
	}
	
	public ResponseBean(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	
	public ResponseBean(String status, String message, T result) {
		super();
		this.status = status;
		this.message = message;
		this.result = result;
	}

	
	public ResponseBean(String status, String message, T result, List<T> results) {
		super();
		this.status = status;
		this.message = message;
		this.result = result;
		this.results = results;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	
}
