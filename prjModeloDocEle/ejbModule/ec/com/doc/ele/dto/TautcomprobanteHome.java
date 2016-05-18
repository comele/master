package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Tautcomprobante.
 * @see ec.com.doc.ele.dto.Tautcomprobante
 * @author Hibernate Tools
 */
@Stateless
public class TautcomprobanteHome {

	private static final Log log = LogFactory.getLog(TautcomprobanteHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tautcomprobante transientInstance) {
		log.debug("persisting Tautcomprobante instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tautcomprobante persistentInstance) {
		log.debug("removing Tautcomprobante instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tautcomprobante merge(Tautcomprobante detachedInstance) {
		log.debug("merging Tautcomprobante instance");
		try {
			Tautcomprobante result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tautcomprobante findById(int id) {
		log.debug("getting Tautcomprobante instance with id: " + id);
		try {
			Tautcomprobante instance = entityManager.find(Tautcomprobante.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
