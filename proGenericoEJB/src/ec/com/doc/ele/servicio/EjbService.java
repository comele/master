package ec.com.doc.ele.servicio;

import java.io.Serializable;
import java.util.Collection;

import ec.com.doc.ele.excepcion.DAOException;
import ec.com.doc.ele.pojo.BaseVO;
import ec.com.doc.ele.pojo.BuscadorDTO;


/**
 * @author walvarez
 *
 */
public interface EjbService{
	
	/**
	 * @param <V>
	 * @param searchDTO
	 * @return
	 * @throws DAOException
	 */	
	public <V extends BuscadorDTO> Collection<V> findObjects(V searchDTO) throws DAOException ;

	/**
	 * @param <V>
	 * @param searchDTO
	 * @return
	 * @throws DAOException
	 */
	public <V extends Serializable> Collection<V> findObjects(BaseVO<V> searchDTO) throws DAOException;

	
	/**
	 * Obtiene un &uacute;nico resultado a partir de una plantilla de
	 * b&uacute;squeda
	 * @return
	 * @throws DAOException
	 */
	public <V extends BuscadorDTO> V findUnique(V searchDTO) throws DAOException ;

	/**
	 * @param <V>
	 * @param searchDTO
	 * @return
	 * @throws DAOException
	 */
	public <V extends Serializable> V findUnique(BaseVO<V> searchDTO) throws DAOException;


	public <T extends BuscadorDTO> void transCreate(T searchDTO) throws DAOException;

	public <T extends BuscadorDTO> void transCreateAll(Collection<T> listSearchDTO) throws DAOException;

	public <T extends BuscadorDTO> void transUpdate(T searchDTO) throws DAOException ;

	public <T extends BuscadorDTO> void transUpdateAll(Collection<T> listSearchDTO) throws DAOException;
	
	public <T extends BuscadorDTO> void transDelete(T searchDTO) throws DAOException ;
}
