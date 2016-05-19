package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Tusuario.
 * @see ec.com.doc.ele.dto.Tusuario
 * @author Hibernate Tools
 */
@Stateless
public class TusuarioHome {

	private static final Log log = LogFactory.getLog(TusuarioHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tusuario transientInstance) {
		log.debug("persisting Tusuario instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tusuario persistentInstance) {
		log.debug("removing Tusuario instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tusuario merge(Tusuario detachedInstance) {
		log.debug("merging Tusuario instance");
		try {
			Tusuario result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tusuario findById(int id) {
		log.debug("getting Tusuario instance with id: " + id);
		try {
			Tusuario instance = entityManager.find(Tusuario.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
