package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tpuntoemision;

public interface TpuntoemisionHomeI {

	void persist(Tpuntoemision transientInstance);
	
	void remove(Tpuntoemision persistentInstance);
	
	Tpuntoemision merge(Tpuntoemision detachedInstance);
	
	Tpuntoemision findById(int id);
}
