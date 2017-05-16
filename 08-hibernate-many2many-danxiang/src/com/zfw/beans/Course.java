package com.zfw.beans;

public class Course {
	private Integer cid;
	private String cname;

	public Course(String cname) {
		super();
		this.cname = cname;
	}

	public Course() {
		super();
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Course(Integer cid, String cname) {
		super();
		this.cid = cid;
		this.cname = cname;
	}

}
