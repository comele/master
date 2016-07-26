package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Tfirmaelectronica;

/**
 * Home object for domain model class Tfirmaelectronica.
 * @see ec.com.doc.ele.dto.Tfirmaelectronica
 * @author Hibernate Tools
 */
@Stateless
public class TfirmaelectronicaHome implements TfirmaelectronicaHomeI{

	private static final Log log = LogFactory.getLog(TfirmaelectronicaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tfirmaelectronica transientInstance) {
		log.debug("persisting Tfirmaelectronica instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tfirmaelectronica persistentInstance) {
		log.debug("removing Tfirmaelectronica instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tfirmaelectronica merge(Tfirmaelectronica detachedInstance) {
		log.debug("merging Tfirmaelectronica instance");
		try {
			Tfirmaelectronica result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tfirmaelectronica findById(int id) {
		log.debug("getting Tfirmaelectronica instance with id: " + id);
		try {
			Tfirmaelectronica instance = entityManager.find(Tfirmaelectronica.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
