/**
 * 
 */
package ec.com.doc.ele.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.collection.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;

import ec.com.doc.ele.ejb.CriterioBusqueda;


@SuppressWarnings("serial")
public abstract class BuscadorDTO implements Serializable {

	// orden
	private OrderBy orderByField;
	private List<OrderBy> orderFields;

	private Boolean countAgain = Boolean.TRUE;

	// relaciones
	private int joinType;

	private String propertyForObtainValue;
	private Map<String, Object> nullRestricionProperties;
	private Map<String, Object> greaterThanProperties;
	private Map<String, String> collectionJoins;

	private CriterioBusqueda criteriaSearch;

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {		
		return super.hashCode();
	}

	/**
	 * 
	 */
	public synchronized void setCollectionJoin(String collectionPropertyName) {
		if (this.collectionJoins == null) {
			this.collectionJoins = new HashMap<String, String>();
		}

		this.collectionJoins.put(collectionPropertyName, collectionPropertyName);
	}

	/**
	 * 
	 * @param collectionPropertyName
	 * @return
	 */
	public Object getCollectionJoin(String collectionPropertyName) {
		if (this.collectionJoins != null && !this.collectionJoins.isEmpty()) {
			return this.collectionJoins.get(collectionPropertyName);
		}
		return null;
	}

	/**
	 * 
	 * @param propertyName
	 */
	public synchronized void isNull(String propertyName) {
		this.isNull(propertyName, false);
	}

	public boolean getIsNull(String propertyName) {
		if (this.nullRestricionProperties != null && !this.nullRestricionProperties.isEmpty()) {
			return this.nullRestricionProperties.containsKey(propertyName);
		}
		return false;
	}

	/**
	 * 
	 * @param propertyName
	 * @param reset
	 */
	public synchronized void isNull(String propertyName, boolean reset) {
		if (this.nullRestricionProperties == null) {
			this.nullRestricionProperties = new HashMap<String, Object>();
		}

		if (!reset) {
			if (!this.nullRestricionProperties.isEmpty()) {
				for (String currentKey : this.nullRestricionProperties.keySet()) {
					this.nullRestricionProperties.remove(currentKey);
				}
			}
		}

		this.nullRestricionProperties.put(propertyName, null);
	}

	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param reset
	 */
	public synchronized void isGreaterThan(String propertyName, Object value, boolean reset) {
		if (this.greaterThanProperties == null) {
			this.greaterThanProperties = new HashMap<String, Object>();
		}

		if (!reset) {
			if (!this.greaterThanProperties.isEmpty()) {
				for (String currentKey : this.greaterThanProperties.keySet()) {
					this.greaterThanProperties.remove(currentKey);
				}
			}
		}

		this.greaterThanProperties.put(propertyName, value);
	}

	/**
	 * 
	 * @param propertyName
	 * @return
	 */
	public Object getIsGreaterThan(String propertyName) {
		if (this.greaterThanProperties != null && !this.greaterThanProperties.isEmpty()) {
			return this.greaterThanProperties.get(propertyName);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean existenCamposOrden() {
		return this.orderFields != null && !this.orderFields.isEmpty();
	}

	/**
	 * @return el orderFields
	 */
	public List<OrderBy> getOrderFields() {
		return orderFields;
	}

	/**
	 * @param orderFields
	 *            el orderFields a establecer
	 */
	public void setOrderFields(List<OrderBy> camposOrden) {
		this.orderFields = camposOrden;
	}


	/**
	 * @return el orderByField
	 */
	public OrderBy getOrderByField() {
		return orderByField;
	}

	/**
	 * @param orderByField
	 *            el orderByField a establecer
	 */
	public void setOrderByField(OrderBy ordenarPor) {
		this.orderByField = ordenarPor;
	}

	/**
	 * @return el joinType
	 */
	public int getJoinType() {
		return joinType;
	}

	/**
	 * @param joinType
	 *            el joinType a establecer
	 */
	public void setJoinType(int tipoJoin) {
		this.joinType = tipoJoin;
	}


	/**
	 * Obtiene la especificaci&oacute;n de si se desea volver a obtener el
	 * conteo de los datos totales existentes en la consulta
	 * 
	 * @return Obtiene el valor de countAgain establecido.
	 */
	public Boolean getCountAgain() {
		return countAgain;
	}

	/**
	 * Obtiene la especificaci&oacute;n de si se desea volver a obtener el
	 * conteo de los datos totales existentes en la consulta
	 * 
	 * @param countAgain
	 *            El valor de countAgain a establecer.
	 */
	public void setCountAgain(Boolean countAgain) {
		this.countAgain = countAgain;
	}

	/**
	 * Verifica si un objeto <code>entity</code> est&aacute; proxificado por el
	 * motor ORM como lazy
	 * 
	 * @param <T>
	 *            El tipo de dato de la entidad que tiene que ser serializable o
	 *            una colecci&acute;n persistente
	 * @param entity
	 *            Entidad a verificar si tiene lazy
	 * @return
	 */
	public static <T extends Serializable> boolean isLazy(T entity) {
		boolean init = false;
		if (entity instanceof HibernateProxy) {
			init = true;
		}
		return init;
	}

	public static <T> boolean isLoaded(T entity) {
		boolean init = true;
		if (entity == null) {
			init = false;
		} else if (entity instanceof HibernateProxy) {
			init = false;
		} else if (entity instanceof PersistentCollection) {
			PersistentCollection persistentCollection = (PersistentCollection) entity;
			if (!persistentCollection.getClass().isAssignableFrom(persistentCollection.getClass())) {
				// Si no es un objeto PersistentBag de Hibernate, esta no ha
				// sido inicializada.
				init = true;
			} else if (!persistentCollection.wasInitialized()) {
				// Si Hibernate no ha cargado la coleccion, no ha sido
				// inicializada
				init = false;
			}
		}
		return init;
	}

	/**
	 * @return el propertyForObtainValue
	 */
	public String getPropertyForObtainValue() {
		return propertyForObtainValue;
	}

	/**
	 * @param propertyForObtainValue
	 *            el propertyForObtainValue a establecer
	 */
	public void setPropertyForObtainValue(String propertyForObtainValue) {
		this.propertyForObtainValue = propertyForObtainValue;
	}

	/**
	 * @return
	 */
	public CriterioBusqueda getCriteriaSearch() {		
		return criteriaSearch;
	}

	/**
	 * @param criteriaSearch
	 */
	public void setCriteriaSearch(CriterioBusqueda criteriaSearch) {
		this.criteriaSearch = criteriaSearch;
	}

	/**
	 * @return
	 */
	public Serializable getPOJO() {
		return this;
	}
}
