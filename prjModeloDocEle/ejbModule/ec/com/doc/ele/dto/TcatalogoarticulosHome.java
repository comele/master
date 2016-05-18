package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Tcatalogoarticulos.
 * @see ec.com.doc.ele.dto.Tcatalogoarticulos
 * @author Hibernate Tools
 */
@Stateless
public class TcatalogoarticulosHome {

	private static final Log log = LogFactory.getLog(TcatalogoarticulosHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tcatalogoarticulos transientInstance) {
		log.debug("persisting Tcatalogoarticulos instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tcatalogoarticulos persistentInstance) {
		log.debug("removing Tcatalogoarticulos instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tcatalogoarticulos merge(Tcatalogoarticulos detachedInstance) {
		log.debug("merging Tcatalogoarticulos instance");
		try {
			Tcatalogoarticulos result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tcatalogoarticulos findById(int id) {
		log.debug("getting Tcatalogoarticulos instance with id: " + id);
		try {
			Tcatalogoarticulos instance = entityManager.find(Tcatalogoarticulos.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
