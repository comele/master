package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Ttipodeducible.
 * @see ec.com.doc.ele.dto.Ttipodeducible
 * @author Hibernate Tools
 */
@Stateless
public class TtipodeducibleHome {

	private static final Log log = LogFactory.getLog(TtipodeducibleHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Ttipodeducible transientInstance) {
		log.debug("persisting Ttipodeducible instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Ttipodeducible persistentInstance) {
		log.debug("removing Ttipodeducible instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Ttipodeducible merge(Ttipodeducible detachedInstance) {
		log.debug("merging Ttipodeducible instance");
		try {
			Ttipodeducible result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Ttipodeducible findById(int id) {
		log.debug("getting Ttipodeducible instance with id: " + id);
		try {
			Ttipodeducible instance = entityManager.find(Ttipodeducible.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
