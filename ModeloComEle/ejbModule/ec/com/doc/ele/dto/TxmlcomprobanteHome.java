package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Txmlcomprobante.
 * @see ec.com.doc.ele.dto.Txmlcomprobante
 * @author Hibernate Tools
 */
@Stateless
public class TxmlcomprobanteHome {

	private static final Log log = LogFactory.getLog(TxmlcomprobanteHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Txmlcomprobante transientInstance) {
		log.debug("persisting Txmlcomprobante instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Txmlcomprobante persistentInstance) {
		log.debug("removing Txmlcomprobante instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Txmlcomprobante merge(Txmlcomprobante detachedInstance) {
		log.debug("merging Txmlcomprobante instance");
		try {
			Txmlcomprobante result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Txmlcomprobante findById(int id) {
		log.debug("getting Txmlcomprobante instance with id: " + id);
		try {
			Txmlcomprobante instance = entityManager.find(Txmlcomprobante.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
