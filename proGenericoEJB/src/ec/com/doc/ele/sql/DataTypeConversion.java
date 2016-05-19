package ec.com.doc.ele.sql;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Ref;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

/**
 * Clase que permite mapear los tipos de dato SQL correspondientes 
 *
 */
public class DataTypeConversion {
	private static final DataTypeConversion instance = new DataTypeConversion();
	
	private DataTypeConversion(){}
			
	public static DataTypeConversion getInstance() {
		return instance;
	}



	/**
	 * Obtiene el tipo de dato en SQL a partir de la clase Java
	 * @param clazz Clase java
	 * @return Valor entero que corresponde al tipo de dato SQL 
	 * tomado de la clase java.sql.Types
	 */
	public int getSqlDataType(Class<?> clazz) {
		int type;
		if (clazz.equals(String.class)) {
			type = Types.VARCHAR;
		} else if (clazz.equals(BigDecimal.class)) {
			type = Types.NUMERIC;
		} else if (clazz.equals(Boolean.class)) {
			type = Types.BOOLEAN;
		} else if (clazz.equals(Byte.class)) {
			type = Types.SMALLINT;
		} else if (clazz.equals(Integer.class)) {
			type = Types.INTEGER;
		} else if (clazz.equals(Long.class)) {
			type = Types.BIGINT;
		} else if (clazz.equals(Float.class)) {
			type = Types.REAL;
		} else if (clazz.equals(Double.class)) {
			type = Types.DOUBLE;
		} else if (clazz.equals(byte[].class)) {
			type = Types.BINARY;
		} else if (clazz.equals(Date.class)) {
			type = Types.DATE;
		} else if (clazz.equals(Time.class)) {
			type = Types.TIME;
		} else if (clazz.equals(Timestamp.class)) {
			type = Types.TIMESTAMP;
		} else if (clazz.equals(Clob.class)) {
			type = Types.CLOB;
		} else if (clazz.equals(Blob.class)) {
			type = Types.BLOB;
		} else if (clazz.equals(Array.class)) {
			type = Types.ARRAY;
		} else if (clazz.equals(Struct.class)) {
			type = Types.STRUCT;
		} else if (clazz.equals(Ref.class)) {
			type = Types.REF;
		} else if (clazz.equals(URL.class)) {
			type = Types.DATALINK;
		} else {
			type = Types.JAVA_OBJECT;
		}
		return type;
	}

	/**
	 * Obtiene el tipo de dato Java correspondiente dado
	 * un tipo de dato SQL tomado de la clase de constantes
	 * java.sql.Types
	 * @param type Tipo de dato SQL del que se quiere obtener su
	 * clase Java correspondiente
	 * @return
	 */
	public Class<?> getJavaType(int type) {
		Class<?> clazz;
		switch (type) {
		case Types.CHAR:
			clazz = String.class;
			break;
		case Types.VARCHAR:
			clazz = String.class;
			break;
		case Types.LONGVARCHAR:
			clazz = String.class;
			break;
		case Types.NUMERIC:
			clazz = java.math.BigDecimal.class;
			break;
		case Types.DECIMAL:
			clazz = java.math.BigDecimal.class;
			break;
		case Types.BIT:
			clazz = boolean.class;
			break;
		case Types.BOOLEAN:
			clazz = boolean.class;
			break;
		case Types.TINYINT:
			clazz = byte.class;
			break;
		case Types.SMALLINT:
			clazz = short.class;
			break;
		case Types.INTEGER:
			clazz = int.class;
			break;
		case Types.BIGINT:
			clazz = long.class;
			break;
		case Types.REAL:
			clazz = float.class;
			break;
		case Types.FLOAT:
			clazz = double.class;
			break;
		case Types.DOUBLE:
			clazz = double.class;
			break;
		case Types.BINARY:
			clazz = byte[].class;
			break;
		case Types.VARBINARY:
			clazz = byte[].class;
			break;
		case Types.LONGVARBINARY:
			clazz = byte[].class;
			break;
		case Types.DATE:
			clazz = java.sql.Date.class;
			break;
		case Types.TIME:
			clazz = java.sql.Time.class;
			break;
		case Types.TIMESTAMP:
			clazz = java.sql.Timestamp.class;
			break;
		case Types.CLOB:
			clazz = Clob.class;
			break;
		case Types.BLOB:
			clazz = Blob.class;
			break;
		case Types.ARRAY:
			clazz = Array.class;
			break;
		case Types.STRUCT:
			clazz = Struct.class;
			break;
		case Types.REF:
			clazz = Ref.class;
			break;
		case Types.DATALINK:
			clazz = java.net.URL.class;
			break;
		default:
			clazz = Object.class;
			break;
		}
		return clazz;

	}
}
