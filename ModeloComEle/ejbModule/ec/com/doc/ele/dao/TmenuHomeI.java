package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tmenu;

public interface TmenuHomeI {

	void persist(Tmenu transientInstance);
	
	void remove(Tmenu persistentInstance);
	
	Tmenu merge(Tmenu detachedInstance);
	
	Tmenu findById(int id);
}
