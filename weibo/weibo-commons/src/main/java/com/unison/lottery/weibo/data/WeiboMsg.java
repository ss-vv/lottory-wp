package com.unison.lottery.weibo.data;

import java.io.Serializable;

/**
 * 微博消息对象
 * @author Wang Lei
 *
 */
public class WeiboMsg implements Serializable {

	private static final long serialVersionUID = -1808865018713673292L;

	/** 微博id */
	private long id;
	/** 原微博id */
	private long postId;
	/** 作者id */
	private long ownerId;
	/** 标题 */
	private String title;
	/** 内容 */
	private String content;
	/** 转发次数 */
	private int forwardCount;
	/** 评论次数 */
	private int commentCount;
	/** 收藏次数 */
	private int favoriateCount;
	/** 分享次数 */
	private int shareCount;
	/** 赞数量 */
	private int likeCount;
	/** 来源 */
	private String from;
	/** 创建时间 */
	private long createTime;
	/**微博类型*/
	private String type;
	
	/** 微博关联方案 */
	private long schameId;
	
	/**投注记录ID*/
	private long betRecordId;
	
	private String fromUrl;
	
	/**微博状态   ： 9=已删除 ；*/
	private int status;
	
	public long getCreateTime() {
		return createTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getForwardCount() {
		return forwardCount;
	}

	public void setForwardCount(int forwardCount) {
		this.forwardCount = forwardCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getFavoriateCount() {
		return favoriateCount;
	}

	public void setFavoriateCount(int favoriateCount) {
		this.favoriateCount = favoriateCount;
	}

	public int getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getFromUrl() {
		return fromUrl;
	}

	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public long getSchameId() {
		return schameId;
	}

	public void setSchameId(long schameId) {
		this.schameId = schameId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getBetRecordId() {
		return betRecordId;
	}

	public void setBetRecordId(long betRecordId) {
		this.betRecordId = betRecordId;
	}
}
