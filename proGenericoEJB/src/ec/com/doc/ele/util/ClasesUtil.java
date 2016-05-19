/*
 * ClasesUtil.java
 * Creado el 15/08/2006
 * Supermaxi
 *
 */
package ec.com.doc.ele.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import ec.com.doc.ele.util.log.LogGenerico;



/**
 * La clase <code>ClasesUtil</code> contiene métodos para manejo de clases por
 * medio de reflexión.
 * @author  walvarez
 */
public class ClasesUtil {

	/**
	 * Invoca por medio de la reflexión un método <code>getNombreAtributo()
	 */
	public static Object invocarMetodoGet(Object obj, String nombreAtributo) {
		StringBuilder nombreMetodo = null;
		Object resultado = null;
		int i = 0;
		final String GET = "get";
		final char PUNTO = '.';

		try {
			if (obj != null && (nombreAtributo != null && nombreAtributo.length() > 0)) {
				do { 
					nombreMetodo = new StringBuilder();
					i = nombreAtributo.indexOf(PUNTO);
					nombreMetodo.append(GET).append(nombreAtributo.substring(0, 1).toUpperCase());
					if (i > -1) {
						nombreMetodo.append(nombreAtributo.substring(1, i));
						nombreAtributo = nombreAtributo.substring(i+1);
					} else {
						nombreMetodo.append(nombreAtributo.substring(1));
					}
					resultado = obj.getClass().getMethod(nombreMetodo.toString()).invoke(obj);
					obj = resultado;
				}while (i > -1 && obj != null);
			}
		} catch (IllegalArgumentException e) {
			LogGenerico.getLogger().error("Error",e);
		} catch (SecurityException e) {
			LogGenerico.getLogger().error("Error",e);
		} catch (IllegalAccessException e) {
			LogGenerico.getLogger().error("Error",e);
		} catch (InvocationTargetException e) {
			LogGenerico.getLogger().error("Error",e);
		} catch (NoSuchMethodException e) {
			LogGenerico.getLogger().info("Error, error no existe metodo: " + nombreMetodo);
		}

		return resultado;
	}


	/**	Invoca por medio de la reflexión un método <code>nombreAtributo()
	 **/

	public static Object invocarMetodo(Object obj, String nombreAtributo){
		Object resultado = null;
		try{
			if(obj != null && (nombreAtributo != null && nombreAtributo.length() > 0)) {
				resultado = obj.getClass().getMethod(nombreAtributo).invoke(obj);
			}
		}
		catch(Exception e){
			LogGenerico.getLogger().error("Error",e);
		}
		return resultado;
	}
	//--------------------------------------------------------------------------
	/**
	 * Invoca por medio de la reflecci&oacute;n un m&eacute;todo <code>setNombreAtributo(parametro)
	 */
	public static void invocarMetodoSet(Object bean, String nombreAtributo, Object valor) {		
		try {
			
			if (nombreAtributo.indexOf(".") >= 0 ) {				
				StringTokenizer tokenizer = new StringTokenizer(nombreAtributo,".");
				Object propertyBean = bean;				
				while (tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					if (tokenizer.hasMoreTokens()) {
						Object propertyValue = PropertyUtils.getProperty(propertyBean, token);
						if (propertyValue == null) {
							Class propertyClass = PropertyUtils.getPropertyType(propertyBean, token);
							propertyValue = propertyClass.newInstance();									
							PropertyUtils.setProperty(propertyBean, token,propertyValue);
							propertyBean = propertyValue;
						}						
					}					
				}
			}			
			PropertyUtils.setProperty(bean, nombreAtributo, valor);
		}
		catch (IllegalAccessException e) {
			LogGenerico.getLogger().error("Error",e);
		}
		catch (InvocationTargetException e) {
			LogGenerico.getLogger().error("Error",e);
		}
		catch (NoSuchMethodException e) {
			LogGenerico.getLogger().error("Error",e);
		} catch (InstantiationException e) {
			LogGenerico.getLogger().error("Error",e);
		}
	}

	//--------------------------------------------------------------------------

	/**
	 * Invoca al constructor de una clase que representa un tipo de dato para 
	 * transformar un objeto <code>String</code> que representa un valor en el 
	 * objeto que corresponde a dicho valor. 
	 */
	public static Object[] invocarConstructorTipoDato(
			String nombreClaseTipoDato, Object[] valoresParametros) {

		Object[] objetos = new Object[valoresParametros.length];
		Object[] parametrosIniciales = new Object[1];

		for(int i = 0; i < valoresParametros.length; i++) {
			Object objeto = null;

			if (valoresParametros[i] != null 
					&& !valoresParametros[i].equals("")) {

				if (nombreClaseTipoDato.equals("java.util.Date")) {
					SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
					ParsePosition par = new ParsePosition(0);
					objeto = sdfInput.parse((String) valoresParametros[i], par);
				}
				else if (valoresParametros[i] instanceof Collection) {
					objeto = valoresParametros[i];
				}
				else {

					/*
					 * Invocar al constructor que recibe un objeto String como 
					 * tipo de dato del parámetro
					 */
					Class[] tiposParametros = {String.class};
					parametrosIniciales[0] = valoresParametros[i];
					Class clase = null;
					Constructor constructor = null;
					try {
						clase = Class.forName(nombreClaseTipoDato);
						constructor = clase.getConstructor(tiposParametros);
						objeto = constructor.newInstance(parametrosIniciales);
					} catch (ClassNotFoundException e) {

					}catch (SecurityException e1) {

					} catch (NoSuchMethodException e1) {

					} catch (IllegalArgumentException e2) {

					} catch (InstantiationException e2) {

					} catch (IllegalAccessException e2) {

					} catch (InvocationTargetException e2) {

					}
				}
				objetos[i] = objeto;
			}
		}
		return objetos;
	}


