/*
 * Creado el 15/08/2006
 * 
 * Autor mbraganza
 *
 */
package ec.com.doc.ele.locator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ec.com.doc.ele.util.ClasesUtil;

public class Utilitarios {

	private static final Utilitarios INSTANCE = new Utilitarios();

	private Utilitarios(){}

	public static Utilitarios getInstance(){
		return INSTANCE;
	}

	/**
	 * Busca objetos dentro de una coleccion dado el nombre del atributo y el 
	 * valor correspondiente. Si el atributo es de tipo <code>String</code>  
	 * se distingue entre mayúsculas y minúsculas y se toma en cuenta toda la
	 * secuencia de caracteres para la búsqueda.
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @param baseCollection
	 * @return
	 * @throws Exception
	 */
	public static<T> Collection<T> simpleSearch(String fieldName, Object fieldValue, Collection<T>  baseCollection) throws Exception{       
		return simpleSearch(fieldName, fieldValue, baseCollection, false);
	}

	/**
	 * Busca objetos dentro de una colección dado el nombre del atributo y el
	 * valor correspondiente. Por medio del parámetro 
	 * <code>habilitarCoincidenciaSecuenciaCaracteres</code> para atributos de 
	 * tipo <code>String</code> es posible distinguir entre mayúsculas y
	 * minúsculas y si se toma en cuenta toda la secuencia de caracteres para la
	 * búsqueda
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @param baseCollection
	 * @param enableCharacterSequenceCoincidence
	 * @return
	 * @throws Exception
	 */
	public static<T> Collection<T> simpleSearch(String fieldName, Object fieldValue, Collection<T> baseCollection, boolean enableCharacterSequenceCoincidence) throws Exception{       
		HashMap<String, Object> object = new HashMap<String, Object>();

		object.put(fieldName, fieldValue);
		Collection<T> resultado = buscarObjetosEnColeccion(baseCollection, object, enableCharacterSequenceCoincidence);
		return resultado;
	}
	
	public static<T> Collection<T> buscarObjetosEnColeccion(Collection<T> elementos, Map<String, Object> propiedades, boolean habilitarCoincidenciaSecuenciaCaracteres) {
		Collection<T> resultado = null;
		Set<String> listaAtributos = null;

		if (elementos != null) {
			resultado = new ArrayList<T>();
			listaAtributos = propiedades.keySet();
			for (T objColeccion : elementos) {
				for (String nombreAtributo : listaAtributos) {
					Object valorAtributo = ClasesUtil.invocarMetodoGet(objColeccion, nombreAtributo);

					if (valorAtributo != null) {
						if ((valorAtributo instanceof String) && habilitarCoincidenciaSecuenciaCaracteres) {
							if (((String) valorAtributo).toUpperCase().contains(((String) propiedades.get(nombreAtributo)).toUpperCase())) {
								resultado.add(objColeccion);
								break;
							}
						} else if (propiedades.get(nombreAtributo).equals(valorAtributo)) {
							resultado.add(objColeccion);
							break;
						}
					}
				}
			}
		}
		return resultado;
	}
}