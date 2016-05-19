package ec.com.doc.ele.test;
import java.util.Collection;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Ignore;

import ec.com.doc.ele.dao.TusuarioHome;
import ec.com.doc.ele.dao.TusuarioHomeI;
import ec.com.doc.ele.dto.Tusuario;
import ec.com.doc.ele.dto.VistaModulosUsuarios;
import ec.com.doc.ele.locator.ComEleLocator;
import ec.com.doc.ele.servicio.EjbService;

public class Test {
	
	public static Properties getInitialContextProperties() {
		Properties initialContextProperties = null;
		if (initialContextProperties == null) {
			initialContextProperties = new Properties();
			initialContextProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			initialContextProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			initialContextProperties.put(Context.PROVIDER_URL, "127.0.0.1:1099");
		}
		return initialContextProperties;
	}
	
	//@org.junit.Test
	//@Ignore
	public void obtenerPreguntas() {

		Context context;
		try {
			context = new InitialContext(getInitialContextProperties());
			TusuarioHomeI beanRemote = (TusuarioHomeI)context.lookup(TusuarioHome.class.getSimpleName() + "/remote");
			Tusuario usuario = beanRemote.findById(1);
			System.out.println("usuario.nombre: " + usuario.getNombreusuario());
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	//@Ignore
	public void obtenerPreguntasService(){
		try {
			Tusuario usuario =ComEleLocator.getInstancia().getUsuariosBeanService().findById(1);
			System.out.println("usuario.nombre: " + usuario.getNombreusuario());
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//@org.junit.Test
	//@Ignore
	public void obtenerUsuarioConDataService() {
		try {
			EjbService ds = (EjbService)ComEleLocator.getInstancia().getSCPDataService();
			Tusuario usuario= new Tusuario();
			usuario.setEstadousuario("ACT");
			Collection<Tusuario> usuarioCol=ds.findObjects(usuario);
			for(Tusuario u:usuarioCol){
				System.out.println("Cuenta: "+ u.getCuentausuario());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	//@org.junit.Test
	@Ignore
	public void obtenerVistaUsuarioConDataService() {
		try {
			EjbService ds = (EjbService)ComEleLocator.getInstancia().getSCPDataService();
			Tusuario usuario= new Tusuario();
			usuario.setIdusuario(1L);
			usuario=ds.findUnique(usuario);
			System.out.println("Usuario encontrado: "+ usuario.getCuentausuario());
			Collection<VistaModulosUsuarios> result = ComEleLocator.getInstancia().getUsuariosBeanService().getArbolAccesosPorUsuario(usuario.getIdusuario());
			System.out.println("Numero de registros: "+ result.size());
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
//	//@org.junit.Test
//	//@Ignore
//public void obtenermedicamento(){
//	
//	try{
//		System.out.println("-- obtenermedicamento");
//		//EjbService ds = (EjbService)CaritasLocator.getInstancia().getSCPDataService();
//		Medicamento medicamentoDTO = new Medicamento();
//		medicamentoDTO.setNombregenerico("ATOR");
//		
//	//	medicamentoDTO.setCatalogo(new Catalogo());
//		//medicamentoDTO.setExistenciasmedicamentos(new ArrayList<Existenciasmedicamento>());
//	//	medicamentoDTO.getExistenciasmedicamentos().add(new Existenciasmedicamento());
//		System.out.println("-- antes de buscar");
//		Collection<Medicamento> listaMedicamentos = ComEleLocator.getInstancia().getSCPDataService().findObjects(medicamentoDTO);
//		//CaritasLocator.getInstancia().get
//		if(listaMedicamentos != null){
//			System.out.println("-- encontrados: "+listaMedicamentos.size());
//			
//		}else{
//			System.out.println("-- nada}");
//		}	
//	}catch(Exception e){
//		System.out.println("Error al listar medicamentos");
//		e.printStackTrace();
//	}
//}
//	
//	//@org.junit.Test
//	public  Periodo obtenerPeriodoActivo() {
//		Periodo periodo = new Periodo();
//		try {
//			
//			periodo.setCatalogo(new Catalogo());
//			periodo.getCatalogo().setCodigocatalogo("ACT");
//
//			Periodo periodoActivo = null;
//			//Collection<Periodo> col= CaritasLocator.getInstancia().getSCPDataService().findObjects(periodo);
//			 periodoActivo = ComEleLocator.getInstancia().getSCPDataService().findUnique(periodo);
////					.getSCPDataService().findUnique(periodo);
//			//System.out.println("col: " + col.size());
//			//System.out.println("col: " + periodoActivo.getFechainicio());
//			if (periodoActivo != null) {				
//				System.out.println("periodo: " + periodoActivo.getCodigoperiodo());			
//			} else {
//				System.out.println("nulo");
//			}
//			
//			/*if(col  != null){
//				periodoActivo = col.iterator().next();
//				System.out.println("--periodo act: "+periodoActivo.getFechafin());
//			}else{
//				System.out.println("--periodo no find ");
//			}*/
//			
//			periodo = periodoActivo;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return periodo;
//	}
//	
//	//@org.junit.Test
//	public void guardarExistenciaMedicina(){
//		try{
//			System.out.println("-- guardarExistenciaMedicina");
//			//EjbService ds = (EjbService)CaritasLocator.getInstancia().getSCPDataService();
//			Medicamento medicamentoDTO = new Medicamento();
//			medicamentoDTO.setNombregenerico("ATOR");
//			
//		//	medicamentoDTO.setCatalogo(new Catalogo());
//			//medicamentoDTO.setExistenciasmedicamentos(new ArrayList<Existenciasmedicamento>());
//		//	medicamentoDTO.getExistenciasmedicamentos().add(new Existenciasmedicamento());
//			System.out.println("-- antes de buscar");
//			Collection<Medicamento> listaMedicamentos = ComEleLocator.getInstancia().getSCPDataService().findObjects(medicamentoDTO);
//			//CaritasLocator.getInstancia().get
//			if(listaMedicamentos != null){
//				Medicamento medicamento = listaMedicamentos.iterator().next();
//				System.out.println("-- encontrados: "+listaMedicamentos.size());
//				System.out.println("-- medicamento: "+medicamento.getNombrecomercial());
//				
//				BaseVO<Existenciasmedicamento> existenciaVO=new BaseVO<Existenciasmedicamento>();
//				System.out.println("-- 1 ");
//				existenciaVO.setBaseDTO(new Existenciasmedicamento());
//				System.out.println("-- 2 ");
//				
//				System.out.println("--usuario Sesion "+medicamento.getUsuarioByUsuarioregistro().getCodigousuario());
//				existenciaVO.getBaseDTO().setUsuarioByUsuarioregistro(medicamento.getUsuarioByUsuarioregistro());
//				System.out.println("-- 3 ");
//				existenciaVO.getBaseDTO().setFecharegistro(new Timestamp(System.currentTimeMillis()));
//				existenciaVO.getBaseDTO().setFechacaducidad(new Timestamp(System.currentTimeMillis()));
//				System.out.println("-- 4 ");
//				Periodo periodo = obtenerPeriodoActivo();
//				System.out.println( "periodo: "+periodo.getCodigoperiodo());
//				//existenciaVO.getBaseDTO().setPeriodo(periodo);
//				existenciaVO.getBaseDTO().setPeriodo(new Periodo());
//				existenciaVO.getBaseDTO().getPeriodo().setCodigoperiodo(periodo.getCodigoperiodo());
//			//	existenciaVO.getBaseDTO().setMedicamento(medicamento);
//				existenciaVO.getBaseDTO().setMedicamento(new Medicamento());
//				existenciaVO.getBaseDTO().getMedicamento().setCodigomedicamento(medicamento.getCodigomedicamento());
//				System.out.println("-- medicamento: "+ medicamento.getNombrecomercial());
//				existenciaVO.getBaseDTO().setCantidaddisponible(6);
//				System.out.println("-- 5 ");
//				existenciaVO.getBaseDTO().setCantidadoriginal(6);
//				System.out.println("-- cant: "+existenciaVO.getBaseDTO().getCantidaddisponible());
//				System.out.println("-- 6 ");
//				ComEleLocator.getInstancia().getServiceBean().transCreate(existenciaVO);
//				
//				
//				System.out.println( "fin: ");
//			}else{
//				System.out.println("-- nada");
//			}	
//			//obtener el objeto de sesion para mandar a guardar
//			
//			
//				//setear usuario de auditoria
//				
//			
//		}catch(Exception e){
//			System.out.println("-- Exception");
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public Medicamento buscarMedicamento(String nomberGenerico){
//		System.out.println("-- buscarMedicamento");
//		try{
//		//	EjbService ds = (EjbService)CaritasLocator.getInstancia().getSCPDataService();
//			Medicamento medicamentoDTO = new Medicamento();
//			medicamentoDTO.setNombregenerico(nomberGenerico);
//			
//		//	medicamentoDTO.setCatalogo(new Catalogo());
//			//medicamentoDTO.setExistenciasmedicamentos(new ArrayList<Existenciasmedicamento>());
//		//	medicamentoDTO.getExistenciasmedicamentos().add(new Existenciasmedicamento());
//			System.out.println("-- antes de buscar");
//			Collection<Medicamento> listaMedicamentos = ComEleLocator.getInstancia().getSCPDataService().findObjects(medicamentoDTO);
//			//CaritasLocator.getInstancia().get
//			if(listaMedicamentos != null){
//				System.out.println("-- encontrados: "+listaMedicamentos.size());
//				return listaMedicamentos.iterator().next();
//			}else{
//				System.out.println("-- nada}");
//				return null;
//			}	
//		}catch(Exception e){
//			System.out.println("Error al listar medicamentos");
//			e.printStackTrace();
//			return null;
//		}
//	}
//	//@org.junit.Test
//	public void findExistenciasMedicamento(){
//		try{
//			System.out.println("-- findExistenciasMedicamento");
//			Medicamento medicamento = buscarMedicamento("ATOR");
//			BaseVO<Existenciasmedicamento> existenciaVO = new BaseVO<Existenciasmedicamento>(new Existenciasmedicamento());
//			//existenciaVO.getBaseDTO().setMedicamento(medicamento);
//		//	existenciaVO.getBaseDTO().setMedicamento(new Medicamento());
//			//existenciaVO.getBaseDTO().getMedicamento().setCodigomedicamento(medicamento.getCodigomedicamento());
//			
//			
//			if(medicamento!= null){
//				//System.out.println("-- es: "+medicamento.getNombrecomercial());
//				Collection<Existenciasmedicamento> col = null;
//				System.out.println("-- fe 1 ");
//				col = ComEleLocator.getInstancia().getServiceBean().findObjects(existenciaVO);
//				System.out.println("-- fe 2 ");
//				if(col!=null){
//					System.out.println("-- col: "+col.size());
//				}else{
//					System.out.println("-- col null");
//				}
//			}else{
//				System.out.println("-- nada}");
//			}
//			
//			
//		}catch(Exception e){
//			System.out.println("-- Exception");
//			e.printStackTrace();
//		}
//		System.out.println("-- findExistenciasMedicamento");
//		
//	}
//	//@org.junit.Test
//	public void obtenerAlimento(){
//		System.out.println("-- obtenerAlimento");
//		try{
//		
//			Alimento alimento = new Alimento();
//			
//			//alimento.setCatalogo(new Catalogo()); 
//			alimento.setNombrealimento("AZUCAR");
//			/*BaseVO<Alimento> aliemntoVO = new BaseVO<Alimento>();
//			aliemntoVO.setBaseDTO(new Alimento());
//			aliemntoVO.getBaseDTO().setNombrealimento("AZUCAR");*/
//			//alimento.setNombrealimento("AZUCAR");
//			//medicamentoDTO.setNombregenerico("ATOR");
//			
//			//alimento.setCatalogo(new Catalogo());
//			//alimento.getCatalogo().setCodigocatalogo("ACT");
//			//medicamentoDTO.setExistenciasmedicamentos(new ArrayList<Existenciasmedicamento>());
//		//	medicamentoDTO.getExistenciasmedicamentos().add(new Existenciasmedicamento());
//			System.out.println("-- antes de buscar");
//			//Collection<Alimento> listaAlimentos = CaritasLocator.getInstancia().getSCPDataService().findObjects(aliemntoVO);
//			Collection<Alimento> listaAlimentos = ComEleLocator.getInstancia().getSCPDataService().findObjects(alimento);
//			//CaritasLocator.getInstancia().get
//			if(listaAlimentos != null){
//				System.out.println("-- encontrados: "+listaAlimentos.size());
//				
//			}else{
//				System.out.println("-- nada}");
//			}	
//		}catch(Exception e){
//			System.out.println("Error al listar medicamentos");
//			e.printStackTrace();
//		}
//	}
//
//	
//	public Usuario obtenerUsuario(){
//		Context context;
//		try {
//			context = new InitialContext(getInitialContextProperties());
//			UsuarioHomeI beanRemote = (UsuarioHomeI)context.lookup(UsuarioHome.class.getSimpleName() + "/remote");
//			Usuario usuario = beanRemote.findById(1);
//			System.out.println("-- usuario: "+ usuario.getCuenta());
//			return usuario;
//		}catch(Exception e){
//			System.out.println("-- exc usuario");
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
//	public Existenciasmedicamento obtenerExistenciaMedicamento(Long id){
//		Context context;
//		try {
//			context = new InitialContext(getInitialContextProperties());
//			System.out.println("-- oem1");
//			//ExistenciasmedicamentoHomeI beanRemote = (ExistenciasmedicamentoHomeI)context.lookup(Existenciasmedicamento.class.getSimpleName() + "/remote");
//			System.out.println("-- oem2");
//			//Existenciasmedicamento existencia = beanRemote.findById(id);
//			Existenciasmedicamento existencia = ComEleLocator.getInstancia().getExistenciaMedicamentoService().findById(id);
//			System.out.println("-- medicamento: "+ existencia.getCantidadoriginal());
//			
//			return existencia;
//			
//			
//			
//			
//		/*	BaseVO<Existenciasmedicamento> existenciavo = new BaseVO<Existenciasmedicamento>();
//			existenciavo.setBaseDTO(new Existenciasmedicamento());
//			existenciavo.getBaseDTO().setMedicamento(new Medicamento());
//			existenciavo.getBaseDTO().getMedicamento().setCodigomedicamento(id);
//			Collection<Existenciasmedicamento> listaExistencias = CaritasLocator.getInstancia().getServiceBean().findObjects(existenciavo);
//			System.out.println("-- existencias: "+ listaExistencias.size());
//			return listaExistencias.iterator().next();*/
//		}catch(Exception e){
//			System.out.println("-- exc obtenerExistenciaMedicamento");
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
//	//@org.junit.Test
//	public void guardarReceta(){
//		System.out.println("-- guardarReceta");
//		try{
//			Periodo periodo = obtenerPeriodoActivo();
//			Usuario usuario = obtenerUsuario();
//			
//			Receta receta = new Receta();
//			
//			receta.setPeriodo(periodo);
//			receta.setUsuarioByUsuarioregistro(usuario);
//			receta.setFecharegistro(new Timestamp(System.currentTimeMillis()));
//			
//			ComEleLocator.getInstancia().getServiceBean().transCreate(receta);
//			
//			
//		}catch(Exception e){
//			System.out.println("Error al listar receta");
//			e.printStackTrace();
//		}
//	}
//	
//	//@org.junit.Test
//	public void guardarRecetaConDetalle(){
//		System.out.println("-- guardarReceta con detalle");
//		try{
//			Periodo periodo = obtenerPeriodoActivo();
//			Usuario usuario = obtenerUsuario();
//			
//			Receta receta = new Receta();
//			receta.setPeriodo(periodo);
//			receta.setUsuarioByUsuarioregistro(usuario);
//			receta.setFecharegistro(new Timestamp(System.currentTimeMillis()));
//			
//			System.out.println("-- receta");
//			
//			//asigna los detalles
//			Collection<Detallereceta> listaDetalles = new ArrayList<Detallereceta>();
//			Detallereceta detalle = new Detallereceta();
//			detalle.setPeriodo(periodo);
//			
//			System.out.println("-- existencia");
//			detalle.setExistenciasmedicamento(obtenerExistenciaMedicamento(3L));
//			detalle.setCantidadentregada(2);
//			detalle.setUsuarioByUsuarioregistro(usuario);
//			detalle.setFecharegistro(new Timestamp(System.currentTimeMillis()));
//			System.out.println("-- detalle");
//			listaDetalles.add(detalle);
//			//receta.setDetallerecetas(listaDetalles);
//			System.out.println("-- antes");
//			//receta.getId().setCodigoreceta(1L);
//			//CaritasLocator.getInstancia().getServiceBean().transCreate(receta);
//			System.out.println("-- despues");
//			//System.out.println("-- codigo receta: "+receta.getCodigoreceta());
//			
//			//detalle.setReceta(receta);
//		//	listaDetalles.add(detalle);
//			//CaritasLocator.getInstancia().getServiceBean().transCreateAll(listaDetalles);
//			//CaritasLocator.getInstancia().getServiceBean().transCreate(detalle);
//			
//		}catch(Exception e){
//			System.out.println("Error al listar receta");
//			e.printStackTrace();
//		}
//	}
//	
//	
//	@org.junit.Test
//	public void guardarRecetaConDetalle2(){
//		System.out.println("-- guardarReceta con detalle");
//		try{
//			Periodo periodo = obtenerPeriodoActivo();
//			Usuario usuario = obtenerUsuario();
//			
//			Receta receta = new Receta();
//			receta.setPeriodo(periodo);
//			receta.setUsuarioByUsuarioregistro(usuario);
//			receta.setFecharegistro(new Timestamp(System.currentTimeMillis()));
//			
//			System.out.println("-- receta");
//			
//			BaseVO<Receta> recetaVO = new BaseVO<Receta>();
//			recetaVO.setBaseDTO(receta);
//			
//			
//			BaseVO<Detallereceta> detalleVO = new BaseVO<Detallereceta>(); 
//			
//			Detallereceta detalle = new Detallereceta();
//			detalle.setPeriodo(periodo);
//			
//			System.out.println("-- existencia");
//			detalle.setExistenciasmedicamento(obtenerExistenciaMedicamento(3L));
//			detalle.setCantidadentregada(2);
//			detalle.setUsuarioByUsuarioregistro(usuario);
//			detalle.setFecharegistro(new Timestamp(System.currentTimeMillis()));
//			detalleVO.setBaseDTO(detalle);
//			System.out.println("-- receta 1");
//			ComEleLocator.getInstancia().getRecetaBeanService().transCrearRecetaConDetalles(recetaVO,detalleVO);
//		//	CaritasLocator.getInstancia().getServiceBean().transCreate(recetaVO);
//			System.out.println("-- receta 2");
//			long codigo = recetaVO.getBaseDTO().getCodigoreceta().longValue();
//			System.out.println("-- receta 3: "+recetaVO.getBaseDTO().getCodigoreceta());
//			System.out.println("-- receta 4: "+codigo);
//		}catch(Exception e){
//			System.out.println("Error al listar receta");
//			e.printStackTrace();
//		}
//	}
}