	//--------------------------------------------------------------------------

	/**
	 * Retorna un objeto tipo <code>Class</code> asociado al nombre de la clase
	 * enviado como parámetro.
	 */
	public static Class invocarClase(String nombreClase) {

		try {
			return Class.forName(nombreClase);

		} catch (ClassNotFoundException e) {
			//la clase no existe y el resultado es null
		}
		return null;
	}
	//--------------------------------------------------------------------------
	/**
	 * Permite identificar si el tipo de dato que se le pasa como parametro es o no un Dato
	 */
	public static Boolean esTipoDato(Class clase) {
		LogGenerico.getLogger().info("Clase: " + clase.getName());
		if (clase == String.class 
				|| clase == Long.class
				|| clase == Integer.class
				|| clase == Date.class
				|| clase == Double.class
				|| clase == Boolean.class
				|| clase == Collection.class
				|| clase == Short.class
				|| clase == Character.class) {

			return new Boolean(true);   
		}    
		return new Boolean(false);
	}


	/**
	 * Retorna 	los nombres de los m&eacute;todos 
	 */
	public static String[] returnNombresMetodosPublicos(String nombreClase) {
		String[] nombresMetodos = null;

		try {
			Class clase = Class.forName(nombreClase);
			Method[] metodos = clase.getMethods();
			nombresMetodos = new String[metodos.length];

			for (int i = 0; i < nombresMetodos.length; i++) {
				nombresMetodos[i] = metodos[i].getName();
			}
		} catch (ClassNotFoundException e) {
			LogGenerico.getLogger().error("Error",e);
		}
		return nombresMetodos;
	}
	/**
	 * Permite retorna informaci&oacute;n de m&eacute;todos .
	 */

	public static Collection returnInformacionMetodo(String nombreMetodo) {
		Collection informacionMetodo = new ArrayList();
		return informacionMetodo;
	}


	/**
	 * Permite retorna informaci&oacute;n de los atributos de la Clase
	 */

	@SuppressWarnings("unchecked")
	public static Collection returnInformacionAtributosClase (
			String nombreClase) {

		Collection atributosClase = new ArrayList();


		try {
			Class clase = Class.forName(nombreClase);
			Field[] atributos = clase.getDeclaredFields();
			for (int i = 0; i < atributos.length; i++) {
				Object[] informacionAtributosClase = new Object[2];
				informacionAtributosClase[0] = atributos[i].getName();
				informacionAtributosClase[1] = atributos[i].getType();
				atributosClase.add(informacionAtributosClase);
			}    
		} catch (ClassNotFoundException e) {
			LogGenerico.getLogger().error("Error",e);

		} catch (Exception e) {
			LogGenerico.getLogger().error("Error",e);
		}
		return atributosClase;
	}

