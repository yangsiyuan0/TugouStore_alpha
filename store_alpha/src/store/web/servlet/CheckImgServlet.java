package store.web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.vcode.utils.VerifyCode;

/**
 * 生成一次性验证码：利用itcast-jar包
 */
public class CheckImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取一次性验证码工具类
			VerifyCode verify = new VerifyCode();
			BufferedImage image = verify.getImage();
			String verifycode = verify.getText();
			// 将验证码信息存入session
			request.getSession().setAttribute("verifycode", verifycode);
			// 将验证码图片绑定到流
			ImageIO.write(image, "jpg", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
