package com.dcservice.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.dcservice.persistence.models.FieldResponse;
import com.dcservice.persistence.models.Option;
import com.dcservice.persistence.models.fields.Field;
import com.dcservice.persistence.models.responses.Response;
import com.ocpsoft.shade.org.apache.commons.logging.Log;
import com.ocpsoft.shade.org.apache.commons.logging.LogFactory;

public class HibernateUtil implements IConnectionManager {

	private static final Log log = LogFactory.getLog(HibernateUtil.class);

	private static SessionFactory sessionFactory;

	private static HibernateUtil instance;

	private static ServiceRegistry serviceRegistry;

	private static List<IConnectionListner> connectionListners;

	public static Map<String, String> connectionSettings;
	
	public HibernateUtil(){
		connectionListners = new ArrayList<IConnectionListner>();
		connectionSettings = new HashMap<String, String>();
	}
	
	public void addAnnotatedClasses(Configuration config) {
		config.addAnnotatedClass(Field.class);
		config.addAnnotatedClass(Option.class);
		config.addAnnotatedClass(Response.class);
		config.addAnnotatedClass(FieldResponse.class);

	}

	public List<String> getViewClasses() {
		List<String> viewClasses = new ArrayList<String>();

		return viewClasses;
	}

	public void addConnectionListener(IConnectionListner listener) {
		connectionListners.add(listener);
	}

	public void removeConnectionListener(IConnectionListner listener) {
		connectionListners.remove(listener);
	}

	public synchronized SessionFactory getSessionFactory()
			throws ExceptionInInitializerError {
		if (sessionFactory == null) {
			createSessionFactory();
		}

		return sessionFactory;
	}

	public static HibernateUtil getInstance() {
		if (instance == null) {
			instance = new HibernateUtil();
		}

		return instance;
	}

	private static void createSessionFactory()
			throws ExceptionInInitializerError {
			Date d1 = new Date();
			System.out.println("HibernateUtil: Opening DB connection.");
			Configuration config = new Configuration();
			HibernateUtil.getInstance().addAnnotatedClasses(config);
			config.configure();

			Map<String, String> params = new HashMap<String, String>();

			params.put(
					"host",
					String.valueOf(config.getProperties().get(
							"hibernate.connection.host")));
			params.put(
					"database",
					String.valueOf(config.getProperties().get(
							"hibernate.connection.database")));

			connectionSettings.put(
					"url",
					String.valueOf(config.getProperties().get(
							"hibernate.connection.url")));
			connectionSettings.put("username",
					config.getProperty("hibernate.connection.username"));
			connectionSettings.put("password",
					config.getProperty("hibernate.connection.password"));

			config.getProperties().put("hibernate.connection.url",
					connectionSettings.get("url"));

			System.out.println("HOST: "
					+ config.getProperty("hibernate.connection.host"));
			System.out.println("DB: "
					+ config.getProperty("hibernate.connection.database"));
			System.out.println("USER: " + connectionSettings.get("username"));
			System.out.println("PASSWORD: "
					+ connectionSettings.get("password"));

			serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(config.getProperties()).build();

			sessionFactory = config.buildSessionFactory(serviceRegistry);

			checkConnection(d1);

	}

	public static void checkConnection() {
		Session s = null;
		s = sessionFactory.openSession();
		s.beginTransaction().commit();
		SessionTracker.getInstance().sessionOpening("HibernateUtil");
		s.close();
		SessionTracker.getInstance().sessionClosing("HibernateUtil");
		s = null;

	}

	private static void checkConnection(Date d1) {
		checkConnection();
		System.out.println(String.format(
				"Connection was successfully opened in %d seconds",
				(new Date().getTime() - d1.getTime()) / 1000));
		onSuccessConnection();
	}

	private static void onSuccessConnection() {
		if (connectionListners == null) {
			return;
		}
		for (IConnectionListner item : connectionListners) {
			item.fireConnetionEstablished();
		}
	}

	private static void onConnectionFail() {
		if (connectionListners == null) {
			return;
		}
		for (IConnectionListner item : connectionListners) {
			item.fireConnetionResufed();
		}
	}

	public static void shutdown() {
		onConnectionFail();
		// Close caches and connection pools
		if (sessionFactory != null) {
			sessionFactory.close();
			sessionFactory = null;
		}

	}

	@Override
	public void handleConfigFileChange() {
		HibernateUtil.shutdown();
		try {
			createSessionFactory();
		} catch (ExceptionInInitializerError e) {
			log.error(e.getMessage(), e);
		}
		System.out.println("SessionFactory recreated");
	}

}
