package test;

import java.text.MessageFormat;

import org.junit.Test;

public class MessageFormatTest {

	@Test
	public void test(){
		String content = "<h3><a href=\"http://localhost:8080/store_alpha/UserServlet?method=active&code={0}\">请点击链接激活账户!</a></h3>";
		String code = "b101d43c44be4e55a9abdd8412fa1126a27c6f55284a4a48a78144388f4c04b7";
		content = MessageFormat.format(content, code);
		System.out.println(content);
		
	}
}
