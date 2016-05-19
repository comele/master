package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Tdetallecomprobante;

/**
 * Home object for domain model class Tdetallecomprobante.
 * @see ec.com.doc.ele.dto.Tdetallecomprobante
 * @author Hibernate Tools
 */
@Stateless
public class TdetallecomprobanteHome {

	private static final Log log = LogFactory.getLog(TdetallecomprobanteHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tdetallecomprobante transientInstance) {
		log.debug("persisting Tdetallecomprobante instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tdetallecomprobante persistentInstance) {
		log.debug("removing Tdetallecomprobante instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tdetallecomprobante merge(Tdetallecomprobante detachedInstance) {
		log.debug("merging Tdetallecomprobante instance");
		try {
			Tdetallecomprobante result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tdetallecomprobante findById(int id) {
		log.debug("getting Tdetallecomprobante instance with id: " + id);
		try {
			Tdetallecomprobante instance = entityManager.find(Tdetallecomprobante.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
