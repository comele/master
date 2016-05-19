/*
 * Creado el 2011-12-21
 */
package ec.com.doc.ele.excepcion;

import javax.ejb.ApplicationException;


/**
 * @author jbalcazar
 * 
 */
@SuppressWarnings("serial")
@ApplicationException(rollback=true)
public class ComEleExcepcion extends RuntimeException{

	/**
	 * Constructor por defecto. 
	 */
	public ComEleExcepcion(){
		super();
	}

	/**
	 * @param message Mensaje de error.
	 */
	public ComEleExcepcion(String message){
		super(message);
	}

	/**
	 * @param message Mensaje de error.
	 * @param cause Causa del error.
	 */
	public ComEleExcepcion(String message, Throwable cause){
		super(message, cause);
	}

	/**
	 * @param cause Causa del Error.
	 */
	public ComEleExcepcion(Throwable cause){
		super(cause);
	}
}

