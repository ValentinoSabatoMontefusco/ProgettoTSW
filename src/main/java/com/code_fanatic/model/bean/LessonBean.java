package com.code_fanatic.model.bean;

public class LessonBean {

	private int number;
	private String title;
	private String content;
	
//	public LessonBean(int number, String title, String content) {
//		
//		this.number = number;
//		this.title = title;
//		this.content = content;
//		
//	}
	
	public LessonBean() {
		
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	//private Course course;
	
	
	
}
