package ec.com.doc.ele.dao;
// Generated 11-may-2016 8:40:39 by Hibernate Tools 4.0.0.Final

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.com.doc.ele.dto.Tmenu;
import ec.com.doc.ele.dto.Tusuario;
import ec.com.doc.ele.dto.Tusuariomenu;
import ec.com.doc.ele.dto.TusuariomenuId;
import ec.com.doc.ele.dto.VistaModulosUsuarios;
import ec.com.doc.ele.ejb.CriterioBusqueda;
import ec.com.doc.ele.ejb.ParametroCriterioBusqueda;
import ec.com.doc.ele.enumeracion.ComparatorTypeEnum;
import ec.com.doc.ele.excepcion.ComEleExcepcion;
import ec.com.doc.ele.excepcion.DAOException;
import ec.com.doc.ele.locator.ComEleEjb;
import ec.com.doc.ele.locator.Utilitarios;
import ec.com.doc.ele.pojo.BaseVO;
import ec.com.doc.ele.servicio.EjbService;
import ec.com.doc.ele.servicio.ServiceLocal;
import ec.com.doc.ele.util.log.LogGenerico;

/**
 * Home object for domain model class Tusuario.
 * @see ec.com.doc.ele.dto.Tusuario
 * @author Hibernate Tools
 */
@Stateless
public class TusuarioHome  implements TusuarioHomeI{

