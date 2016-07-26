package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tautcomprobante;

public interface TautcomprobanteHomeI {

	 void persist(Tautcomprobante transientInstance);
	 
	 void remove(Tautcomprobante persistentInstance);
	 
	 Tautcomprobante merge(Tautcomprobante detachedInstance);
	 
	 Tautcomprobante findById(int id);
}
