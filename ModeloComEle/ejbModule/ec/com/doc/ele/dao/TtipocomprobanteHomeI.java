package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Ttipocomprobante;

public interface TtipocomprobanteHomeI {

	void persist(Ttipocomprobante transientInstance);
	
	void remove(Ttipocomprobante persistentInstance);
	
	Ttipocomprobante merge(Ttipocomprobante detachedInstance);
	
	Ttipocomprobante findById(int id);
}
