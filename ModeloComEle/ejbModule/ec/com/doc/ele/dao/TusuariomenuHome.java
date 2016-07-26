package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Tusuariomenu;
import ec.com.doc.ele.dto.TusuariomenuId;

/**
 * Home object for domain model class Tusuariomenu.
 * @see ec.com.doc.ele.dto.Tusuariomenu
 * @author Hibernate Tools
 */
@Stateless
public class TusuariomenuHome implements TusuariomenuHomeI{

	private static final Log log = LogFactory.getLog(TusuariomenuHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tusuariomenu transientInstance) {
		log.debug("persisting Tusuariomenu instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tusuariomenu persistentInstance) {
		log.debug("removing Tusuariomenu instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tusuariomenu merge(Tusuariomenu detachedInstance) {
		log.debug("merging Tusuariomenu instance");
		try {
			Tusuariomenu result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tusuariomenu findById(TusuariomenuId id) {
		log.debug("getting Tusuariomenu instance with id: " + id);
		try {
			Tusuariomenu instance = entityManager.find(Tusuariomenu.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
