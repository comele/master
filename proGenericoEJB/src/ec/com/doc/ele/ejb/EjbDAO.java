package ec.com.doc.ele.ejb;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ec.com.doc.ele.enumeracion.ComparatorTypeEnum;
import ec.com.doc.ele.excepcion.DAOException;
import ec.com.doc.ele.pojo.Join;
import ec.com.doc.ele.pojo.NamedParameterQuery;
import ec.com.doc.ele.pojo.BuscadorDTO;
import ec.com.doc.ele.util.RangeValue;


/**
 * @author walvarez
 *
 * @param <x>
 */
public interface EjbDAO<x extends BuscadorDTO> extends Serializable{
	void setEntityManager(EntityManager entityManager);

	void populateNamedParameterQuery(Query query, Collection<NamedParameterQuery> namedParameters) throws DAOException;

	<T> Collection<T> findByQuery(Query query, Class<T> clase);

	<T extends Serializable> HashMap<String, Object> getIdRestrictions(T objetoRaiz, String idName);

	<T extends Serializable> void addIdRestrictions(T objetoVO, String nombreIdObjetoRaiz, Root<?> root, Predicate where) throws DAOException;

	<T extends Serializable> Predicate addIdRestrictions(T objetoVO, String nombreIdObjetoRaiz, From<?, ?> root, Predicate where, String alias) throws DAOException;

	<T extends Serializable> StringBuilder addIdRestrictions(T objetoVO, String aliasEntidad, String nombreIdObjetoRaiz, StringBuilder where, Map<String, NamedParameterQuery> params)
			throws DAOException;

	<T extends Serializable> Predicate addIdRestrictions(T objetoVO, CriterioBusqueda criteriaSearch, String nombreIdObjetoRaiz, Root<?> root, Predicate where) throws DAOException;

	<T extends Serializable> Predicate addIdRestrictions(T objetoVO, CriterioBusqueda criteriaSearch, String nombreIdObjetoRaiz, From<?, ?> root, Predicate where, String alias)
			throws DAOException;

	<T extends Serializable> Predicate addIdRestrictions(T objetoVO, CriterioBusqueda criteriaSearch, String nombreRelacion, String nombreIdObjetoRaiz, From<?, ?> root, Predicate where,
			String alias) throws DAOException;

	<T extends Serializable> StringBuilder addIdRestrictions(T objetoVO, String aliasEntidad, CriterioBusqueda criteriaSearch, String nombreRelacion, String nombreIdObjetoRaiz,
			StringBuilder where, Map<String, NamedParameterQuery> params) throws DAOException;

	<T extends Serializable> Predicate addExampleRestrictions(T objetoVO, From<?, ?> root, Predicate where, CriterioBusqueda criteriaSearch, StringBuilder criteriaPath,
			boolean enableLike, String... propiedadesAexcluir) throws DAOException;

	<T extends Serializable> Predicate addExampleRestrictions(T objetoVO, From<?, ?> root, Predicate where, boolean enableLike, String... propiedadesAexcluir) throws DAOException;

	<T extends Serializable> Predicate addExampleRestrictions(T objetoVO, From<?, ?> root, Predicate where, String alias, boolean enableLike, String... propiedadesAexcluir)
			throws DAOException;

	<T extends Serializable> void addExampleRestrictions(T objetoVO, String aliasEntidad, StringBuilder where, Map<String, NamedParameterQuery> params, Boolean enableLike,
			String... excludedProperties) throws DAOException;

	<T extends Serializable> Predicate addExampleRestrictions(T objetoVO, From<?, ?> root, Predicate where, String alias, CriterioBusqueda criteriaSearch, StringBuilder criteriaPath,
			boolean enableLike, String... propiedadesAexcluir) throws DAOException;

	<T> Predicate addExpression(Class<?> classPojo, Path<?> root, Predicate where, String propertyName, T propertyValue, Class<?> propertyType, ComparatorTypeEnum comparatorID);

	<T> Predicate addExpression(Class<?> classPojo, Path<?> root, Predicate where, String alias, String propertyName, T propertyValue, Class<?> propertyType,
			ComparatorTypeEnum comparatorID);

	<T> Predicate addExpression(Class<?> classPojo, Path<?> root, Predicate where, String propertyName, T propertyValue, Class<?> propertyType, ComparatorTypeEnum comparatorID,
			Boolean denyComparation);

	<T> Predicate addExpression(Class<?> classPojo, Path<?> root, Predicate where, String alias, String fieldProperty, T propertyValue, Class<?> propertyType,
			ComparatorTypeEnum comparatorID, Boolean denyComparation);

	<T> StringBuilder addExpression(Class<?> classPojo, String aliasEntidad, StringBuilder where, Map<String, NamedParameterQuery> params, String fieldProperty, T propertyValue,
			Class<?> propertyType, ComparatorTypeEnum comparatorID) throws DAOException;

	<T> StringBuilder addExpression(Class<?> classPojo, String aliasEntidad, StringBuilder where, Map<String, NamedParameterQuery> params, String fieldProperty, T propertyValue,
			Class<?> propertyType, ComparatorTypeEnum comparatorID, Boolean denyComparation) throws DAOException;

