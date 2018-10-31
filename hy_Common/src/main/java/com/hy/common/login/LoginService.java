

package com.hy.common.login;

import com.hy.common.model.Account;
import com.swetake.util.Qrcode;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 登录业务
 */
public class LoginService {

	public static final LoginService me = new LoginService();
	private final Account accountDao = new Account().dao();

	// 存放登录用户的 cacheName
	public static final String loginAccountCacheName = "loginAccount";

	// "jfinalId" 仅用于 cookie 名称，其它地方如 cache 中全部用的 "sessionId" 来做 key
	public static final String sessionIdName = "jfinalId";
	public void encoderQRCoder(String content, HttpServletResponse response) {
		try {
			Qrcode handler = new Qrcode();
			handler.setQrcodeErrorCorrect('M');
			handler.setQrcodeEncodeMode('B');
			handler.setQrcodeVersion(7);
			byte[] contentBytes = content.getBytes("UTF-8");
			BufferedImage bufImg = new BufferedImage(168, 168, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 168, 168);
			gs.setColor(Color.BLACK);// 设定图像颜色：BLACK
			int pixoff = 2; // 设置偏移量 不设置肯能导致解析出错
			// 输出内容：二维码
			if (contentBytes.length > 0 && contentBytes.length < 124) {
				boolean[][] codeOut = handler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				System.err.println("出错啦");
			}
			gs.dispose();
			bufImg.flush();
			ImageIO.write(bufImg, "jpg", response.getOutputStream());// 生成二维码QRCode图片


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
