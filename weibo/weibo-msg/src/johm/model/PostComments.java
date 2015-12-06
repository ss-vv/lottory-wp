package com.unison.lottery.weibo.msg.model;

import java.util.List;

import redis.clients.johm.CollectionList;
import redis.clients.johm.Id;
import redis.clients.johm.Model;
import redis.clients.johm.Reference;
import redis.clients.johm.utils.ObjectHelper;

/**
 * 微博的评论关系类。
 * 
 * @author Yang Bo
 */
@Model
public class PostComments {
	@Id
	private Long id;
	
	@Reference
	private Post post;
	
	@CollectionList(of=Comment.class)
	private List<Comment> comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	

    @Override
    public int hashCode() {
    	Object[] thisFields = new Object[]{
    			id,
    			post,
    			comments
    	};
    	return ObjectHelper.hashCode(thisFields);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PostComments other = (PostComments) obj;

    	Object[] thisFields = new Object[]{
    			id,
    			post,
    			comments
    	};
    	
    	Object[] otherFields = new Object[]{
    			other.id,
    			other.post,
    			other.comments
    	};
    	return ObjectHelper.equals(thisFields, otherFields, this, obj);
    }
}
