/**
 * 
 */
package ec.com.doc.ele.pojo;

@SuppressWarnings("serial")
public class NamedParameter extends AbstractParameter {

	private String prefix;
	
	public NamedParameter(String name, String token, String prefix){
		this.setName(new StringBuilder(prefix).append(name).toString());
		this.setToken(token);
		this.prefix = prefix;
	}

	/**
	 * @return el prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix el prefix a establecer
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
