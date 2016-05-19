package ec.com.doc.ele.ejb.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.QueryException;

import ec.com.doc.ele.anotaciones.BetweenIncludeRestriction;
import ec.com.doc.ele.anotaciones.ComparatorTypeField;
import ec.com.doc.ele.anotaciones.DisjunctionField;
import ec.com.doc.ele.anotaciones.ForeignField;
import ec.com.doc.ele.anotaciones.INRestriction;
import ec.com.doc.ele.anotaciones.IdField;
import ec.com.doc.ele.anotaciones.NotInRestriction;
import ec.com.doc.ele.anotaciones.NotPersistentField;
import ec.com.doc.ele.anotaciones.RelationField;
import ec.com.doc.ele.constantes.QueryParametersType;
import ec.com.doc.ele.ejb.CriterioBusqueda;
import ec.com.doc.ele.ejb.DefinirRestriccion;
import ec.com.doc.ele.ejb.EjbDAO;
import ec.com.doc.ele.ejb.ParametroCriterioBusqueda;
import ec.com.doc.ele.enumeracion.ComparatorTypeEnum;
import ec.com.doc.ele.excepcion.DAOException;
import ec.com.doc.ele.pojo.Join;
import ec.com.doc.ele.pojo.NamedParameterQuery;
import ec.com.doc.ele.pojo.OrderBy;
import ec.com.doc.ele.pojo.QueryParameters;
import ec.com.doc.ele.pojo.BuscadorDTO;
import ec.com.doc.ele.recursos.GenericoMensajes;
import ec.com.doc.ele.util.ClasesUtil;
import ec.com.doc.ele.util.RangeValue;
import ec.com.doc.ele.util.log.LogGenerico;


/**
 * @author walvarez
 *
 * @param <x>
 */
@SuppressWarnings("serial")
public class EjbDAOImpl<x extends BuscadorDTO> implements EjbDAO<x>{

	protected EntityManager entityManager;
	private static final String DOT = ".";

	private boolean existRelationsOneToMany = false;

	/**
	 * COnjunto en el que almacenar&aacute;n los c&oacute;digos hash de los
	 * objetos que hayan sido utilizados para una consulta espec&iacute;fica.
	 * Esto ayudar&aacute; a detectar posibles caidas en recursividad infinita
	 * al agregar relaciones
	 */
	private Set<Integer> relationsHashCodeSet = new LinkedHashSet<Integer>();

	/**
	 * Concatenador de nombres de alias para propiedades de objetos persistibles
	 * por hibernate, ej:
	 */
	protected final String ALIAS_CONCATENATOR = "_";

	/**
	 * Tabla Hash que almacena en memoria una lista de objetos
	 * <code>Field</code> correspondientes a una clase tomada como clave de la
	 * Tabla Hash
	 */
	static Map<Class<?>, Collection<Field>> fieldsQueryMap = new HashMap<Class<?>, Collection<Field>>();

	static Map<Class<?>, Set<Field>> allFieldsMapped = new HashMap<Class<?>, Set<Field>>();

	static Map<Class<?>, Set<Field>> associationFieldsMapped = new HashMap<Class<?>, Set<Field>>();

	static Map<Class<?>, Set<Field>> transientFieldsMap = new HashMap<Class<?>, Set<Field>>();

	static final String ALIAS_ROOT = "root";

	public EjbDAOImpl() {
	}

