package com.zfw.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Test;

import com.zfw.beans.Country;
import com.zfw.beans.Minister;

public class TestOne2Many_shuangxiang {
	@Test
	//country在维护
	public void one_to_many() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();

			Minister minister1 = new Minister("aaa");
			Country country = new Country("USA");
			// 关联关系建立在这里完成
			country.getMinisters().add(minister1);
			
			session.save(country);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}
	//minister在维护
	@Test
	public void one_to_many2() {
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			
			Country country = new Country("USA");
			Minister minister1 = new Minister("aaa");
			minister1.setCountry(country);
			
			//谁维护就把谁save
			session.save(minister1);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
	}

}
