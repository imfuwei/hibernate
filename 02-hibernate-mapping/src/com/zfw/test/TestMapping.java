package com.zfw.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.zfw.beans.Student;
import com.zfw.beans.Teacher;

public class TestMapping {
	@Test
	public void test() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Teacher teacher = new Teacher("zhangsan", "13", 91);
			session.save(teacher);
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}
}
