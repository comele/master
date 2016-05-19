/**
 * 
 */
package ec.com.doc.ele.pojo;

import ec.com.doc.ele.enumeracion.BooleanClauseEnum;

public class OrClause {
	
	private Object[] values;
	private final BooleanClauseEnum CLAUSE = BooleanClauseEnum.OR;
	
	
	public OrClause(){
		//
	}
	
	public OrClause(Object[] values){
		this.values = values;
	}
	
	/**
	 * 
	 * @param value
	 */
	public OrClause(Object value){
		this.values = new Object[] {value};
	}

	/**
	 * @return el values
	 */
	public Object[] getValues() {
		return values;
	}

	/**
	 * @param values el values a establecer
	 */
	public void setValues(Object[] values) {
		this.values = values;
	}

	/**
	 * @return el cLAUSE
	 */
	public BooleanClauseEnum getCLAUSE() {
		return CLAUSE;
	}
}
