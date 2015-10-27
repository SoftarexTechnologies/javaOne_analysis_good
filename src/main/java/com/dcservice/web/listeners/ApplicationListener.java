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
		HibernateUtil.getInstance().addConnectionListener(this);
		HibernateUtil.getInstance().getSessionFactory();
	}

	@Override
	public void fireConnetionEstablished() {
	}

	@Override
	public void fireConnetionResufed() {
	}

}
