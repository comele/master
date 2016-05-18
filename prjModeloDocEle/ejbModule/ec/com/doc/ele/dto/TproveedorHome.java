package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Tproveedor.
 * @see ec.com.doc.ele.dto.Tproveedor
 * @author Hibernate Tools
 */
@Stateless
public class TproveedorHome {

	private static final Log log = LogFactory.getLog(TproveedorHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tproveedor transientInstance) {
		log.debug("persisting Tproveedor instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tproveedor persistentInstance) {
		log.debug("removing Tproveedor instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tproveedor merge(Tproveedor detachedInstance) {
		log.debug("merging Tproveedor instance");
		try {
			Tproveedor result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tproveedor findById(int id) {
		log.debug("getting Tproveedor instance with id: " + id);
		try {
			Tproveedor instance = entityManager.find(Tproveedor.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
