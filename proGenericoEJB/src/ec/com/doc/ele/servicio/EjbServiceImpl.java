package ec.com.doc.ele.servicio;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;

import ec.com.doc.ele.ejb.EjbDAO;
import ec.com.doc.ele.ejb.impl.EjbDAOImpl;
import ec.com.doc.ele.excepcion.DAOException;
import ec.com.doc.ele.pojo.BaseVO;
import ec.com.doc.ele.pojo.BuscadorDTO;


/**
 * @author walvarez
 *
 */
public class EjbServiceImpl implements EjbService{
	/**
	 * acceso a la capa de DAO
	 */

	private EjbDAO<BuscadorDTO> dao;

	
	public void setEntityManager(EntityManager em) {
		this.dao = new EjbDAOImpl<BuscadorDTO>(em);
	}


	/**
	 * @param <V>
	 * @param searchDTO
	 * @return
	 * @throws DAOException
	 */	
	@SuppressWarnings("unchecked")
	public <V extends BuscadorDTO> Collection<V> findObjects(V searchDTO) throws DAOException {
		return (Collection<V>) dao.findPage(searchDTO, Boolean.TRUE);
	}

	/**
	 * @param <V>
	 * @param searchDTO
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public <V extends Serializable> Collection<V> findObjects(BaseVO<V> searchDTO) throws DAOException {
		return (Collection<V>) dao.findPage(searchDTO, Boolean.TRUE);
	}

	
	/**
	 * Obtiene un &uacute;nico resultado a partir de una plantilla de
	 * b&uacute;squeda
	 * @param <V>
	 * @param searchDTO
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public <V extends BuscadorDTO> V findUnique(V searchDTO) throws DAOException {
		return (V) dao.findUnique(searchDTO);
	}

	/**
	 * @param <V>
	 * @param searchDTO
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public <V extends Serializable> V findUnique(BaseVO<V> searchDTO) throws DAOException {
		return (V) dao.findUnique(searchDTO);
	}

	public <T extends BuscadorDTO> void transCreate(T searchDTO) throws DAOException {
		dao.crearObjeto(searchDTO);
		
	}


	public <T extends BuscadorDTO> void transCreateAll(Collection<T> listSearchDTO) throws DAOException {
		for(T obj : listSearchDTO){
			dao.crearObjeto(obj);
		}
		
	}


	public <T extends BuscadorDTO> void transUpdate(T searchDTO) throws DAOException {
		dao.actualizarObjeto(searchDTO);
		
	}


	public <T extends BuscadorDTO> void transUpdateAll(Collection<T> listSearchDTO) throws DAOException {
		for(T obj : listSearchDTO){
			dao.actualizarObjeto(obj);
		}
		
	}


	public <T extends BuscadorDTO> void transDelete(T searchDTO) throws DAOException {
		dao.eliminarObjeto(searchDTO);
	}


	public <T extends BuscadorDTO> void detach(T searchDTO) throws DAOException {
		this.dao.detach(searchDTO);
	}
}
