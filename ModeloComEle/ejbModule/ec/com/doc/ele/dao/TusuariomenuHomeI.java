package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tusuariomenu;
import ec.com.doc.ele.dto.TusuariomenuId;

public interface TusuariomenuHomeI {

	void persist(Tusuariomenu transientInstance);
	
	void remove(Tusuariomenu persistentInstance);
	
	Tusuariomenu merge(Tusuariomenu detachedInstance);
	
	Tusuariomenu findById(TusuariomenuId id);
}
