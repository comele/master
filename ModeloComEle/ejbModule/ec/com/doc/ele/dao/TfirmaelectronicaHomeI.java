package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tfirmaelectronica;

public interface TfirmaelectronicaHomeI {

	void persist(Tfirmaelectronica transientInstance);
	
	void remove(Tfirmaelectronica persistentInstance);
	
	Tfirmaelectronica merge(Tfirmaelectronica detachedInstance);
	
	Tfirmaelectronica findById(int id);
}
