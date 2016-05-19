package ec.com.doc.ele.ejb;

import java.util.Collection;

import ec.com.doc.ele.pojo.NamedParameterQuery;



public interface DefinirRestriccion {
	String[] getFieldsInRestriction();
	String getRestriction();
	public Collection<NamedParameterQuery> getParameters();
}
