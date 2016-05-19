/*
 * Creado el 14/08/2008
 *
 */
package ec.com.doc.ele.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ec.com.doc.ele.enumeracion.ComparatorTypeEnum;


/**
 * Clase para el control de b&uacute;squeda
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface  ComparatorTypeField {	
	String field() default "";
	ComparatorTypeEnum comparatorType () default ComparatorTypeEnum.EQUAL_COMPARATOR;
	boolean enabled() default true;
}
