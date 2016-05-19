/**
 * 
 */
package ec.com.doc.ele.enumeracion;

public enum BooleanClauseEnum {
	OR("or"),
	AND("and");
	
	private final String value;
	
	private BooleanClauseEnum(String value){
		this.value = value;
	}

	/**
	 * @return el value
	 */
	public String getValue() {
		return value;
	}
}
