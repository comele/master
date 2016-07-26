package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tproveedor;

public interface TproveedorHomeI {

	void persist(Tproveedor transientInstance);
	
	void remove(Tproveedor persistentInstance);
	
	Tproveedor merge(Tproveedor detachedInstance);
	
	Tproveedor findById(int id);
}
