package com.code_fanatic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import com.code_fanatic.model.bean.CourseBean;
import com.code_fanatic.model.bean.LessonBean;

public class CourseDAO implements IGenericDAO<CourseBean, Integer> {

	DataSource dataSource;
	private static final String TABLE_NAME = "courses INNER JOIN products ON products.id = courses.product_id";
	
	public CourseDAO(DataSource dataSource) {
		
		this.dataSource = dataSource;
		System.out.print("DataSource connesso al DAO");
	}

	public void doSave(CourseBean bean) throws SQLException {

		
	}


	public boolean doDelete(int key) throws SQLException {

		return false;
	}

	public CourseBean doRetrieveByKey(Integer key) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		CourseBean course = null;
		
		try {
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ? ");
			
			prepStmt.setInt(1, key);
			
			ResultSet rs = prepStmt.executeQuery();
			
			if (rs.next()) {
				course = buildCourse(rs, connection);
			} else {
			
				System.out.print("Course not found");
			}
			
		} catch (SQLException e) {
			
			System.out.print(e.getMessage());
		} finally {
			try {
				if (prepStmt != null) 
					prepStmt.close();
			} finally {
				if (connection != null) 
					connection.close();
			}
		}
		
		return course;
		
		
		
		
		
	}

	public Collection<CourseBean> doRetrieveAll(String order) throws SQLException {

		Connection connection = null;
		Statement stmt = null;
		Collection<CourseBean> courses = new ArrayList<CourseBean>();
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			
			String sqlQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + order;
			
			ResultSet rs = stmt.executeQuery(sqlQuery);
			while (rs.next()) {
				courses.add(buildCourse(rs,  connection));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		
		return courses;
	}

	
	private CourseBean buildCourse(ResultSet rs, Connection conn) throws SQLException {
		
		CourseBean course = new CourseBean();
		course.setId(rs.getInt("Id"));
		course.setName(rs.getString("name"));
		course.setDescription(rs.getString("description"));
		course.setPrice(rs.getFloat("price"));
		course.setLesson_count(rs.getInt("lesson_count"));
		
		ArrayList<LessonBean> lessons = new ArrayList<LessonBean>();
		
		if (course.getLesson_count() > 0) {
			
			PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM lessons WHERE course_id = ?");
			prepStmt.setInt(1,  course.getId());
			
			rs = prepStmt.executeQuery();
			
			
			while (rs.next()) {
				LessonBean lesson = new LessonBean();
				lesson.setNumber(rs.getInt("number"));
				lesson.setTitle(rs.getString("title"));
				lesson.setContent(rs.getString("Content"));
				
				lessons.add(lesson);
			}
			
			prepStmt.close();
			
		}
			
		course.setLessons(lessons);
		
		return course;
	
	}
}
