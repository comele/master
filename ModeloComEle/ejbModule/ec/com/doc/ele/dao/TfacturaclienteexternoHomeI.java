package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tfacturaclienteexterno;

public interface TfacturaclienteexternoHomeI {

	void persist(Tfacturaclienteexterno transientInstance);
	
	void remove(Tfacturaclienteexterno persistentInstance);
	
	Tfacturaclienteexterno merge(Tfacturaclienteexterno detachedInstance);
	
	Tfacturaclienteexterno findById(int id);
}
