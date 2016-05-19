/**
 * 
 */
package ec.com.doc.ele.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RelationField {
	public enum JoinType {LEFT, INNER, FULL};
	
	JoinType joinType() default JoinType.LEFT;
}
