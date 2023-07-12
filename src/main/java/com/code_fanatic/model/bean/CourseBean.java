package com.code_fanatic.model.bean;

import java.util.ArrayList;


public class CourseBean extends ProductBean {

	private int lesson_count;
	private ArrayList<LessonBean> lessons;
		
	
	public CourseBean() {
		
	}
	
	public CourseBean(ProductBean pb) {
		
		if (pb.getId() != 0)
			setId(pb.getId());
		setName(pb.getName());
		setDescription(pb.getDescription());
		setPrice(pb.getPrice());
		setType(pb.getType());
		
	}

//	public CourseBean(int id, String name, String description, int price, int lesson_count) {
//		
//		super(id, name, description, price);
//		this.lesson_count = lesson_count;
//	}
//	
//	public CourseBean(int id, String name, String description, float price, int lesson_count,
//			ArrayList<LessonBean> lessons) {
//		super(id, name, description, price);
//		this.lesson_count = lesson_count;
//		this.lessons = lessons;
//	}

	
	
	public ArrayList<LessonBean> getLessons() {
		return lessons;
	}

	public void setLessons(ArrayList<LessonBean> lessons) {
		this.lessons = lessons;
	}

	public int getLesson_count() {
		return lesson_count;
	}

	public void setLesson_count(int lesson_count) {
		this.lesson_count = lesson_count;
	}
	
}
