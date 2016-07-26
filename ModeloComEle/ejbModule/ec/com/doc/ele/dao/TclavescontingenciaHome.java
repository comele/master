package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Tclavescontingencia;

/**
 * Home object for domain model class Tclavescontingencia.
 * @see ec.com.doc.ele.dto.Tclavescontingencia
 * @author Hibernate Tools
 */
@Stateless
public class TclavescontingenciaHome implements TclavescontingenciaHomeI{

	private static final Log log = LogFactory.getLog(TclavescontingenciaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tclavescontingencia transientInstance) {
		log.debug("persisting Tclavescontingencia instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tclavescontingencia persistentInstance) {
		log.debug("removing Tclavescontingencia instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tclavescontingencia merge(Tclavescontingencia detachedInstance) {
		log.debug("merging Tclavescontingencia instance");
		try {
			Tclavescontingencia result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tclavescontingencia findById(int id) {
		log.debug("getting Tclavescontingencia instance with id: " + id);
		try {
			Tclavescontingencia instance = entityManager.find(Tclavescontingencia.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
