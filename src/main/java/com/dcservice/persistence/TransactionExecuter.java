package com.dcservice.persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.dcservice.common.exceptions.PersistenceBeanException;
import com.dcservice.persistence.dao.DaoManager;
import com.ocpsoft.shade.org.apache.commons.logging.Log;
import com.ocpsoft.shade.org.apache.commons.logging.LogFactory;

public class TransactionExecuter  {
	private static Object monitor = new Object();
	
	public static final Log log = LogFactory.getLog(TransactionExecuter.class);

	public static void execute(IAction action) throws IllegalAccessException, PersistenceBeanException {
		execute(action, DaoManager.getSession());
	}

	public static synchronized void execute(IAction action, Session session) {
		synchronized (monitor) {
			Transaction tr = null;
			try {
				tr = session.beginTransaction();

				action.execute(session);
				if (tr != null && tr.getStatus() == TransactionStatus.ACTIVE) {
					tr.rollback();
				}
			} finally {
				if (tr != null && tr.getStatus() == TransactionStatus.ACTIVE
						&& tr.getStatus() != TransactionStatus.ROLLED_BACK) {
					try {
						tr.commit();
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
				action.onExecuted();
			}
		}
	}
}
