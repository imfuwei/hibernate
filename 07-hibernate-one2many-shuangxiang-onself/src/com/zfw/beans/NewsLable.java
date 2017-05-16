package com.zfw.beans;

import java.util.HashSet;
import java.util.Set;

public class NewsLable {
	private Integer id;
	private String name;
	private String content;
	private Set<NewsLable> chirdNewsLables;// 站在一方的角度，来为多方设置关联属性
	private NewsLable parentNewsLable;// 站在多方的角度，来为一方设置关联属性
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Set<NewsLable> getChirdNewsLables() {
		return chirdNewsLables;
	}
	public void setChirdNewsLables(Set<NewsLable> chirdNewsLables) {
		this.chirdNewsLables = chirdNewsLables;
	}
	public NewsLable getParentNewsLable() {
		return parentNewsLable;
	}
	public void setParentNewsLable(NewsLable parentNewsLable) {
		this.parentNewsLable = parentNewsLable;
	}
	public NewsLable() {
		chirdNewsLables=new HashSet<NewsLable>();
	}
	public NewsLable(String name, String content) {
		this();
		this.name = name;
		this.content = content;
	}
	
}
