package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Terroresautorizacionsri;

/**
 * Home object for domain model class Terroresautorizacionsri.
 * @see ec.com.doc.ele.dto.Terroresautorizacionsri
 * @author Hibernate Tools
 */
@Stateless
public class TerroresautorizacionsriHome {

	private static final Log log = LogFactory.getLog(TerroresautorizacionsriHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Terroresautorizacionsri transientInstance) {
		log.debug("persisting Terroresautorizacionsri instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Terroresautorizacionsri persistentInstance) {
		log.debug("removing Terroresautorizacionsri instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Terroresautorizacionsri merge(Terroresautorizacionsri detachedInstance) {
		log.debug("merging Terroresautorizacionsri instance");
		try {
			Terroresautorizacionsri result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Terroresautorizacionsri findById(int id) {
		log.debug("getting Terroresautorizacionsri instance with id: " + id);
		try {
			Terroresautorizacionsri instance = entityManager.find(Terroresautorizacionsri.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
