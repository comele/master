/**
 * 
 */
package ec.com.doc.ele.constantes;

import ec.com.doc.ele.recursos.GenericoMensajes;


public class SearchConstants {

	public final String INITIAL_STRING_VALUE = GenericoMensajes.getString("genericoDato.codigoInicializaAtributosID");
	public final Integer INITIAL_INTEGER_VALUE = Integer.valueOf(INITIAL_STRING_VALUE);
	public final Long INITIAL_LONG_VALUE = Long.valueOf(INITIAL_STRING_VALUE);
	public final String SEPARATION_EXPRESSION = GenericoMensajes.getString("genericoDato.consultas.expresionSeparacion");
	public final String ASCENDING_ORDER = GenericoMensajes.getString("genericoDato.consultas.orden.ascendente");
	public final String DESCENDING_ORDER = GenericoMensajes.getString("genericoDato.consultas.orden.descendente");
	
	private static SearchConstants instancia;
	
	private SearchConstants(){};
	
	/**
	 * 
	 * @return
	 */
	public synchronized static SearchConstants obtenerInstancia(){
		if (instancia == null){
			instancia = new SearchConstants();
		}
		return instancia;
	}
	
}
