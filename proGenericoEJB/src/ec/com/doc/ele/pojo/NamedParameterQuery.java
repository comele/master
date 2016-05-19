package ec.com.doc.ele.pojo;

import java.util.ArrayList;
import java.util.Collection;

import ec.com.doc.ele.sql.DataTypeConversion;
import ec.com.doc.ele.util.ClasesUtil;


/**
 * Clase que permite establecer los par&aacute;metros nombrados en una consulta
 * que utiliza JPA-QL
 */
public class NamedParameterQuery implements QueryParameter{
	String name;
	Object value;
	private NamedParameter parameter;
	int type;
	/**
	 * Constructor por defecto
	 */
	public NamedParameterQuery(){		
	}
	
	/**
	 * Constructor que inicializa el nombre y el valor del par&aacute;metro nombrado
	 * @param name nombre del par&aacute;metro nombrado
	 * @param value valor del par&aacute;metro nombrado
	 */
	public <T> NamedParameterQuery(String name, T value){
		this.name = name;
		this.value = value;
		type = DataTypeConversion.getInstance().getSqlDataType(value.getClass());
	}
	
	/**
	 * Constructor que inicializa el nombre, el valor y los datos del parmetro con nombre
	 * @param name nombre del par&aacute;metro nombrado
	 * @param value valor del par&aacute;metro nombrado
	 * @param parameter datos del par&aacute;metro con nombre
	 */
	public <T> NamedParameterQuery(String name, T value, NamedParameter parameter){
		this(name, value);
		this.parameter = parameter;
	}

	
	/**
	 * Constructor que inicializa el nombre y el valor del par&aacute;metro nombrado
	 * @param name nombre del par&aacute;metro nombrado
	 * @param value valor del par&aacute;metro nombrado
	 * @param propertyOfValue propiedad del par&aacute;metro nombrado en caso de ser un objeto valor compuesto
	 */
	public <T> NamedParameterQuery(String name, T pojo, String property){
		this.name = name;
		extractParameterValue(pojo, property);
		type = DataTypeConversion.getInstance().getSqlDataType(value.getClass());
	}

	/**
	 * Extraer los valores de los par&aacute;metros dado un objeto compuesto y una propiedad del objeto
	 * @param <T> Tipo de dato del objeto pojo
	 * @param pojo Objeto compuesto del que se extraer&aacute; los par&aacute;metros
	 * @param property Propiedad del objeto compuesto que corresponder&aacute; al valor del par&aacute;metro
	 */
	private <T> void extractParameterValue(T pojo, String property) {
		if(pojo != null){
			if(pojo instanceof Object[]){
				Object[] pojoArray = (Object[])pojo;
				Object[] values = new Object[pojoArray.length];
				int i = 0;
				for(Object pojoElement : pojoArray){
					Object propertyValue = ClasesUtil.invocarMetodoGet(pojoElement, property);
					values[i++] = propertyValue;
				}
				value = values;
			}
			else if(pojo instanceof Collection<?>){
				Collection<?> pojoArray = (Collection<?>)pojo;
				Collection<Object> values = new ArrayList<Object>(pojoArray.size());
				for(Object pojoElement : pojoArray){
					Object propertyValue = ClasesUtil.invocarMetodoGet(pojoElement, property);
					values.add(propertyValue);
				}
				value = values;
			}
			else {
				value = ClasesUtil.invocarMetodoGet(pojo, property);
			}
		}
	}	
	/**Obtener el nombre del par&aacute;metro nombrado
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Establecer el nombre del par&aacute;metro nombrado
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Obtener el valor correspondiente al par&aacute;metro nombrado
	 * @return
	 */
	public Object getValue() {
		return value;
	}
	/**
	 * Establecer el valor correspondiente al par&aacute;metro nombrado
	 * @param value
	 */
	public<T> void setValue(T value) {
		this.value = value;
		type = DataTypeConversion.getInstance().getSqlDataType(value.getClass());
	}
	
	/**
	 * Establecer el valor de un par&aacute;metro a partir de un objeto compuesto
	 * @param <T> Tipo de dato del objeto
	 * @param pojo Objeto compuesto del que se extraer&aacute;n el o los valores del par&aacute;metro nombrado
	 * @param property Propiedad del objeto compuesto que corresponde al valor o los valores del par&aacute;metro
	 */
	public<T> void setValue(T pojo, String property) {
		extractParameterValue(pojo, property);
		type = DataTypeConversion.getInstance().getSqlDataType(value.getClass());
	}

	/**
	 * Obtener el tipo de dato JDBC para SQL
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * Establecer el tipo de dato JDBC para SQL
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return el parameter
	 */
	public NamedParameter getParameter() {
		return parameter;
	}

	/**
	 * @param parameter el parameter a establecer
	 */
	public void setParameter(NamedParameter parameter) {
		this.parameter = parameter;
	}
	
	

}
