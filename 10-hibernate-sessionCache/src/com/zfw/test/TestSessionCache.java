package com.zfw.test;

import org.hibernate.Session;
import org.junit.Test;

import com.zfw.beans.Student;

public class TestSessionCache {
	/**
	 * 准备数据
	 */
	@Test
	public void test() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();

			for (int i = 0; i < 10; i++) {
				Student student = new Student("n_" + i, i, 60 + i);
				session.save(student);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	@Test
	public void testSessionCache() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			Student student = session.get(Student.class, 3);
			System.out.println("第一次查询     :" + student);

			Student student1 = session.get(Student.class, 3);
			System.out.println("第二次查询     :" + student);
			// 清空session
			session.clear();

			Student student3 = session.get(Student.class, 3);
			System.out.println("第三次查询     :" + student);

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

}
