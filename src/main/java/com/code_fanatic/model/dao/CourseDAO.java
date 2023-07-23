package com.code_fanatic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.naming.java.javaURLContextFactory;
import org.eclipse.jdt.internal.compiler.ast.TrueLiteral;

import com.code_fanatic.control.admin.OrdersRecapServlet;
import com.code_fanatic.control.utils.SecurityUtils;
import com.code_fanatic.model.bean.CourseBean;
import com.code_fanatic.model.bean.LessonBean;

public class CourseDAO implements IGenericDAO<CourseBean, Integer> {
	private static final Logger LOGGER = Logger.getLogger(CourseDAO.class.getName());

	DataSource dataSource;
	private static final String TABLE_NAME = "courses INNER JOIN products ON products.id = courses.product_id";

	public CourseDAO(DataSource dataSource) {

		this.dataSource = dataSource;
		System.out.print("DataSource connesso al DAO");
	}

	public synchronized void doSave(CourseBean bean) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		
		try {
			
		connection = dataSource.getConnection();
	

		if (bean.getId() != 0) {

			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?;");

			prepStmt.setInt(1, bean.getId());
			ResultSet rs = prepStmt.executeQuery();

			if (rs.next()) {
//					
//					MerchBean newBean = new MerchBean();
//					newBean.setId(bean.getId());
//					newBean.setName(bean.getName());
//					newBean.setDescription(bean.getDescription());
//					newBean.setPrice(bean.getPrice());
//					newBean.setType("Merchandise");
//					newBean.setAmount(bean.getAmount());

				prepStmt.close();
				prepStmt = connection.prepareStatement(
						"UPDATE products SET name = ?, description = ?, " + "type = ?, price = ? WHERE id = ?;");

				prepStmt.setString(1, bean.getName());
				prepStmt.setString(2, bean.getDescription());
				prepStmt.setString(3, bean.getType());
				prepStmt.setFloat(4, bean.getPrice());
				prepStmt.setInt(5, bean.getId());

				prepStmt.executeUpdate();
				prepStmt.close();
				prepStmt = connection.prepareStatement("UPDATE courses SET lesson_count = ?, WHERE product_id = ?;");

				prepStmt.setInt(1, bean.getLesson_count());
				prepStmt.setInt(2, bean.getId());
				System.err.println("Corsio allegedly modificato");
			}
		} else {

			prepStmt = connection.prepareStatement(
					"INSERT INTO products (name, description, type, price) " + " VALUE (?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);

			prepStmt.setString(1, bean.getName());
			prepStmt.setString(2, bean.getDescription());
			prepStmt.setString(3, bean.getType());
			prepStmt.setFloat(4, bean.getPrice());

			prepStmt.executeUpdate();

			ResultSet tempRs = prepStmt.getGeneratedKeys();
			if (tempRs.next()) {

				bean.setId(tempRs.getInt(1));
				prepStmt.close();
				prepStmt = connection.prepareStatement("INSERT INTO courses (product_id, lesson_count) VALUES(?, ?);");

				prepStmt.setInt(1, bean.getId());
				prepStmt.setInt(2, bean.getLesson_count());
				prepStmt.executeUpdate();

				System.err.println("Corsio allegedly inserito");
			} else {
				System.err.println("Qualcosa non andò bene");
			}

		}
	} finally {
			
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
	}

	public synchronized boolean doDelete(int key) throws SQLException {

		return false;
	}

	public synchronized CourseBean doRetrieveByKey(Integer key) throws SQLException {

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

	public synchronized Collection<CourseBean> doRetrieveAll(String order) throws SQLException {

		Connection connection = null;
		PreparedStatement prepStmt = null;
		Collection<CourseBean> courses = new ArrayList<CourseBean>();

		try {
			connection = dataSource.getConnection();
			String sanitizedOrder = SecurityUtils.sanitizeForCourse(order);
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " ORDER BY " + sanitizedOrder + ";");

			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				courses.add(buildCourse(rs, connection));
			}
		} catch (SQLException e) {

			LOGGER.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (prepStmt != null)
					prepStmt.close();
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
		course.setType(rs.getString("type"));

		ArrayList<LessonBean> lessons = new ArrayList<LessonBean>();
		PreparedStatement prepStmt = null;
		try {
			
		if (course.getLesson_count() > 0) {

			prepStmt = conn.prepareStatement("SELECT * FROM lessons WHERE course_id = ?");
			prepStmt.setInt(1, course.getId());

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
		} finally {
		
			if (prepStmt != null)
				prepStmt.close();
			
		}
		return course;

	}
}
