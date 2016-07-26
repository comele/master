package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Tpuntoemision;

/**
 * Home object for domain model class Tpuntoemision.
 * @see ec.com.doc.ele.dto.Tpuntoemision
 * @author Hibernate Tools
 */
@Stateless
public class TpuntoemisionHome implements TpuntoemisionHomeI{

	private static final Log log = LogFactory.getLog(TpuntoemisionHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tpuntoemision transientInstance) {
		log.debug("persisting Tpuntoemision instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tpuntoemision persistentInstance) {
		log.debug("removing Tpuntoemision instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tpuntoemision merge(Tpuntoemision detachedInstance) {
		log.debug("merging Tpuntoemision instance");
		try {
			Tpuntoemision result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tpuntoemision findById(int id) {
		log.debug("getting Tpuntoemision instance with id: " + id);
		try {
			Tpuntoemision instance = entityManager.find(Tpuntoemision.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
