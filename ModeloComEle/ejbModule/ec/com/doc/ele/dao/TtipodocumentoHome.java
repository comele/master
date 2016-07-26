package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Ttipodocumento;

/**
 * Home object for domain model class Ttipodocumento.
 * @see ec.com.doc.ele.dto.Ttipodocumento
 * @author Hibernate Tools
 */
@Stateless
public class TtipodocumentoHome implements TtipodocumentoHomeI{

	private static final Log log = LogFactory.getLog(TtipodocumentoHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Ttipodocumento transientInstance) {
		log.debug("persisting Ttipodocumento instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Ttipodocumento persistentInstance) {
		log.debug("removing Ttipodocumento instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Ttipodocumento merge(Ttipodocumento detachedInstance) {
		log.debug("merging Ttipodocumento instance");
		try {
			Ttipodocumento result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Ttipodocumento findById(int id) {
		log.debug("getting Ttipodocumento instance with id: " + id);
		try {
			Ttipodocumento instance = entityManager.find(Ttipodocumento.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