	Collection<Field> getFields(Class<?> objectClass, Collection<String> fields);

	String getIDNameByAnnotation(x object) throws DAOException;

	int updateByID(x object, String[] fieldsForUpadate, boolean clearCache) throws DAOException;

	void actualizarObjeto(x objeto) throws DAOException;

	void addRelations(Root<?> qbcSelect, List<Join> joins) throws DAOException;

	void crearOactualizarObjeto(x objeto) throws DAOException;

	void eliminarObjeto(x objeto) throws DAOException;

	void crearObjeto(x objetoVO) throws DAOException;

	String obtenerNombreAtributoId(String nombreIdObjetoRaiz, String nombreAtributo) throws Exception;

	<T extends BuscadorDTO> void establecerOrden(T objetoRaiz, From<?, ?> root, CriteriaQuery<?> qbcSelect) throws DAOException;

	<T extends BuscadorDTO> void establecerOrden(T objetoRaiz, String aliasEntity, StringBuilder orderByBuilder) throws DAOException;

	String establecerOrden(BuscadorDTO objetoRaiz);

	String getAttributeNameByAnnotation(Object objetoVO, Class<? extends Annotation> anotacion);

	String getIdName(x plantillaBusqueda) throws DAOException;

	<T> CriteriaQuery<?> createCriteria(T objetoRaiz, boolean limpiarCache) throws Exception;

	<T> CriteriaQuery<?> createCriteria(T objetoRaiz, String aliasObjetoRaiz, boolean limpiarCache) throws Exception;

	<T> CriteriaQuery<?> createCriteria(Class<T> claseObjetoRaiz, boolean limpiarCache) throws Exception;

	Query createQuery(String query, boolean clearCache) throws Exception;

	int eliminarObjetos(Class<x> claseObjeto) throws DAOException;

	Predicate getRegisterDateRestriction(x rootObject, Root<x> root, RangeValue<Timestamp> rangoFechaRegistro);

	<T extends Serializable> void addIdRestrictions(T plantillaBusqueda, Root<?> root, CriteriaBuilder qbcSelect) throws DAOException;

	<T extends Serializable> Predicate addRelations(T objetoVO, From<?, ?> root, Predicate where, CriteriaQuery<?> qbcSelect, CriterioBusqueda criteriaSearch, boolean enableLike,
			String... excludedProperties) throws DAOException;

	<T extends Serializable> Predicate addRelations(T objetoVO, From<?, ?> root, Predicate where, CriteriaQuery<?> qbcSelect) throws DAOException;

	Collection<x> findBySimpleCriteriaQuery(x plantillaBusqueda) throws DAOException;

	Collection<x> findBySimpleCriteriaQuery(x plantillaBusqueda, boolean clearCache, boolean enableLike, String... excludedProperties) throws DAOException;

	Collection<x> findBySimpleCriteriaQuery(x objetoVO, String nombreId, List<Join> joins) throws DAOException;

	Collection<x> findPage(x plantillaBusqueda, boolean enableLike, String... excludedProperties) throws DAOException;

	Query getQuery(x plantillaBusqueda, boolean enableLike, boolean enableOrder, String... excludedProperties) throws DAOException;

	Query getQuery(x plantillaBusqueda, String selectClause, boolean enableLike, boolean enableOrder, String... excludedProperties) throws DAOException;

	x findUniqueBySimpleCriteriaQuery(x plantillaBusqueda) throws DAOException;

	x findUnique(x plantillaBusqueda) throws DAOException;

	x findUniqueBySimpleCriteriaQuery(x plantillaBusqueda, CriterioBusqueda criteriaSearch) throws DAOException;

	x findUniqueBySimpleCriteriaQuery(x plantillaBusqueda, boolean clearCache, boolean enableLike, String... excludedProperties) throws DAOException;

	x findUnique(x plantillaBusqueda, boolean clearCache, boolean enableLike, String... excludedProperties) throws DAOException;

	x findUniqueBySimpleCriteriaQuery(x plantillaBusqueda, CriterioBusqueda criteriaSearch, boolean clearCache, boolean enableLike, String... excludedProperties) throws DAOException;

	<T> Collection<T> findFieldValueBySimpleCriteriaQuery(String propertyForObtainValue, x plantillaBusqueda, boolean clearCache, boolean enableLike, String... excludedProperties)
			throws DAOException;

	<T> Collection<T> findFieldValueBySimpleCriteriaQuery(String propertyForObtainValue, boolean applyDistinct, x plantillaBusqueda, boolean clearCache, boolean enableLike,
			String... excludedProperties) throws DAOException;

	Object findMaxValueBySimpleCriteriaQuery(String propertyForObtainMaxValue, x plantillaBusqueda, boolean clearCache, boolean enableLike, String... excludedProperties)
			throws DAOException;

	
	Object findSumValueBySimpleCriteriaQuery(String propertyForObtainSumValue, x plantillaBusqueda, boolean clearCache, boolean enableLike, String... excludedProperties)
			throws DAOException;
	
	/**
	 * Hace no persistente a un objeto que lo es
	 * @param searchDTO
	 * @throws x
	 */
	void detach(x searchDTO) throws DAOException;

	DAOException convertDAOException(PersistenceException ex);
}