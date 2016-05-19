/**
 * 
 */
package ec.com.doc.ele.constantes;

public enum QueryParametersType {

	LIST(" , "),
	AND_LIST(" and ");
	
	private final String token;
	
	private QueryParametersType(String token){
		this.token = token;
	}

	/**
	 * @return el token
	 */
	public String getToken() {
		return token;
	}
	
	
}
