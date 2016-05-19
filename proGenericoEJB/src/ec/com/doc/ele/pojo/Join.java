/**
 * 
 */
package ec.com.doc.ele.pojo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Join implements Serializable {
	public static final int INNER_JOIN = 1;
	public static final int LEFT_JOIN = 2;
	public static final int FULL_JOIN = 3;
	
	private String property;
	private String alias;
	private int joinType;
	
	private Join(){}
	
	private static Join createJoin (String property, String alias, int joinType){
		Join join = new Join();
		join.setAlias(alias);
		join.setJoinType(joinType);
		join.setProperty(property);
		
		return join;
	}
	
	public static Join innerJoin(String property, String alias){
		return createJoin(property, alias, INNER_JOIN);
	}
	
	public static Join leftJoin(String property, String alias){
		return createJoin(property, alias, LEFT_JOIN);
	}
	
	public static Join fullJoin(String property, String alias){
		return createJoin(property, alias, FULL_JOIN);
	}

	/**
	 * @return el alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias el alias a establecer
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	

	/**
	 * @return el joinType
	 */
	public int getJoinType() {
		return joinType;
	}

	/**
	 * @param joinType el joinType a establecer
	 */
	public void setJoinType(int joinType) {
		this.joinType = joinType;
	}

	/**
	 * @return el property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property el property a establecer
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	
	
	
}
