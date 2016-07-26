package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Tfacturaclienteexterno;

/**
 * Home object for domain model class Tfacturaclienteexterno.
 * @see ec.com.doc.ele.dto.Tfacturaclienteexterno
 * @author Hibernate Tools
 */
@Stateless
public class TfacturaclienteexternoHome implements TfacturaclienteexternoHomeI{

	private static final Log log = LogFactory.getLog(TfacturaclienteexternoHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tfacturaclienteexterno transientInstance) {
		log.debug("persisting Tfacturaclienteexterno instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tfacturaclienteexterno persistentInstance) {
		log.debug("removing Tfacturaclienteexterno instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tfacturaclienteexterno merge(Tfacturaclienteexterno detachedInstance) {
		log.debug("merging Tfacturaclienteexterno instance");
		try {
			Tfacturaclienteexterno result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tfacturaclienteexterno findById(int id) {
		log.debug("getting Tfacturaclienteexterno instance with id: " + id);
		try {
			Tfacturaclienteexterno instance = entityManager.find(Tfacturaclienteexterno.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
