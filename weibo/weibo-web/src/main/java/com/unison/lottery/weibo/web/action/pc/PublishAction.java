package com.unison.lottery.weibo.web.action.pc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.UploadFileResult;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.util.FileUpload;

/**
 * 发布手机微博消息
 * @author Wang Lei
 *
 */
public class PublishAction extends BaseAction {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 3577708384263535007L;
	@Autowired
	private MessageService messageService;
	private WeiboMsg weiboMsg;
	private long postId;
	private long weiboId;
	private PageResult<WeiboMsg> data = new PageResult<WeiboMsg>();
	private List<WeiboMsg> list = new ArrayList<>();
	
	private File filedata;
	private String filedataContentType;
	private String filedataFileName;
	private UploadFileResult fileUploadResult;
	
	private String upload;
	
	/** 准备发微博 */
	public String toPublish(){
		try {
			
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.ERROR);
			logger.error("{}", data.getDesc(), e);
		}
		return SUCCESS;
	}
	
	/** 准备转发微博 */
	public String toForward(){
		try {
			setWeiboMsg(messageService.getWeiboVoById(postId));
			if(weiboMsg == null || weiboMsg.getId() == 0){
				data.fail(Constant.ResultMessage.FORWARD_FAIL_POST);
				return SUCCESS;
			}
			list.add(weiboMsg);
			data.setResults(list);
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.ERROR);
			logger.error("{}",data.getDesc(), e);
		}
		return SUCCESS;
	}
	
	/** 发微博 */
	public String publish(){
		try {
			long uid = getUserLaicaiWeiboId();
			if(uid < 1){
				data.fail(Constant.ResultMessage.PUBLISH_FAIL_LOGIN);
				logger.info(data.getDesc());
				return SUCCESS;
			}
			Document d = Jsoup.parseBodyFragment(weiboMsg.getContent());
			String s = d.text();
			int count = s.replaceAll("/[\t\r\n]+/g", "").length();
			if(count > Constant.WeiboContentLength.POST){
				data.fail("字数应小于1000字！");
				logger.info(data.getDesc());
				return SUCCESS;
			}
			if(StringUtils.isNotBlank(weiboMsg.getContent())){
				Whitelist whitelist = new Whitelist();
				whitelist.addTags("br","p","img");
				whitelist.addAttributes("img", "src");
				whitelist.addAttributes("img", "alt");
				whitelist.addAttributes("img", "_src");
				weiboMsg.setContent(
					Jsoup.clean(weiboMsg.getContent(), whitelist)
				);
			}
			weiboMsg.setOwnerId(uid);
			if(weiboMsg.getPostId()>0){
				list.add(messageService.forward(weiboMsg));
			}else{
				if(StringUtils.isBlank(weiboMsg.getContent())){
					data.fail("内容为空！");
					logger.info(data.getDesc());
					return SUCCESS;
				}
				list.add(messageService.publish(weiboMsg));
			}
			data.setResults(list);
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.PUBLISH_FAIL + e.getMessage());
			logger.error("{}", data.getDesc(),e);
		}
		return SUCCESS;
	}
	
	public String ajaxUploadImage(){
		this.fileUploadResult = new UploadFileResult();
		this.fileUploadResult.setDesc("");
		this.fileUploadResult.setResultCode("");
		this.fileUploadResult.setUserId(getUserLaicaiWeiboId());
		if(filedata !=null){
			String imgUrl = FileUpload.uploadWeiboImage(filedata, filedataContentType, filedataFileName);
			this.fileUploadResult.setImageURL(imgUrl);
		}else{
			this.fileUploadResult.fail("抱歉，因网络问题上传图片失败!");
		}
		return SUCCESS;
	}
	
	/** 修改(长)微博 */
	public String editLong(){
		try {
			long uid = getUserLaicaiWeiboId();
			if(uid < 1){
				data.fail(Constant.ResultMessage.PUBLISH_FAIL_LOGIN);
				logger.info(data.getDesc());
				return SUCCESS;
			}
			if(weiboMsg == null || weiboMsg.getId() < 1 || weiboMsg.getPostId() > 0){
				data.fail(Constant.ResultMessage.PUBLISH_UPDATE_FAIL);
				logger.info(data.getDesc());
				return SUCCESS;
			}
			messageService.editLong(getUserLaicaiWeiboId(), weiboMsg);
			data.setResultCode(weiboMsg.getId()+"");
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.PUBLISH_UPDATE_FAIL);
			logger.error("{}", data.getDesc(),e);
		}
		return SUCCESS;
	}
	
	/** 删除微博 */
	public String delete(){
		try {
			messageService.delete(getUserLaicaiWeiboId(), postId);
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.DELETE_FAIL);
			logger.error("{}", data.getDesc(),e);
		}
		return SUCCESS;
	}
	
	public WeiboMsg getWeiboMsg() {
		return weiboMsg;
	}

	public void setWeiboMsg(WeiboMsg weiboMsg) {
		this.weiboMsg = weiboMsg;
	}

	public PageResult<WeiboMsg> getData() {
		return data;
	}

	public void setData(PageResult<WeiboMsg> data) {
		this.data = data;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public long getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(long weiboId) {
		this.weiboId = weiboId;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public UploadFileResult getFileUploadResult() {
		return fileUploadResult;
	}

	public void setFileUploadResult(UploadFileResult fileUploadResult) {
		this.fileUploadResult = fileUploadResult;
	}

	public String getUpload() {
		return upload;
	}

	public void setUpload(String upload) {
		this.upload = upload;
	}

	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}

	public String getFiledataContentType() {
		return filedataContentType;
	}

	public void setFiledataContentType(String filedataContentType) {
		this.filedataContentType = filedataContentType;
	}

	public String getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}
}
