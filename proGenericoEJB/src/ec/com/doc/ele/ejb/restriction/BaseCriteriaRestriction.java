package ec.com.doc.ele.ejb.restriction;


/**
 * @author walvarez
 *
 */
@SuppressWarnings("serial")
public abstract class BaseCriteriaRestriction implements CriteriaRestriction{	
	private String alias;
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	
	
}
