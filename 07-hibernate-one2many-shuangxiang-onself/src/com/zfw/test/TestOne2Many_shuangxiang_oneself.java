package com.zfw.test;

import org.hibernate.Session;
import org.junit.Test;

import com.zfw.beans.NewsLable;


public class TestOne2Many_shuangxiang_oneself {
	@Test
	//站在一方的角度来设置关联关系
	public void one(){
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			NewsLable foolballNews = new NewsLable("足球新闻", "足球新闻足球新闻足球新闻足球新闻");
			NewsLable basketballNews = new NewsLable("篮球新闻", "篮球新闻篮球新闻篮球新闻篮球新闻");
			
			NewsLable sportNews = new NewsLable("运动新闻", "运动新闻运动新闻运动新闻运动新闻");
			//这里设置关联关系
			sportNews.getChirdNewsLables().add(basketballNews);
			sportNews.getChirdNewsLables().add(foolballNews);
			
			session.save(sportNews);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
	}
	@Test
	//站在多方的角度来设置关联关系
	public void many(){
		Session session = HbnUtil.getSession();
		try {
			session.beginTransaction();
			NewsLable sportNews = new NewsLable("运动新闻", "运动新闻运动新闻运动新闻运动新闻");
			
			
			NewsLable foolballNews = new NewsLable("足球新闻", "足球新闻足球新闻足球新闻足球新闻");
			NewsLable basketballNews = new NewsLable("篮球新闻", "篮球新闻篮球新闻篮球新闻篮球新闻");
			
			//这里设置关联关系
			foolballNews.setParentNewsLable(sportNews);
			basketballNews.setParentNewsLable(sportNews);
			
			session.save(foolballNews);
			session.save(basketballNews);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
	}
}
