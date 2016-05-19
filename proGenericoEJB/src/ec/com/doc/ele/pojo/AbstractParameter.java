/**
 * 
 */
package ec.com.doc.ele.pojo;

@SuppressWarnings("serial")
public class AbstractParameter implements Parameter{

	private String name;
	private String token;
	
	/**
	 * @return el name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name el name a establecer
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return el token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token el token a establecer
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
}
