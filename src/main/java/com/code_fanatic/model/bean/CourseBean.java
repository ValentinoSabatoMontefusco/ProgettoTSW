package com.code_fanatic.model.bean;

import java.util.List;


public class CourseBean extends ProductBean {

	private int lesson_count;
	private List<LessonBean> lessons;
		
	
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
	
	public List<LessonBean> getLessons() {
		return lessons;
	}

	public void setLessons(List<LessonBean> lessons) {
		this.lessons = lessons;
	}

	public int getLessonCount() {
		return lesson_count;
	}

	public void setLessonCount(int lesson_count) {
		this.lesson_count = lesson_count;
	}
	
}
