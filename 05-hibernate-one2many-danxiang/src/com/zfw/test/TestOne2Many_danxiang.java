package com.zfw.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Test;

import com.zfw.beans.Country;
import com.zfw.beans.Minister;

public class TestOne2Many_danxiang {

	@Test
	public void one_to_many() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();

			Minister minister1 = new Minister("aaa");
			Minister minister2 = new Minister("bbb");
			Minister minister3 = new Minister("ccc");
			Set<Minister> ministers = new HashSet<Minister>();
			ministers.add(minister1);
			ministers.add(minister2);
			ministers.add(minister3);

			Country country = new Country("USA");
			// 关联关系建立在这里完成
			country.setMinisters(ministers);

			session.save(minister1);
			session.save(minister2);
			session.save(minister3);

			session.save(country);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}

	@Test
	public void one_to_many2() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();

			Minister minister1 = new Minister("aaa");
			Minister minister2 = new Minister("bbb");
			Minister minister3 = new Minister("ccc");
			Set<Minister> ministers = new HashSet<Minister>();
			ministers.add(minister1);
			ministers.add(minister2);
			ministers.add(minister3);
			
			Country country = new Country("USA");
			// 关联关系建立在这里完成
			country.getMinisters().add(minister1);
			country.getMinisters().add(minister2);
			country.getMinisters().add(minister3);
			
			
			/*可以在映射文件中加入级联属性为cascade="save-update"来让他们发生级联
			 * session.save(minister1); session.save(minister2);
			 * session.save(minister3);
			 */

			session.save(country);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}

}
