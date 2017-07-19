package store.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 提供JavaBean对象的工厂类
 *  - 设计初衷:降低dao\service中接口与实现类间的耦合度
 *  - 利用技术:反射+xml配置与解析
 * @author yang
 *
 */
public class BeanFactory {
	//对xml文档进行解析(dom4j)
	
	public static Object getBean(String name){
		
		try {
			// 获取核心对象:saxReader
			SAXReader reader = new SAXReader();
			// 加载xml文件:获得document对象
			/*Document document = reader.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("applicationContext.xml"));*/
			Document document = reader.read(BeanFactory.class.getClassLoader().getResourceAsStream("applicationContext.xml"));
			// 获取标签元素:利用XPath语法
			Element element = (Element) document.selectSingleNode("//bean[@id='"+name+"']");
			// 获取标签中的属性值
			String value = element.attributeValue("class");
			// 根据属性值(bean实现类的全路径),反射获取Class对象
			Class<?> clazz = Class.forName(value);
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
