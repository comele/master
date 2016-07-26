package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Testablecimiento;

public interface TestablecimientoHomeI {

	void persist(Testablecimiento transientInstance);
	
	void remove(Testablecimiento persistentInstance);
	
	Testablecimiento merge(Testablecimiento detachedInstance);
	
	Testablecimiento findById(int id);
}
