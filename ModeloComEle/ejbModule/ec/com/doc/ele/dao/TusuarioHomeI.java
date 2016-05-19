package ec.com.doc.ele.dao;

// Generated 14-dic-2011 22:52:05 by Hibernate Tools 3.4.0.CR1

import java.util.Collection;

import javax.ejb.Remote;

import ec.com.doc.ele.dto.Tmenu;
import ec.com.doc.ele.dto.Tusuario;
import ec.com.doc.ele.dto.VistaModulosUsuarios;
import ec.com.doc.ele.excepcion.ComEleExcepcion;
import ec.com.doc.ele.pojo.BaseVO;

/**
 * Home object for domain model class Usuario.
 * @see ec.com.doc.ele.dto.Usuario
 * @author Hibernate Tools
 */
@Remote
public interface TusuarioHomeI {

	
	public void persist(Tusuario transientInstance);

	public void remove(Tusuario persistentInstance);
	
	public Tusuario merge(Tusuario detachedInstance);

	public Tusuario findById(long id);
	
	public Collection<VistaModulosUsuarios>  obtenerAccesosPorUsuario(VistaModulosUsuarios vistaAccesosDTO);
	
	public Collection<VistaModulosUsuarios> getArbolAccesosPorUsuario(Long usuarioId);
	
	/**
	 * Actualizar usuarios con sus modulos
	 * @param perfilVO
	 * @param accesosList
	 * @throws SCPException
	 */
	public void transActualizarUsuario(BaseVO<Tusuario>  usuarioVO, Collection<Tmenu> menuList) throws ComEleExcepcion ;
}
