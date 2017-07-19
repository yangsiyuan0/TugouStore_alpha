package store.utils;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class MyEncodingInvocationHandler implements InvocationHandler {

	private HttpServletRequest request;
	private boolean hasEncode;
	private String encoding;
	
	public MyEncodingInvocationHandler(HttpServletRequest request,String encoding) {
		super();
		this.request = request;
		this.encoding = encoding;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		switch (method.getName()) {
		case "getParameter":
			return getParameter((String)args[0]);
		case "getParameterValues":
			return getParameterValues((String)args[0]);
		case "getParameterMap":
			return getParameterMap();
		default:
			return method.invoke(request, args);
		}
		
	}
	/**
	 * 获得一个key对应多个值的参数	例如CheckBox
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String[] getParameterValues(String name) throws UnsupportedEncodingException {
		return getParameterMap().get(name);
	}

	/**
	 * 获得一个key对应一个值得参数，例如radio
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getParameter(String name) throws UnsupportedEncodingException {
		Map<String, String[]> params = getParameterMap();
		if(null!=params.get(name) && params.get(name).length>0){
			return params.get(name)[0];
		}
		
		return null ;
	}

	/**
	 * 获取所有参数
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String,String[]> getParameterMap() throws UnsupportedEncodingException{
		//解决get提交的中文乱码
		if("GET".equalsIgnoreCase(request.getMethod())){
			//获得所有参数
			Map<String, String[]> params = request.getParameterMap();
			if (!hasEncode) { // 防止重复编码
				for (Map.Entry<String, String[]> param : params.entrySet()) {
					String[] values = param.getValue();
					//对参数值进行解码编码操作
					for (int i = 0; i < values.length; i++) {
						values[i] = new String(values[i].getBytes("ISO-8859-1"),encoding);
					}
				}
				hasEncode = true;
			}
			return params;
		}
		
		//解决PUT POST等的中文乱码问题
		request.setCharacterEncoding(encoding);
		return request.getParameterMap();
	}
	
}
