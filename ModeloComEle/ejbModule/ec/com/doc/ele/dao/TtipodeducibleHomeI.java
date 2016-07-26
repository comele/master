package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Ttipodeducible;

public interface TtipodeducibleHomeI {

	void persist(Ttipodeducible transientInstance);
	
	void remove(Ttipodeducible persistentInstance);
	
	Ttipodeducible merge(Ttipodeducible detachedInstance);
	
	Ttipodeducible findById(int id);
}
