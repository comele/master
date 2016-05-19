/**
 * 
 */
package ec.com.doc.ele.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RangeValue<x extends Object> implements Serializable{

	private x bottomValue;
	private x topValue;
	
	public RangeValue(){}
	
	public RangeValue(x bottomValue, x topValue){
		this.bottomValue = bottomValue;
		this.topValue = topValue;
	}
	
	/**
	 * @return el bottomValue
	 */
	public x getBottomValue() {
		return bottomValue;
	}
	/**
	 * @param bottomValue el bottomValue a establecer
	 */
	public void setBottomValue(x bottomValue) {
		this.bottomValue = bottomValue;
	}
	
	/**
	 * @return el topValue
	 */
	public x getTopValue() {
		return topValue;
	}
	/**
	 * @param topValue el topValue a establecer
	 */
	public void setTopValue(x topValue) {
		this.topValue = topValue;
	}
	
	/**
	 * 
	 * @param rangeValue
	 * @return
	 */
	public static Boolean isNotEmpty(RangeValue rangeValue){
	    return rangeValue != null && rangeValue.getBottomValue() != null && rangeValue.getTopValue() != null;
	}
}
