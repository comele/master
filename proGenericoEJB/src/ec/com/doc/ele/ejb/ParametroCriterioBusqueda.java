/*
 * Creado el 16/09/2008
 *
 */
package ec.com.doc.ele.ejb;

import java.io.Serializable;

import ec.com.doc.ele.enumeracion.ComparatorTypeEnum;


@SuppressWarnings("serial")
public class ParametroCriterioBusqueda<T> implements Serializable{

    private String parameterPattern;
    private T[] parameterValues;
    private ComparatorTypeEnum comparator;
    private boolean denyComparation;  

    /**
     * Constructor para CriteriaSearchParameter y utiliza el comparador <code>EQUAL_COMPARATOR</code>.
     * @param parameterPattern El patr&oacute;n separado con puntos que representa el nombre del par&aacute;metro.
     */
    public ParametroCriterioBusqueda(String parameterPattern)
    {
        this(parameterPattern, ComparatorTypeEnum.EQUAL_COMPARATOR);
    }    

    /**
     * Constructor para CriteriaSearchParameter.
     *
     * @param parameterPattern Nombre del par&aacute;metro.
     * @param comparator Indica que operador ser&aacute; utilizado para la b&uacute;squeda (ej. like, =, <, ...).
     */
    public ParametroCriterioBusqueda(String parameterPattern,ComparatorTypeEnum comparator) {        
        this.parameterPattern = parameterPattern;        
        this.comparator = comparator; 
        denyComparation = Boolean.FALSE;
    }

    /**
     * Constructor para CriteriaSearchParameter.
     * @param parameterPattern Nombre del par&aacute;metro.
     * @param parameterValues Valores que ser&aacute;n tomados en la b&uacute;squeda de acuerdo al par&aacute;metro especificado
     * @param comparator Indica que operador ser&aacute; utilizado para la b&uacute;squeda (ej. like, =, <, ...).
     */
    public ParametroCriterioBusqueda(String parameterPattern, ComparatorTypeEnum comparator, T... parameterValues) {        
        this(parameterPattern,comparator,Boolean.FALSE,parameterValues);
    }
    
    /**
     * Constructor para CriteriaSearchParameter.
     * @param parameterPattern Nombre del par&aacute;metro.
     * @param denyComparation Denegar el operador de comparaci&oacute;n
     * @param parameterValues Valores que ser&aacute;n tomados en la b&uacute;squeda de acuerdo al par&aacute;metro especificado
     * @param comparator Indica que operador ser&aacute; utilizado para la b&uacute;squeda (ej. like, =, <, ...).
     */
    public ParametroCriterioBusqueda(String parameterPattern, ComparatorTypeEnum comparator,Boolean denyComparation, T... parameterValues) {        
        this.parameterPattern = parameterPattern;        
        this.comparator = comparator;  
        this.denyComparation = denyComparation;
        this.parameterValues = parameterValues;
    }
       
    /**
     * @return El nombre del par&aacute;metro (separado con  punto ej: person.address.street).
     */
    public String getParameterPattern()
    {
        return parameterPattern;
    }

    /**
     * Establece el nombre del par&aacute;metro.
     *
     * @param parameterPattern El patr&oacute;n tomado como nombre de par&aacute;metro (separado con punto ej: person.address.street).
     */
    public void setParameterPattern(String parameterPattern)
    {
        this.parameterPattern = parameterPattern;
    }

 	/**
	 * @return El comparador utilizado en el par&aacute;metro para la consulta.
	 */
	public ComparatorTypeEnum getComparator() {
		return comparator;
	}

	/**
	 * Establece el tipo de comparador a utilizar en la consulta de acuerdo al par&aacute:metro
	 * @param comparator El tipo de comparador a utilizar en la consulta de acuerdo al par&aacute;metro.
	 */
	public void setComparator(ComparatorTypeEnum comparator) {
		this.comparator = comparator;
	}
	
	/**
	 * @return the denyComparation
	 */
	public boolean isDenyComparation() {
		return denyComparation;
	}

	/**
	 * @param denyComparation the denyComparation to set
	 */
	public void setDenyComparation(boolean denyComparation) {
		this.denyComparation = denyComparation;
	}

	/**
	 * @return Obtiene el valor de parameterValues establecido.
	 */
	public Object getParameterValues() {
		if(parameterValues == null || parameterValues.length == 0){
			return null;
		}
		if(parameterValues.length == 1){
			return parameterValues[0];
		}
		return parameterValues;
	}
	
	/**
	 * Obtener el primer elemento de los par&aacute;metros
	 * @return
	 */
	public T getParameterValue(){
		return parameterValues == null || parameterValues.length == 0? null : parameterValues[0];
	}

	/**
	 * @param parameterValues El valor de parameterValues a establecer.
	 */
	public void setParameterValues(T[] parameterValues) {
		this.parameterValues = parameterValues;
	}

}
