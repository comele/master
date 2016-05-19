/**
 * 
 */
package ec.com.doc.ele.locator;

import ec.com.doc.ele.dao.TusuarioHomeI;
import ec.com.doc.ele.excepcion.ComEleExcepcion;
import ec.com.doc.ele.servicio.EjbService;

public class ComEleLocator extends ServiceLocator {
	
	private static final ComEleLocator INSTANCIA = new ComEleLocator();
	
	/**
	 * 
	 */
	private ComEleLocator(){
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static final ComEleLocator getInstancia(){
		return INSTANCIA;
	}

	@Override
	protected String getApplicationName() {
		return ComEleEjb.APP_NAME;
	}

	@Override
	protected String getProviderURL() {
		return "127.0.0.1:1099";
	}

	@Override
	protected String getServiceBeanName() {
		return ComEleEjb.SCP_SERVICE;
	}
	
	/**
	 * 
	 * @return
	 * @throws ComEleExcepcion
	 */
	public EjbService getSCPDataService() throws ComEleExcepcion{
		try {
			return (super.getSimpleBean(ComEleEjb.APP_NAME, ComEleEjb.SCP_SERVICE, EjbService.class));
		}catch (ComEleExcepcion e) {
			throw new ComEleExcepcion(e);
		}
	}
	
	public TusuarioHomeI getUsuariosBeanService()throws ComEleExcepcion{
		try {
			return getBean(ComEleEjb.SC_USUARIO_SERVICE,TusuarioHomeI.class);
		}catch (ComEleExcepcion e) {
			throw new ComEleExcepcion(e);
		}
	}
	
//	public PersonaHomeI getPersonasBeanService()throws ComEleExcepcion{
//		try {
//			return getBean(ComEleEjb.SC_PERSONA_SERVICE,PersonaHomeI.class);
//		}catch (ComEleExcepcion e) {
//			throw new ComEleExcepcion(e);
//		}
//	}
	
//	public ExistenciasmedicamentoHomeI getExistenciaMedicamentoService()throws ComEleExcepcion{
//		try {
//			return getBean(ComEleEjb.SC_EXISTENCIAMEDICAMENTO_SERVICE,ExistenciasmedicamentoHomeI.class);
//		}catch (ComEleExcepcion e) {
//			throw new ComEleExcepcion(e);
//		}
//	}
//	
//	public RecetaHomeI getRecetaBeanService()throws ComEleExcepcion{
//		try {
//			return getBean(ComEleEjb.SC_RECETA_SERVICE,RecetaHomeI.class);
//		}catch (ComEleExcepcion e) {
//			throw new ComEleExcepcion(e);
//		}
//	}
	
}