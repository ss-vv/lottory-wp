package com.xhcms.lottery.lang;
/**
 *  注册码状态类枚举
 * @author 
 *
 */
public enum RegistCodeStatus {
	 UNVALID(0,"无效"),
     VALID(1,"有效"),
     OVERDUE(2,"已过期"),
     USED(3,"已被使用");
    
     private int status;
     private String meaning;
     private RegistCodeStatus(int code,String meaning){
    	 this.status=code;
    	 this.meaning=meaning;
     }
     
     public static RegistCodeStatus codeToRegistr(int status){
    	 for(RegistCodeStatus r:RegistCodeStatus.values()){
    		 if(status==r.status){
    			 return r;
    		 }
    	 }
    	 return null;
     }
     public static String getCodeMeaning(RegistCodeStatus code){
    	 for(RegistCodeStatus r:RegistCodeStatus.values()){
    		  if(code.name()==r.name()){
    			  return r.meaning;
    		  }
    	 }
    	 return "";
     }
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
