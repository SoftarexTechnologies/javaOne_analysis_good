package com.dcservice.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dcservice.persistence.HibernateUtil;
import com.dcservice.persistence.IConnectionListner;

public class ApplicationListener implements ServletContextListener, IConnectionListner {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		HibernateUtil.shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		HibernateUtil.addConnectionListener(this);
        try {
			HibernateUtil.getSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fireConnetionEstablished() {
	}

	@Override
	public void fireConnetionResufed() {
	}

}
