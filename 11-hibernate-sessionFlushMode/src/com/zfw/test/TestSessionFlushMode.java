package com.zfw.test;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.junit.Test;

import com.zfw.beans.Student;

public class TestSessionFlushMode {
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

	// 删除到了刷新时间点后的底层操作
	@Test
	public void testFlush() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();

			// get查询出的对象放入到session缓存中了
			Student student = session.get(Student.class, 2);
			// delete一到达刷新时间点，马上就执行delete语句，更新session中的数据
			session.delete(student);

			// query是个刷新时间点，此时再从session中查询，所以session中没有id为2的对象了，但没提交，所以没同步到数据库中，所以DB中还是有id为2的对象
			List<Student> list = session.createQuery("from Student").list();
			for (Student student2 : list) {
				System.out.println(student2);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}

	// 更新到了刷新时间点后的底层操作
	@Test
	public void testFlushUpdate() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();

			// get查询出的对象放入到session缓存中了
			Student student = session.get(Student.class, 2);
			// update到了刷新时间点后，会将修改的数据与快照中的相比较，如果不一致，就执行Update(),如果一致就不执行更新语句
			student.setName("wang");
			//query是一个刷新时间点
			session.createQuery("from Student").list();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}

	// 保存与刷新时间点的底层操作
	@Test
	public void testFlushSave() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();

			Student student = new Student("张三", 12, 99);
			// save()直接把数据写入到了session缓存中，不用等到刷新时间点，而delete()和update()不一样，他们是对session中现有数据进行修改，所以要到刷新时间点
			session.save(student);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}
	// 修改session设置刷新模式
	@Test
	public void testFlushMode() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			session.setFlushMode(FlushMode.COMMIT);
			// get查询出的对象放入到session缓存中了
			Student student = session.get(Student.class, 2);
			// update到了刷新时间点后，会将修改的数据与快照中的相比较，如果不一致，就执行Update(),如果一致就不执行更新语句
			student.setName("zhang");
			//query是一个刷新时间点
			session.createQuery("from Student").list();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
}