	/**
	 * Retorna un valor entero que resulta de la comparación entre dos objetos 
	 * instancias de las clases String, Double, Long, Integer, Date, Timestamp o 
	 * Character. El resultado depende del orden natural de dichas clases:
	 */
	public static int valorComparacion(Object o1, Object o2) {
		int valor = 0;

		if (o1 == null && o2 != null) {
			valor = 1;
		} else if (o1 != null && o2 == null) {
			valor = -1;
		} else if (o1 != null && o2 != null){
			if (o1 instanceof String && o2 instanceof String) {
				valor = ((String) o1).compareTo((String) o2); 
			} else if (o1 instanceof Double && o2 instanceof Double) {
				valor = ((Double) o1).compareTo((Double) o2);
			} else if (o1 instanceof Long && o2 instanceof Long) {
				valor = ((Long) o1).compareTo((Long) o2);
			} else if (o1 instanceof Integer && o2 instanceof Integer) {
				valor = ((Integer) o1).compareTo((Integer) o2);
			} else if (o1 instanceof Timestamp && o2 instanceof Timestamp){
				valor = ((Timestamp) o1).compareTo((Timestamp) o2);
			} else if (o1 instanceof Time && o2 instanceof Time){
				valor = ((Time) o1).compareTo((Time) o2);
			} else if (o1 instanceof java.sql.Date && o2 instanceof java.sql.Date){
				valor = ((java.sql.Date) o1).compareTo((java.sql.Date) o2);
			} else if (o1 instanceof Date && o2 instanceof Date) {
				valor = ((Date) o1).compareTo((Date) o2);
			} else if (o1 instanceof Character && o2 instanceof Character) {
				valor = ((Character) o1).compareTo((Character) o2);
			} else if (o1 instanceof Boolean && o2 instanceof Boolean) {
				valor = ((Boolean) o1).compareTo((Boolean) o2);
			}
		}

		return valor;
	}
	
	
	/**
	 * 
	 * @param objectMethodClass
	 * @param methodName
	 * @param methodParameters
	 * @return
	 */
	public static Object invokeMethod(Object objectMethodClass, MethodInformation methodInformation){
		Method method = null;
		Object result = null;

		try {
			method = objectMethodClass.getClass().getMethod(methodInformation.getMethodName(), methodInformation.getMethodParametersClasses());
			result = method.invoke(objectMethodClass, methodInformation.getMethodParametersValues());
		} catch (SecurityException e) {
			LogGenerico.getLogger().error("Error",e);
		} catch (NoSuchMethodException e) {
			LogGenerico.getLogger().error("Error",e);
		} catch (IllegalArgumentException e) {
			LogGenerico.getLogger().error("Error",e);
		} catch (IllegalAccessException e) {
			LogGenerico.getLogger().error("Error",e);
		} catch (InvocationTargetException e) {
			LogGenerico.getLogger().error("Error",e);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param methodInformation
	 * @return
	 */
    public static Object getBeanFactory(MethodInformation methodInformation) {
        Object bean = null;
        Class claseBean = null;
        
        try {
            claseBean = Class.forName(methodInformation.getFactoryClassName());
            bean = claseBean.getMethod(methodInformation.getGetBeanMethodName(), new Class[] {String.class}).invoke(claseBean.newInstance(), new Object[] {methodInformation.getBeanName()});
        } catch (MissingResourceException e) {
            LogGenerico.getLogger().error("Error",e);
        } catch (ClassNotFoundException e) {
            LogGenerico.getLogger().error("Error",e);
        } catch (IllegalArgumentException e) {
            LogGenerico.getLogger().error("Error",e);
        } catch (SecurityException e) {
            LogGenerico.getLogger().error("Error",e);
        } catch (IllegalAccessException e) {
            LogGenerico.getLogger().error("Error",e);
        } catch (InvocationTargetException e) {
            LogGenerico.getLogger().error("Error",e);
        } catch (NoSuchMethodException e) {
            LogGenerico.getLogger().error("Error",e);
        } catch (InstantiationException e) {
            LogGenerico.getLogger().error("Error",e);
        }
        
        return bean;
    }

    /**
     * 
     * @param <T>
     * @param beanClass
     * @param properties
     * @return
     */
    public static <T> Object createInstance(Class<T> beanClass, Map<String, Object> properties){
    	Object bean = null;
    	
    	if (beanClass != null && properties != null && !properties.isEmpty()){
    		try {
    			bean = beanClass.newInstance();
				
				for (Map.Entry<String, Object> property : properties.entrySet()){
					invocarMetodoSet(bean, property.getKey(), property.getValue());
				}
				
			} catch (IllegalAccessException e) {
				LogGenerico.getLogger().error("Error",e);
			} catch (InstantiationException e) {
				LogGenerico.getLogger().error("Error",e);
			}
    	}
    	
    	return bean;
    }
    
    /**
     * 
     * @param objeto
     * @param anotacion
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Collection<Field> getFieldsByAnnotation(Object objeto, Class anotacion){
		Field[] fieldArray = null;
		Collection<Field> fieldList = null;
		Collection<Field> annotatedFields = null;
		
		fieldArray = objeto.getClass().getDeclaredFields();
		if (fieldArray != null && fieldArray.length > 0){
			fieldList = Arrays.asList(fieldArray);
			annotatedFields = CollectionUtils.select(fieldList, PredicateUtils.invokerPredicate("isAnnotationPresent", new Class[]{Class.class}, new Object[]{anotacion}));
			fieldList = null;
			fieldArray = null;
		}
		
		return annotatedFields;
	}
    
    
    /**
     * Construye un codigo hash de un objeto segun los tributos seleccionados.
     */
    public static Integer getHashCode(Object object, String... fields){
    	Object[] propertiesValues;
    	int fieldsLength;
    	
    	if (object != null && !ArrayUtils.isEmpty(fields)){
    		fieldsLength = fields.length;
    		propertiesValues = new Object[fieldsLength];
    		
    		for (int i = 0; i < fieldsLength; i++){
    			propertiesValues[i] = ClasesUtil.invocarMetodoGet(object, fields[i]);
    		}
    		
    		return getHashCode(object, propertiesValues);
    	}
    	
    	return null;
    }
    
    /**
     * 
     * @param object
     * @param propertiesValues
     * @return
     */
    public static Integer getHashCode(Object object, Object... propertiesValues){
    	return new HashCodeBuilder().append(propertiesValues).toHashCode();
    	
    }
}
