package ec.com.doc.ele.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ec.com.doc.ele.enumeracion.ComparatorTypeEnum;


/**
 * Anotaci&oacute;n que permite realizar b&uacute;squedas sobre campos
 * no persistentes utilizando el operador OR
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DisjunctionField {
	String field();
	ComparatorTypeEnum comparatorType () default ComparatorTypeEnum.EQUAL_COMPARATOR; 
}
