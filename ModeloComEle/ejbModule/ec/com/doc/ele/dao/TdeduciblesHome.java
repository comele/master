package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Tdeducibles;

/**
 * Home object for domain model class Tdeducibles.
 * @see ec.com.doc.ele.dto.Tdeducibles
 * @author Hibernate Tools
 */
@Stateless
public class TdeduciblesHome {

	private static final Log log = LogFactory.getLog(TdeduciblesHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tdeducibles transientInstance) {
		log.debug("persisting Tdeducibles instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tdeducibles persistentInstance) {
		log.debug("removing Tdeducibles instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tdeducibles merge(Tdeducibles detachedInstance) {
		log.debug("merging Tdeducibles instance");
		try {
			Tdeducibles result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tdeducibles findById(int id) {
		log.debug("getting Tdeducibles instance with id: " + id);
		try {
			Tdeducibles instance = entityManager.find(Tdeducibles.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
