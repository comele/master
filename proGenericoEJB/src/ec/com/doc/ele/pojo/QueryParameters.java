/**
 * 
 */
package ec.com.doc.ele.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ec.com.doc.ele.constantes.QueryParametersType;
import ec.com.doc.ele.util.ClasesUtil;


@SuppressWarnings("serial")
public class QueryParameters<T extends QueryParameter> implements Serializable {

	private QueryParametersType type;
	private Map<String, T> parameters;
	private Map<String, Object> parametersValues;
	private String parametersSentence;

	private QueryParameters(){
		//
	}
	
	private QueryParameters(QueryParametersType type){
		this.type = type;
	}
	
	/**
	 * 
	 * @param object
	 * @param fields
	 * @param queryParametersType
	 * @return
	 */
	public static QueryParameters<NamedParameterQuery> buildQueryNamedParameters(Object object, 
			String[] fields, 
			QueryParametersType queryParametersType,
			String token,
			String prefix){
		Object value = null;
		QueryParameters<NamedParameterQuery> parameters = null;
		
		if (fields != null && fields.length > 0){
			parameters = new QueryParameters<NamedParameterQuery>(queryParametersType);
			for (String field : fields){
				value = ClasesUtil.invocarMetodoGet(object, field);
				if (value != null){
					parameters.addParameter(new NamedParameterQuery(field, value, new NamedParameter(field, token, prefix)));
				}
			}
		}
		
		return parameters;
	}
	
	/**
	 * 
	 * @param object
	 * @param fields
	 * @param queryParametersType
	 * @return
	 */
	public static QueryParameters<NamedParameterQuery> buildQueryNamedParameters(Map<String, Object> parametersValues, 
			QueryParametersType queryParametersType,
			String token,
			String prefix){
		QueryParameters<NamedParameterQuery> parameters = null;
		final String DOT = ".";
		final String BLANK = "";
		
		if (parametersValues != null && !parametersValues.isEmpty()){
			parameters = new QueryParameters<NamedParameterQuery>(queryParametersType);
			for (Map.Entry<String, Object> parameterValue : parametersValues.entrySet()){
				parameters.addParameter(new NamedParameterQuery(parameterValue.getKey(), parameterValue.getValue(), new NamedParameter(StringUtils.replaceChars(parameterValue.getKey(), DOT, BLANK), token, prefix)));
			}
		}
		
		return parameters;
	}
	
	/**
	 * 
	 * @param parametersValues
	 * @param token
	 * @param prefix
	 */
	@SuppressWarnings("unchecked")
	public void addAsNamedParameters(Map<String, Object> parametersValues, String token, String prefix){
		if (parametersValues != null && !parametersValues.isEmpty()){
			for (Map.Entry<String, Object> parameterValue : parametersValues.entrySet()){
				this.addParameter( (T) new NamedParameterQuery(parameterValue.getKey(), parameterValue.getValue(), new NamedParameter(parameterValue.getKey(), token, prefix)));
			}
		}
	}
		

	/**
	 * @return el parameters
	 */
	public Map<String, T> getParameters() {
		return parameters;
	}


	/**
	 * @return el type
	 */
	public QueryParametersType getType() {
		return type;
	}

	/**
	 * @param type el type a establecer
	 */
	public void setType(QueryParametersType type) {
		this.type = type;
	}

	/**
	 * 
	 * @param parameter
	 */
	public void addParameter(T parameter){
		if (this.parameters == null){
			this.parameters = new HashMap<String, T>();
		}
		this.parameters.put(parameter.getName(), parameter);
	}

	/**
	 * 
	 * @param parameter
	 */
	public void removeParameter(T parameter){
		if (this.parameters != null && !this.parameters.isEmpty()){
			this.parameters.values().remove(parameter);
		}
	}

	/**
	 * 
	 * @param name
	 */
	public void removeParameter(String name){
		if (this.parameters != null && !this.parameters.isEmpty()){
			this.parameters.remove(name);
		}
	}


	/**
	 * 
	 * @param renew
	 * @return
	 */
	public Map<String, Object> getParametersValues(boolean renew){

		if (this.parameters != null && !this.parameters.isEmpty()){
			if (this.parametersValues == null){
				this.parametersValues = new HashMap<String, Object>();
				renew = true;
			}

			if (renew){	
				if (!this.parametersValues.isEmpty()){
					this.parametersValues.clear();
				}
				for (Map.Entry<String, T> parameter : this.parameters.entrySet()){
					this.parametersValues.put(parameter.getValue().getParameter().getName(), parameter.getValue().getValue());
				}
			}
		}

		return this.parametersValues;
	}
	
	
	/**
	 * 
	 * @param renew
	 * @return
	 */
	public String getParametersSentence(boolean renew){
		Collection<String> expresions = null;  
		
		if (this.parameters != null && !this.parameters.isEmpty()){
			if (this.parametersSentence == null){
				renew = true;
			}

			if (renew){	
				this.parametersSentence = null;
				expresions = new ArrayList<String>(this.parameters.size());
				for (Map.Entry<String, T> parameter : this.parameters.entrySet()){
					expresions.add(new StringBuilder(parameter.getValue().getName())
						.append("=")
						.append(parameter.getValue().getParameter().getToken())
						.append(parameter.getValue().getParameter().getName())
						.toString());
				}
				this.parametersSentence = StringUtils.join(expresions, this.type.getToken());
			}
		}

		return this.parametersSentence;
	}



}
