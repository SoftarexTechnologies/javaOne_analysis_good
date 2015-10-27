package com.dcservice.persistence;

import static com.dcservice.common.helpers.ValidationHelper.isNullOrEmpty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dcservice.common.exceptions.PersistenceBeanException;
import com.ocpsoft.shade.org.apache.commons.logging.Log;
import com.ocpsoft.shade.org.apache.commons.logging.LogFactory;

public class PersistenceSession {
	public static transient final Log log = LogFactory
			.getLog(PersistenceSession.class);

	private Session session;

	private String id;

	public PersistenceSession() {
		id = UUID.randomUUID().toString();
	}

	public Session getSession() throws PersistenceBeanException {
		if (session == null || !session.isConnected() || !session.isOpen()) {
			session = createSession();
		}
		return session;
	}
	
	public Session getSession(String host, String database)
			throws PersistenceBeanException {

		if (session == null) {
			session = createSession(host, database, null, null);
		}
		return session;
	}

	public Session getSession(String host, String database, String username,
			String password) throws PersistenceBeanException {
		if (session == null) {
			session = createSession(host, database, username, password);
		}
		return session;
	}

	public static PersistenceSession getInstance() {
		return new PersistenceSession();
	}

	@Override
	public String toString() {
		return id;
	}

	public static Session createSession() throws PersistenceBeanException {
		Session session = null;
		try {
			session = HibernateUtil.getInstance().getSessionFactory().openSession();
			SessionTracker.getInstance().sessionOpening("PersistenceSession");
			session.setFlushMode(FlushMode.COMMIT);
			session.setCacheMode(CacheMode.IGNORE);
		} catch (ExceptionInInitializerError ex) {
			throw new PersistenceBeanException(
					"Error of Hibernate Session creating...", ex);
		}
		return session;
	}

	private static Session createSession(String host, String database,
			String username, String password) throws PersistenceBeanException {
		log.info("Session.createSession()...");
		Session session = null;
		try {
			Configuration config = new Configuration();
			HibernateUtil.getInstance().addAnnotatedClasses(config);
			config.configure();

			Map<String, String> params = new HashMap<String, String>();

			if (isNullOrEmpty(host)) {
				params.put(
						"host",
						String.valueOf(config.getProperties().get(
								"hibernate.connection.host")));
			} else {
				params.put("host", host);
			}

			if (isNullOrEmpty(database)) {
				params.put(
						"database",
						String.valueOf(config.getProperties().get(
								"hibernate.connection.database")));
			} else {
				params.put("database", database);
			}

			config.getProperties().put(
					"hibernate.connection.url",
					String.valueOf(config.getProperties().get(
							"hibernate.connection.url")));

			if (!isNullOrEmpty(username)) {
				config.getProperties().put("hibernate.connection.username",
						username);
			}
			if (!isNullOrEmpty(password)) {
				config.getProperties().put("hibernate.connection.password",
						password);
			}

			SessionFactory sessionFactory = config.buildSessionFactory();
			session = sessionFactory.openSession();
			session.setFlushMode(FlushMode.COMMIT);
		} catch (ExceptionInInitializerError ex) {
			log.info("createSession(). exception : " + ex);
			throw new PersistenceBeanException(
					"Error of Hibernate Session creating...", ex);
		}
		log.info("Session.createSession()...");
		return session;
	}

	public static Configuration getConfiguration(Logger outLog) {
		Configuration cfg = null;
		try(InputStream is = new FileInputStream(new File("./hibernate.cfg.xml"))) {
			outLog.info("Trying to read Hibernate cfg from current dir...");
			cfg = new Configuration().addInputStream(is).configure();
			outLog.info("Hibernate cfg has been read from current dir...");
		} catch (IOException e) {
			outLog.info("Trying to read Hibernate cfg by current ClassLoader...");
			try(InputStream is2 = PersistenceSession.class
					.getResourceAsStream("/hibernate.cfg.xml")){
				if (is2 != null) {
					cfg = new Configuration().addInputStream(is2).configure();
	
					outLog.info("Hibernate cfg has been read by current ClassLoader...");
				}
			}catch (IOException e2) {
				outLog.error(e2.getMessage(), e2);
			}
		} 
		if (cfg == null) {
			cfg = new Configuration().configure();
		}
		return cfg;
	}

	public void closeSession() {
		if (session != null) {
			session.clear();
			session.close();
			SessionTracker.getInstance().sessionClosing("PersistenceSession");
			session = null;
		}
	}
}
