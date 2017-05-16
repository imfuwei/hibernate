package com.zfw.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HbnUtil {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessioinFactory() {
		if (sessionFactory == null || sessionFactory.isClosed()) {
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		}
		return sessionFactory;
	}

	public static Session getSession() {
		return getSessioinFactory().getCurrentSession();
	}
}
