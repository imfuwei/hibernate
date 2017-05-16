package com.zfw.beans;

import java.util.HashSet;
import java.util.Set;

public class Course {
	private Integer cid;
	private String cname;
	private Set<Student> students;
	
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Course(String cname) {
		this();
		this.cname = cname;
	}

	public Course() {
		students =new HashSet<Student>();
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


}
