package com.avi6.blog.dto;

public class MemberDTO {
	   private String password;	    
	   private String memberId;

	    public String getMemberId() {
	        return memberId;
	    }

	    public void setMemberId(String email) {
	        this.memberId = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }
}
