package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Tcliente;

/**
 * Home object for domain model class Tcliente.
 * @see ec.com.doc.ele.dto.Tcliente
 * @author Hibernate Tools
 */
@Stateless
public class TclienteHome implements TclienteHomeI {

	private static final Log log = LogFactory.getLog(TclienteHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tcliente transientInstance) {
		log.debug("persisting Tcliente instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tcliente persistentInstance) {
		log.debug("removing Tcliente instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tcliente merge(Tcliente detachedInstance) {
		log.debug("merging Tcliente instance");
		try {
			Tcliente result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tcliente findById(int id) {
		log.debug("getting Tcliente instance with id: " + id);
		try {
			Tcliente instance = entityManager.find(Tcliente.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
