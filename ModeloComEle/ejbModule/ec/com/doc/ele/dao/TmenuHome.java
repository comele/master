package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Tmenu;

/**
 * Home object for domain model class Tmenu.
 * @see ec.com.doc.ele.dto.Tmenu
 * @author Hibernate Tools
 */
@Stateless
public class TmenuHome {

	private static final Log log = LogFactory.getLog(TmenuHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tmenu transientInstance) {
		log.debug("persisting Tmenu instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tmenu persistentInstance) {
		log.debug("removing Tmenu instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tmenu merge(Tmenu detachedInstance) {
		log.debug("merging Tmenu instance");
		try {
			Tmenu result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tmenu findById(int id) {
		log.debug("getting Tmenu instance with id: " + id);
		try {
			Tmenu instance = entityManager.find(Tmenu.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
