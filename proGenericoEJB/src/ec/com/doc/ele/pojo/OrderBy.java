/**
 * 
 */
package ec.com.doc.ele.pojo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class OrderBy implements Serializable {
	public static final String ORDER_ASC = "ASC";
	public static final String ORDER_DESC = "DESC";
	
	private String alias;
	private String property;
	private String orderType;
	private String propertyAlias;
	private String secuenceOrderClause;
	private String[] orderClause;
	
	private OrderBy(){}
	
	/**
	 * 
	 * @param property
	 * @param orderType
	 * @return
	 */
	private static OrderBy createOrderBy(String property, String alias, String orderType){
		OrderBy orderBy = new OrderBy();
		
		orderBy.setOrderType(orderType);
		orderBy.setProperty(property);
		orderBy.setAlias(alias);
		orderBy.setPropertyAlias(alias == null ? property : alias + "." + property);
		orderBy.setOrderClause(new String[]{property});
		
		return orderBy;
	}
	
	/**
	 * 
	 * @param orderType
	 * @param secuenceOrderClause
	 * @param orderClause
	 * @return
	 */
	private static OrderBy createOrderBy(String orderType, String secuenceOrderClause, String... orderClause){
		OrderBy orderBy = new OrderBy();
		
		orderBy.setOrderClause(orderClause);
		orderBy.setSecuenceOrderClause(secuenceOrderClause);
		orderBy.setOrderType(orderType);
		
		return orderBy;
	}
	
	/**
	 * 
	 * @param property
	 * @param alias
	 * @return
	 */
	public static OrderBy orderAsc(String property, String alias){
		return createOrderBy(property, alias, ORDER_ASC);
	}
	
	/**
	 * 
	 * @param property
	 * @param alias
	 * @return
	 */
	public static OrderBy orderDesc(String property, String alias){
		return createOrderBy(property, alias, ORDER_DESC);
	}
	
	/**
	 * 
	 * @param properties
	 * @return
	 */
	public static OrderBy orderAsc(String... properties){
		return createOrderBy(ORDER_ASC, getOrderByClause(ORDER_ASC, properties), properties);
	}
	
	/**
	 * 
	 * @param properties
	 * @return
	 */
	public static OrderBy orderDesc(String... properties){
		return createOrderBy(ORDER_DESC, getOrderByClause(ORDER_DESC, properties), properties);
	}
	
	
	public static boolean isOrderAsc(OrderBy orderBy){
		return orderBy.getOrderType().equals(ORDER_ASC);
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param orderType
	 * @param properties
	 * @return
	 */
	protected static String getOrderByClause(String orderType, String... properties){
		StringBuilder orderByClause = null;
		int nProperties = 0;
		
		if (properties == null){
			return "";
		}
		orderByClause = new StringBuilder();
		nProperties = properties.length;
		
		for (int i = 0; i < nProperties; i++){
			orderByClause.append(properties[i]).append(" ").append(orderType);
			if (i < nProperties - 1){
				orderByClause.append(",");
			}
		}
		
		return orderByClause.toString();
	}
	
	/**
	 * 
	 * @param orderType
	 * @param properties
	 * @return
	 */
	public static String getOrderByClause(List<OrderBy> properties, String alias){
		StringBuilder orderByClause = null;
		int nProperties = 0;
		
		if (properties == null){
			return "";
		}
		orderByClause = new StringBuilder();
		nProperties = properties.size();
		
		for (int i = 0; i < nProperties; i++){
			OrderBy orderBy = properties.get(i);
			if (alias != null){
				orderByClause.append(alias).append(".").append(orderBy.getProperty());
				
			} else {
				orderByClause.append(orderBy.getPropertyAlias());
			}
			orderByClause.append(" ").append(orderBy.getOrderType());
			if (i < nProperties - 1){
				orderByClause.append(",");
			}
		}
		
		return orderByClause.toString();
	}
	
	
	
	
	/**
	 * @return el orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType el orderType a establecer
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	 * @return el propertyAlias
	 */
	public String getPropertyAlias() {
		return propertyAlias;
	}

	/**
	 * @param propertyAlias el propertyAlias a establecer
	 */
	public void setPropertyAlias(String propertyAlias) {
		this.propertyAlias = propertyAlias;
	}

	/**
	 * @return el orderClause
	 */
	public String[] getOrderClause() {
		return orderClause;
	}

	/**
	 * @param orderClause el orderClause a establecer
	 */
	public void setOrderClause(String[] orderClause) {
		this.orderClause = orderClause;
	}

	/**
	 * @return el secuenceOrderClause
	 */
	public String getSecuenceOrderClause() {
		return secuenceOrderClause;
	}

	/**
	 * @param secuenceOrderClause el secuenceOrderClause a establecer
	 */
	public void setSecuenceOrderClause(String secuenceOrderClause) {
		this.secuenceOrderClause = secuenceOrderClause;
	}

	
	
	
	
}
