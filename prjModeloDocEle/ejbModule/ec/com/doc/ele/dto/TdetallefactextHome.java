package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Tdetallefactext.
 * @see ec.com.doc.ele.dto.Tdetallefactext
 * @author Hibernate Tools
 */
@Stateless
public class TdetallefactextHome {

	private static final Log log = LogFactory.getLog(TdetallefactextHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tdetallefactext transientInstance) {
		log.debug("persisting Tdetallefactext instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tdetallefactext persistentInstance) {
		log.debug("removing Tdetallefactext instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tdetallefactext merge(Tdetallefactext detachedInstance) {
		log.debug("merging Tdetallefactext instance");
		try {
			Tdetallefactext result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tdetallefactext findById(int id) {
		log.debug("getting Tdetallefactext instance with id: " + id);
		try {
			Tdetallefactext instance = entityManager.find(Tdetallefactext.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
