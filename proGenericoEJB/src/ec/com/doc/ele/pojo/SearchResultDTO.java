/**
 * 
 */
package ec.com.doc.ele.pojo;

import java.io.Serializable;
import java.util.Collection;

@SuppressWarnings("serial")
public class SearchResultDTO <x extends Object> implements Serializable{

	private Long countResults;
	private Collection<x> results;
	
	
	public SearchResultDTO(){
		this.countResults = 0L;
	}
	/**
	 * @return el countResults
	 */
	public Long getCountResults() {
		return countResults;
	}
	/**
	 * @param countResults el countResults a establecer
	 */
	public void setCountResults(Long countResults) {
		this.countResults = countResults;
	}
	/**
	 * @return el results
	 */
	public Collection<x> getResults() {
		return results;
	}
	/**
	 * @param results el results a establecer
	 */
	public void setResults(Collection<x> results) {
		this.results = results;
	}
	
}
