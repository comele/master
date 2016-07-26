package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Ttipodocumento;

public interface TtipodocumentoHomeI {

	void persist(Ttipodocumento transientInstance);
	
	void remove(Ttipodocumento persistentInstance);
	
	Ttipodocumento merge(Ttipodocumento detachedInstance);
	
	Ttipodocumento findById(int id);
}
