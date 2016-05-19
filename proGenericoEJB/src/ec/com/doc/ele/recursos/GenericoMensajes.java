/*
 * FrameworkMessages.java
 * Creado el 29/06/2005
 */
package ec.com.doc.ele.recursos;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/** 
 * <p>Se encarga de buscar una llave del archivo de propiedades determinado <code>framework</code></p>
 */
public class GenericoMensajes {
	
	private static final String BUNDLE_NAME = "caritas.com.ec.recursos.genericoDao"; 
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	/**
	 * <p>Devulve el valor de una llave enviada como par&aacute;metro.</p>
	 * 
	 * @param key			Llave de archivo properties.
	 * @return 				Devuelve el valor de la llave.
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (Exception e) {
			return '!' + key + '!';
		}
	}	
	
	/**
	 * Obtiene un mensaje del archivo de internacionalizaci&oacute;n con ciertos par&aacute;metros de relleno
	 * @param key Clave del archivo de internacionalizaci&oacute;n
	 * @param parameters Par&aacute;metros de relleno
	 * @return Mensaje obtenido desde el archivo de internacionalizaci&oacute;n con los par&aacute;metros establecidos
	 */
	public static String getString(String key, String... parameters) {
		try {
			MessageFormat formatter = new MessageFormat(RESOURCE_BUNDLE.getString(key),RESOURCE_BUNDLE.getLocale());
			return formatter.format(parameters);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}	
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws MissingResourceException
	 */
	public static Integer getInteger(String key) throws MissingResourceException{
		return Integer.parseInt(GenericoMensajes.getString(key));
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws MissingResourceException
	 */
	public static Long getLong(String key) throws MissingResourceException{
		return Long.parseLong(GenericoMensajes.getString(key));
	}

}
