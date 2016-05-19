package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Testablecimiento;

/**
 * Home object for domain model class Testablecimiento.
 * @see ec.com.doc.ele.dto.Testablecimiento
 * @author Hibernate Tools
 */
@Stateless
public class TestablecimientoHome {

	private static final Log log = LogFactory.getLog(TestablecimientoHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Testablecimiento transientInstance) {
		log.debug("persisting Testablecimiento instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Testablecimiento persistentInstance) {
		log.debug("removing Testablecimiento instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Testablecimiento merge(Testablecimiento detachedInstance) {
		log.debug("merging Testablecimiento instance");
		try {
			Testablecimiento result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Testablecimiento findById(int id) {
		log.debug("getting Testablecimiento instance with id: " + id);
		try {
			Testablecimiento instance = entityManager.find(Testablecimiento.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
