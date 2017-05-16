package com.zfw.test;

import org.hibernate.Session;
import org.junit.Test;

import com.zfw.beans.Course;
import com.zfw.beans.Student;

public class Testmany2many_danxiang {
	@Test
	public void studentTest(){
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			Student student = new Student("张三");
			Student student2 = new Student("李四");
			
			Course course1 = new Course("struts2");			
			Course course2 = new Course("hibernate5");			
			Course course3 = new Course("spring4");			
			
			student.getCourses().add(course1);
			student.getCourses().add(course2);
			
			student2.getCourses().add(course3);
			student2.getCourses().add(course2);
			
			session.save(student2);
			session.save(student);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	
}
