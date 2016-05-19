package ec.com.doc.ele.ejb.transformador;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.transform.ResultTransformer;

import ec.com.doc.ele.util.ClasesUtil;


@SuppressWarnings("serial")
public class TransformadorDTO implements ResultTransformer{

	private final Class<?> resultClass;
	private String[] propertyNames;
	private String[] propertyNamesDtoResult;
	
	

	/**
	 * Constructor que recibe el nombre de la clase que ser&aacute; instanciada
	 * para cada conjunto de resultados obtenidos de la consulta.
	 * @param resultClass
	 */
	public TransformadorDTO(Class<?> resultClass) {
		if ( resultClass == null ) {
			throw new IllegalArgumentException( "La clase resultado no puede ser nula" );
		}
		this.resultClass = resultClass;
	}
	
	
	/**
	 * Constructor que recibe el nombre de la clase que ser&aacute; instanciada
	 * para cada conjunto de resultados obtenidos de la consulta.
	 * @param resultClass
	 * @param propertyNames los nombres de las propiedades de los objetos que conforman la tupla de la consulta
	 * @param propertyNamesDtoResult los nombres de las propiedades del DTO a retornar correspondientes a los nombres de las propiedades 
	 * de la tupla de la consulta
	 */
	public TransformadorDTO(Class<?> resultClass,String[] propertyNames, String[] propertyNamesDtoResult) {
		if ( resultClass == null ) {
			throw new IllegalArgumentException( "La clase resultado no puede ser nula" );
		}
		if ( propertyNames == null || propertyNames.length == 0) {
			throw new IllegalArgumentException( "El argumento propertyNames tiene que tener al menos un elemento" );
		}
		if ( propertyNamesDtoResult == null || propertyNamesDtoResult.length == 0) {
			throw new IllegalArgumentException( "El argumento propertyNamesDtoResult tiene que tener al menos un elemento" );
		}
		this.resultClass = resultClass;
		this.propertyNames = propertyNames;
		this.propertyNamesDtoResult = propertyNamesDtoResult;
		
	}
	
	/**
	 * Establece los valores de cada elemento de <code>tuple</code> en la propiedad indicada
	 * correspondientemente en <code>aliases</code>.
	 * Cada elemento del aliases debe contener un path que indique el nombre de una propiedad
	 * accesible con reflexi&oacute;n.
	 * @see org.hibernate.transform.ResultTransformer#transformTuple(java.lang.Object[], java.lang.String[])
	 */	
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;

		try {
			result = resultClass.newInstance();		
			for ( int i = 0; i < aliases.length; i++ ) {
				if ( aliases[i] != null && tuple[i] != null) {							
					ClasesUtil.invocarMetodoSet(result, aliases[i], tuple[i]);
				}				
			}
		}
		catch ( InstantiationException e ) {
			throw new HibernateException( "No se puede instanciar la clase resultante: " + resultClass.getName() );
		}
		catch ( IllegalAccessException e ) {
			throw new HibernateException( "No se puede instanciar la clase resultante: " + resultClass.getName() );
		}

		return result;
	}

	/**
	 * Retorna una colecci&oacute;n de elementos transformados
	 * @see org.hibernate.transform.ResultTransformer#transformList(java.util.List)
	 */	
	@SuppressWarnings("rawtypes")
	public List transformList(List collection) {
		return collection;
	}


	/**
	 * @return the resultClass
	 */
	public Class<?> getResultClass() {
		return resultClass;
	}

	/**
	 * @return the propertyNames
	 */
	public String[] getPropertyNames() {
		return propertyNames;
	}

	/**
	 * @param propertyNames the propertyNames to set
	 */
	public void setPropertyNames(String[] propertyNames) {
		this.propertyNames = propertyNames;
	}

	/**
	 * @return the propertyNamesDtoResult
	 */
	public String[] getPropertyNamesDtoResult() {
		return propertyNamesDtoResult;
	}

	/**
	 * @param propertyNamesDtoResult the propertyNamesDtoResult to set
	 */
	public void setPropertyNamesDtoResult(String[] propertyNamesDtoResult) {
		this.propertyNamesDtoResult = propertyNamesDtoResult;
	}
}
