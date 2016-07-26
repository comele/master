package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tdeducibles;

public interface TdeduciblesHomeI {

	void persist(Tdeducibles transientInstance);
	
	void remove(Tdeducibles persistentInstance);
	
	Tdeducibles merge(Tdeducibles detachedInstance);
	
	Tdeducibles findById(int id);
}
