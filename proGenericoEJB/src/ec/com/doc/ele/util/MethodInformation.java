/**
 * 
 */
package ec.com.doc.ele.util;
import java.io.Serializable;

@SuppressWarnings("serial")
public class MethodInformation implements Serializable{

	private String methodName;
	private String beanName;
	private String factoryClassName;
	private String getBeanMethodName;
	private Object[] methodParametersValues;
	private Class[] methodParametersClasses;
	
	/**
	 * @return el beanName
	 */
	public String getBeanName() {
		return beanName;
	}
	/**
	 * @param beanName el beanName a establecer
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	
	
	/**
	 * @return el factoryClassName
	 */
	public String getFactoryClassName() {
		return factoryClassName;
	}
	/**
	 * @param factoryClassName el factoryClassName a establecer
	 */
	public void setFactoryClassName(String factoryClassName) {
		this.factoryClassName = factoryClassName;
	}
	/**
	 * @return el getBeanMethodName
	 */
	public String getGetBeanMethodName() {
		return getBeanMethodName;
	}
	/**
	 * @param getBeanMethodName el getBeanMethodName a establecer
	 */
	public void setGetBeanMethodName(String getBeanMethodName) {
		this.getBeanMethodName = getBeanMethodName;
	}
	/**
	 * @return el methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName el methodName a establecer
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return el methodParametersValues
	 */
	public Object[] getMethodParametersValues() {
		return methodParametersValues;
	}
	/**
	 * @param methodParametersValues el methodParametersValues a establecer
	 */
	public void setMethodParametersValues(Object[] methodParametersValues) {
		this.methodParametersValues = methodParametersValues;
	}
	/**
	 * @return el methodParametersClasses
	 */
	public Class[] getMethodParametersClasses() {
		return methodParametersClasses;
	}
	/**
	 * @param methodParametersClasses el methodParametersClasses a establecer
	 */
	public void setMethodParametersClasses(Class[] methodParametersClasses) {
		this.methodParametersClasses = methodParametersClasses;
	}
	
}