	private static final Log log = LogFactory.getLog(TusuarioHome.class);
	@EJB(name = ComEleEjb.SCP_SERVICE, beanInterface = ServiceLocal.class)
	public EjbService dataService;

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Tusuario transientInstance) {
		log.debug("persisting Tusuario instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Tusuario persistentInstance) {
		log.debug("removing Tusuario instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Tusuario merge(Tusuario detachedInstance) {
		log.debug("merging Tusuario instance");
		try {
			Tusuario result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Tusuario findById(long id) {
		log.debug("getting Tusuario instance with id: " + id);
		try {
			Tusuario instance = entityManager.find(Tusuario.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public Collection<VistaModulosUsuarios>  obtenerAccesosPorUsuario(VistaModulosUsuarios vistaAccesosDTO) {
		Collection<VistaModulosUsuarios> vistaAccesosCol= new ArrayList<VistaModulosUsuarios>();
		try {
			
			StringBuilder queryBuilder = null;
			queryBuilder = new StringBuilder("SELECT distinct M.codigomodulo,M.labelacceso,M.descripcionacceso,M.orden,U.estado,U.codigousuario,M.modulo.codigomodulo,M.url ");
			queryBuilder.append(",M.mostrarenpanel,M.estiloimagen ");
			queryBuilder.append(" FROM Usuario U INNER JOIN U.modulousuariosForCodigousuario MU ");
			queryBuilder.append(" INNER JOIN MU.modulo M ");
			queryBuilder.append(" WHERE U.estado=:pEstado AND M.estado=:pEstado AND MU.estado=:pEstado AND M.visible='SI'");
			queryBuilder.append(" AND U.codigousuario=:pUsuario");
			//and idPadre
			if(vistaAccesosDTO.getIdaccesopadre() == null){
				queryBuilder.append(" AND M.modulo.codigomodulo is null");
			}
			if(vistaAccesosDTO.getIdaccesopadre() != null){
				queryBuilder.append(" AND M.modulo.codigomodulo = :pIdPadre");
			}
			queryBuilder.append(" ORDER BY M.orden ");
			Query query = entityManager.createQuery(queryBuilder.toString());
			query.setParameter("pUsuario",vistaAccesosDTO.getIdusuario());
			query.setParameter("pEstado",vistaAccesosDTO.getEstado());
			if(vistaAccesosDTO.getIdaccesopadre() != null){
				query.setParameter("pIdPadre",vistaAccesosDTO.getIdaccesopadre());
			}
			
			List<?> listaResp=  query.getResultList(); 
			if(listaResp.size()>0){
				VistaModulosUsuarios vistaAccesosRespDTO= null;
				for(Iterator<?> it = listaResp.iterator(); it.hasNext();) {
					vistaAccesosRespDTO=new VistaModulosUsuarios();
	                Object[] dato = (Object[]) it.next();
	                vistaAccesosRespDTO.setIdacceso((String)dato[0]);
	                vistaAccesosRespDTO.setLabelacceso((String)dato[1]);
	                vistaAccesosRespDTO.setDescripcionacceso((String)dato[2]);
	                vistaAccesosRespDTO.setOrden((Short)dato[3]);
	                vistaAccesosRespDTO.setEstado((String)dato[4]);
	                vistaAccesosRespDTO.setIdusuario((Long)dato[5]);
	                vistaAccesosRespDTO.setIdaccesopadre((String)dato[6]);
	                vistaAccesosRespDTO.setUrl((String)dato[7]);
	                vistaAccesosRespDTO.setMostrarenpanel((String)dato[8]);
	                vistaAccesosRespDTO.setEstiloimagen((String)dato[9]);
					vistaAccesosCol.add(vistaAccesosRespDTO);
				}
			}
			return vistaAccesosCol;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public Collection<VistaModulosUsuarios> getArbolAccesosPorUsuario(Long usuarioId) {
		VistaModulosUsuarios viewAccessItemDtoBusqueda = null;
		final String ESTADO_ACTIVO = "ACT";
		viewAccessItemDtoBusqueda= new VistaModulosUsuarios();
		viewAccessItemDtoBusqueda.setIdusuario(usuarioId.longValue());
		viewAccessItemDtoBusqueda.setEstado(ESTADO_ACTIVO);
		//Obtener solo accesos padres
		Collection<VistaModulosUsuarios> viewAccessItemDtoCol = this.obtenerAccesosPorUsuario(viewAccessItemDtoBusqueda);
			for (VistaModulosUsuarios visAccDTO : viewAccessItemDtoCol) {
				visAccDTO.setVistaAccesosDTOCol(this.getAccesosRecursivo(visAccDTO, ESTADO_ACTIVO));
			}

			return viewAccessItemDtoCol;
	}
	private Collection<VistaModulosUsuarios> getAccesosRecursivo(VistaModulosUsuarios vistaAccesosDTO, String estadoActivo) {
		VistaModulosUsuarios vistaAccesosDtoBusqueda = null;

			vistaAccesosDtoBusqueda = new VistaModulosUsuarios();
			vistaAccesosDtoBusqueda.setIdaccesopadre(vistaAccesosDTO.getIdacceso());
			vistaAccesosDtoBusqueda.setEstado(estadoActivo);
			vistaAccesosDtoBusqueda.setIdusuario(vistaAccesosDTO.getIdusuario().longValue());

			Collection<VistaModulosUsuarios> vistaAccesosDtoCol = this.obtenerAccesosPorUsuario(vistaAccesosDtoBusqueda);
			if (vistaAccesosDtoCol != null && !vistaAccesosDtoCol.isEmpty()) {
				for (VistaModulosUsuarios visAccDto2 : vistaAccesosDtoCol) {
					visAccDto2.setVistaAccesosPadre(vistaAccesosDTO);
					visAccDto2.setVistaAccesosDTOCol(this.getAccesosRecursivo(visAccDto2, estadoActivo));
				}
			} 
			return vistaAccesosDtoCol;
	}
	
	/**
	 * Actualizar usuarios con sus modulos
	 * @param perfilVO
	 * @param accesosList
	 * @throws SCPException
	 */
	public void transActualizarUsuario(BaseVO<Tusuario>  usuarioVO, Collection<Tmenu> menuList) throws ComEleExcepcion {
		try {
			Tusuario usuario=usuarioVO.getBaseDTO();
			Long codigoUsuario=usuario.getIdusuario();
			
			//Guardar usuario
			Tusuario usuarioNuevoDto=new Tusuario();
			usuarioNuevoDto.setIdusuario(codigoUsuario.longValue());
			usuarioNuevoDto=dataService.findUnique(usuarioNuevoDto);
			if(usuarioNuevoDto!=null){
				if(usuario!=null){
					dataService.transUpdate(usuario);
				}
			}
					//Crear coleccion de modulos a insertar
					if(menuList!=null){
							String id="id";
							Collection<Tusuariomenu> menuNuevos = new ArrayList<Tusuariomenu>(menuList.size());
							Tusuariomenu usuarioMenuNuevosDTO= null;
							for(Tmenu menu:menuList){
								usuarioMenuNuevosDTO= new Tusuariomenu();
								TusuariomenuId usuarioMenuID= new TusuariomenuId();
								usuarioMenuID.setIdusuario(codigoUsuario);
								usuarioMenuID.setIdmenu(menu.getIdmenu());
								usuarioMenuNuevosDTO.setId(usuarioMenuID);
								usuarioMenuNuevosDTO.setEstadousumen("ACT");
								menuNuevos.add(usuarioMenuNuevosDTO);
							}
							
							
							//Guardar los Accesos asignado a un Perfil
							usuarioMenuNuevosDTO= new Tusuariomenu();
							TusuariomenuId usuarioMenuID= new TusuariomenuId();
							usuarioMenuID.setIdusuario(codigoUsuario);
							usuarioMenuNuevosDTO.setId(usuarioMenuID);
							usuarioMenuNuevosDTO.setTmenu(new Tmenu());
							usuarioMenuNuevosDTO.getTmenu().setCriteriaSearch(new CriterioBusqueda());
							usuarioMenuNuevosDTO.getTmenu().getCriteriaSearch().addCriteriaSearchParameter(new ParametroCriterioBusqueda<String>("url",ComparatorTypeEnum.IS_NOT_NULL));
							Collection<Tusuariomenu> modulosExistentes = dataService.findObjects(usuarioMenuNuevosDTO);
							id="id";
							//Iterar coleccion para buscar si hay que crear o modificar 
							for (Tusuariomenu usuMenDTO : modulosExistentes) {
								Collection<Tusuariomenu> usuariosMenuEncontrados;				
								usuariosMenuEncontrados = Utilitarios.simpleSearch(id, usuMenDTO.getId(), menuNuevos);
								if (usuariosMenuEncontrados == null || usuariosMenuEncontrados.isEmpty()) {
									if (usuMenDTO.getEstadousumen().equals("ACT")) {
										//inactivar el padre en caso que exista un solo hijo
										inactivarAccesos(usuMenDTO.getId().getIdmenu(),codigoUsuario);
										
									}
								} else {
									if (usuMenDTO.getEstadousumen().equals("INA")) {
										activarAccesos(usuMenDTO.getId().getIdmenu(),codigoUsuario);
									}
								}
								
								for(Tusuariomenu usuMenDTOElim : menuNuevos){
									if((usuMenDTOElim.getId().getIdmenu()!=null && usuMenDTO.getId().getIdmenu() !=null) && usuMenDTOElim.getId().getIdmenu().intValue()== usuMenDTO.getId().getIdmenu().intValue()){
										menuNuevos.remove(usuMenDTOElim);
										break;
									}
								}
							}
							for (Tusuariomenu usuModDTO : menuNuevos) {
								crearAccesos(usuModDTO.getId().getIdmenu(),codigoUsuario);
							}
					}
					
		} catch (DAOException e) {
			throw new ComEleExcepcion(e);
		} catch (Exception e) {
			throw new ComEleExcepcion(e);
		}
	}
	
	private void inactivarAccesos(Long codigoMenu ,Long codigoUsuario){
		try {
			Tmenu menuDTO= new Tmenu();
			menuDTO.setIdmenu(codigoMenu);
			menuDTO.setTmenu(new Tmenu());
			menuDTO=dataService.findUnique(menuDTO);
			if(menuDTO!=null){
				Tusuariomenu usuariosMenuDTO= new Tusuariomenu();
				usuariosMenuDTO.setId(new TusuariomenuId());
				usuariosMenuDTO.getId().setIdmenu(menuDTO.getIdmenu());
				usuariosMenuDTO.getId().setIdusuario(codigoUsuario);
				usuariosMenuDTO=dataService.findUnique(usuariosMenuDTO);
				if(menuDTO.getTmenu()==null){
					usuariosMenuDTO.setEstadousumen("INA");
					dataService.transUpdate(usuariosMenuDTO);
				}else{
					Tmenu menuDTO2= new Tmenu();
					menuDTO2.setTmenu(new Tmenu());
					menuDTO2.getTmenu().setIdmenu(menuDTO.getTmenu().getIdmenu());
					Collection<Tmenu> menuCol=dataService.findObjects(menuDTO2);
					if(menuCol!=null && menuCol.size()>0){
						Long []idBuscar= new Long[menuCol.size()];
						int i=0;
						for(Tmenu menDTO:menuCol){
							idBuscar[i]=menDTO.getIdmenu();
							LogGenerico.getLogger().info("getIdacceso(): "+menDTO.getIdmenu());
							i++;
						}
						Tusuariomenu usuMenDTO= new Tusuariomenu();
						usuMenDTO.setId(new TusuariomenuId());
						usuMenDTO.getId().setIdusuario(codigoUsuario);
						usuMenDTO.setCriteriaSearch(new CriterioBusqueda());
						usuMenDTO.getCriteriaSearch().addCriteriaSearchParameter(new ParametroCriterioBusqueda <Long>("id.idmenu",ComparatorTypeEnum.IN_COMPARATOR,idBuscar));
						usuMenDTO.setEstadousumen("ACT");
						Collection<Tusuariomenu> perAccCol=dataService.findObjects(usuMenDTO);
						LogGenerico.getLogger().info("perAccCol: "+perAccCol.size());
						if(perAccCol!=null && perAccCol.size()==1){
							usuMenDTO=perAccCol.iterator().next();
							usuMenDTO.setEstadousumen("INA");
							dataService.transUpdate(usuMenDTO);
							inactivarAccesos(menuDTO.getTmenu().getIdmenu(),codigoUsuario);
						}
						else{
							usuariosMenuDTO.setEstadousumen("INA");
							dataService.transUpdate(usuariosMenuDTO);
						}
					}
				}
			}
		} catch (DAOException e) {
			throw new ComEleExcepcion(e);
		}
	}
	
	private void activarAccesos(Long codigoModulo ,Long codigoUsuario){
		try {
			Tmenu menuDTO= new Tmenu();
			menuDTO.setIdmenu(codigoModulo);
			menuDTO.setTmenu(new Tmenu());
			menuDTO=dataService.findUnique(menuDTO);
			if(menuDTO!=null){
				Tusuariomenu usuarioMenuDTO= new Tusuariomenu();
				usuarioMenuDTO.setId(new TusuariomenuId());
				usuarioMenuDTO.getId().setIdmenu(menuDTO.getIdmenu());
				usuarioMenuDTO.getId().setIdusuario(codigoUsuario);
				usuarioMenuDTO=dataService.findUnique(usuarioMenuDTO);
				if(menuDTO.getTmenu()==null){
					if(usuarioMenuDTO.getEstadousumen().equals("INA")){
						usuarioMenuDTO.setEstadousumen("ACT");
						dataService.transUpdate(usuarioMenuDTO);
					}
				}else{
					if(usuarioMenuDTO.getEstadousumen().equals("INA")){
						usuarioMenuDTO.setEstadousumen("ACT");
						dataService.transUpdate(usuarioMenuDTO);
						activarAccesos(menuDTO.getTmenu().getIdmenu(),codigoUsuario);
					}
				}
			}
		} catch (DAOException e) {
			throw new ComEleExcepcion(e);
		}
	}
	
	private void crearAccesos(Long codigoMenu ,Long codigoUsuario){
		try {
			Tmenu menuDTO= new Tmenu();
			menuDTO.setIdmenu(codigoMenu);
			menuDTO.setTmenu(new Tmenu());
			menuDTO=dataService.findUnique(menuDTO);
			if(menuDTO!=null){
				Tusuariomenu usuarioMenuDTO= new Tusuariomenu();
				usuarioMenuDTO.setId(new TusuariomenuId());
				usuarioMenuDTO.getId().setIdmenu(menuDTO.getIdmenu());
				usuarioMenuDTO.getId().setIdusuario(codigoUsuario);
				usuarioMenuDTO=dataService.findUnique(usuarioMenuDTO);
				if(menuDTO.getTmenu()==null){
					if(usuarioMenuDTO==null){
						usuarioMenuDTO= new Tusuariomenu();
						usuarioMenuDTO.setId(new TusuariomenuId());
						usuarioMenuDTO.getId().setIdmenu(menuDTO.getIdmenu());
						usuarioMenuDTO.getId().setIdusuario(codigoUsuario);
						usuarioMenuDTO.setEstadousumen("ACT");
						dataService.transCreate(usuarioMenuDTO);
					}
					else{
						if(usuarioMenuDTO.getEstadousumen().equals("INA")){
							usuarioMenuDTO= new Tusuariomenu();
							usuarioMenuDTO.setId(new TusuariomenuId());
							usuarioMenuDTO.getId().setIdmenu(menuDTO.getIdmenu());
							usuarioMenuDTO.getId().setIdusuario(codigoUsuario);
							usuarioMenuDTO.setEstadousumen("ACT");
							dataService.transUpdate(usuarioMenuDTO);
						}
					}
				}else{
					if(usuarioMenuDTO==null){
						usuarioMenuDTO= new Tusuariomenu();
						usuarioMenuDTO.setId(new TusuariomenuId());
						usuarioMenuDTO.getId().setIdmenu(menuDTO.getIdmenu());
						usuarioMenuDTO.getId().setIdusuario(codigoUsuario);
						usuarioMenuDTO.setEstadousumen("ACT");
						dataService.transCreate(usuarioMenuDTO);
						crearAccesos(menuDTO.getTmenu().getIdmenu(),codigoUsuario);
					}
					else{
						if(usuarioMenuDTO.getEstadousumen().equals("INA")){
							usuarioMenuDTO= new Tusuariomenu();
							usuarioMenuDTO.setId(new TusuariomenuId());
							usuarioMenuDTO.getId().setIdmenu(menuDTO.getIdmenu());
							usuarioMenuDTO.getId().setIdusuario(codigoUsuario);
							usuarioMenuDTO.setEstadousumen("ACT");
							dataService.transUpdate(usuarioMenuDTO);
						}
					}
				}
			}
		} catch (DAOException e) {
			throw new ComEleExcepcion(e);
		}
	}

	public EjbService getDataService() {
		return dataService;
	}
}
