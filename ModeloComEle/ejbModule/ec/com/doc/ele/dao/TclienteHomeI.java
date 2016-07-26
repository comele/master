package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tcliente;

public interface TclienteHomeI {

	void persist(Tcliente transientInstance);
	
	void remove(Tcliente persistentInstance);
	
	Tcliente merge(Tcliente detachedInstance);
	
	Tcliente findById(int id);
}
