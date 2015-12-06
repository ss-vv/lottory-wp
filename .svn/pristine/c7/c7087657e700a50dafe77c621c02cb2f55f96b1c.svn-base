/**
 * 
 */
package com.xhcms.ucenter.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author bean
 * 
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "uc_message")
public class MessagePO implements Serializable {

	private static final long serialVersionUID = -7416271696871474851L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "sys_message_id")
	private long sysMessageId;

	@Column(name = "user_id", nullable = false)
	private long userId;

	@Column(name = "username", length = 32, nullable = false)
	private String username;

	@Column(name = "type")
	private int type;

	@Column(name = "isread", nullable = false)
	private int read;

	@Column(name = "subject", nullable = false)
	private String subject;

	@Column(name = "note", nullable = false)
	private String note;

	@Column(name = "authorid")
	private long authorId;

	@Column(name = "author", length = 32)
	private String author;

	@Column(name = "created_time", nullable = false)
	private Date createTime;

	@Column(name = "del_status", nullable = false)
	private int delStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSysMessageId() {
		return sysMessageId;
	}

	public void setSysMessageId(long sysMessageId) {
		this.sysMessageId = sysMessageId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

}
