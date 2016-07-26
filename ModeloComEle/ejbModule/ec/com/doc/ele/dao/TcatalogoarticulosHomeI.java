package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tcatalogoarticulos;

public interface TcatalogoarticulosHomeI {

	void persist(Tcatalogoarticulos transientInstance);
	
	void remove(Tcatalogoarticulos persistentInstance);
	
	Tcatalogoarticulos merge(Tcatalogoarticulos detachedInstance);
	
	Tcatalogoarticulos findById(int id);
}
