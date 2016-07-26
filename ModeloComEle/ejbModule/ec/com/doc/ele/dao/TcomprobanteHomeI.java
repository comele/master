package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tcomprobante;

public interface TcomprobanteHomeI {
	
	void persist(Tcomprobante transientInstance);
	
	void remove(Tcomprobante persistentInstance);
	
	Tcomprobante merge(Tcomprobante detachedInstance);
	
	Tcomprobante findById(int id);

}
