package ec.com.doc.ele.ejb.restriction;

import java.io.Serializable;

import org.hibernate.criterion.Criterion;

/**
 * @author walvarez
 *
 */
public interface CriteriaRestriction extends Serializable{
	public Criterion getCriteriaRestriction();
	public void setAlias(String alias);
	public String getAlias();
}
