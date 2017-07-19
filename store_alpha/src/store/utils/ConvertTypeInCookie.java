package store.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 用于处理浏览记录中的：cookie中的字符串的格式互换问题
 * 1. 传递状态下：商品pid之间以-连接 （demo:12-23-1-25）
 * 2. 存取状态下：商品ID存放于集合中
 * @author yang
 *
 */
public class ConvertTypeInCookie {
	
	/**
	 * 用于将List的浏览记录信息，转换成字符串形式：以便存储到cookie中
	 * @param list
	 * @return
	 */
	public static String getString(List<String> list){
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str).append("-");
		}
		return sb.substring(0,sb.length()-1); //开始index为0不能省略，否则当长度为1时有可能会被切掉
	}

	/**
	 * 用于将cookie中获取的浏览记录信息转化为List：以便进行存取、修改
	 * @param value
	 * @return
	 */
	public static LinkedList<String> getList(String value){
		String[] ids = value.split("-");
		LinkedList<String> list = new LinkedList<String>(Arrays.asList(ids));
		return list;
	}
	/**
	 * 用于将字符串存储到cookie中
	 */
	public static void addCookie(){
		
	}
}
