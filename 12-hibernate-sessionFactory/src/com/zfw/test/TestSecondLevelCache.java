package com.zfw.test;

import java.util.Set;

import net.sf.ehcache.search.aggregator.Min;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.zfw.beans.Country;
import com.zfw.beans.Minister;

public class TestSecondLevelCache {
	@Test
	public void test() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			Country country = new Country("USA");
			Minister minister1 = new Minister("aaa");
			Minister minister2 = new Minister("bbb");
			Minister minister3 = new Minister("ccc");
			country.getMinisters().add(minister1);
			country.getMinisters().add(minister2);
			country.getMinisters().add(minister3);
			session.save(country);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	//证明类存在二级缓存中
	@Test
	public void testSecondLevelCacha() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			Minister minister = session.get(Minister.class, 2);
			System.out.println(minister);
			// 清空session缓存
			session.clear();

			// 在此处，拿到了数据，而且底层没有执行select语句，现在session也已清空，并且拿到的对象也不是同一个，所以证明是从二级缓存里拿到的
			Minister minister2 = session.get(Minister.class, 2);
			System.out.println(minister2);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	//证明集合存在二级缓存中仅仅缓存的是集合的id
	@Test
	public void testSecondLevelCacha_jihe() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			Country country = session.get(Country.class, 1);
			Set<Minister> ministers = country.getMinisters();
			for (Minister minister : ministers) {
				System.out.println(minister);
			}
			
			// 清空session缓存
			session.clear();
			// 拿到了数据 ，并且也执行select语句,但没有执行的是根据id查询的select。 证明集合数据也存在二级缓存中,但缓存了id
			Country country2 = session.get(Country.class, 1);
			Set<Minister> ministers2 = country2.getMinisters();
			for (Minister minister : ministers2) {
				System.out.println(minister.getMid());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	//证明Query缓存查询出来的结果也会放入一二级缓存中，query默认情况下是不会一二级缓存中读取数据的
	@Test
	public void testSecondLevelCacha_query() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			//第一次查询 用query
			String hql="from Country where cid=1";
			Country country = (Country) session.createQuery(hql).uniqueResult();
			System.out.println(country);
			
			//第二次查询，用get，没有执行select 语句，证明query查询把结果放入到了session缓存中了
			Country country2 = session.get(Country.class, 1);
			System.out.println(country2);
			
			//第三次查询，用query不清空session,证明query默认情况下是不会一级缓存中读取数据的
			Country country3 = (Country) session.createQuery("from Country where cid=1").uniqueResult();
			System.out.println(country3);
			
			session.clear();
			//第四次查询，用query清空session,证明query默认情况下是不会二级缓存中读取数据的
			Country country4 = (Country) session.createQuery("from Country where cid=1").uniqueResult();
			System.out.println(country4);
			
			
			
			
			session.getTransaction().rollback();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
}
