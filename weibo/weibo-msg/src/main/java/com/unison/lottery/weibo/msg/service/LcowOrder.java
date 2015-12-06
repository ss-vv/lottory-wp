package com.unison.lottery.weibo.msg.service;

import java.util.Comparator;

import com.unison.lottery.weibo.data.vo.CommentVO;

/**
 * Order enum of List Comments Of WeiboMsg.
 * 
 * @author Yang Bo
 */
public enum LcowOrder {
	MOST_RECENT,	// 最近的在前
	MOST_OLD,		// 最早的在前
	MOST_PRAISED;	// 最多赞的在前

	public Comparator<CommentVO> comparator() {
		switch(this){
		case MOST_RECENT:
			return new Comparator<CommentVO>() {
				@Override
				public int compare(CommentVO o1, CommentVO o2) {
					return o2.getCreateTime().compareTo(o1.getCreateTime());
				}
			};
		case MOST_OLD:
			return new Comparator<CommentVO>() {
				@Override
				public int compare(CommentVO o1, CommentVO o2) {
					return o1.getCreateTime().compareTo(o2.getCreateTime());
				}
			};
		case MOST_PRAISED:
			return new Comparator<CommentVO>() {
				@Override
				public int compare(CommentVO o1, CommentVO o2) {
					return (int)(o2.getPraisedCount() - o1.getPraisedCount());
				}
			};
		default:
			return null;
		}
	}
}
