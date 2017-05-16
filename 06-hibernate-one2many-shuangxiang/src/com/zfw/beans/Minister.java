package com.zfw.beans;

public class Minister {
	private Integer mid;
	private String mname;
	private Country country;
	public Minister(String mname) {
		super();
		this.mname = mname;
	}
	public Minister() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
}
