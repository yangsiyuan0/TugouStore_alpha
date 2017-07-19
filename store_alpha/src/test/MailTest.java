package test;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.junit.Test;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

/**
 * 测试邮件发送
 * @author yang
 *
 */
public class MailTest {
	
	@Test
	public void test(){
		// 1. 获取session
		Session session = MailUtils.createSession("smtp.yeah.net", "yangsiyuan0", "wap5891ysy");
		// 2. 创建mail对象：发件人、收件人、标题、正文
		String code = "123sdfjklsdkljrsiduoi1123";
		Mail mail = new Mail("yangsiyuan0@yeah.net","siyuan.yang@foxmail.com","来自黑马官方商城的激活邮件!","<h1>来自购物天堂黑马官方商城的激活邮件:请点击下面链接激活!</h1><h3><a href='http://localhost:8080/store_v2.0/UserServlet?method=active&code="+code+"'>http://localhost:8080/store_v2.0/UserServlet?method=active&code="+code+"</a></h3>");
		// 3. 发送
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
	}
}
