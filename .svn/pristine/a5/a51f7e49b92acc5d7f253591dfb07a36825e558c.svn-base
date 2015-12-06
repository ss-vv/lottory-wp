package com.xhcms.ucenter.sso.client;


public final class UserProfileThreadContextHolder {
	private static ThreadLocal<UserProfile> threadLocal = new ThreadLocal<UserProfile>();
	
	private UserProfileThreadContextHolder(){
	}
	
	public final static void setUserProfile(UserProfile userProfile) {
		threadLocal.set(userProfile);
	}
	
	public final static UserProfile getUserProfile() {
		UserProfile userProfile = (UserProfile)threadLocal.get();
		
		if(userProfile == null) {
			userProfile = new GuestUserProfile();
		}
		
		return userProfile;
	}
}
