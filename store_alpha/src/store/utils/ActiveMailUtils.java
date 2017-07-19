package store.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

/**
 * 发送激活邮件的工具类
 * @author yang
 *
 */
public class ActiveMailUtils {
	public static void sendMail(String email,String code){
		
		try {
			//加载邮箱的相关配置文件:邮箱服务器地址,发件邮箱,密码(利用SAX技术)
			Properties pro = new Properties();
			pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MailConfig.properties"));
			// 1. 获取session
			Session session = MailUtils.createSession(pro.getProperty("host"), pro.getProperty("username"), pro.getProperty("password"));
			// 2. 创建mail对象：发件人、收件人、标题、正文
			String content = pro.getProperty("content");
			content = MessageFormat.format(content, code); // 替换超链接中的占位符
			Mail mail = new Mail(pro.getProperty("sendMail"),email,pro.getProperty("subject"),content);
			// 3. 发送
			MailUtils.send(session, mail);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
	}
}
