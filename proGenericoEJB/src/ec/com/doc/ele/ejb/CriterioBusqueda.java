/*
 * Creado el 30/09/2008
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de código - Plantillas de código
 */
package ec.com.doc.ele.ejb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import ec.com.doc.ele.ejb.transformador.TransformadorDTO;


/**
 * Estructura que ayuda a definir los distintos tipos de criterios de b&uacute;squeda 
 */
@SuppressWarnings("serial")
public class CriterioBusqueda implements Serializable{
	/**
	 * Tabla hash que almacena los diferentes par&aacute;metros que son tomados
	 * como criterios de b&uacute;squeda de una entidad persistible
	 */
	private Map<String,ParametroCriterioBusqueda<?>> criteriaParameterMap;	
	
	private Set<String> proyeccionesDistinct;
	
	private TransformadorDTO resultTransformer;
	
	/**
	 * Inicializa la tabla hash de almacenamiento de los par&aacute;metros
	 * de los criterios de b&uacute;squeda
	 */
	public CriterioBusqueda(){
		criteriaParameterMap = new HashMap<String, ParametroCriterioBusqueda<?>>();		
	}
	
	/**
	 * Agrega un par&aacute;metro de criterio de b&uacute;squeda
	 * @param criteriaParameter
	 */
	public void addCriteriaSearchParameter(ParametroCriterioBusqueda<?> criteriaParameter){		
		criteriaParameterMap.put(criteriaParameter.getParameterPattern(), criteriaParameter);
		
	}
	
	/**
	 * Agrega un conjunto de propiedades para aplicar en una consulta aplicando
	 * la cl&aacute;usula <code>DISTINCT</code>
	 * @param parameterPattern
	 */
	public void addDistinctSearchParameter(String... parameterPatterns){
		resultTransformer = null;
		if(proyeccionesDistinct == null){
			proyeccionesDistinct = new LinkedHashSet<String>();
		}
		for(String parameterPattern : parameterPatterns){
			proyeccionesDistinct.add(parameterPattern);
		}
		
	}
	
	public void addDistinctSearchParameter(Class<?> resultClass,String[] distinctPropertyNames,String[] propertyNamesResult){
		proyeccionesDistinct = null;
		resultTransformer = new TransformadorDTO(resultClass, distinctPropertyNames, propertyNamesResult);
	}
	
	public void addDistinctSearchParameter(Class<?> resultClass,String[] distinctPropertyNames){
		proyeccionesDistinct = null;
		resultTransformer = new TransformadorDTO(resultClass, distinctPropertyNames, distinctPropertyNames);
	}
	
	/**
	 * Obtiene un par&aacute;metor de criterio de b&uacute;squeda
	 * @param parameterPattern patr&oacute;n del nombre del par&aacute;metro que corresponde a un nombre de propiedad
	 * de la entidad de b&uacute;squeda. Este patr&oacute;n debe contener nombres de propiedades separados por puntos.
	 * Ej: entidad.hijo.propiedad1, entidad.propiedad1, etc.
	 * @return
	 */
	public ParametroCriterioBusqueda<?> getCriteriaSearchParameter(String parameterPattern){
		return criteriaParameterMap.get(parameterPattern);
	}
	
	/**
	 * @return Obtiene el valor de criteriaParameterMap establecido.
	 */
	public Map<String, ParametroCriterioBusqueda<?>> getCriteriaParameterMap() {
		return criteriaParameterMap;
	}

	public Set<String> getProyeccionesDistinct() {
		return proyeccionesDistinct;
	}

	/**
	 * @return the resultTransformer
	 */
	public TransformadorDTO getResultTransformer() {
		return resultTransformer;
	}
	
	
	
	

}
