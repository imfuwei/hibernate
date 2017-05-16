package com.zfw.beans;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.zfw.utils.HbnUtils;

public class utilTest {
	@Test
	// 增
	public void testSavaForUtils() {
		// 1.获取session
		Session session = HbnUtils.getSession();
		try {
			// 2.开启事务
			session.beginTransaction();
			Student student = new Student("ww", 32, 100);
			// 3.增
			session.save(student);
			// 4.提交事务
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 5.回滚事务
			session.getTransaction().rollback();
		}
	}

	@Test
	public void testPersist() {
		// 1.获取session
		Session session = HbnUtils.getSession();
		try {
			// 2.开启事务
			session.beginTransaction();
			// 3.操作
			Student student = new Student("ls", 32, 1);
			session.persist(student);// persist是jpa的api接口中的规范
			// 4.提交事务
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 5.回滚事务
			session.getTransaction().rollback();
		}
	}

	// 删
	@Test
	public void testDelete() {
		// 1.获取session
		Session session = HbnUtils.getSession();
		try {
			// 2.开启事务
			session.beginTransaction();
			// 3.操作
			Student student = new Student();
			student.setId(2);
			session.delete(student);// 删除是根据id进行删除的
			// 4.提交事务
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 5.回滚事务
			session.getTransaction().rollback();
		}
	}

	// 改
	@Test
	public void testUpdate() {
		// 1.获取session
		Session session = HbnUtils.getSession();
		try {
			// 2.开启事务
			session.beginTransaction();
			// 3.操作
			Student student = new Student();
			student.setId(1);
			session.update(student);// 修改是根据id进行删除的
			// 4.提交事务
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 5.回滚事务
			session.getTransaction().rollback();
		}
	}

	// savaorupdate
	// 改
	@Test
	public void testSaveOrUpdate() {
		// 1.获取session
		Session session = HbnUtils.getSession();
		try {
			// 2.开启事务
			session.beginTransaction();
			// 3.操作
			Student student = new Student("张三", 12, 99);
			student.setId(1);// saveOrUpdate执行依据是看操作对象是否有id，有id就update，无id就save
			session.saveOrUpdate(student);
			// 4.提交事务
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 5.回滚事务
			session.getTransaction().rollback();
		}
	}

	// 查
	@Test
	public void testGet() {
		// 1.获取session
		Session session = HbnUtils.getSession();
		try {
			// 2.开启事务
			session.beginTransaction();
			// 3.操作
			Student student = session.get(Student.class, 1);
			System.out.println(student);
			// 4.提交事务
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 5.回滚事务
			session.getTransaction().rollback();
		}
	}

	// 查
	@Test
	public void testLoad() {
		// 1.获取session
		Session session = HbnUtils.getSession();
		try {
			// 2.开启事务
			session.beginTransaction();
			// 3.操作
			Student student = session.load(Student.class, 1);
			System.out.println(student);
			// 4.提交事务
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 5.回滚事务
			session.getTransaction().rollback();
		}
	}

	@Test
	public void testUpdate2() {
		Session session = HbnUtils.getSession();
		try {
			// 4.开启事务
			session.beginTransaction();
			// session.getTransaction().begin();
			// 5.操作
			Student student2 = session.load(Student.class, 1);
			System.out.println(student2);
			student2.setName("张三");
			session.saveOrUpdate(student2);
			// 6.事务提交
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 7.事务回滚
			session.getTransaction().rollback();
		}
	}
}
