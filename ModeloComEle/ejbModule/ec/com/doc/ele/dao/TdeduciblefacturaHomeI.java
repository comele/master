package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tdeduciblefactura;

public interface TdeduciblefacturaHomeI {

	void persist(Tdeduciblefactura transientInstance);
	
	void remove(Tdeduciblefactura persistentInstance);
	
	Tdeduciblefactura merge(Tdeduciblefactura detachedInstance);
	
	Tdeduciblefactura findById(int id);
}
