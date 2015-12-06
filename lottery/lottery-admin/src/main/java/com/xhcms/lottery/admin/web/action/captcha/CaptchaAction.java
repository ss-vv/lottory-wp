package com.xhcms.lottery.admin.web.action.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import com.opensymphony.xwork2.ActionContext;
import com.xhcms.lottery.admin.web.action.BaseAction;

public class CaptchaAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
		return generateCaptcha();
	}
	
	private String generateCaptcha() {
		byte[] captchaChallengeAsJpeg = null;
		// the output stream to render the captcha image as jpeg into
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// get the session id that will identify the generated captcha.
			// the same id must be used to validate the response, the session id
			// is a good candidate!
			String captchaId = request.getSession().getId();
			// call the ImageCaptchaService getChallenge method
			BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID(captchaId,request.getLocale());

			// a jpeg encoder
			ImageIO.write(challenge, "jpeg", jpegOutputStream);
			captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
			
			// flush it in the response
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			ServletOutputStream responseOutputStream = response.getOutputStream();
			responseOutputStream.write(captchaChallengeAsJpeg);	//output captcha image
			
			responseOutputStream.flush();
			responseOutputStream.close();
		} catch (Exception e) {
			 addActionError("验证码加载失败");
	         return INPUT;
		}
		return SUCCESS;
	}
}
