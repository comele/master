package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Tcomprobante.
 * @see ec.com.doc.ele.dto.Tcomprobante
 * @author Hibernate Tools
 */
@Stateless
public class TcomprobanteHome {

	private static final Log log = LogFactory.getLog(TcomprobanteHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tcomprobante transientInstance) {
		log.debug("persisting Tcomprobante instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tcomprobante persistentInstance) {
		log.debug("removing Tcomprobante instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tcomprobante merge(Tcomprobante detachedInstance) {
		log.debug("merging Tcomprobante instance");
		try {
			Tcomprobante result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tcomprobante findById(int id) {
		log.debug("getting Tcomprobante instance with id: " + id);
		try {
			Tcomprobante instance = entityManager.find(Tcomprobante.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
