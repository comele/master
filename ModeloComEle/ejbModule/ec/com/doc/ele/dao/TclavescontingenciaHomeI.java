package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tclavescontingencia;

public interface TclavescontingenciaHomeI {
	
	void persist(Tclavescontingencia transientInstance);
	
	void remove(Tclavescontingencia persistentInstance);
	
	Tclavescontingencia merge(Tclavescontingencia detachedInstance);
	
	Tclavescontingencia findById(int id);

}
