package com.zfw.test;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.zfw.beans.Student;

public class N1Test {
	
	/**
	 * 
	 * 用list是每次都从数据库中查询(N)，并把查询出来的详情放到session缓存中
	 */
	@Test
	public void test() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			String hql = "from Student";
			List<Student> list = session.createQuery(hql).list();
			for (Student student : list) {
				System.out.println(student);
			}
			/*
			 * for (int j = 3; j < 51; j++) { Student student = new Student();
			 * student.setId(j); session.delete(student); }
			 */
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 而用iterate第一次会先查id(1),然后再从数据库中通过这个id去一个个查详情（N）查到子之后再把详情放到session缓存中.这就是N+1问题 
	 * 第二次，先查id,然后再通过id从session缓存中查详情，如果有就直接拿出来，不去数据库中查了，没有就去数据库中查
	 */
	@Test
	public void testIterate() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			String hql = "from Student";
			List<Student> list = session.createQuery(hql).list();
			Iterator<Student> it2 = session.createQuery(hql).iterate();
			while (it2.hasNext()) {
				Student student = it2.next();
				System.out.println(student);
			}
			/*
			 * for (int j = 3; j < 51; j++) { Student student = new Student();
			 * student.setId(j); session.delete(student); }
			 */
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	/**
	 * 用两者可以解决N+1问题，先用list把详情从数据库中查到，并放到缓存中，然后，再用iterate查id，直接通过id在session缓存中拿出来
	 * ，这样比较快
	 */
	@Test
	public void testListAndIterate() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			String hql = "from Student";
			Iterator<Student> it = session.createQuery(hql).iterate();
			while (it.hasNext()) {
				Student student = it.next();
				System.out.println(student);
			}
			Iterator<Student> it2 = session.createQuery(hql).iterate();
			while (it2.hasNext()) {
				Student student = it2.next();
				System.out.println(student);
			}
			/*
			 * for (int j = 3; j < 51; j++) { Student student = new Student();
			 * student.setId(j); session.delete(student); }
			 */
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
}
