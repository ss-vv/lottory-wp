package com.unison.lottery.api.vGroupMembers.bo;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.unison.lottery.api.vGroupMembers.data.GroupUser;

public class MyComparator implements Comparator<GroupUser> {

	@Override
	public int compare(GroupUser arg0, GroupUser arg1) {
		Collator instance = Collator.getInstance(Locale.CHINA);
        return instance.compare(arg0.getNickName(), arg1.getNickName());
	}
}
