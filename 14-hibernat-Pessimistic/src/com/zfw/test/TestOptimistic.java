package com.zfw.test;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.junit.Test;

import com.zfw.beans.Student;

public class TestOptimistic {
	@Test
	public void test() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();

			for (int i = 0; i < 10; i++) {
				Student student = new Student("n_" + i, 20 + i, 50 + i);
				session.save(student);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	@Test
	public void testOptimistic() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			Student student = session.get(Student.class,1, LockMode.PESSIMISTIC_WRITE);
			System.out.println(student);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}
}
