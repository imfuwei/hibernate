package com.zfw.beans;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javassist.convert.Transformer;

import javax.xml.crypto.dsig.Transform;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.junit.Test;

import com.zfw.utils.HbnUtils;

public class queryTest {
	/**
	 * 准备数据
	 */
	@Test
	public void savaSomeToDB() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			for (int i = 0; i < 50; i++) {
				Student student = new Student("zhang" + i, 20 + i, 30 + i);
				session.save(student);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	@Test
	/**
	 * 使用sql语句查询所有
	 */
	public void query_SQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();

			String sql = "select s_id,s_name,s_age,s_score from t_student";

			List<Student> list = session.createSQLQuery(sql)
					.addEntity(Student.class).list();
			for (Student s : list) {
				System.out.println(s);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	@Test
	/**
	 * 使用hql语句查询所有
	 */
	public void query_HQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			// sql中出现的是表名和字段名
			// hql中出现的是类名和属性名
			String hql = "from Student";
			List<Student> list = session.createQuery(hql).list();
			for (Student student : list) {
				System.out.println(student);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	@Test
	/**
	 * QBC使用hibernate标准查询所有
	 */
	public void query_QBC() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			List<Student> list = session.createCriteria(Student.class).list();
			for (Student student : list) {
				System.out.println(student);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 查询结果排序
	 */
	@Test
	public void query_orderby_SQL() {
		Session session = HbnUtils.getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			/*
			 * String sql="select min(s_id) from t_student";//asc List<Integer>
			 * list = session.createSQLQuery(sql).list(); for (Integer student :
			 * list) { System.out.println(student); }
			 */
			String sql = "select s_id,s_name,s_age,s_score from t_student order by s_score desc";// asc
			List<Student> list = session.createSQLQuery(sql)
					.addEntity(Student.class).list();
			for (Student student : list) {
				System.out.println(student);
			}
			/*
			 * for (int i = 101; i <=150; i++) { Student object = new Student();
			 * object.setId(i); session.delete(object); }
			 */
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	/**
	 * HQL查询结果排序
	 */
	@Test
	public void query_orderby_HQL() {
		Session session = HbnUtils.getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			String hql = "from Student order by score desc";// asc
			List<Student> list = session.createQuery(hql).list();
			for (Student student : list) {
				System.out.println(student);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	/**
	 * QBC 查询结果排序
	 */
	@Test
	public void query_orderby_QBC() {
		Session session = HbnUtils.getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			List<Student> list = session.createCriteria(Student.class)
					.addOrder(Order.desc("score")).list();
			for (Student student : list) {
				System.out.println(student);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	/**
	 * HQL动态参数绑定查询
	 */
	@Test
	public void query_dyn_HQL() {
		Session session = HbnUtils.getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			String hql = "from Student where score>? and score<?";
			List<Student> list = session.createQuery(hql).setDouble(0, 60)
					.setDouble(1, 70).list();
			for (Student student : list) {
				System.out.println(student);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}

	}

	/**
	 * HQL动态参数绑定查询起一个别名
	 */
	@Test
	public void query_dyn_HQL2() {
		Session session = HbnUtils.getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			String hql = "from Student where score>:myscore and score<:myscope2";
			List<Student> list = session.createQuery(hql)
					.setDouble("myscore", 60).setDouble("myscope2", 70).list();
			for (Student student : list) {
				System.out.println(student);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	/**
	 * HQL另外一方法动态参数查询
	 */
	@Test
	public void query_dyn_HQL3() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			String hql = "from Student where score>:myscore";
			List<Student> list = session.createQuery(hql)
					.setParameter("myscore", 50.0).list();// 注意用parameter一定要是相同的数据类型
			// String hql="from Student where score>?";
			// List<Student> list = session.createQuery(hql).setParameter(0,
			// 50.0).list();//注意用parameter一定要是相同的数据类型
			for (Student student : list) {
				System.out.println(student);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 分页查询sql
	 */
	@Test
	public void query_limit_SQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			String sql = "select * from t_student limit ?,?";// limit
																// startindex,pagesize分页查询
			List<Student> list = session.createSQLQuery(sql)
					.addEntity(Student.class).setInteger(0, 3).setInteger(1, 4)
					.list();
			for (Student student : list) {
				System.out.println(student);
			}

			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 分页查询hql
	 */
	@Test
	public void query_limit_HQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			String hql = "from Student";
			List<Student> list = session.createQuery(hql).setFirstResult(3)// 相当于startindex
					.setMaxResults(4)// 相当于pagesize
					.list();
			for (Student student : list) {
				System.out.println(student);
			}

			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 分页查询qbc
	 */
	@Test
	public void query_limit_QBC() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			List<Student> list = session.createCriteria(Student.class)
					.setFirstResult(3)// 相当于startindex
					.setMaxResults(4)// 相当于pagesize
					.list();
			for (Student student : list) {
				System.out.println(student);
			}

			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 模糊查询SQL
	 * 
	 */
	@Test
	public void query_mohu_SQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			String sql = "select s_name from t_student where s_name like :myname";
			List<String> list = session.createSQLQuery(sql)
					.setString("myname", "%zhang3%").list();
			for (String integer : list) {
				System.out.println(integer);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 模糊查询HQL
	 * 
	 */
	@Test
	public void query_mohu_HQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			String hql = "from Student where name like :myname";
			List<Student> list = session.createQuery(hql)
					.setString("myname", "%zhang4%").list();
			for (Student integer : list) {
				System.out.println(integer);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 唯一性查询HQL
	 * 
	 */
	@Test
	public void query_unique_HQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			String hql = "from Student where id=:myid";
			Student student = (Student) session.createQuery(hql)
					.setInteger("myid", 10).uniqueResult();
			System.out.println(student);
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 唯一性查询SQL
	 * 
	 */
	@Test
	public void query_unique_SQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			String sql = "select * from t_student where s_id=?";
			Student student = (Student) session.createSQLQuery(sql)
					.addEntity(Student.class).setInteger(0, 10).uniqueResult();
			System.out.println(student);
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 唯一性查询QBC
	 * 
	 */
	@Test
	public void query_unique_QBC() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			Student student = (Student) session.createCriteria(Student.class)
					.add(Restrictions.eq("id", 10)).uniqueResult();// 查询结果唯一
			System.out.println(student);
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 聚合函数查询SQL
	 */
	@Test
	public void query_juhe_SQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();

			String sql = "select count(*) from t_student";
			BigInteger count = (BigInteger) session.createSQLQuery(sql)
					.uniqueResult();
			System.out.println(count);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 聚合函数查询HQL
	 */
	@Test
	public void query_juhe_HQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();

			String hql = "select count(*) from Student";
			Long count = (Long) session.createQuery(hql).uniqueResult();
			System.out.println(count);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 聚合函数查询QBC
	 */
	@Test
	public void query_juhe_QBC() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			Long result = (Long) session.createCriteria(Student.class)
					.setProjection(Projections.count("id")).uniqueResult();
			System.out.println(result);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 投影查询SQL(就是查特定字段)
	 */
	@Test
	public void query_touying_SQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			String sql = "select s_name name,s_age age from t_student";

			List<Student> list = session
					.createSQLQuery(sql)
					.setResultTransformer(
							Transformers.aliasToBean(Student.class))// aliasToBean()方法首先会创建一个空的
																	// Student
																	// 对象，然后会将别名与
																	// Student
																	// 属性名对
																	// 比，再用查询出的值初始化创建的
																	// Student
																	// 对象.所以别名要和属性名相同
					.list();
			for (Student student : list) {
				System.out.println(student);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 投影查询HQL(就是查特定字段)
	 */
	@Test
	public void query_touying_HQL() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			String hql = "select new Student(name,age) from Student";// 需要在实体类中定义要查询属性的构造方法
			List<Student> list = session.createQuery(hql).list();
			for (Student student : list) {
				System.out.println(student);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	/**
	 * 投影查询QBC(就是查特定字段)太麻烦了，不写了，用时再查
	 */
	@Test
	public void query_touying_QBC() {
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	
	
	/**
	 * 分组查询
	 */
	@Test
	public void query_group_SQL(){
		Session session = HbnUtils.getSession();
		try {
			session.beginTransaction();
			String sql="select s_age from t_student group by s_age";
			List<Object> obj = session.createSQLQuery(sql).list();
			for (Object object : obj) {
				System.out.println(object);
			}
			
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	
	/**
	 * 命名查询，就是在映射文件中，将查询语句配置进去
	 */
	@Test
	public void query_Named() {
		Session session = HbnUtils.getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			List<Student> list = session.getNamedQuery("query_ByScore")
					.setDouble("myscope2", 70).list();
			for (Student student : list) {
				System.out.println(student);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

}