	public EjbDAOImpl(EntityManager em) {
		this.entityManager = em;

	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected Serializable getPOJO(Object objetoVO) {
		Serializable pojo;
		if (objetoVO instanceof BuscadorDTO) {
			pojo = ((BuscadorDTO) objetoVO).getPOJO();
		} else {
			pojo = (Serializable) objetoVO;
		}
		return pojo;
	}

	public void populateNamedParameterQuery(Query query,
			Collection<NamedParameterQuery> namedParameters)
			throws DAOException {
		for (NamedParameterQuery namedParameter : namedParameters) {
			query.setParameter(namedParameter.getName(),
					namedParameter.getValue());
		}
	}

	private String propertyNameToAlias(Object objetoVO, String propertyName)
			throws DAOException {

		String[] propertyPath = propertyName.split("[.]");
		StringBuilder propertyBuilder = new StringBuilder(ALIAS_ROOT);
		Object objeto = getPOJO(objetoVO);

		try {
			boolean existEntity;
			Attribute<?, ?> type;
			EntityType<?> classMetadata;
			for (String propiedad : propertyPath) {
				existEntity = false;
				if (objeto != null) {
					try {
						classMetadata = entityManager.getEntityManagerFactory()
								.getMetamodel().entity(objeto.getClass());
						type = classMetadata.getAttribute(propiedad);
						switch (type.getPersistentAttributeType()) {
						// Tipo de campo simple
						case BASIC:
						case EMBEDDED:

							break;
						// Campo tipo relacion uno a uno o muchos a uno
						case ONE_TO_ONE:
						case MANY_TO_ONE:
							// Campo tipo relacion uno a varios o varios a
							// varios
						case ONE_TO_MANY:
						case MANY_TO_MANY:
						case ELEMENT_COLLECTION:
							objeto = ClasesUtil.invocarMetodoGet(objeto,
									propiedad);

							if (objeto instanceof Collection<?>) {
								Collection<?> objetoCol = (Collection<?>) objeto;
								if (objetoCol.iterator().hasNext()) {
									objeto = objetoCol.iterator().next();
									existEntity = true;
								}
							}
							else {
								existEntity = true;
							}
							break;
						default:

						}
					} catch (QueryException e) {
						objeto = null;
					}
				}

				if (existEntity) {
					propertyBuilder.append(ALIAS_CONCATENATOR);
					propertyBuilder.append(propiedad);
				} else if (propertyBuilder.length() > 0) {
					propertyBuilder.append(DOT).append(propiedad);
				} else {
					propertyBuilder.append(propiedad);
				}
			}
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (SecurityException e) {
			throw new DAOException(e);
		}

		return propertyBuilder.toString();
	}

	private String propertyNameToAlias(Class<?> clazz, String propertyName)
			throws DAOException {

		String[] propertyPath = propertyName.split("[.]");
		StringBuilder propertyBuilder = new StringBuilder(ALIAS_ROOT);

		try {
			boolean existEntity;
			Attribute<?, ?> type;
			EntityType<?> classMetadata;
			for (String propiedad : propertyPath) {
				existEntity = false;
				classMetadata = entityManager.getEntityManagerFactory()
						.getMetamodel().entity(clazz);
				type = classMetadata.getAttribute(propiedad);
				switch (type.getPersistentAttributeType()) {
				// Tipo de campo simple
				case BASIC:
				case EMBEDDED:

					break;
				// Campo tipo relacion uno a uno o muchos a uno
				case ONE_TO_ONE:
				case MANY_TO_ONE:
					// Campo tipo relacion uno a varios o varios a varios
				case ONE_TO_MANY:
				case MANY_TO_MANY:
				case ELEMENT_COLLECTION:
					existEntity = true;
					break;
				default:

				}

				if (existEntity) {
					propertyBuilder.append(ALIAS_CONCATENATOR);
					propertyBuilder.append(propiedad);
				} else if (propertyBuilder.length() > 0) {
					propertyBuilder.append(DOT).append(propiedad);
				} else {
					propertyBuilder.append(propiedad);
				}
			}
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (SecurityException e) {
			throw new DAOException(e);
		}

		return propertyBuilder.toString();
	}

	@SuppressWarnings("unchecked")
	public <T> Collection<T> findByQuery(Query query, Class<T> clase) {
		return (Collection<T>) query.getResultList();
	}


	/**
	 * Obtiene los atributos del id del objeto raíz de la consulta que son
	 * diferentes de <code>null</code>
	 * 
	 * 
	 * @param objetoVO
	 *            objeto sobre el cual se requiere realizar la consulta
	 * @param nombreId
	 *            nombre del atributo id del objeto raiz
	 * @return un objeto tipo <code>HashMap</code> cuya clave es el nombre del
	 *         atributo del id y el valor el valor del atributo. Solamente son
	 *         tomados en cuenta los atributos cuyo valor es diferente de <code>
	 * 			null</code>
	 */
	/*
	 * TODO: actualmente el valor de la propiedad del objeto id se esta
	 * comparando con NULL para agregar al HashMap, se deberia agregar
	 * funcionalidad para compararlo con algun valor enviado como parametro si
	 * es que los atributos del objeto ID del objeto raiz de la consulta son
	 * inicializados con algun valor en el constructor
	 */

	protected <T extends Serializable> HashMap<String, Object> getIdRestrictions(
			T objetoVO, String idName, boolean addIdNameToProperty) {
		Object idObject = null;
		Object valueFieldIdObject = null;
		HashMap<String, Object> idRestrictions = null;
		Field[] atributos = null;
		final String idField = addIdNameToProperty ? idName + DOT : "";
		Object objetoRaiz = getPOJO(objetoVO);

		idObject = ClasesUtil.invocarMetodoGet(objetoRaiz, idName);

		if (idObject != null) {
			idRestrictions = new HashMap<String, Object>();
			if (isSimpleObject(idObject)) {
				idRestrictions.put(idName, idObject);
			} else {
				atributos = idObject.getClass().getDeclaredFields();
				for (Field fieldIdObject : atributos) {
					if (!Modifier.isStatic(fieldIdObject.getModifiers())) {
						valueFieldIdObject = ClasesUtil.invocarMetodoGet(
								idObject, fieldIdObject.getName());

						if (valueFieldIdObject != null) {
							idRestrictions
									.put(new StringBuilder(idField).append(
											fieldIdObject.getName()).toString(),
											valueFieldIdObject);
						}
						valueFieldIdObject = null;
					}
				}
			}
		}
		return idRestrictions;
	}

	public <T extends Serializable> HashMap<String, Object> getIdRestrictions(
			T objetoRaiz, String idName) {
		return this.getIdRestrictions(objetoRaiz, idName, true);
	}

	/**
	 * Verifica si el objeto pertenece a un tipo de dato simple
	 * 
	 * @param object
	 * @return
	 */
	protected boolean isSimpleObject(Object object) {
		return object instanceof java.lang.String
				|| object instanceof java.lang.Boolean
				|| object instanceof java.lang.Character
				|| object instanceof java.lang.Number;
	}

	/**
	 * Verifica si el objeto pertenece a un tipo de dato simple
	 * 
	 * @param object
	 * @return
	 */
	protected boolean isSimpleClass(Class<?> clazz) {
		return clazz.getName().startsWith("java.lang")
				|| clazz.getName().startsWith("[Ljava.lang");
	}

	public <T extends Serializable> void addIdRestrictions(T objetoVO,
			String nombreIdObjetoRaiz, Root<?> root, Predicate where)
			throws DAOException {
		addIdRestrictions(objetoVO, nombreIdObjetoRaiz, root, where, null);
	}

	public <T extends Serializable> Predicate addIdRestrictions(T objetoVO,
			String nombreIdObjetoRaiz, From<?, ?> root, Predicate where,
			String alias) throws DAOException {
		CriterioBusqueda criteriaSearch = getCriteriaSearchFromVO(objetoVO);
		if (criteriaSearch == null) {

			try {

				Object objetoRaiz = getPOJO(objetoVO);

				Object idObject = ClasesUtil.invocarMetodoGet(objetoRaiz,
						nombreIdObjetoRaiz);

				if (idObject != null) {
					if (isSimpleObject(idObject)) {
						where = addExpression(objetoRaiz.getClass(), root,
								where, alias, nombreIdObjetoRaiz, idObject,
								idObject.getClass(),
								ComparatorTypeEnum.EQUAL_COMPARATOR);
					} else {
						Field[] atributos = idObject.getClass()
								.getDeclaredFields();
						Path<?> idRoot = root.get(nombreIdObjetoRaiz);
						for (Field fieldIdObject : atributos) {
							if (!Modifier
									.isStatic(fieldIdObject.getModifiers())) {
								Object valueFieldIdObject = ClasesUtil
										.invocarMetodoGet(idObject,
												fieldIdObject.getName());

								if (valueFieldIdObject != null) {
									where = addExpression(
											objetoRaiz.getClass(), idRoot,
											where, alias, fieldIdObject
													.getName().toString(),
											valueFieldIdObject,
											idObject.getClass(),
											ComparatorTypeEnum.EQUAL_COMPARATOR);
								}

							}
						}
					}
				}

			} catch (PersistenceException e) {
				throw convertDAOException(e);
			} catch (Exception e) {
				throw new DAOException(e);
			}
		} else {
			where = addIdRestrictions(objetoVO, criteriaSearch,
					nombreIdObjetoRaiz, root, where, alias);
		}
		return where;

	}

	public <T extends Serializable> StringBuilder addIdRestrictions(T objetoVO,
			String aliasEntidad, String nombreIdObjetoRaiz,
			StringBuilder where, Map<String, NamedParameterQuery> params)
			throws DAOException {
		CriterioBusqueda criteriaSearch = getCriteriaSearchFromVO(objetoVO);
		if (criteriaSearch == null) {

			try {

				Object objetoRaiz = getPOJO(objetoVO);

				Object idObject = ClasesUtil.invocarMetodoGet(objetoRaiz,
						nombreIdObjetoRaiz);

				if (idObject != null) {
					if (isSimpleObject(idObject)) {
						addExpression(objetoRaiz.getClass(), aliasEntidad,
								where, params, nombreIdObjetoRaiz, idObject,
								idObject.getClass(),
								ComparatorTypeEnum.EQUAL_COMPARATOR);
					} else {
						Field[] atributos = idObject.getClass()
								.getDeclaredFields();
						for (Field fieldIdObject : atributos) {
							if (!Modifier
									.isStatic(fieldIdObject.getModifiers())) {
								Object valueFieldIdObject = ClasesUtil
										.invocarMetodoGet(idObject,
												fieldIdObject.getName());

								if (valueFieldIdObject != null) {
									addExpression(objetoRaiz.getClass(),
											aliasEntidad, where, params,
											nombreIdObjetoRaiz + DOT
													+ fieldIdObject.getName(),
											valueFieldIdObject,
											idObject.getClass(),
											ComparatorTypeEnum.EQUAL_COMPARATOR);
								}

							}
						}
					}
				}

			} catch (PersistenceException e) {
				throw convertDAOException(e);
			} catch (Exception e) {
				throw new DAOException(e);
			}
		} else {
			addIdRestrictions(objetoVO, aliasEntidad, criteriaSearch,
					aliasEntidad, nombreIdObjetoRaiz, where, params);
		}
		return where;

	}

	public <T extends Serializable> Predicate addIdRestrictions(T objetoVO,
			CriterioBusqueda criteriaSearch, String nombreIdObjetoRaiz,
			Root<?> root, Predicate where) throws DAOException {
		return addIdRestrictions(objetoVO, criteriaSearch, nombreIdObjetoRaiz,
				root, where, null);
	}

	public <T extends Serializable> Predicate addIdRestrictions(T objetoVO,
			CriterioBusqueda criteriaSearch, String nombreIdObjetoRaiz,
			From<?, ?> root, Predicate where, String alias) throws DAOException {
		return addIdRestrictions(objetoVO, criteriaSearch, null,
				nombreIdObjetoRaiz, root, where, alias);
	}

	public <T extends Serializable> Predicate addIdRestrictions(T objetoVO,
			CriterioBusqueda criteriaSearch, String nombreRelacion,
			String nombreIdObjetoRaiz, From<?, ?> root, Predicate where,
			String alias) throws DAOException {

		Object idObject = null;
		Object valueFieldIdObject = null;
		Field[] atributos = null;

		try {
			Object objetoRaiz = getPOJO(objetoVO);
			// methodIdName = new StringBuilder(GET);
			idObject = PropertyUtils
					.getProperty(objetoRaiz, nombreIdObjetoRaiz);
			// Verificamos si el campo de la clave primaria es no nulo
			if (idObject != null) {
				// si la clave primaria es un objeto simple, agregamos
				// directamente la restriccion
				if (isSimpleObject(idObject)) {
					StringBuilder pathId = new StringBuilder();
					if (nombreRelacion != null && nombreRelacion.length() > 0) {
						pathId.append(nombreRelacion).append(".");
					}
					pathId.append(nombreIdObjetoRaiz);
					ParametroCriterioBusqueda<?> criteriaParameter = criteriaSearch
							.getCriteriaSearchParameter(pathId.toString());
					// establemos la restriccion del CriteriaQuery search en el
					// campo
					// de la clave primaria
					if (criteriaParameter == null) {
						where = addExpression(objetoRaiz.getClass(), root,
								where, alias, nombreIdObjetoRaiz, idObject,
								null, ComparatorTypeEnum.EQUAL_COMPARATOR);
					} else if (criteriaParameter.getParameterValues() == null) {
						where = addExpression(objetoRaiz.getClass(), root,
								where, alias, nombreIdObjetoRaiz, idObject,
								null, criteriaParameter.getComparator(),
								criteriaParameter.isDenyComparation());
					} else {
						where = addExpression(objetoRaiz.getClass(), root,
								where, alias, nombreIdObjetoRaiz,
								criteriaParameter.getParameterValues(), null,
								criteriaParameter.getComparator(),
								criteriaParameter.isDenyComparation());
					}
				}
				// si la clave primaria es compuesta, invocamos sus campos
				else {
					atributos = idObject.getClass().getDeclaredFields();
					Path<?> idRoot = root.get(nombreIdObjetoRaiz);
					for (Field fieldIdObject : atributos) {
						try {
							// Si el campo del ID no es estatico
							if (!Modifier
									.isStatic(fieldIdObject.getModifiers())) {
								StringBuilder pathId = new StringBuilder();
								String nombreCampo = nombreIdObjetoRaiz + "."
										+ fieldIdObject.getName();
								if (nombreRelacion != null
										&& nombreRelacion.length() > 0) {
									pathId.append(nombreRelacion).append(".");
								}
								pathId.append(nombreCampo);
								// invocamos el metodo get del campo de la clave
								// primaria
								valueFieldIdObject = PropertyUtils.getProperty(
										idObject, fieldIdObject.getName());
								// idObject.getClass().getMethod(methodIdObjectName.toString()).invoke(idObject);
								// Verificamos si se ha establecido un criterio
								// en el CriteriaQuery search
								ParametroCriterioBusqueda<?> criteriaParameter = criteriaSearch
										.getCriteriaSearchParameter(pathId
												.toString());
								// si es nulo, establecemos la restriccion del
								// campo con el operador igual
								if (criteriaParameter == null
										&& valueFieldIdObject != null) {
									where = addExpression(
											objetoRaiz.getClass(), idRoot,
											where, alias,
											fieldIdObject.getName(),
											valueFieldIdObject, null,
											ComparatorTypeEnum.EQUAL_COMPARATOR);
								} else if (criteriaParameter != null) {
									// si no es nulo y no tiene establecido un
									// conjunto de valores en el CriteriaQuery
									// search
									// aplicamos el valor establecido en el
									// campo con el operador establecido en el
									// CriteriaQuery search
									if (criteriaParameter.getParameterValues() == null
											&& valueFieldIdObject != null) {
										where = addExpression(
												objetoRaiz.getClass(), idRoot,
												where, alias,
												fieldIdObject.getName(),
												valueFieldIdObject, null,
												criteriaParameter
														.getComparator());
									}
									// si existe un valor establecido en el
									// CriteriaQuery search, aplicamos el valor
									// y el
									// operador establecimo
									// en este sobre el campo de la clave
									// primaria
									else {
										where = addExpression(
												objetoRaiz.getClass(), idRoot,
												where, alias,
												fieldIdObject.getName(),
												criteriaParameter
														.getParameterValues(),
												null,
												criteriaParameter
														.getComparator());
									}
								}
							}
						} catch (IllegalArgumentException e) {
							LogGenerico.getLogger().info("IllegalArgumentException");
						} catch (SecurityException e) {
							LogGenerico.getLogger().info("SecurityException");
						} catch (IllegalAccessException e) {
							LogGenerico.getLogger().info("IllegalAccessException");
						} catch (InvocationTargetException e) {
							LogGenerico.getLogger().info("InvocationTargetException");
						} catch (NoSuchMethodException e) {
							LogGenerico.getLogger().info(
									"No existe el acceso para la propiedad:{}",
									fieldIdObject.getName());
						}

					}

				}
			}
			// si es nulo, puede ser que se trate de una clave primaria simple y
			// que la restriccion
			// venga establecido en el CriteriaQuery search
			else {
				ParametroCriterioBusqueda<?> criteriaParameter = criteriaSearch
						.getCriteriaSearchParameter(nombreIdObjetoRaiz);
				if (criteriaParameter != null
						&& criteriaParameter.getParameterValues() != null) {
					where = addExpression(objetoRaiz.getClass(), root, where,
							alias, nombreIdObjetoRaiz,
							criteriaParameter.getParameterValues(), null,
							criteriaParameter.getComparator());

				}
			}
		} catch (SecurityException e) {
			LogGenerico.getLogger().info("SecurityException");
		} catch (IllegalArgumentException e) {
			LogGenerico.getLogger().info("IllegalArgumentException");
		} catch (IllegalAccessException e) {
			LogGenerico.getLogger().info("IllegalAccessException");
		} catch (InvocationTargetException e) {
			LogGenerico.getLogger().info("InvocationTargetException");
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return where;
	}

	public <T extends Serializable> StringBuilder addIdRestrictions(T objetoVO,
			String aliasEntidad, CriterioBusqueda criteriaSearch,
			String nombreRelacion, String nombreIdObjetoRaiz,
			StringBuilder where, Map<String, NamedParameterQuery> params)
			throws DAOException {

		Object idObject = null;
		Object valueFieldIdObject = null;
		Field[] atributos = null;
		// StringBuilder methodIdName = null;
		// StringBuilder methodIdObjectName = null;
		// final String GET = "get";

		try {
			Object objetoRaiz = getPOJO(objetoVO);
			// methodIdName = new StringBuilder(GET);
			idObject = PropertyUtils
					.getProperty(objetoRaiz, nombreIdObjetoRaiz);
			// objetoRaiz.getClass().getMethod(methodIdName.append(nombreIdObjetoRaiz.substring(0,
			// 1).toUpperCase()).append(nombreIdObjetoRaiz.substring(1)).toString()).invoke(objetoRaiz);
			// Verificamos si el campo de la clave primaria es no nulo
			if (idObject != null) {
				// si la clave primaria es un objeto simple, agregamos
				// directamente la restriccion
				if (isSimpleObject(idObject)) {
					StringBuilder pathId = new StringBuilder();
					if (nombreRelacion != null && nombreRelacion.length() > 0) {
						pathId.append(nombreRelacion).append(DOT);
					}
					pathId.append(nombreIdObjetoRaiz);
					ParametroCriterioBusqueda<?> criteriaParameter = criteriaSearch
							.getCriteriaSearchParameter(pathId.toString());
					// establemos la restriccion del CriteriaQuery search en el
					// campo
					// de la clave primaria
					if (criteriaParameter == null) {
						// qbcSelect.add(eq(new
						// StringBuilder().append(nombreIdObjetoRaiz).toString(),
						// idObject));
						addExpression(objetoRaiz.getClass(), aliasEntidad,
								where, params, nombreIdObjetoRaiz, idObject,
								null, ComparatorTypeEnum.EQUAL_COMPARATOR);
					} else if (criteriaParameter.getParameterValues() == null) {
						where = addExpression(objetoRaiz.getClass(),
								aliasEntidad, where, params,
								nombreIdObjetoRaiz, idObject, null,
								criteriaParameter.getComparator(),
								criteriaParameter.isDenyComparation());
					} else {
						where = addExpression(objetoRaiz.getClass(),
								aliasEntidad, where, params,
								nombreIdObjetoRaiz,
								criteriaParameter.getParameterValues(), null,
								criteriaParameter.getComparator(),
								criteriaParameter.isDenyComparation());
					}
				}
				// si la clave primaria es compuesta, invocamos sus campos
				else {
					atributos = idObject.getClass().getDeclaredFields();
					for (Field fieldIdObject : atributos) {
						// methodIdObjectName = new
						// StringBuilder(GET).append(fieldIdObject.getName().substring(0,
						// 1).toUpperCase()).append(fieldIdObject.getName().substring(1));
						try {
							// Si el campo del ID no es estatico
							if (!Modifier
									.isStatic(fieldIdObject.getModifiers())) {
								StringBuilder pathId = new StringBuilder();
								String nombreCampo = nombreIdObjetoRaiz + DOT
										+ fieldIdObject.getName();
								if (nombreRelacion != null
										&& nombreRelacion.length() > 0) {
									pathId.append(nombreRelacion).append(DOT);
								}
								pathId.append(nombreCampo);
								// invocamos el metodo get del campo de la clave
								// primaria
								valueFieldIdObject = PropertyUtils.getProperty(
										idObject, fieldIdObject.getName());
								// idObject.getClass().getMethod(methodIdObjectName.toString()).invoke(idObject);
								// Verificamos si se ha establecido un criterio
								// en el CriteriaQuery search

								ParametroCriterioBusqueda<?> criteriaParameter = criteriaSearch
										.getCriteriaSearchParameter(pathId
												.toString());
								// si es nulo, establecemos la restriccion del
								// campo con el operador igual
								if (criteriaParameter == null
										&& valueFieldIdObject != null) {
									// qbcSelect.add(eq("_root."+nombreCampo,
									// valueFieldIdObject));
									where = addExpression(
											objetoRaiz.getClass(),
											aliasEntidad, where, params,
											nombreCampo, valueFieldIdObject,
											null,
											ComparatorTypeEnum.EQUAL_COMPARATOR);
								} else if (criteriaParameter != null) {
									// si no es nulo y no tiene establecido un
									// conjunto de valores en el CriteriaQuery
									// search
									// aplicamos el valor establecido en el
									// campo con el operador establecido en el
									// CriteriaQuery search
									if (criteriaParameter.getParameterValues() == null
											&& valueFieldIdObject != null) {
										where = addExpression(
												objetoRaiz.getClass(),
												aliasEntidad, where, params,
												nombreCampo,
												valueFieldIdObject, null,
												criteriaParameter
														.getComparator());
									}
									// si existe un valor establecido en el
									// CriteriaQuery search, aplicamos el valor
									// y el
									// operador establecimo
									// en este sobre el campo de la clave
									// primaria
									else {
										where = addExpression(
												objetoRaiz.getClass(),
												aliasEntidad, where, params,
												nombreCampo,
												criteriaParameter
														.getParameterValues(),
												null,
												criteriaParameter
														.getComparator());
									}
								}
							}
						} catch (IllegalArgumentException e) {
							LogGenerico.getLogger().info("IllegalArgumentException");
						} catch (SecurityException e) {
							LogGenerico.getLogger().info("SecurityException");
						} catch (IllegalAccessException e) {
							LogGenerico.getLogger().info("IllegalAccessException");
						} catch (InvocationTargetException e) {
							LogGenerico.getLogger().info("InvocationTargetException");
						} catch (NoSuchMethodException e) {
							LogGenerico.getLogger().info(
									"No existe el acceso para la propiedad:{}",
									fieldIdObject.getName());
						}

					}

				}
			}
			// si es nulo, puede ser que se trate de una clave primaria simple y
			// que la restriccion
			// venga establecido en el CriteriaQuery search
			else {
				ParametroCriterioBusqueda<?> criteriaParameter = criteriaSearch
						.getCriteriaSearchParameter(nombreIdObjetoRaiz);
				if (criteriaParameter != null
						&& criteriaParameter.getParameterValues() != null) {
					where = addExpression(objetoRaiz.getClass(), aliasEntidad,
							where, params, nombreIdObjetoRaiz,
							criteriaParameter.getParameterValues(), null,
							criteriaParameter.getComparator());

				}
			}
		} catch (SecurityException e) {
			LogGenerico.getLogger().info("SecurityException");
		} catch (IllegalArgumentException e) {
			LogGenerico.getLogger().info("IllegalArgumentException");
		} catch (IllegalAccessException e) {
			LogGenerico.getLogger().info("IllegalAccessException");
		} catch (InvocationTargetException e) {
			LogGenerico.getLogger().info("InvocationTargetException");
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return where;
	}

	/**
	 * Agrega restricciones que est&aacute;n localizados en campos no
	 * persistentes
	 * 
	 * @param <T>
	 *            Tipo de objeto de la plantilla de b&uacute;squeda o una
	 *            relaci&oacute;n del mismo
	 * @param objetoVO
	 *            Objeto plantilla de b&uacute;squeda
	 * @param qbcSelect
	 *            Objeto para realizar los criterios de b&uacute;squeda
	 * @param campo
	 *            Campo al que se debe agregar las restricciones de consulta
	 * @throws DAOException
	 */
	protected <T> Predicate addRestrictionFromTransientFields(T objetoVO,
			From<?, ?> root, Predicate where) throws DAOException {
		Class<?> classPojo = objetoVO.getClass();
		if (objetoVO instanceof BuscadorDTO) {
			Object pojo = ((BuscadorDTO) objetoVO).getPOJO();
			classPojo = pojo.getClass();
			if (objetoVO != pojo) {
				Collection<Field> camposNoPersistentesVO = transientFieldsMap
						.get(objetoVO.getClass());
				if (camposNoPersistentesVO != null) {
					for (Field campo : camposNoPersistentesVO) {
						if (!campo
								.isAnnotationPresent(NotPersistentField.class)) {
							where = addRestrictionFromTransientFields(objetoVO,
									root, where, campo);
						}
					}
				}
			}
		} else {
			classPojo = objetoVO.getClass();
		}
		Collection<Field> camposNoPersistentes = transientFieldsMap
				.get(classPojo);
		if (camposNoPersistentes != null) {
			for (Field campo : camposNoPersistentes) {
				if (!campo.isAnnotationPresent(NotPersistentField.class)) {
					where = addRestrictionFromTransientFields(objetoVO, root,
							where, campo);
				}
			}
		}
		return where;
	}

	protected <T> void addRestrictionFromTransientFields(T objetoVO,
			String aliasEntidad, StringBuilder where,
			Map<String, NamedParameterQuery> params) throws DAOException {
		Class<?> classPojo = objetoVO.getClass();
		if (objetoVO instanceof BuscadorDTO) {
			Object pojo = ((BuscadorDTO) objetoVO).getPOJO();
			classPojo = pojo.getClass();
			if (objetoVO != pojo) {
				Collection<Field> camposNoPersistentesVO = transientFieldsMap
						.get(objetoVO.getClass());
				if (camposNoPersistentesVO != null) {
					for (Field campo : camposNoPersistentesVO) {
						if (!campo
								.isAnnotationPresent(NotPersistentField.class)) {
							addRestrictionFromTransientFields(objetoVO,
									aliasEntidad, where, params, campo);
						}
					}
				}
			}
		} else {
			classPojo = objetoVO.getClass();
		}
		Collection<Field> camposNoPersistentes = transientFieldsMap
				.get(classPojo);
		if (camposNoPersistentes != null) {
			for (Field campo : camposNoPersistentes) {
				if (!campo.isAnnotationPresent(NotPersistentField.class)) {
					addRestrictionFromTransientFields(objetoVO, aliasEntidad,
							where, params, campo);
				}
			}
		}
	}

	/**
	 * Agrega restricciones que est&aacute;n localizados en campos no
	 * persistentes
	 * 
	 * @param <T>
	 *            Tipo de objeto de la plantilla de b&uacute;squeda o una
	 *            relaci&oacute;n del mismo
	 * @param objetoVO
	 *            Objeto plantilla de b&uacute;squeda
	 * @param qbcSelect
	 *            Objeto para realizar los criterios de b&uacute;squeda
	 * @param campo
	 *            Campo al que se debe agregar las restricciones de consulta
	 * @throws DAOException
	 */
	private <T> Predicate addRestrictionFromTransientFields(T objetoVO,
			From<?, ?> root, Predicate where, Field campo) throws DAOException {
		Class<?> classPojo = getPOJO(objetoVO).getClass();
		if (campo.isAnnotationPresent(INRestriction.class)) {
			INRestriction inRestriction = campo
					.getAnnotation(INRestriction.class);
			addExpression(classPojo, root, where, inRestriction.field(),
					ClasesUtil.invocarMetodoGet(objetoVO, campo.getName()),
					campo.getType(), ComparatorTypeEnum.IN_COMPARATOR);
		} else if (campo.isAnnotationPresent(NotInRestriction.class)) {
			NotInRestriction inRestriction = campo
					.getAnnotation(NotInRestriction.class);
			addExpression(classPojo, root, where, inRestriction.field(),
					ClasesUtil.invocarMetodoGet(objetoVO, campo.getName()),
					campo.getType(), ComparatorTypeEnum.NOT_IN_COMPARATOR);
		} else if (campo.isAnnotationPresent(BetweenIncludeRestriction.class)) {
			String fieldName = campo.getAnnotation(
					BetweenIncludeRestriction.class).field();
			addExpression(classPojo, root, where, fieldName,
					ClasesUtil.invocarMetodoGet(objetoVO, campo.getName()),
					campo.getType(),
					ComparatorTypeEnum.BETWEEN_INCLUDE_COMPARATOR);
		} else if (campo.isAnnotationPresent(DisjunctionField.class)) {
			String fieldName = campo.getAnnotation(DisjunctionField.class)
					.field();
			addExpression(classPojo, root, where, fieldName,
					ClasesUtil.invocarMetodoGet(objetoVO, campo.getName()),
					campo.getType(), campo
							.getAnnotation(DisjunctionField.class)
							.comparatorType());
		} else if (DefinirRestriccion.class.isAssignableFrom(campo.getType())) {
			DefinirRestriccion restriction = (DefinirRestriccion) ClasesUtil
					.invocarMetodoGet(objetoVO, campo.getName());
			if (restriction != null) {
				// TODO: AGREGAR RESTRICCIONES EN FORMATO JPA 2
				// restriction.setAlias(qbcSelect.getAlias()+ALIAS_CONCATENATOR);
				// qbcSelect.add(restriction.getCriteriaRestriction());
			}
		}
		return where;
	}

	private <T> void addRestrictionFromTransientFields(T objetoVO,
			String aliasEntidad, StringBuilder where,
			Map<String, NamedParameterQuery> params, Field campo)
			throws DAOException {
		Class<?> classPojo = getPOJO(objetoVO).getClass();
		if (campo.isAnnotationPresent(INRestriction.class)) {
			INRestriction inRestriction = campo
					.getAnnotation(INRestriction.class);
			addExpression(classPojo, aliasEntidad, where, params,
					inRestriction.field(),
					ClasesUtil.invocarMetodoGet(objetoVO, campo.getName()),
					campo.getType(), ComparatorTypeEnum.IN_COMPARATOR);
		} else if (campo.isAnnotationPresent(NotInRestriction.class)) {
			NotInRestriction inRestriction = campo
					.getAnnotation(NotInRestriction.class);
			addExpression(classPojo, aliasEntidad, where, params,
					inRestriction.field(),
					ClasesUtil.invocarMetodoGet(objetoVO, campo.getName()),
					campo.getType(), ComparatorTypeEnum.NOT_IN_COMPARATOR);
		} else if (campo.isAnnotationPresent(BetweenIncludeRestriction.class)) {
			String fieldName = campo.getAnnotation(
					BetweenIncludeRestriction.class).field();
			addExpression(classPojo, aliasEntidad, where, params, fieldName,
					ClasesUtil.invocarMetodoGet(objetoVO, campo.getName()),
					campo.getType(),
					ComparatorTypeEnum.BETWEEN_INCLUDE_COMPARATOR);
		} else if (campo.isAnnotationPresent(DisjunctionField.class)) {
			String fieldName = campo.getAnnotation(DisjunctionField.class)
					.field();
			addExpression(classPojo, aliasEntidad, where, params, fieldName,
					ClasesUtil.invocarMetodoGet(objetoVO, campo.getName()),
					campo.getType(), campo
							.getAnnotation(DisjunctionField.class)
							.comparatorType());
		} else if (DefinirRestriccion.class.isAssignableFrom(campo.getType())) {
			DefinirRestriccion definedRestriction = (DefinirRestriccion) ClasesUtil
					.invocarMetodoGet(objetoVO, campo.getName());
			if (definedRestriction != null) {
				StringBuilder restrictionBuilder = new StringBuilder(
						definedRestriction.getRestriction());
				String[] alias = new String[definedRestriction
						.getFieldsInRestriction().length];
				String[] fields = definedRestriction.getFieldsInRestriction();
				for (int i = 0; i < fields.length; i++) {
					alias[i] = propertyNameToAlias(classPojo, fields[i]);
				}
				String restriction = StringUtils.replaceEach(
						restrictionBuilder.toString(), fields, alias);
				if (where.length() > 0) {
					where.append(" and");
				}
				where.append(" (").append(restriction).append(")");
				if (definedRestriction.getParameters() != null) {
					for (NamedParameterQuery param : definedRestriction
							.getParameters()) {
						params.put(param.getName(), param);
					}
				}
			}
		}
	}

	public <T extends Serializable> Predicate addExampleRestrictions(
			T objetoVO, From<?, ?> root, Predicate where,
			CriterioBusqueda criteriaSearch, StringBuilder criteriaPath,
			boolean enableLike, String... propiedadesAexcluir)
			throws DAOException {
		return addExampleRestrictions(objetoVO, root, where, null,
				criteriaSearch, criteriaPath, enableLike, propiedadesAexcluir);
	}

	public <T extends Serializable> Predicate addExampleRestrictions(
			T objetoVO, From<?, ?> root, Predicate where, boolean enableLike,
			String... propiedadesAexcluir) throws DAOException {
		return addExampleRestrictions(objetoVO, root, where, null, enableLike,
				propiedadesAexcluir);
	}

	public <T extends Serializable> Predicate addExampleRestrictions(
			T objetoVO, From<?, ?> root, Predicate where, String alias,
			boolean enableLike, String... propiedadesAexcluir)
			throws DAOException {

		CriterioBusqueda criteriaSearch = getCriteriaSearchFromVO(objetoVO);
		if (criteriaSearch == null) {
			Serializable pojo = getPOJO(objetoVO);
			Class<?> classPojo = pojo.getClass();

			generateFieldsByVO(pojo);

			Set<Field> camposMapeadosSimples = allFieldsMapped.get(classPojo);

			BuscadorDTO searchDTO = objetoVO instanceof BuscadorDTO ? (BuscadorDTO) objetoVO
					: null;

			// eliminar las propiedades que se van a excluir
			Set<String> excludedPropertySet = new LinkedHashSet<String>();
			if (propiedadesAexcluir != null && propiedadesAexcluir.length > 0) {
				excludedPropertySet = new LinkedHashSet<String>(
						propiedadesAexcluir.length);
				excludedPropertySet.addAll(Arrays.asList(propiedadesAexcluir));
			}

			try {
				EntityType<?> classMetadata = entityManager
						.getEntityManagerFactory().getMetamodel()
						.entity(classPojo);

				// Agregar restricciones a la clave primaria
				where = addIdRestrictions(
						pojo,
						classMetadata.getId(
								classMetadata.getIdType().getJavaType())
								.getName(), root, where, alias);

				// Agregar restricciones de campos persistentes que no son
				// asociaciones con otras clases
				if (camposMapeadosSimples != null) {
					for (Field campo : camposMapeadosSimples) {
						if (!excludedPropertySet.contains(campo.getName())
								|| !campo
										.isAnnotationPresent(NotPersistentField.class)) {
							if (campo
									.isAnnotationPresent(ComparatorTypeField.class)) {
								ComparatorTypeField comparatorType = campo
										.getAnnotation(ComparatorTypeField.class);
								where = addExpression(
										classPojo,
										root,
										where,
										alias,
										campo.getName(),
										ClasesUtil.invocarMetodoGet(pojo,
												campo.getName()),
										campo.getType(),
										comparatorType.comparatorType());
							}
							// TODO: Eliminar esta condicion del ForeignField
							// porque se reemplaza su funcionalidad
							// con ComparatorTypeField
							else if (campo
									.isAnnotationPresent(ForeignField.class)) {
								ForeignField foreignField = campo
										.getAnnotation(ForeignField.class);
								if (foreignField.enableLike()) {
									where = addExpression(
											classPojo,
											root,
											where,
											alias,
											campo.getName(),
											ClasesUtil.invocarMetodoGet(pojo,
													campo.getName()),
											campo.getType(),
											ComparatorTypeEnum.LIKE_ANYWHERE_COMPARATOR);
								} else {
									where = addExpression(classPojo, root,
											where, alias, campo.getName(),
											ClasesUtil.invocarMetodoGet(pojo,
													campo.getName()),
											campo.getType(),
											ComparatorTypeEnum.EQUAL_COMPARATOR);
								}
							}

							// restriccion is null
							else if (searchDTO != null
									&& searchDTO.getIsNull(campo.getName())) {
								where = addExpression(classPojo, root, where,
										alias, campo.getName(), null,
										campo.getType(),
										ComparatorTypeEnum.IS_NULL);
							}
							/*
							 * Diferentes tipos de restricciones. TODO:
							 * Actualmente si un mismo campo tiene diferentes
							 * tipos de restriccciones, se le aplica unicamente
							 * la ultima. Se debe validar cuando un campo puede
							 * aceptar diferentes tipos de restricciones en una
							 * sentencia o no validar para que SQL lo resuelva.
							 */
							else if (searchDTO != null
									&& searchDTO.getIsGreaterThan(campo
											.getName()) != null) {
								where = addExpression(
										classPojo,
										root,
										where,
										alias,
										campo.getName(),
										searchDTO.getIsGreaterThan(campo
												.getName()),
										campo.getType(),
										ComparatorTypeEnum.GREATER_THAN_COMPARATOR);
							} else if (enableLike
									&& campo.getType().equals(String.class)) {
								where = addExpression(
										classPojo,
										root,
										where,
										alias,
										campo.getName(),
										ClasesUtil.invocarMetodoGet(pojo,
												campo.getName()),
										campo.getType(),
										ComparatorTypeEnum.LIKE_ANYWHERE_COMPARATOR);
							} else {
								where = addExpression(
										classPojo,
										root,
										where,
										alias,
										campo.getName(),
										ClasesUtil.invocarMetodoGet(pojo,
												campo.getName()),
										campo.getType(),
										ComparatorTypeEnum.EQUAL_COMPARATOR);
							}
						}

					}
				}
				// Agregar restricciones de campos no persistentes
				where = addRestrictionFromTransientFields(objetoVO, root, where);
			} catch (PersistenceException e) {
				throw convertDAOException(e);
			} catch (Exception e) {
				throw new DAOException(e);
			}

		} else {
			where = addExampleRestrictions(objetoVO, root, where,
					criteriaSearch, new StringBuilder(""), enableLike,
					propiedadesAexcluir);
		}
		return where;

	}

	public <T extends Serializable> void addExampleRestrictions(T objetoVO,
			String aliasEntidad, StringBuilder where,
			Map<String, NamedParameterQuery> params, Boolean enableLike,
			String... excludedProperties) throws DAOException {

		CriterioBusqueda criteriaSearch = getCriteriaSearchFromVO(objetoVO);
		if (criteriaSearch == null) {
			Serializable pojo = getPOJO(objetoVO);
			Class<?> classPojo = pojo.getClass();

			generateFieldsByVO(pojo);

			Set<Field> camposMapeadosSimples = allFieldsMapped.get(classPojo);

			BuscadorDTO searchDTO = objetoVO instanceof BuscadorDTO ? (BuscadorDTO) objetoVO
					: null;

			// eliminar las propiedades que se van a excluir
			Set<String> excludedPropertySet = new LinkedHashSet<String>();

			try {
				EntityType<?> classMetadata = entityManager
						.getEntityManagerFactory().getMetamodel()
						.entity(classPojo);
				String idName = classMetadata.getId(
						classMetadata.getIdType().getJavaType()).getName();
				// Agregar restricciones a la clave primaria
				addIdRestrictions(pojo, aliasEntidad, idName, where, params);

				// Agregar restricciones de campos persistentes que no son
				// asociaciones con otras clases
				if (camposMapeadosSimples != null) {
					for (Field campo : camposMapeadosSimples) {
						if (!excludedPropertySet.contains(campo.getName())
								|| !campo
										.isAnnotationPresent(NotPersistentField.class)) {
							if (campo
									.isAnnotationPresent(ComparatorTypeField.class)) {
								ComparatorTypeField comparatorType = campo
										.getAnnotation(ComparatorTypeField.class);
								where = addExpression(
										classPojo,
										aliasEntidad,
										where,
										params,
										campo.getName(),
										ClasesUtil.invocarMetodoGet(pojo,
												campo.getName()),
										campo.getType(),
										comparatorType.comparatorType());
							}
							// TODO: Eliminar esta condicion del ForeignField
							// porque se reemplaza su funcionalidad
							// con ComparatorTypeField
							else if (campo
									.isAnnotationPresent(ForeignField.class)) {
								ForeignField foreignField = campo
										.getAnnotation(ForeignField.class);
								if (foreignField.enableLike()) {
									where = addExpression(
											classPojo,
											aliasEntidad,
											where,
											params,
											campo.getName(),
											ClasesUtil.invocarMetodoGet(pojo,
													campo.getName()),
											campo.getType(),
											ComparatorTypeEnum.LIKE_ANYWHERE_COMPARATOR);
								} else {
									where = addExpression(classPojo,
											aliasEntidad, where, params,
											campo.getName(),
											ClasesUtil.invocarMetodoGet(pojo,
													campo.getName()),
											campo.getType(),
											ComparatorTypeEnum.EQUAL_COMPARATOR);
								}
							}

							// restriccion is null
							else if (searchDTO != null
									&& searchDTO.getIsNull(campo.getName())) {
								where = addExpression(classPojo, aliasEntidad,
										where, params, campo.getName(), null,
										campo.getType(),
										ComparatorTypeEnum.IS_NULL);
							}
							/*
							 * Diferentes tipos de restricciones. TODO:
							 * Actualmente si un mismo campo tiene diferentes
							 * tipos de restriccciones, se le aplica unicamente
							 * la ultima. Se debe validar cuando un campo puede
							 * aceptar diferentes tipos de restricciones en una
							 * sentencia o no validar para que SQL lo resuelva.
							 */
							else if (searchDTO != null
									&& searchDTO.getIsGreaterThan(campo
											.getName()) != null) {
								where = addExpression(
										classPojo,
										aliasEntidad,
										where,
										params,
										campo.getName(),
										searchDTO.getIsGreaterThan(campo
												.getName()),
										campo.getType(),
										ComparatorTypeEnum.GREATER_THAN_COMPARATOR);
							} else if (enableLike
									&& campo.getType().equals(String.class)) {
								where = addExpression(
										classPojo,
										aliasEntidad,
										where,
										params,
										campo.getName(),
										ClasesUtil.invocarMetodoGet(pojo,
												campo.getName()),
										campo.getType(),
										ComparatorTypeEnum.LIKE_ANYWHERE_COMPARATOR);
							} else if (!campo.getName().equals(idName)) {
								where = addExpression(
										classPojo,
										aliasEntidad,
										where,
										params,
										campo.getName(),
										ClasesUtil.invocarMetodoGet(pojo,
												campo.getName()),
										campo.getType(),
										ComparatorTypeEnum.EQUAL_COMPARATOR);
							}
						}

					}
				}
				// Agregar restricciones de campos no persistentes
				addRestrictionFromTransientFields(objetoVO, aliasEntidad,
						where, params);
			} catch (PersistenceException e) {
				throw convertDAOException(e);
			} catch (Exception e) {
				throw new DAOException(e);
			}

		} else {
			addExampleRestrictions(objetoVO, aliasEntidad, where, params,
					criteriaSearch, new StringBuilder(""), enableLike);
		}

	}

	public <T extends Serializable> Predicate addExampleRestrictions(
			T objetoVO, From<?, ?> root, Predicate where, String alias,
			CriterioBusqueda criteriaSearch, StringBuilder criteriaPath,
			boolean enableLike, String... propiedadesAexcluir)
			throws DAOException {
		Boolean enableProjections = Boolean.FALSE;
		return addExampleRestrictions(objetoVO, root, where, alias,
				criteriaSearch, criteriaPath, enableLike, enableProjections,
				propiedadesAexcluir);
	}

	/**
	 * Agrega una expresi&oacute;n de b&uacute;squeda al objeto CriteriaQuery
	 * actual a partir de
	 * 
	 * @param <T>
	 * @param objetoRaiz
	 * @param qbcSelect
	 * @param criteriaSearch
	 * @param criteriaPath
	 * @param enableLike
	 * @param propiedadesAexcluir
	 * @throws DAOException
	 */
	private <T extends Serializable> Predicate addExampleRestrictions(
			T objetoVO, From<?, ?> root, Predicate where, String alias,
			CriterioBusqueda criteriaSearch, StringBuilder criteriaPath,
			boolean enableLike, boolean enableProjections,
			String... propiedadesAexcluir) throws DAOException {

		// eliminar las propiedades que se van a excluir
		Set<String> excludedPropertySet = new LinkedHashSet<String>();
		if (propiedadesAexcluir != null && propiedadesAexcluir.length > 0) {
			excludedPropertySet.addAll(Arrays.asList(propiedadesAexcluir));
		}

		Serializable pojo = getPOJO(objetoVO);
		Class<?> classPojo = pojo.getClass();
		generateFieldsByVO(pojo);
		Set<Field> camposMapeadosSimples = allFieldsMapped.get(classPojo);

		try {

			EntityType<?> classMetadata = entityManager
					.getEntityManagerFactory().getMetamodel().entity(classPojo);
			String nombreRelacion = null;
			if (criteriaPath != null && criteriaPath.length() > 0) {
				nombreRelacion = criteriaPath.toString();
				criteriaPath.append(".");
			}
			BuscadorDTO searchDTO = objetoVO instanceof BuscadorDTO ? (BuscadorDTO) objetoVO
					: null;

			// Agregar restricciones a la clave primaria

			where = addIdRestrictions(pojo, criteriaSearch, nombreRelacion,
					classMetadata
							.getId(classMetadata.getIdType().getJavaType())
							.getName(), root, where, alias);

			// Agregar restricciones de campos persistentes que no son
			// asociaciones con otras clases
			if (camposMapeadosSimples != null) {
				for (Field campo : camposMapeadosSimples) {
					if (!excludedPropertySet.contains(campo.getName())
							|| !campo
									.isAnnotationPresent(NotPersistentField.class)) {
						StringBuilder parameterPattern;
						if (criteriaPath != null && criteriaPath.length() > 0) {
							parameterPattern = new StringBuilder(
									criteriaPath.toString()).append(campo
									.getName());
						} else {
							parameterPattern = new StringBuilder(
									campo.getName());
						}

						ParametroCriterioBusqueda<?> criteriaSearchParameter = criteriaSearch
								.getCriteriaSearchParameter(parameterPattern
										.toString());

						if (criteriaSearchParameter != null) {
							if (criteriaSearchParameter.getParameterValues() == null) {
								addExpression(
										pojo.getClass(),
										root,
										where,
										alias,
										campo.getName(),
										ClasesUtil.invocarMetodoGet(pojo,
												campo.getName()),
										campo.getType(),
										criteriaSearchParameter.getComparator(),
										criteriaSearchParameter
												.isDenyComparation());
							} else {
								addExpression(
										pojo.getClass(),
										root,
										where,
										alias,
										campo.getName(),
										criteriaSearchParameter
												.getParameterValues(),
										campo.getType(),
										criteriaSearchParameter.getComparator(),
										criteriaSearchParameter
												.isDenyComparation());
							}
						} else if (campo
								.isAnnotationPresent(ComparatorTypeField.class)) {
							ComparatorTypeField comparatorType = campo
									.getAnnotation(ComparatorTypeField.class);
							addExpression(
									pojo.getClass(),
									root,
									where,
									alias,
									campo.getName(),
									ClasesUtil.invocarMetodoGet(pojo,
											campo.getName()), campo.getType(),
									comparatorType.comparatorType());
						}
						// restriccion is null
						else if (searchDTO != null
								&& searchDTO.getIsNull(campo.getName())) {
							addExpression(pojo.getClass(), root, where, alias,
									campo.getName(), null, campo.getType(),
									ComparatorTypeEnum.IS_NULL);
						}
						/*
						 * Diferentes tipos de restricciones. TODO: Actualmente
						 * si un mismo campo tiene diferentes tipos de
						 * restriccciones, se le aplica unicamente la ultima. Se
						 * debe validar cuando un campo puede aceptar diferentes
						 * tipos de restricciones en una sentencia o no validar
						 * para que SQL lo resuelva.
						 */
						else if (searchDTO != null
								&& searchDTO.getIsGreaterThan(campo.getName()) != null) {
							addExpression(
									pojo.getClass(),
									root,
									where,
									alias,
									campo.getName(),
									searchDTO.getIsGreaterThan(campo.getName()),
									campo.getType(),
									ComparatorTypeEnum.GREATER_THAN_COMPARATOR);
						} else if (enableLike
								&& campo.getType().equals(String.class)) {
							addExpression(
									pojo.getClass(),
									root,
									where,
									alias,
									campo.getName(),
									ClasesUtil.invocarMetodoGet(pojo,
											campo.getName()), campo.getType(),
									ComparatorTypeEnum.LIKE_ANYWHERE_COMPARATOR);
						} else {
							addExpression(
									pojo.getClass(),
									root,
									where,
									alias,
									campo.getName(),
									ClasesUtil.invocarMetodoGet(pojo,
											campo.getName()), campo.getType(),
									ComparatorTypeEnum.EQUAL_COMPARATOR);
						}
					}
				}
			}

			// Agregar restricciones de campos no persistentes
			addRestrictionFromTransientFields(objetoVO, root, where);

			return where;
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Agrega una expresi&oacute;n de b&uacute;squeda al objeto CriteriaQuery
	 * actual a partir de
	 * 
	 * @param <T>
	 * @param objetoRaiz
	 * @param qbcSelect
	 * @param criteriaSearch
	 * @param criteriaPath
	 * @param enableLike
	 * @param propiedadesAexcluir
	 * @throws DAOException
	 */
	private <T extends Serializable> void addExampleRestrictions(T objetoVO,
			String aliasEntidad, StringBuilder where,
			Map<String, NamedParameterQuery> params,
			CriterioBusqueda criteriaSearch, StringBuilder criteriaPath,
			boolean enableLike, String... propiedadesAexcluir)
			throws DAOException {

		// eliminar las propiedades que se van a excluir
		Set<String> excludedPropertySet = new LinkedHashSet<String>();
		if (propiedadesAexcluir != null && propiedadesAexcluir.length > 0) {
			excludedPropertySet.addAll(Arrays.asList(propiedadesAexcluir));
		}

		Serializable pojo = getPOJO(objetoVO);
		Class<?> classPojo = pojo.getClass();
		generateFieldsByVO(pojo);
		Set<Field> camposMapeadosSimples = allFieldsMapped.get(classPojo);

		try {

			EntityType<?> classMetadata = entityManager
					.getEntityManagerFactory().getMetamodel().entity(classPojo);
			String nombreRelacion = null;
			if (criteriaPath != null && criteriaPath.length() > 0) {
				nombreRelacion = criteriaPath.toString();
				criteriaPath.append(".");
			}
			BuscadorDTO searchDTO = objetoVO instanceof BuscadorDTO ? (BuscadorDTO) objetoVO
					: null;

			// Agregar restricciones a la clave primaria
			String idName = classMetadata.getId(
					classMetadata.getIdType().getJavaType()).getName();
			where = addIdRestrictions(pojo, aliasEntidad, criteriaSearch,
					nombreRelacion, idName, where, params);

			// Agregar restricciones de campos persistentes que no son
			// asociaciones con otras clases
			if (camposMapeadosSimples != null) {
				for (Field campo : camposMapeadosSimples) {
					if (!excludedPropertySet.contains(campo.getName())
							|| !campo
									.isAnnotationPresent(NotPersistentField.class)) {
						StringBuilder parameterPattern;
						if (criteriaPath != null && criteriaPath.length() > 0) {
							parameterPattern = new StringBuilder(
									criteriaPath.toString()).append(campo
									.getName());
						} else {
							parameterPattern = new StringBuilder(
									campo.getName());
						}

						ParametroCriterioBusqueda<?> criteriaSearchParameter = criteriaSearch
								.getCriteriaSearchParameter(parameterPattern
										.toString());

						if (criteriaSearchParameter != null) {
							if (criteriaSearchParameter.getParameterValues() == null) {
								addExpression(
										pojo.getClass(),
										aliasEntidad,
										where,
										params,
										campo.getName(),
										ClasesUtil.invocarMetodoGet(pojo,
												campo.getName()),
										campo.getType(),
										criteriaSearchParameter.getComparator(),
										criteriaSearchParameter
												.isDenyComparation());
							} else {
								addExpression(
										pojo.getClass(),
										aliasEntidad,
										where,
										params,
										campo.getName(),
										criteriaSearchParameter
												.getParameterValues(),
										campo.getType(),
										criteriaSearchParameter.getComparator(),
										criteriaSearchParameter
												.isDenyComparation());
							}
						} else if (campo
								.isAnnotationPresent(ComparatorTypeField.class)) {
							ComparatorTypeField comparatorType = campo
									.getAnnotation(ComparatorTypeField.class);
							addExpression(
									pojo.getClass(),
									aliasEntidad,
									where,
									params,
									campo.getName(),
									ClasesUtil.invocarMetodoGet(pojo,
											campo.getName()), campo.getType(),
									comparatorType.comparatorType());
						}
						// restriccion is null
						else if (searchDTO != null
								&& searchDTO.getIsNull(campo.getName())) {
							addExpression(pojo.getClass(), aliasEntidad, where,
									params, campo.getName(), null,
									campo.getType(), ComparatorTypeEnum.IS_NULL);
						}
						/*
						 * Diferentes tipos de restricciones. TODO: Actualmente
						 * si un mismo campo tiene diferentes tipos de
						 * restriccciones, se le aplica unicamente la ultima. Se
						 * debe validar cuando un campo puede aceptar diferentes
						 * tipos de restricciones en una sentencia o no validar
						 * para que SQL lo resuelva.
						 */
						else if (searchDTO != null
								&& searchDTO.getIsGreaterThan(campo.getName()) != null) {
							addExpression(
									pojo.getClass(),
									aliasEntidad,
									where,
									params,
									campo.getName(),
									searchDTO.getIsGreaterThan(campo.getName()),
									campo.getType(),
									ComparatorTypeEnum.GREATER_THAN_COMPARATOR);
						} else if (enableLike
								&& campo.getType().equals(String.class)) {
							addExpression(
									pojo.getClass(),
									aliasEntidad,
									where,
									params,
									campo.getName(),
									ClasesUtil.invocarMetodoGet(pojo,
											campo.getName()), campo.getType(),
									ComparatorTypeEnum.LIKE_ANYWHERE_COMPARATOR);
						} else if (!campo.getName().equals(idName)) {
							addExpression(
									pojo.getClass(),
									aliasEntidad,
									where,
									params,
									campo.getName(),
									ClasesUtil.invocarMetodoGet(pojo,
											campo.getName()), campo.getType(),
									ComparatorTypeEnum.EQUAL_COMPARATOR);
						}
					}
				}
			}

			// Agregar restricciones de campos no persistentes
			addRestrictionFromTransientFields(objetoVO, aliasEntidad, where,
					params);

		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Obtener el objeto CriteriaQuery Search a partir de un objeto VO
	 * 
	 * @param objetoVO
	 * @return
	 */
	protected CriterioBusqueda getCriteriaSearchFromVO(Object objetoVO) {
		CriterioBusqueda criteriaSearch;
		if (objetoVO instanceof BuscadorDTO) {
			criteriaSearch = ((BuscadorDTO) objetoVO).getCriteriaSearch();
		} else {
			criteriaSearch = null;
		}
		return criteriaSearch;
	}

	public <T> Predicate addExpression(Class<?> classPojo, Path<?> root,
			Predicate where, String propertyName, T propertyValue,
			Class<?> propertyType, ComparatorTypeEnum comparatorID) {
		return addExpression(classPojo, root, where, null, propertyName,
				propertyValue, propertyType, comparatorID);
	}

	public <T> Predicate addExpression(Class<?> classPojo, Path<?> root,
			Predicate where, String alias, String propertyName,
			T propertyValue, Class<?> propertyType,
			ComparatorTypeEnum comparatorID) {
		return addExpression(classPojo, root, where, alias, propertyName,
				propertyValue, propertyType, comparatorID, Boolean.FALSE);

	}

	public <T> Predicate addExpression(Class<?> classPojo, Path<?> root,
			Predicate where, String propertyName, T propertyValue,
			Class<?> propertyType, ComparatorTypeEnum comparatorID,
			Boolean denyComparation) {
		return addExpression(classPojo, root, where, null, propertyName,
				propertyValue, propertyType, comparatorID, denyComparation);
	}

	@SuppressWarnings("unchecked")
	public <T> Predicate addExpression(Class<?> classPojo, Path<?> root,
			Predicate where, String alias, String fieldProperty,
			T propertyValue, Class<?> propertyType,
			ComparatorTypeEnum comparatorID, Boolean denyComparation) {
		String propertyName;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		propertyName = fieldProperty;
		if (propertyValue instanceof String) {
			propertyValue = (T) StringUtils.trimToNull((String) propertyValue);
		}

		if (propertyValue != null) {

			if (propertyValue instanceof Comparable<?>[]
					&& ((Comparable[]) propertyValue).length == 0) {
				return where;
			}
			if (propertyValue instanceof Collection<?>) {
				if (((Collection<?>) propertyValue).isEmpty()) {
					return where;
				}
			}

			switch (comparatorID) {
			case LIKE_EXACT_COMPARATOR:
			case LIKE_END_COMPARATOR:
			case LIKE_START_COMPARATOR:
			case LIKE_ANYWHERE_COMPARATOR: {
				if (propertyValue instanceof String) {
					where = setComparation(where, criteriaBuilder,
							criteriaBuilder.like(
									root.get(propertyName).as(String.class),
									(String) propertyValue), denyComparation);

				} else if (propertyValue instanceof String[]) {

					String[] values = (String[]) propertyValue;

					CriteriaBuilder junctionCriteria = entityManager
							.getCriteriaBuilder();

					for (String value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.like(
								root.get(propertyName).as(String.class),
								(String) value), denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				} else if (propertyValue instanceof Collection<?>) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Collection<?> values = (Collection<?>) propertyValue;
					for (Object value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.like(
								root.get(propertyName).as(String.class),
								(String) value), denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				}
				break;
			}
			case INSENSITIVE_LIKE_EXACT_COMPARATOR:
			case INSENSITIVE_LIKE_END_COMPARATOR:
			case INSENSITIVE_LIKE_START_COMPARATOR:
			case INSENSITIVE_LIKE_ANYWHERE_COMPARATOR: {
				if (propertyValue instanceof String) {
					where = setComparation(where, criteriaBuilder,
							criteriaBuilder.like(criteriaBuilder.lower(root
									.get(propertyName).as(String.class)),
									((String) propertyValue).toLowerCase()),
							denyComparation);
				} else if (propertyValue instanceof String[]) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					String[] values = (String[]) propertyValue;
					for (String value : values) {
						setComparation(
								criteriaBuilder,
								criteriaBuilder.like(
										criteriaBuilder.lower(root.get(
												propertyName).as(String.class)),
										value.toLowerCase()), denyComparation,
								Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				} else if (propertyValue instanceof Collection<?>) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Collection<?> values = (Collection<?>) propertyValue;
					for (Object value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.like(
								criteriaBuilder.lower(root.get(propertyName)
										.as(String.class)), ((String) value)
										.toLowerCase()), denyComparation,
								Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				}
				break;
			}
			case EQUAL_COMPARATOR: {
				if (propertyValue instanceof Comparable<?>) {
					where = setComparation(where, criteriaBuilder,
							criteriaBuilder.equal(root.get(propertyName),
									propertyValue), denyComparation);
				} else if (propertyValue instanceof Comparable<?>[]) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Comparable<?>[] values = (Comparable[]) propertyValue;
					for (Comparable<?> value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.equal(
								root.get(propertyName), value),
								denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				} else if (propertyValue instanceof Collection<?>) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Collection<?> values = (Collection<?>) propertyValue;
					for (Object value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.equal(
								root.get(propertyName), value),
								denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				}
				break;
			}

			case NOT_EQUAL_COMPARATOR: {
				if (propertyValue instanceof Comparable<?>) {
					where = setComparation(where, criteriaBuilder,
							criteriaBuilder.equal(root.get(propertyName),
									propertyValue), !denyComparation);
				} else if (propertyValue instanceof Comparable<?>[]) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Comparable<?>[] values = (Comparable[]) propertyValue;
					for (Comparable<?> value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.equal(
								root.get(propertyName), value),
								!denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				} else if (propertyValue instanceof Collection<?>) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Collection<?> values = (Collection<?>) propertyValue;
					for (Object value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.equal(
								root.get(propertyName), value),
								!denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				}
				break;
			}

			case GREATER_THAN_OR_EQUAL_COMPARATOR: {

				if (propertyValue instanceof Number) {
					where = setComparation(where, criteriaBuilder,
							criteriaBuilder.ge(
									root.get(propertyName).as(Number.class),
									(Number) propertyValue), denyComparation);
				} else if (propertyValue instanceof Number[]) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Number[] values = (Number[]) propertyValue;
					for (Number value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.ge(root
								.get(propertyName).as(Number.class), value),
								denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				} else if (propertyValue instanceof Collection<?>) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Collection<Number> values = (Collection<Number>) propertyValue;
					for (Number value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.ge(root
								.get(propertyName).as(Number.class), value),
								denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				}
				break;
			}
			case GREATER_THAN_COMPARATOR: {
				if (propertyValue instanceof Number) {
					where = setComparation(where, criteriaBuilder,
							criteriaBuilder.gt(
									root.get(propertyName).as(Number.class),
									(Number) propertyValue), denyComparation);
				} else if (propertyValue instanceof Number[]) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Number[] values = (Number[]) propertyValue;
					for (Number value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.gt(root
								.get(propertyName).as(Number.class), value),
								denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				} else if (propertyValue instanceof Collection<?>) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Collection<Number> values = (Collection<Number>) propertyValue;
					for (Number value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.gt(root
								.get(propertyName).as(Number.class), value),
								denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				}
				break;
			}
			case LESS_THAN_OR_EQUAL_COMPARATOR: {
				if (propertyValue instanceof Number) {
					where = setComparation(where, criteriaBuilder,
							criteriaBuilder.le(
									root.get(propertyName).as(Number.class),
									(Number) propertyValue), denyComparation);
				} else if (propertyValue instanceof Number[]) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Number[] values = (Number[]) propertyValue;
					for (Number value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.le(root
								.get(propertyName).as(Number.class), value),
								denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				} else if (propertyValue instanceof Collection<?>) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Collection<Number> values = (Collection<Number>) propertyValue;
					for (Number value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.le(root
								.get(propertyName).as(Number.class), value),
								denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				}
				break;
			}
			case LESS_THAN_COMPARATOR: {
				if (propertyValue instanceof Number) {
					where = setComparation(where, criteriaBuilder,
							criteriaBuilder.lt(
									root.get(propertyName).as(Number.class),
									(Number) propertyValue), denyComparation);
				} else if (propertyValue instanceof Number[]) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Number[] values = (Number[]) propertyValue;
					for (Number value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.lt(root
								.get(propertyName).as(Number.class), value),
								denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				} else if (propertyValue instanceof Collection<?>) {
					CriteriaBuilder junctionCriteria = entityManager
							.getEntityManagerFactory().getCriteriaBuilder();
					Collection<Number> values = (Collection<Number>) propertyValue;
					for (Number value : values) {
						setComparation(criteriaBuilder, criteriaBuilder.lt(root
								.get(propertyName).as(Number.class), value),
								denyComparation, Boolean.TRUE);
					}
					criteriaBuilder.and(where, junctionCriteria.disjunction());
				}
				break;
			}
			case IN_COMPARATOR: {
				if (propertyValue instanceof java.util.Collection<?>) {
					java.util.Collection<?> collectionValues = (java.util.Collection<?>) propertyValue;
					where = criteriaBuilder.and(where, root.get(propertyName)
							.in(collectionValues));

				} else if (propertyValue instanceof Comparable<?>[]) {
					Object[] comparableValues = (Object[]) propertyValue;
					where = criteriaBuilder.and(where, root.get(propertyName)
							.in(comparableValues));

				} else if (propertyValue instanceof Comparable<?>) {
					where = setComparation(where, criteriaBuilder,
							criteriaBuilder.equal(
									root.get(propertyName).as(Object.class),
									propertyValue), denyComparation);
				}
				break;
			}
			case NOT_IN_COMPARATOR: {
				if (propertyValue instanceof Collection<?>) {
					Collection<?> collectionValues = (Collection<?>) propertyValue;
					where = criteriaBuilder.and(
							where,
							criteriaBuilder.not(root.get(propertyName).in(
									collectionValues)));
				} else if (propertyValue instanceof Comparable<?>[]) {
					Comparable<?>[] comparableValues = (Comparable[]) propertyValue;
					where = criteriaBuilder.and(
							where,
							criteriaBuilder.not(root.get(propertyName).in(
									(Object[]) comparableValues)));
				} else if (propertyValue instanceof Comparable<?>) {
					where = setComparation(where, criteriaBuilder,
							criteriaBuilder.equal(
									root.get(propertyName).as(Object.class),
									propertyValue), !denyComparation);
				}
				break;
			}

			case BETWEEN_INCLUDE_COMPARATOR: {
				if (propertyValue instanceof RangeValue<?>) {
					RangeValue<?> rangeValue = (RangeValue<?>) propertyValue;

					if (rangeValue.getBottomValue() != null) {
						where = setComparation(where, criteriaBuilder,
								criteriaBuilder
										.ge(root.get(propertyName).as(
												Number.class),
												(Number) propertyValue),
								denyComparation);
					}

					if (rangeValue.getTopValue() != null) {
						where = setComparation(where, criteriaBuilder,
								criteriaBuilder
										.le(root.get(propertyName).as(
												Number.class),
												(Number) propertyValue),
								denyComparation);
					}
				} else if (propertyValue instanceof Comparable<?>[]) {
					Comparable<?>[] values = (Comparable[]) propertyValue;
					if (values.length == 2) {
						if (values[0] != null) {
							where = setComparation(where, criteriaBuilder,
									criteriaBuilder.ge(root.get(propertyName)
											.as(Number.class),
											(Number) values[0]),
									denyComparation);
						}
						if (values[1] != null) {
							where = setComparation(where, criteriaBuilder,
									criteriaBuilder.ge(root.get(propertyName)
											.as(Number.class),
											(Number) values[1]),
									denyComparation);
						}
					}

				}
				break;
			}

			}
		} else if (comparatorID.equals(ComparatorTypeEnum.IS_NULL)) {
			where = setNullComparation(
					where,
					criteriaBuilder,
					criteriaBuilder.isNull(root.get(propertyName).as(
							Object.class)), denyComparation);

		} else if (comparatorID.equals(ComparatorTypeEnum.IS_NOT_NULL)) {
			where = setNullComparation(
					where,
					criteriaBuilder,
					criteriaBuilder.isNull(root.get(propertyName).as(
							Object.class)), !denyComparation);
		}
		return where;
	}

	public <T> StringBuilder addExpression(Class<?> classPojo,
			String aliasEntidad, StringBuilder where,
			Map<String, NamedParameterQuery> params, String fieldProperty,
			T propertyValue, Class<?> propertyType,
			ComparatorTypeEnum comparatorID) throws DAOException {
		return addExpression(classPojo, aliasEntidad, where, params,
				fieldProperty, propertyValue, propertyType, comparatorID,
				Boolean.FALSE);

	}

	@SuppressWarnings("unchecked")
	public <T> StringBuilder addExpression(Class<?> classPojo,
			String aliasEntidad, StringBuilder where,
			Map<String, NamedParameterQuery> params, String fieldProperty,
			T propertyValue, Class<?> propertyType,
			ComparatorTypeEnum comparatorID, Boolean denyComparation)
			throws DAOException {
		String propertyName;
		propertyName = aliasEntidad + "." + fieldProperty;

		if (propertyValue instanceof String) {
			propertyValue = (T) StringUtils.trimToNull((String) propertyValue);
		}

		if (propertyValue != null) {
			if (where.length() > 0) {
				where.append(" and");
			}

			if (propertyValue instanceof Comparable<?>[]
					&& ((Comparable[]) propertyValue).length == 0) {
				return where;
			}
			if (propertyValue instanceof Collection<?>) {
				if (((Collection<?>) propertyValue).isEmpty()) {
					return where;
				}
			}

			switch (comparatorID) {
			case LIKE_EXACT_COMPARATOR:
			case LIKE_END_COMPARATOR:
			case LIKE_START_COMPARATOR:
			case LIKE_ANYWHERE_COMPARATOR: {

				if (propertyValue instanceof String) {
					if (denyComparation) {
						where.append(" not");
					}
					where.append(" ").append(propertyName);
					where.append(" like ").append(
							addNamedParameter(
									params,
									comparatorID.getExpression()
											.buildExpression(
													(String) propertyValue)));

				} else if (propertyValue instanceof String[]) {

					String[] values = (String[]) propertyValue;

					where.append(" (");
					int i;
					for (i = 0; i < values.length - 1; i++) {
						if (denyComparation) {
							where.append(" not");
						}
						where.append(" ").append(propertyName);
						where.append(" like ").append(
								addNamedParameter(
										params,
										comparatorID.getExpression()
												.buildExpression(
														(String) values[i])));
						where.append(" or");
					}

					if (denyComparation) {
						where.append(" not");
					}
					where.append(" ").append(propertyName);
					where.append(" like ")
							.append(addNamedParameter(
									params,
									comparatorID
											.getExpression()
											.buildExpression((String) values[i])));
					where.append(" )");

				} else if (propertyValue instanceof Collection<?>) {
					Collection<?> values = (Collection<?>) propertyValue;
					where.append(" (");
					Iterator<?> it = values.iterator();
					while (it.hasNext()) {
						if (denyComparation) {
							where.append(" not");
						}
						where.append(" ").append(propertyName);
						where.append(" like ").append(
								addNamedParameter(
										params,
										comparatorID.getExpression()
												.buildExpression(
														(String) it.next())));
						if (it.hasNext()) {
							where.append(" or");
						}
					}
					where.append(" )");
				}
				break;
			}
			case INSENSITIVE_LIKE_EXACT_COMPARATOR:
			case INSENSITIVE_LIKE_END_COMPARATOR:
			case INSENSITIVE_LIKE_START_COMPARATOR:
			case INSENSITIVE_LIKE_ANYWHERE_COMPARATOR: {
				if (propertyValue instanceof String) {

					if (denyComparation) {
						where.append(" not");
					}
					where.append(" lower(").append(propertyName).append(")");
					where.append(" like ").append(
							addNamedParameter(
									params,
									comparatorID.getExpression()
											.buildExpression(
													((String) propertyValue)
															.toLowerCase())));
				} else if (propertyValue instanceof String[]) {
					String[] values = (String[]) propertyValue;
					where.append(" (");
					int i;
					for (i = 0; i < values.length - 1; i++) {

						if (denyComparation) {
							where.append(" not");
						}
						where.append(" lower(").append(propertyName)
								.append(")");
						where.append(" like ")
								.append(addNamedParameter(
										params,
										comparatorID
												.getExpression()
												.buildExpression(
														values[i].toLowerCase())));
						where.append(" or");
					}
					where.append(" lower(").append(propertyName).append(")");
					if (denyComparation) {
						where.append(" not");
					}
					where.append(" like ").append(
							addNamedParameter(
									params,
									comparatorID.getExpression()
											.buildExpression(
													values[i].toLowerCase())));
					where.append(" )");
				} else if (propertyValue instanceof Collection<?>) {
					Collection<?> values = (Collection<?>) propertyValue;
					where.append(" (");
					Iterator<?> it = values.iterator();
					while (it.hasNext()) {
						if (denyComparation) {
							where.append(" not");
						}
						where.append(" lower(").append(propertyName)
								.append(")");
						where.append(" like ")
								.append(addNamedParameter(
										params,
										comparatorID.getExpression()
												.buildExpression(
														String.valueOf(
																it.next())
																.toLowerCase())));
						if (it.hasNext()) {
							where.append(" or");
						}
					}
					where.append(" )");
				}
				break;
			}
			case EQUAL_COMPARATOR: {
				addExpression(where, params, propertyName, propertyValue, "=",
						denyComparation);
				break;
			}

			case NOT_EQUAL_COMPARATOR: {
				addExpression(where, params, propertyName, propertyValue, "=",
						!denyComparation);
				break;
			}

			case GREATER_THAN_OR_EQUAL_COMPARATOR: {
				addExpression(where, params, propertyName, propertyValue, ">=",
						denyComparation);
				break;
			}
			case GREATER_THAN_COMPARATOR: {
				addExpression(where, params, propertyName, propertyValue, ">",
						denyComparation);
				break;
			}
			case LESS_THAN_OR_EQUAL_COMPARATOR: {
				addExpression(where, params, propertyName, propertyValue, "<=",
						denyComparation);
				break;
			}
			case LESS_THAN_COMPARATOR: {
				addExpression(where, params, propertyName, propertyValue, "<",
						denyComparation);
				break;
			}
			case IN_COMPARATOR: {
				if (denyComparation) {
					where.append(" not");
				}
				if (propertyValue instanceof Comparable<?>) {
					addExpression(where, params, propertyName, propertyValue,
							"=", denyComparation);
				} else if (propertyValue instanceof Collection<?>) {
					where.append(" ").append(propertyName);
					where.append(" in(")
							.append(addNamedParameter(params, propertyValue))
							.append(")");

				} else if (propertyValue instanceof Object[]) {
					where.append(" ").append(propertyName);
					Object[] values = (Object[]) propertyValue;
					where.append(" in(");
					int i;
					for (i = 0; i < values.length - 1; i++) {
						where.append(addNamedParameter(params, values[i]))
								.append(", ");
					}
					where.append(addNamedParameter(params, values[i]));
					where.append(")");

				}
				break;
			}
			case NOT_IN_COMPARATOR: {
				if (!denyComparation) {
					where.append(" not");
				}
				if (propertyValue instanceof Comparable<?>) {
					addExpression(where, params, propertyName, propertyValue,
							"=", denyComparation);
				} else if (propertyValue instanceof Collection<?>) {
					where.append(" ").append(propertyName);
					where.append(" in(")
							.append(addNamedParameter(params, propertyValue))
							.append(" )");

				} else if (propertyValue instanceof Object[]) {
					where.append(" ").append(propertyName);
					Object[] values = (Object[]) propertyValue;
					where.append(" in(");
					int i;
					for (i = 0; i < values.length - 1; i++) {
						where.append(addNamedParameter(params, values[i]))
								.append(", ");
					}
					where.append(addNamedParameter(params, values[i]));
					where.append(")");
				}
				break;
			}

			case BETWEEN_INCLUDE_COMPARATOR: {
				if (propertyValue instanceof RangeValue<?>) {
					RangeValue<?> rangeValue = (RangeValue<?>) propertyValue;

					if (rangeValue.getBottomValue() != null) {
						addExpression(where, params, propertyName,
								rangeValue.getBottomValue(), ">=",
								denyComparation);
					}

					if (rangeValue.getTopValue() != null) {
						if (rangeValue.getBottomValue() != null) {
							where.append(" and");
						}
						addExpression(where, params, propertyName,
								rangeValue.getTopValue(), "<=", denyComparation);
					}
				} else if (propertyValue instanceof Comparable<?>[]) {
					Comparable<?>[] values = (Comparable[]) propertyValue;
					if (values.length == 2) {
						if (values[0] != null) {
							addExpression(where, params, propertyName,
									values[0], ">=", denyComparation);
						}
						if (values[1] != null) {
							if (values[0] != null) {
								where.append(" and");
							}
							addExpression(where, params, propertyName,
									values[1], ">=", denyComparation);
						}
					}

				}
				break;
			}
			}
		} else if (comparatorID.equals(ComparatorTypeEnum.IS_NULL)) {
			if (where.length() > 0) {
				where.append(" and");
			}
			if (denyComparation) {
				where.append(" not");
			}
			where.append(" ").append(propertyName);
			where.append(" is null");

		} else if (comparatorID.equals(ComparatorTypeEnum.IS_NOT_NULL)) {
			if (where.length() > 0) {
				where.append(" and");
			}
			if (!denyComparation) {
				where.append(" not");
			}
			where.append(" ").append(propertyName);
			where.append(" is null");
		}
		return where;
	}

	private <T> String addNamedParameter(
			Map<String, NamedParameterQuery> params, T propertyValue) {
		String prefixParam = "p";
		String paramName = prefixParam + String.valueOf(params.size());
		String token = ":";

		params.put(paramName, new NamedParameterQuery(paramName, propertyValue));
		return token + paramName;
	}

	private <T> void addExpression(StringBuilder where,
			Map<String, NamedParameterQuery> params, String propertyName,
			T propertyValue, String operator, Boolean denyComparation) {
		if (propertyValue instanceof Comparable<?>) {
			if (denyComparation) {
				where.append(" not");
			}
			where.append(" ").append(propertyName);
			where.append(" ").append(operator).append(" ")
					.append(addNamedParameter(params, propertyValue));
		} else if (propertyValue instanceof Comparable<?>[]) {
			Comparable<?>[] values = (Comparable[]) propertyValue;
			where.append(" (");
			int i;
			for (i = 0; i < values.length - 1; i++) {
				if (denyComparation) {
					where.append(" not");
				}
				where.append(" ").append(propertyName);
				where.append(" ").append(operator).append(" ")
						.append(addNamedParameter(params, values[i]));
				where.append(" or");
			}

			if (denyComparation) {
				where.append(" not");
			}
			where.append(" ").append(propertyName);
			where.append(" ").append(operator).append(" ")
					.append(addNamedParameter(params, values[i]));
			where.append(" )");
		} else if (propertyValue instanceof Collection<?>) {
			Collection<?> values = (Collection<?>) propertyValue;
			where.append(" (");
			Iterator<?> it = values.iterator();
			while (it.hasNext()) {
				if (denyComparation) {
					where.append(" not");
				}
				where.append(" ").append(propertyName);
				where.append(" ").append(operator).append(" ")
						.append(addNamedParameter(params, it.next()));
				if (it.hasNext()) {
					where.append(" or");
				}
			}
			where.append(" )");
		}
	}

	private Predicate setNullComparation(Predicate where,
			CriteriaBuilder criteriaBuilder, Predicate criterion,
			Boolean denyComparation) {
		if (denyComparation) {
			where = criteriaBuilder.and(where,
					criteriaBuilder.isNotNull(criterion));
		} else {
			where = criteriaBuilder.and(where,
					criteriaBuilder.isNull(criterion));
		}
		return where;
	}

	private Predicate setComparation(Predicate where,
			CriteriaBuilder criteriaBuilder, Predicate criterion,
			Boolean denyComparation) {
		if (denyComparation) {
			where = criteriaBuilder.and(where, criteriaBuilder.not(criterion));
		} else {
			where = criteriaBuilder.and(where, criterion);
		}
		return where;
	}

	private Predicate setComparation(CriteriaBuilder criteriaBuilder,
			Predicate criterion, Boolean denyComparation, Boolean disjunction) {
		Predicate criterionReturn = criterion;
		if (disjunction) {
			if (denyComparation) {
				criterionReturn = criteriaBuilder.or(criterion,
						criteriaBuilder.not(criterion));
			} else {
				criterionReturn = criteriaBuilder.or(criterion, criterion);
			}
		} else {
			if (denyComparation) {
				criterionReturn = criteriaBuilder.and(criterion,
						criteriaBuilder.not(criterion));
			} else {
				criterionReturn = criteriaBuilder.and(criterion, criterion);
			}
		}
		return criterionReturn;
	}

	/**
	 * Generar los listados de propiedades para cada clase tomada en cuenta en
	 * la b&uacute;squeda para los campos persistentes, campos persistentes tipo
	 * asociaci&oacute;n y campos no persistentes
	 * 
	 * @param clase
	 * @throws DAOException
	 */
	protected void generateFieldsByClass(Class<?> clase) throws DAOException {
		if (!allFieldsMapped.containsKey(clase)) {

			// Meta informacion de las clases que estan mapeadas en hibernate
			EntityType<?> classMetaData = entityManager
					.getEntityManagerFactory().getMetamodel().entity(clase);
			if (classMetaData == null) {
				throw new DAOException("La clase " + clase.getName()
						+ " no esta mapeada como entidad persistible");
			}

			// Agregar el nombre del campo de la clase que corresponde a la
			// clave primaria de acuerdo
			// a los mapeos de Hibernate

			Set<String> propertyNamesSet = new LinkedHashSet<String>();

			// Agregar los nombres de los campos de la clase que estan mapeados
			// en el Hibernate
			for (Attribute<?, ?> attr : classMetaData.getAttributes()) {
				propertyNamesSet.add(attr.getName());
			}

			// que estan mapeados en JPA
			Class<?> superClass = generateFieldsByClass(clase, clase,
					propertyNamesSet);
			// Buscamos el campo mapeado en el padre de la clase
			while (!(superClass.equals(BuscadorDTO.class)
					|| superClass.equals(Object.class) || superClass == null
					|| propertyNamesSet == null || propertyNamesSet.isEmpty())) {
				superClass = generateFieldsByClass(clase, superClass,
						propertyNamesSet);
			}

		}
	}

	/**
	 * Obtener campos de consulta a partir de un objeto VO
	 * 
	 * @param objetoVO
	 * @throws DAOException
	 */
	protected void generateFieldsByVO(Object objetoVO) throws DAOException {
		Object pojo = getPOJO(objetoVO);
		// si el POJO persistente es igual al Objeto VO
		if (pojo == objetoVO) {
			generateFieldsByClass(objetoVO.getClass());
		}
		// si el POJO persistente y el VO son diferentes, se obtiene los campos
		// persistentes del POJO y
		// los campos no persistentes del VO
		else {
			generateFieldsByClass(pojo.getClass());
			Class<?> classVO = objetoVO.getClass();
			generateFieldsByClass(pojo.getClass(), classVO, null);
		}

	}

	/**
	 * Obtener los campos de una clase que pueden encontrarse en su
	 * declaraci&oacute;n o en clases padres objectClass
	 * 
	 * @param objectClassRoot
	 *            Clase a verificar los campos a los que se puede tener acceso
	 * @param objectClass
	 *            Clase padre que tiene la definici&oacuten; de los campos
	 *            accesibles desde objectClassRoot
	 * @param fields
	 *            Colecci&oacute;n de los campos definidos en la clase
	 *            objectClassRoot y heredados por objectClass
	 */
	protected Class<?> generateFieldsByClass(Class<?> objectClassRoot,
			Class<?> objectClass, Collection<String> fields) {
		EntityType<?> classMetaData = entityManager.getEntityManagerFactory()
				.getMetamodel().entity(objectClassRoot);
		// agregar los campos que estan declarados en la clase
		for (Field declaredField : objectClass.getDeclaredFields()) {
			// Si el campo esta mapeado en Hibernate
			if (fields != null && fields.contains(declaredField.getName())) {
				switch (classMetaData.getAttribute(declaredField.getName())
						.getPersistentAttributeType()) {
				// Tipo de campo simple
				case BASIC:
				case EMBEDDED:
					Set<Field> fieldsMapped = allFieldsMapped
							.get(objectClassRoot);
					if (fieldsMapped == null) {
						fieldsMapped = new LinkedHashSet<Field>();
						allFieldsMapped.put(objectClassRoot, fieldsMapped);
					}
					// asociationFieldsMapped
					fieldsMapped.add(declaredField);
					break;
				// Campo tipo relacion uno a uno o muchos a uno
				case ONE_TO_ONE:
				case MANY_TO_ONE:
					// Campo tipo relacion uno a varios o varios a varios
				case ONE_TO_MANY:
				case MANY_TO_MANY:
				case ELEMENT_COLLECTION:
					Set<Field> associationFields = associationFieldsMapped
							.get(objectClassRoot);
					if (associationFields == null) {
						associationFields = new LinkedHashSet<Field>();
						associationFieldsMapped.put(objectClassRoot,
								associationFields);
					}
					associationFields.add(declaredField);
					break;
				default:

				}

				// eliminamos del listado de campos mapeados a buscar
				fields.remove(declaredField.getName());
			} else {
				Set<Field> transientFields = transientFieldsMap
						.get(objectClassRoot);
				if (transientFields == null) {
					transientFields = new LinkedHashSet<Field>();
					transientFields.add(declaredField);
					transientFieldsMap.put(objectClassRoot, transientFields);
				} else if (!transientFields.contains(declaredField)) {
					transientFields.add(declaredField);
				}
			}

		}

		return objectClass.getSuperclass();
	}

	public Collection<Field> getFields(Class<?> objectClass,
			Collection<String> fields) {
		Set<Field> completeFields = new LinkedHashSet<Field>();

		completeFields = new LinkedHashSet<Field>();
		Class<?> superClass = objectClass.getClass();

		superClass = getFields(fields, completeFields, superClass);

		while (superClass != null
				&& !(superClass.equals(BuscadorDTO.class) || superClass
						.equals(Object.class)) && !fields.isEmpty()) {
			superClass = getFields(fields, completeFields, superClass);
		}

		return completeFields;
	}

	private Class<?> getFields(Collection<String> fields,
			Set<Field> completeFields, Class<?> superClass) {
		for (Field field : superClass.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers())) {
				completeFields.add(field);
				if (fields.contains(field.getName())) {
					fields.remove(field.getName());
				}

			}
		}
		return superClass.getSuperclass();
	}

	public List<Field> getFieldsByClass(Class<?> clase,
			String... excludeProperties) {
		if (!fieldsQueryMap.containsKey(clase)) {

			// todos los campos que estan mapeados en el archivo de hibernate
			Collection<Field> camposCompletos = null;

			// Meta informacion de las clases que estan mapeadas en hibernate
			EntityType<?> classMetaData = entityManager
					.getEntityManagerFactory().getMetamodel().entity(clase);
			Set<String> fieldsMappedCol = new LinkedHashSet<String>();
			// Agregar el nombre del campo de la clase que corresponde a la
			// clave primaria de acuerdo
			// a los mapeos de Hibernate
			LogGenerico.getLogger().info("_mapa:::" + fieldsMappedCol);
			LogGenerico.getLogger().info("_clase:::" + classMetaData);
			fieldsMappedCol.add(classMetaData.getId(
					classMetaData.getIdType().getJavaType()).getName());

			// Agregar los nombres de los campos de la clase que estan mapeados
			// en el Hibernate
			for (Attribute<?, ?> attr : classMetaData.getAttributes()) {
				fieldsMappedCol.add(attr.getName());
			}

			// Obtener una lista de objetos Field de acuerdo a los nombres de
			// los campos
			// que estan mapeados en el Hibernate
			camposCompletos = getFields(clase, fieldsMappedCol);
			// Poner la lista de campos en el cache de campos de acuerdo a su
			// clase
			fieldsQueryMap.put(clase, camposCompletos);

		}
		// Obtener una lista clonada de los campos ya que pueden ser eliminados
		// por exclusion de consulta
		return getClonedFields(fieldsQueryMap.get(clase));
	}

	public List<Field> getClonedFields(Collection<Field> fieldList) {
		List<Field> cloneFieldList = new ArrayList<Field>(fieldList.size());
		cloneFieldList.addAll(fieldList);
		return cloneFieldList;
	}

	public String getIDNameByAnnotation(x object) throws DAOException {
		Collection<Field> annotatedFields = null;

		annotatedFields = ClasesUtil.getFieldsByAnnotation(object,
				IdField.class);

		if (annotatedFields == null || annotatedFields.isEmpty()) {
			throw new DAOException(
					"No existe un campo con la anotacion de identificador");
		}

		if (annotatedFields.size() > 1) {
			throw new DAOException(
					"Existe más de un campo con la anotacion de identificador");
		}

		return annotatedFields.iterator().next().getName();
	}

	public int updateByID(x object, String[] fieldsForUpadate,
			boolean clearCache) throws DAOException {
		try {
			String parametersUpdateSentence = null;
			StringBuilder update = null;
			QueryParameters<NamedParameterQuery> updateParametersSentence = null;
			QueryParameters<NamedParameterQuery> restrictions = null;
			Map<String, Object> properties = null;
			Query query = null;

			final String NAMED_PARAMETER_TOKEN = ":";
			final String NAMED_PARAMETER_PREFIX = "p";
			final String NAMED_PARAMETER_RESTRICTION_PREFIX = "pr";

			update = new StringBuilder("update ");
			update.append(object.getClass().getName());
			update.append(" set ");

			updateParametersSentence = QueryParameters
					.buildQueryNamedParameters(object, fieldsForUpadate,
							QueryParametersType.LIST, NAMED_PARAMETER_TOKEN,
							NAMED_PARAMETER_PREFIX);
			if (updateParametersSentence != null) {
				parametersUpdateSentence = updateParametersSentence
						.getParametersSentence(false);

				if (parametersUpdateSentence != null) {
					update.append(parametersUpdateSentence);
					restrictions = QueryParameters.buildQueryNamedParameters(
							this.getIdRestrictions(object,
									this.getIDNameByAnnotation(object), true),
							QueryParametersType.AND_LIST,
							NAMED_PARAMETER_TOKEN,
							NAMED_PARAMETER_RESTRICTION_PREFIX);

					if (restrictions != null) {
						update.append(" where ");
						update.append(restrictions.getParametersSentence(false));

						properties = updateParametersSentence
								.getParametersValues(false);
						properties.putAll(restrictions
								.getParametersValues(false));

						if (clearCache) {
							entityManager.clear();
						}

						LogGenerico.getLogger().error("sentencia update: {}", update);
						LogGenerico.getLogger().error("parametros: " + properties);
						query = entityManager.createQuery(update.toString());
						for (Map.Entry<String, Object> param : properties
								.entrySet()) {
							query.setParameter(param.getKey(), param.getValue());
						}
						return query.executeUpdate();
					}
				}
			}
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(
					GenericoMensajes.getString("genericoDao.mensajes.error.actualizar"),
					e);
		}

		return 0;
	}

	public void actualizarObjeto(x objeto) throws DAOException {
		try {
			entityManager.clear();
			entityManager.merge(getPOJO(objeto));
			entityManager.flush();
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(
					GenericoMensajes.getString("genericoDao.mensajes.error.actualizar"),
					e);
		}
	}

	public void addRelations(Root<?> qbcSelect, List<Join> joins)
			throws DAOException {
		JoinType joinJpa;

		if (qbcSelect == null) {
			throw new DAOException("el objeto CriteriaQuery no puede ser nulo");
		}

		for (Join join : joins) {
			if (join.getJoinType() == Join.LEFT_JOIN) {
				joinJpa = JoinType.LEFT;
			} else {
				joinJpa = JoinType.INNER;
			}
			qbcSelect.join(join.getProperty(), joinJpa);
		}
	}

	public void crearOactualizarObjeto(x objeto) throws DAOException {
		String idName = null;
		Object id = null;

		try {
			Serializable pojo = getPOJO(objeto);
			LogGenerico.getLogger().info("Clase del objeto a crear o actualizar:{}",
					pojo.getClass());
			EntityType<?> entity = entityManager.getEntityManagerFactory()
					.getMetamodel().entity(pojo.getClass());
			idName = entity.getId(entity.getIdType().getJavaType()).getName();
			if (idName != null) {
				id = ClasesUtil.invocarMetodoGet(pojo, idName);
				if (id != null) {

					Boolean actualizarObjeto = Boolean.TRUE;
					for (Field fieldID : id.getClass().getFields()) {
						Object fieldIdValue = ClasesUtil.invocarMetodoGet(id,
								fieldID.getName());
						if (fieldIdValue == null) {
							actualizarObjeto = Boolean.FALSE;
							break;
						}
					}

					if (actualizarObjeto) {
						this.actualizarObjeto(objeto);

					} else {
						this.crearObjeto(objeto);
					}
				}
			}

		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(GenericoMensajes.getString("genericoDao.mensajes.error.registrar"),e);
		}
	}

	public void eliminarObjeto(x objeto) throws DAOException {
		try {
			entityManager.clear();
			Boolean clearCache = Boolean.TRUE;
			Boolean enableLike = Boolean.TRUE;
			objeto=findUnique(objeto, clearCache, enableLike);
			entityManager.remove(getPOJO(objeto));
			entityManager.flush();
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(GenericoMensajes.getString("genericoDao.mensajes.error.eliminar"),
					e);
		}
	}

	@SuppressWarnings("unused")
	public void crearObjeto(x objetoVO) throws DAOException {
		try {
			Serializable pojo;
			Set<Field> camposMapeadosSimples;
			EntityType<?> classMetaData;
			Object id;
			Collection<Field> sequenceAnnotatedFields;

			entityManager.clear();
			pojo = getPOJO(objetoVO);
			classMetaData = entityManager.getMetamodel()
					.entity(pojo.getClass());

			id = ClasesUtil.invocarMetodoGet(pojo,
					classMetaData
							.getId(classMetaData.getIdType().getJavaType())
							.getName());
			generateFieldsByVO(objetoVO);
			camposMapeadosSimples = allFieldsMapped.get(pojo.getClass());
			entityManager.persist(pojo);
			entityManager.flush();
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(GenericoMensajes.getString("genericoDao.mensajes.error.crear"),e);
		}

	}

	public String obtenerNombreAtributoId(String nombreIdObjetoRaiz,
			String nombreAtributo) throws Exception {
		return new StringBuilder().append(nombreIdObjetoRaiz).append(".")
				.append(nombreAtributo).toString();
	}

	public <T extends BuscadorDTO> void establecerOrden(T objetoRaiz,
			From<?, ?> root, CriteriaQuery<?> qbcSelect) throws DAOException {
		Boolean enableProjections = Boolean.FALSE;
		establecerOrden(objetoRaiz, root, qbcSelect, enableProjections);
	}

	public <T extends BuscadorDTO> void establecerOrden(T objetoRaiz,
			String aliasEntity, StringBuilder orderByBuilder)
			throws DAOException {
		// orden por varios campos y tipos
		if (objetoRaiz.existenCamposOrden()) {
			orderByBuilder.append(" order by ");
			Iterator<OrderBy> itOrderBy = objetoRaiz.getOrderFields()
					.iterator();
			while (itOrderBy.hasNext()) {
				OrderBy orderBy = itOrderBy.next();
				String propertyAlias = propertyNameToAlias(objetoRaiz,
						orderBy.getPropertyAlias());
				if (propertyAlias.indexOf(DOT) >= 0)
					// ordern ascendente
					if (OrderBy.isOrderAsc(orderBy)) {
						orderByBuilder.append(
								propertyNameToAlias(objetoRaiz,
										orderBy.getPropertyAlias())).append(
								" asc");
						if (itOrderBy.hasNext()) {
							orderByBuilder.append(",");
						}
					}

					// orden descendente
					else {
						orderByBuilder.append(
								propertyNameToAlias(objetoRaiz,
										orderBy.getPropertyAlias())).append(
								" desc");
						if (itOrderBy.hasNext()) {
							orderByBuilder.append(",");
						}
					}

			}
		}

		// orden por varios campos y un solo tipo
		else if (objetoRaiz.getOrderByField() != null) {
			// orden ascendente
			if (OrderBy.isOrderAsc(objetoRaiz.getOrderByField())) {
				orderByBuilder.append(" order by ");
				String[] ordersBy = objetoRaiz.getOrderByField()
						.getOrderClause();
				int i;
				for (i = 0; i < ordersBy.length - 1; i++) {

					orderByBuilder.append(propertyNameToAlias(objetoRaiz,
							ordersBy[i]));
					orderByBuilder.append(" asc");

					orderByBuilder.append(",");
				}
				orderByBuilder.append(propertyNameToAlias(objetoRaiz,
						ordersBy[i]));
				orderByBuilder.append(" asc");

			}

			// orden descendente
			else {
				orderByBuilder.append(" order by ");
				String[] ordersBy = objetoRaiz.getOrderByField()
						.getOrderClause();
				int i;
				for (i = 0; i < ordersBy.length - 1; i++) {

					orderByBuilder.append(propertyNameToAlias(objetoRaiz,
							ordersBy[i]));
					orderByBuilder.append(" desc");

					orderByBuilder.append(",");
				}
				orderByBuilder.append(propertyNameToAlias(objetoRaiz,
						ordersBy[i]));
				orderByBuilder.append(" desc");

			}
		}
	}

	/**
	 * @param objetoRaiz
	 * @param qbcSelect
	 * @throws DAOException
	 */
	private <T extends BuscadorDTO> void establecerOrden(T objetoRaiz,
			From<?, ?> root, CriteriaQuery<?> qbcSelect,
			Boolean enableProjections) throws DAOException {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		// orden por varios campos y tipos
		if (objetoRaiz.existenCamposOrden()) {
			for (OrderBy orderBy : objetoRaiz.getOrderFields()) {
				// ordern ascendente
				if (OrderBy.isOrderAsc(orderBy)) {
					qbcSelect.orderBy(criteriaBuilder.asc(root.get(orderBy
							.getPropertyAlias())));
				}

				// orden descendente
				else {
					qbcSelect.orderBy(criteriaBuilder.desc(root.get(orderBy
							.getPropertyAlias())));
				}

			}
		}

		// orden por varios campos y un solo tipo
		else if (objetoRaiz.getOrderByField() != null) {
			// orden ascendente
			if (OrderBy.isOrderAsc(objetoRaiz.getOrderByField())) {

				for (String atributo : objetoRaiz.getOrderByField()
						.getOrderClause()) {
					// ordern ascendente
					if (OrderBy.isOrderAsc(objetoRaiz.getOrderByField())) {
						qbcSelect.orderBy(criteriaBuilder.asc(root
								.get(atributo)));
					}

					// orden descendente
					else {
						qbcSelect.orderBy(criteriaBuilder.desc(root
								.get(atributo)));
					}
				}
			}

			// orden descendente
			else {
				for (String atributo : objetoRaiz.getOrderByField()
						.getOrderClause()) {

					qbcSelect.orderBy(criteriaBuilder.desc(root.get(atributo)));
					// qbcSelect.addOrder(Order.desc(atributo));
				}
			}
		}
	}

	public String establecerOrden(BuscadorDTO objetoRaiz) {
		if (objetoRaiz.getOrderByField() != null) {
			return " order by "
					+ objetoRaiz.getOrderByField().getSecuenceOrderClause();
		}
		return "";
	}

	public String getAttributeNameByAnnotation(Object objetoVO,
			Class<? extends Annotation> anotacion) {
		String attributeName = null;
		Object objeto = getPOJO(objetoVO);
		Field[] campos = objeto.getClass().getDeclaredFields();

		for (Field campo : campos) {
			if (campo.isAnnotationPresent(anotacion)) {
				attributeName = campo.getName();
				break;
			}
		}
		return attributeName;
	}

	public String getIdName(x plantillaBusqueda) throws DAOException {
		String idName = null;
		Object pojo = getPOJO(plantillaBusqueda);
		Field[] campos = pojo.getClass().getDeclaredFields();

		for (Field campo : campos) {
			if (campo.isAnnotationPresent(IdField.class)) {
				idName = campo.getName();
				break;
			}
		}
		return idName;
	}

	public <T> CriteriaQuery<?> createCriteria(T objetoRaiz,
			boolean limpiarCache) throws Exception {
		return createCriteria(objetoRaiz, null, limpiarCache);
	}

	public <T> CriteriaQuery<?> createCriteria(T objetoRaiz,
			String aliasObjetoRaiz, boolean limpiarCache) throws Exception {
		return createCriteria(getPOJO(objetoRaiz).getClass(), aliasObjetoRaiz,
				limpiarCache);
	}

	public <T> CriteriaQuery<?> createCriteria(Class<T> claseObjetoRaiz,
			boolean limpiarCache) throws Exception {
		return this.createCriteria(claseObjetoRaiz, null, limpiarCache);
	}

	/**
	 * 
	 * @param claseObjetoRaiz
	 * @param aliasObjetoRaiz
	 * @param limpiarCache
	 * @return
	 * @throws Exception
	 */
	private <T> CriteriaQuery<?> createCriteria(Class<T> claseObjetoRaiz,
			String aliasObjetoRaiz, boolean limpiarCache) throws Exception {
		try {
			if (limpiarCache) {
				entityManager.clear();
			}

			return entityManager.getCriteriaBuilder().createQuery();
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		}

	}

	public Query createQuery(String query, boolean clearCache) throws Exception {
		try {

			if (clearCache) {
				entityManager.clear();
			}
			return entityManager.createQuery(query);
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		}

	}

	public int eliminarObjetos(Class<x> claseObjeto) throws DAOException {
		int cantidadRegistrosAfectados = 0;
		StringBuffer delete = null;
		Query jpqlDelete = null;

		try {
			delete = new StringBuffer();
			delete.append("delete from ").append(claseObjeto.getName());
			jpqlDelete = entityManager.createQuery(delete.toString());
			cantidadRegistrosAfectados = jpqlDelete.executeUpdate();
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}

		return cantidadRegistrosAfectados;
	}

	/**
	 * 
	 * @param root
	 * @return
	 */
	protected <T extends BuscadorDTO> Set<Field> getFields(T root) {
		Class<?> rootClass;
		Set<Field> fields;

		rootClass = root.getClass();
		fields = new LinkedHashSet<Field>();
		while (rootClass != null && rootClass != BuscadorDTO.class) {
			for (Field field : rootClass.getDeclaredFields()) {
				fields.add(field);
			}

			rootClass = rootClass.getSuperclass();
		}
		return fields;
	}

	/**
	 * 
	 * @param relationField
	 * @return
	 */
	protected JoinType getJoinType(RelationField relationField) {
		if (relationField
				.joinType()
				.equals(RelationField.JoinType.LEFT)) {
			return JoinType.LEFT;
		}

		return JoinType.INNER;
	}

	public Predicate getRegisterDateRestriction(x rootObject, Root<x> root,
			RangeValue<Timestamp> rangoFechaRegistro) {
		return null;
	}

	public <T extends Serializable> void addIdRestrictions(T plantillaBusqueda,
			Root<?> root, CriteriaBuilder qbcSelect) throws DAOException {
		HashMap<String, Object> idRestrictions = null;
		String idName = null;

		if (qbcSelect == null) {
			throw new DAOException("el objeto CriteriaQuery no puede ser nulo");
		}

		try {
			idName = this.getAttributeNameByAnnotation(plantillaBusqueda,
					IdField.class);
			if (idName != null) {
				idRestrictions = this.getIdRestrictions(plantillaBusqueda,
						idName);
				if (idRestrictions != null) {
					for (String idRestriction : idRestrictions.keySet()) {
						qbcSelect.and(qbcSelect.equal(root.get(idRestriction),
								idRestrictions.get(idRestriction)));
					}
				}
			}
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (SecurityException e) {
			LogGenerico.getLogger().error("Error", e);

		} catch (IllegalArgumentException e) {
			LogGenerico.getLogger().error("Error", e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public <T extends Serializable> Predicate addRelations(T objetoVO,
			From<?, ?> root, Predicate where, CriteriaQuery<?> qbcSelect,
			CriterioBusqueda criteriaSearch, boolean enableLike,
			String... excludedProperties) throws DAOException {
		Boolean enableOrder = Boolean.TRUE;
		return addRelations(objetoVO, root, where, qbcSelect, criteriaSearch,
				enableLike, enableOrder, excludedProperties);
	}

	private <T extends Serializable> Predicate addRelations(T objetoVO,
			From<?, ?> root, Predicate where, CriteriaQuery<?> criteriaQuery,
			CriterioBusqueda criteriaSearch, boolean enableLike,
			boolean enableOrder, String... excludedProperties)
			throws DAOException {
		StringBuilder nombreCampoAliasInicial = new StringBuilder("");
		StringBuilder nombreCampoInicial = new StringBuilder("");
		existRelationsOneToMany = false;
		relationsHashCodeSet.clear();
		Serializable pojo = getPOJO(objetoVO);
		relationsHashCodeSet.add(pojo.hashCode());
		Predicate whereRestriction = addRelations(objetoVO, root, where,
				criteriaQuery, criteriaSearch, nombreCampoAliasInicial,
				nombreCampoInicial, enableLike, enableOrder, excludedProperties);
		if (existRelationsOneToMany) {
			// qbcSelect.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			existRelationsOneToMany = false;
		}
		return whereRestriction;
	}

	private <T extends Serializable> void addRelations(T objetoVO,
			String aliasEntity, StringBuilder select, StringBuilder where,
			Map<String, NamedParameterQuery> params,
			CriterioBusqueda criteriaSearch, boolean enableLike,
			boolean enableOrder, String... excludedProperties)
			throws DAOException {
		existRelationsOneToMany = Boolean.FALSE;
		relationsHashCodeSet.clear();
		Serializable pojo = getPOJO(objetoVO);
		relationsHashCodeSet.add(pojo.hashCode());
		addRelations(objetoVO, select, where, params, criteriaSearch,
				new StringBuilder(aliasEntity), new StringBuilder(aliasEntity),
				enableLike, enableOrder, excludedProperties);
	}

	/**
	 * Agrega las relaciones de asociaci&oacute;n de la entidad persistente
	 * especificada en <code>objetoRaiz</code> y establece los criterios de
	 * b&uacute;squeda utilizando <code>criteriaSearch</code> de cada entidad de
	 * la consulta
	 * 
	 * @param <T>
	 *            Especifica que el objeto a determinar sus relaciones debe ser
	 *            un objeto serializable
	 * @param objetoVO
	 *            Objeto a encontrar sus relaciones de asociaci&oacute;n
	 * @param criteriaBuilder
	 *            Objeto CriteriaQuery al que se agregar&aacute; la
	 *            relaci&oacute; y los criterios de b&uacute;squeda
	 * @param criteriaSearch
	 *            Estructura que contiene los criterios de b&uacute;squeda
	 * @param nombreCampoAliasPadre
	 *            Nombre del alias del objeto CriteriaQuery padre
	 * @param nombreCampoPadre
	 *            Nombre del campo de la relaci&ocute;n que ser&aacute; tomado
	 *            para agregar la relaci&oacute;n en el objeto
	 *            <code>qbcSelect</code>
	 * @param enableLike
	 *            Especifica si las b&uacute;squedas de campos tipo cadena se
	 *            realizar&aacute;n utilizando el operador <code>LIKE</code> en
	 *            el que se hace una coincidencia de cualquier lugar de la
	 *            cadena.
	 * @param excludedProperties
	 *            Especifica las propiedades que ser&aacute;n excluidas en los
	 *            criterios de b&uacute;squeda de la consulta
	 * @throws DAOException
	 */
	private <T extends Serializable, V extends Serializable> Predicate addRelations(
			T objetoVO, From<?, ?> root, Predicate where,
			CriteriaQuery<?> criteriaQuery, CriterioBusqueda criteriaSearch,
			StringBuilder nombreCampoAliasPadre,
			StringBuilder nombreCampoPadre, boolean enableLike,
			boolean enableOrder, String... excludedProperties)
			throws DAOException {
		JoinType joinType = JoinType.LEFT;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		if (criteriaBuilder == null) {
			throw new DAOException("el objeto CriteriaQuery no puede ser nulo");
		}
		generateFieldsByVO(objetoVO);
		Serializable pojo = getPOJO(objetoVO);
		Class<?> classPojo = pojo.getClass();
		Collection<Field> mappingFields = associationFieldsMapped
				.get(classPojo);
		// eliminar las propiedades que se van a excluir
		Set<String> excludedPropertySet = new LinkedHashSet<String>();
		if (excludedProperties != null && excludedProperties.length > 0) {
			excludedPropertySet = new LinkedHashSet<String>(
					excludedProperties.length);
			excludedPropertySet.addAll(Arrays.asList(excludedProperties));
		}
		boolean existEntity;
		try {
			if (mappingFields != null) {
				for (Field campo : mappingFields) {
					existEntity = false;
					if (!excludedPropertySet.contains(campo.getName())) {
						Object resultado = ClasesUtil.invocarMetodoGet(pojo,
								campo.getName());
						if (resultado == null) {
							if (objetoVO instanceof BuscadorDTO) {
								BuscadorDTO s = (BuscadorDTO) objetoVO;

								if (s.getCollectionJoin(campo.getName()) != null) {
									// establecer el nombre del alias de la
									// propiedad
									StringBuilder nombreCampoAlias = new StringBuilder(
											nombreCampoAliasPadre);
									nombreCampoAlias.append(campo.getName());
									// qbcSelect.setFetchMode(campo.getName(),
									// JoinType.JOIN);
									// qbcSelect.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
									root.join(campo.getName(), joinType);
									if (enableOrder) {
										root.fetch(campo.getName(), joinType);
									}
								}
							}
						} else {

							if (resultado instanceof Collection<?>) {
								Collection<?> resultadoCol = (Collection<?>) resultado;
								if (resultadoCol.iterator().hasNext()) {
									resultado = resultadoCol.iterator().next();
									existRelationsOneToMany = true;
									existEntity = true;
								}
							} else if (resultado instanceof Map<?, ?>) {
								if (((Map<?, ?>) resultado).entrySet()
										.iterator().hasNext()) {
									Map.Entry<?, ?> entryMap = (Map.Entry<?, ?>) ((Map<?, ?>) resultado)
											.entrySet().iterator().next();
									resultado = entryMap.getValue();
									existEntity = true;
								}
							} else {
								existEntity = true;
							}

							if (existEntity
									&& resultado instanceof Serializable
									&& !relationsHashCodeSet.contains(resultado
											.hashCode())) {
								// establecer el nombre del campo
								StringBuilder nombreCampo = new StringBuilder(
										nombreCampoPadre);
								nombreCampo.append(campo.getName());
								// establecer el nombre del alias de la
								// propiedad
								StringBuilder nombreCampoAlias = new StringBuilder(
										nombreCampoAliasPadre);
								nombreCampoAlias.append(campo.getName());

								if (campo
										.isAnnotationPresent(RelationField.class)) {
									joinType = getJoinType(campo
											.getAnnotation(RelationField.class));
								}
								From<?, ?> relationCriteria;
								StringBuilder alias = new StringBuilder(
										nombreCampoAlias.toString());

								relationCriteria = root.join(campo.getName(),
										joinType);
								if (enableOrder) {
									root.fetch(campo.getName(), joinType);
								}

								// restricciones por el resto de atributos del
								// campo de la relacion
								// /si no se ha especificado un conjunto de
								// criterios de busqueda para las entidades
								// /implicadas en la consulta
								CriterioBusqueda criteriaSearchVO = getCriteriaSearchFromVO(resultado);
								if (criteriaSearch == null
										&& criteriaSearchVO == null) {
									where = addExampleRestrictions(
											(Serializable) resultado,
											relationCriteria, where,
											alias.toString(), enableLike);
								} else if (criteriaSearchVO == null) {
									where = addExampleRestrictions(
											(Serializable) resultado,
											relationCriteria, where,
											alias.toString(), criteriaSearch,
											nombreCampo, enableLike);

								}
								// /si se ha especificado un conjunto de
								// criterios de busqueda para las entidades
								// /implicadas en la consulta
								else {
									where = addExampleRestrictions(
											(Serializable) resultado,
											relationCriteria, where,
											alias.toString(), criteriaSearchVO,
											null, enableLike);
								}
								if (enableOrder
										&& resultado instanceof BuscadorDTO) {
									establecerOrden((BuscadorDTO) resultado,
											relationCriteria, criteriaQuery);
								}
								relationsHashCodeSet.add(resultado.hashCode());
								where = addRelations((Serializable) resultado,
										relationCriteria, where, criteriaQuery,
										criteriaSearch, alias, nombreCampo,
										enableLike, enableOrder,
										excludedProperties);
							}
						}
					}

				}
			}
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (SecurityException e) {
			LogGenerico.getLogger().error("Error de seguridad", e);
		} catch (IllegalArgumentException e) {
			LogGenerico.getLogger().error("Argumentos ilegales", e);
		}
		return where;
	}

	private <T extends Serializable, V extends Serializable> void addRelations(
			T objetoVO, StringBuilder select, StringBuilder where,
			Map<String, NamedParameterQuery> params,
			CriterioBusqueda criteriaSearch, StringBuilder nombreCampoAliasPadre,
			StringBuilder nombreCampoPadre, boolean enableLike,
			boolean enableOrder, String... excludedProperties)
			throws DAOException {
		JoinType joinType = JoinType.LEFT;
		generateFieldsByVO(objetoVO);
		Serializable pojo = getPOJO(objetoVO);
		Class<?> classPojo = pojo.getClass();
		Collection<Field> mappingFields = associationFieldsMapped
				.get(classPojo);
		// eliminar las propiedades que se van a excluir
		Set<String> excludedPropertySet = new LinkedHashSet<String>();
		if (excludedProperties != null && excludedProperties.length > 0) {
			excludedPropertySet = new LinkedHashSet<String>(
					excludedProperties.length);
			excludedPropertySet.addAll(Arrays.asList(excludedProperties));
		}
		boolean existEntity;
		try {
			if (mappingFields != null) {
				for (Field campo : mappingFields) {
					existEntity = false;
					if (!excludedPropertySet.contains(campo.getName())) {
						Object resultado = ClasesUtil.invocarMetodoGet(pojo,
								campo.getName());
						if (resultado == null) {
							if (objetoVO instanceof BuscadorDTO) {
								BuscadorDTO s = (BuscadorDTO) objetoVO;

								if (s.getCollectionJoin(campo.getName()) != null) {
									// establecer el nombre del alias de la
									// propiedad
									StringBuilder nombreCampo = new StringBuilder(
											nombreCampoPadre).append(DOT);
									nombreCampo.append(campo.getName());
									StringBuilder nombreCampoAlias = new StringBuilder(
											nombreCampoAliasPadre)
											.append(ALIAS_CONCATENATOR);
									nombreCampoAlias.append(campo.getName());
									select.append(" left join ");
									if (enableOrder) {
										select.append("fetch ");
									}
									select.append(nombreCampoAliasPadre)
											.append(DOT)
											.append(campo.getName())
											.append(" ")
											.append(nombreCampoAlias);

									if (!existRelationsOneToMany) {
										select.insert("select".length(),
												" distinct");
										existRelationsOneToMany = Boolean.TRUE;
									}

								}
							}
						} else {

							if (resultado instanceof Collection<?>) {
								Collection<?> resultadoCol = (Collection<?>) resultado;
								if (resultadoCol.iterator().hasNext()) {
									resultado = resultadoCol.iterator().next();

									if (!existRelationsOneToMany) {
										select.insert("select".length(),
												" distinct");
										existRelationsOneToMany = Boolean.TRUE;
									}

									existEntity = Boolean.TRUE;
								}
							} else if (resultado instanceof Map<?, ?>) {
								if (((Map<?, ?>) resultado).entrySet()
										.iterator().hasNext()) {
									Map.Entry<?, ?> entryMap = (Map.Entry<?, ?>) ((Map<?, ?>) resultado)
											.entrySet().iterator().next();
									resultado = entryMap.getValue();
									existEntity = Boolean.TRUE;
								}
							} else {
								existEntity = Boolean.TRUE;
							}

							if (existEntity
									&& resultado instanceof Serializable
									&& !relationsHashCodeSet.contains(resultado
											.hashCode())) {
								// establecer el nombre del campo
								StringBuilder nombreCampo = new StringBuilder(
										nombreCampoPadre).append(DOT);
								nombreCampo.append(campo.getName());
								// establecer el nombre del alias de la
								// propiedad
								StringBuilder nombreCampoAlias = new StringBuilder(
										nombreCampoAliasPadre)
										.append(ALIAS_CONCATENATOR);
								nombreCampoAlias.append(campo.getName());

								if (campo
										.isAnnotationPresent(RelationField.class)) {
									joinType = getJoinType(campo
											.getAnnotation(RelationField.class));
								}
								String alias = nombreCampoAlias.toString();
								if (joinType.equals(JoinType.INNER)) {
									select.append(" inner join ");
								} else {
									select.append(" left join ");
								}
								if (enableOrder) {
									select.append("fetch ");
								}
								select.append(nombreCampoAliasPadre)
										.append(DOT).append(campo.getName())
										.append(" ").append(nombreCampoAlias);

								// restricciones por el resto de atributos del
								// campo de la relacion
								// /si no se ha especificado un conjunto de
								// criterios de busqueda para las entidades
								// /implicadas en la consulta
								CriterioBusqueda criteriaSearchVO = getCriteriaSearchFromVO(resultado);
								if (criteriaSearch == null
										&& criteriaSearchVO == null) {
									addExampleRestrictions(
											(Serializable) resultado, alias,
											where, params, enableLike);
								} else if (criteriaSearchVO == null) {
									addExampleRestrictions(
											(Serializable) resultado, alias,
											where, params, criteriaSearch,
											nombreCampo, enableLike);

								}
								// /si se ha especificado un conjunto de
								// criterios de busqueda para las entidades
								// /implicadas en la consulta
								else {
									addExampleRestrictions(
											(Serializable) resultado, alias,
											where, params, criteriaSearchVO,
											null, enableLike);
								}

								relationsHashCodeSet.add(resultado.hashCode());
								addRelations((Serializable) resultado, select,
										where, params, criteriaSearch,
										nombreCampoAlias, nombreCampo,
										enableLike, enableOrder,
										excludedProperties);
							}
						}
					}

				}
			}
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (SecurityException e) {
			LogGenerico.getLogger().error("Error de seguridad", e);
		} catch (IllegalArgumentException e) {
			LogGenerico.getLogger().error("Argumentos ilegales", e);
		}
	}

	public <T extends Serializable> Predicate addRelations(T objetoVO,
			From<?, ?> root, Predicate where, CriteriaQuery<?> qbcSelect)
			throws DAOException {
		CriterioBusqueda criteriaSearch = getCriteriaSearchFromVO(objetoVO);

		Boolean enableLike = Boolean.TRUE;
		Boolean enableOrder = Boolean.TRUE;
		return addRelations(objetoVO, root, where, qbcSelect, criteriaSearch,
				enableLike, enableOrder);

	}

	public Collection<x> findBySimpleCriteriaQuery(x plantillaBusqueda)
			throws DAOException {
		Boolean clearCache = Boolean.TRUE;
		Boolean enableLike = Boolean.TRUE;
		return findBySimpleCriteriaQuery(plantillaBusqueda, clearCache,
				enableLike);
	}

	@SuppressWarnings("unchecked")
	public Collection<x> findBySimpleCriteriaQuery(x plantillaBusqueda,
			boolean clearCache, boolean enableLike,
			String... excludedProperties) throws DAOException {
		Collection<x> registros = null;
		CriteriaQuery<?> qbcSelect = null;

		try {

			CriteriaBuilder criteriaBuilder = entityManager
					.getCriteriaBuilder();
			qbcSelect = criteriaBuilder.createQuery();
			Root<?> root = qbcSelect
					.from(getPOJO(plantillaBusqueda).getClass());
			Predicate whereRestrictions = criteriaBuilder.conjunction();
			// restricciones por el resto de atributos
			whereRestrictions = addExampleRestrictions(plantillaBusqueda, root,
					whereRestrictions, enableLike, excludedProperties);

			// orden
			establecerOrden(plantillaBusqueda, root, qbcSelect);

			// relaciones
			whereRestrictions = addRelations(plantillaBusqueda, root,
					whereRestrictions, qbcSelect,
					getCriteriaSearchFromVO(plantillaBusqueda), enableLike,
					excludedProperties);

			qbcSelect.multiselect(root);
			qbcSelect.where(whereRestrictions);

			// ejecutar consulta
			registros = (Collection<x>) entityManager.createQuery(qbcSelect)
					.getResultList();

		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return registros;
	}

	@SuppressWarnings("unchecked")
	public Collection<x> findBySimpleCriteriaQuery(x objetoVO, String nombreId,
			List<Join> joins) throws DAOException {
		Collection<x> registros = null;
		CriteriaQuery<?> qbcSelect = null;

		try {
			Boolean activarLike = Boolean.TRUE;
			CriteriaBuilder criteriaBuilder = entityManager
					.getCriteriaBuilder();
			qbcSelect = criteriaBuilder.createQuery();
			Root<?> root = qbcSelect.from(getPOJO(objetoVO).getClass());
			Predicate whereRestrictions = criteriaBuilder.conjunction();
			// restricciones por el resto de atributos
			whereRestrictions = this.addExampleRestrictions(objetoVO, root,
					whereRestrictions, activarLike);

			// relaciones
			// TODO: restricciones por atributos de relaciones
			if (joins != null) {
				addRelations(root, joins);
			} else {
				whereRestrictions = this.addRelations(objetoVO, root,
						whereRestrictions, qbcSelect);
			}
			qbcSelect.where(whereRestrictions);

			qbcSelect.multiselect(root);
			// ejecutar consulta
			registros = (Collection<x>) entityManager.createQuery(qbcSelect)
					.getResultList();

		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return registros;
	}

	@SuppressWarnings("unchecked")
	public Collection<x> findPage(x plantillaBusqueda, boolean enableLike,
			String... excludedProperties) throws DAOException {
		try {
			Boolean enableOrder = Boolean.TRUE;
			Query query = getQuery(plantillaBusqueda, ALIAS_ROOT, enableLike,
					enableOrder, excludedProperties);
			return query.getResultList();

		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public Query getQuery(x plantillaBusqueda, boolean enableLike,
			boolean enableOrder, String... excludedProperties)
			throws DAOException {
		return getQuery(plantillaBusqueda, ALIAS_ROOT, enableLike, enableOrder,
				excludedProperties);
	}

	public Query getQuery(x plantillaBusqueda, String selectClause,
			boolean enableLike, boolean enableOrder,
			String... excludedProperties) throws DAOException {
		try {

			StringBuilder where = new StringBuilder("");
			StringBuilder orderBy = new StringBuilder("");
			Map<String, NamedParameterQuery> params = new HashMap<String, NamedParameterQuery>();
			Object pojo = getPOJO(plantillaBusqueda);

			StringBuilder select = new StringBuilder("select ").append(
					selectClause).append(" from ");
			select.append(pojo.getClass().getName()).append(" ")
					.append(ALIAS_ROOT);
			addExampleRestrictions(plantillaBusqueda, ALIAS_ROOT, where,
					params, enableLike, excludedProperties);
			if (enableOrder) {
				establecerOrden(plantillaBusqueda, ALIAS_ROOT, orderBy);
			}

			addRelations(plantillaBusqueda, ALIAS_ROOT, select, where, params,
					plantillaBusqueda.getCriteriaSearch(), enableLike,
					enableOrder, excludedProperties);

			if (where.length() > 0) {
				select.append(" where ").append(where);
			}
			if (orderBy.length() > 0) {
				select.append(orderBy);
			}
			
			LogGenerico.getLogger().info(select.toString());
			Query query = entityManager.createQuery(select.toString());
			populateNamedParameterQuery(query, params.values());
			LogGenerico.getLogger().info("consulta:{}", select);
			return query;

		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}


	public x findUniqueBySimpleCriteriaQuery(x plantillaBusqueda)
			throws DAOException {
		Boolean clearCache = Boolean.TRUE;
		Boolean enableLike = Boolean.TRUE;
		return findUniqueBySimpleCriteriaQuery(plantillaBusqueda, clearCache,enableLike);
	}

	public x findUnique(x plantillaBusqueda) throws DAOException {
		Boolean clearCache = Boolean.TRUE;
		Boolean enableLike = Boolean.TRUE;
		return findUnique(plantillaBusqueda, clearCache, enableLike);
	}

	public x findUniqueBySimpleCriteriaQuery(x plantillaBusqueda,
			CriterioBusqueda criteriaSearch) throws DAOException {
		Boolean clearCache = Boolean.TRUE;
		Boolean enableLike = Boolean.TRUE;
		return findUniqueBySimpleCriteriaQuery(plantillaBusqueda,
				criteriaSearch, clearCache, enableLike);
	}

	@SuppressWarnings("unchecked")
	public x findUniqueBySimpleCriteriaQuery(x plantillaBusqueda,
			boolean clearCache, boolean enableLike,
			String... excludedProperties) throws DAOException {
		x registro = null;
		CriteriaQuery<?> qbcSelect = null;

		try {

			CriteriaBuilder criteriaBuilder = entityManager
					.getCriteriaBuilder();

			Class<?> classPojo = getPOJO(plantillaBusqueda).getClass();
			qbcSelect = criteriaBuilder.createQuery(classPojo);
			//Select * from classPojo
			Root<?> root = qbcSelect.from(classPojo);
			//Agregar restricciones
			Predicate p = criteriaBuilder.conjunction();
			this.addExampleRestrictions(plantillaBusqueda, root, p, enableLike,
					excludedProperties);

			Boolean enableOrder = Boolean.TRUE;
			// relaciones
			this.addRelations(plantillaBusqueda, root, p, qbcSelect,
					getCriteriaSearchFromVO(plantillaBusqueda), enableLike,
					enableOrder, excludedProperties);

			Query query = entityManager.createQuery(qbcSelect);

			// ejecutar consulta
			registro = (x) query.getSingleResult();

		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return registro;
	}

	@SuppressWarnings("unchecked")
	public x findUnique(x plantillaBusqueda, boolean clearCache,
			boolean enableLike, String... excludedProperties)
			throws DAOException {
		try {
			Boolean enableOrder = Boolean.TRUE;
			Query query = getQuery(plantillaBusqueda, ALIAS_ROOT, enableLike,
					enableOrder, excludedProperties);
			return (x) query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public x findUniqueBySimpleCriteriaQuery(x plantillaBusqueda,
			CriterioBusqueda criteriaSearch, boolean clearCache,
			boolean enableLike, String... excludedProperties)
			throws DAOException {
		x registro = null;
		CriteriaQuery<?> qbcSelect = null;

		try {
			CriteriaBuilder criteriaBuilder = entityManager
					.getCriteriaBuilder();

			Class<?> classPojo = getPOJO(plantillaBusqueda).getClass();
			qbcSelect = criteriaBuilder.createQuery(classPojo);
			Root<?> root = qbcSelect.from(classPojo);
			Predicate p = criteriaBuilder.conjunction();
			this.addExampleRestrictions(plantillaBusqueda, root, p,
					criteriaSearch, new StringBuilder(""), enableLike,
					excludedProperties);

			Boolean enableOrder = Boolean.TRUE;
			// relaciones
			this.addRelations(plantillaBusqueda, root, p, qbcSelect,
					criteriaSearch, enableLike, enableOrder, excludedProperties);

			Query query = entityManager.createQuery(qbcSelect);

			// ejecutar consulta
			registro = (x) query.getSingleResult();

		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return registro;
	}

	public <T> Collection<T> findFieldValueBySimpleCriteriaQuery(
			String propertyForObtainValue, x plantillaBusqueda,
			boolean clearCache, boolean enableLike,
			String... excludedProperties) throws DAOException {

		return this.findFieldValueBySimpleCriteriaQuery(propertyForObtainValue,
				Boolean.FALSE, plantillaBusqueda, clearCache, enableLike,
				excludedProperties);
	}

	@SuppressWarnings("unchecked")
	public <T> Collection<T> findFieldValueBySimpleCriteriaQuery(
			String propertyForObtainValue, boolean applyDistinct,
			x plantillaBusqueda, boolean clearCache, boolean enableLike,
			String... excludedProperties) throws DAOException {
		CriteriaQuery<?> qbcFieldValue = null;
		try {

			CriteriaBuilder criteriaBuilder = entityManager
					.getCriteriaBuilder();

			Class<?> classPojo = getPOJO(plantillaBusqueda).getClass();
			qbcFieldValue = criteriaBuilder.createQuery(classPojo);
			Root<?> root = qbcFieldValue.from(classPojo);
			Predicate p = criteriaBuilder.conjunction();
			this.addExampleRestrictions(plantillaBusqueda, root, p, enableLike,
					excludedProperties);

			Boolean enableOrder = Boolean.TRUE;
			// relaciones
			this.addRelations(plantillaBusqueda, root, p, qbcFieldValue,
					getCriteriaSearchFromVO(plantillaBusqueda), enableLike,
					enableOrder, excludedProperties);

			qbcFieldValue.multiselect(root.get(propertyForObtainValue));
			qbcFieldValue.distinct(applyDistinct);

			Query query = entityManager.createQuery(qbcFieldValue);

			return query.getResultList();

		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public Object findMaxValueBySimpleCriteriaQuery(
			String propertyForObtainMaxValue, x plantillaBusqueda,
			boolean clearCache, boolean enableLike,
			String... excludedProperties) throws DAOException {
		CriteriaQuery<?> qbcMaxValue = null;
		try {

			CriteriaBuilder criteriaBuilder = entityManager
					.getCriteriaBuilder();

			Class<?> classPojo = getPOJO(plantillaBusqueda).getClass();
			qbcMaxValue = criteriaBuilder.createQuery(classPojo);
			Root<?> root = qbcMaxValue.from(classPojo);
			Predicate p = criteriaBuilder.conjunction();
			this.addExampleRestrictions(plantillaBusqueda, root, p, enableLike,
					excludedProperties);

			Boolean enableOrder = Boolean.FALSE;
			// relaciones
			this.addRelations(plantillaBusqueda, root, p, qbcMaxValue,
					getCriteriaSearchFromVO(plantillaBusqueda), enableLike,
					enableOrder, excludedProperties);

			qbcMaxValue.multiselect(criteriaBuilder.max(root.get(
					propertyForObtainMaxValue).as(Number.class)));

			Query query = entityManager.createQuery(qbcMaxValue);

			return query.getSingleResult();

		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public Object findSumValueBySimpleCriteriaQuery(
			String propertyForObtainSumValue, x plantillaBusqueda,
			boolean clearCache, boolean enableLike,
			String... excludedProperties) throws DAOException {
		CriteriaQuery<?> qbcMaxValue = null;
		try {
			CriteriaBuilder criteriaBuilder = entityManager
					.getCriteriaBuilder();

			Class<?> classPojo = getPOJO(plantillaBusqueda).getClass();
			qbcMaxValue = criteriaBuilder.createQuery(classPojo);
			Root<?> root = qbcMaxValue.from(classPojo);
			Predicate p = criteriaBuilder.conjunction();
			this.addExampleRestrictions(plantillaBusqueda, root, p, enableLike,
					excludedProperties);
			Boolean enableOrder = Boolean.FALSE;
			// relaciones
			this.addRelations(plantillaBusqueda, root, p, qbcMaxValue,
					getCriteriaSearchFromVO(plantillaBusqueda), enableLike,
					enableOrder, excludedProperties);

			qbcMaxValue.multiselect(criteriaBuilder.sum(root.get(
					propertyForObtainSumValue).as(Number.class)));

			Query query = entityManager.createQuery(qbcMaxValue);

			return query.getSingleResult();

		} catch (PersistenceException e) {
			throw convertDAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public DAOException convertDAOException(PersistenceException ex) {

		if (ex instanceof EntityExistsException) {
			return new DAOException(GenericoMensajes.getString("genericoDao.mensajes.error.uniqueConstraint"),ex);
		}
		if (ex instanceof NonUniqueResultException) {
			return new DAOException(GenericoMensajes.getString("genericoDao.mensajes.error.nonUnique"),ex);
		}

		return new DAOException(ex);
	}

	public void detach(x searchDTO) throws DAOException {
		this.entityManager.detach(this.getPOJO(searchDTO));
	}
}