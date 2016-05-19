package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Ttipocomprobante;

/**
 * Home object for domain model class Ttipocomprobante.
 * @see ec.com.doc.ele.dto.Ttipocomprobante
 * @author Hibernate Tools
 */
@Stateless
public class TtipocomprobanteHome {

	private static final Log log = LogFactory.getLog(TtipocomprobanteHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Ttipocomprobante transientInstance) {
		log.debug("persisting Ttipocomprobante instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Ttipocomprobante persistentInstance) {
		log.debug("removing Ttipocomprobante instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Ttipocomprobante merge(Ttipocomprobante detachedInstance) {
		log.debug("merging Ttipocomprobante instance");
		try {
			Ttipocomprobante result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Ttipocomprobante findById(int id) {
		log.debug("getting Ttipocomprobante instance with id: " + id);
		try {
			Ttipocomprobante instance = entityManager.find(Ttipocomprobante.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
