package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Tdeduciblefactura;

/**
 * Home object for domain model class Tdeduciblefactura.
 * @see ec.com.doc.ele.dto.Tdeduciblefactura
 * @author Hibernate Tools
 */
@Stateless
public class TdeduciblefacturaHome implements TdeduciblefacturaHomeI{

	private static final Log log = LogFactory.getLog(TdeduciblefacturaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tdeduciblefactura transientInstance) {
		log.debug("persisting Tdeduciblefactura instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tdeduciblefactura persistentInstance) {
		log.debug("removing Tdeduciblefactura instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tdeduciblefactura merge(Tdeduciblefactura detachedInstance) {
		log.debug("merging Tdeduciblefactura instance");
		try {
			Tdeduciblefactura result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tdeduciblefactura findById(int id) {
		log.debug("getting Tdeduciblefactura instance with id: " + id);
		try {
			Tdeduciblefactura instance = entityManager.find(Tdeduciblefactura.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
