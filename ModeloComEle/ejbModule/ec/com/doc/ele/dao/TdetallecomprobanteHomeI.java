package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tdetallecomprobante;

public interface TdetallecomprobanteHomeI {

	void persist(Tdetallecomprobante transientInstance);
	
	void remove(Tdetallecomprobante persistentInstance);
	
	Tdetallecomprobante merge(Tdetallecomprobante detachedInstance);
	
	Tdetallecomprobante findById(int id);
}
