package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Terroresautorizacionsri;

public interface TerroresautorizacionsriHomeI {

	void persist(Terroresautorizacionsri transientInstance);
	
	void remove(Terroresautorizacionsri persistentInstance);
	
	Terroresautorizacionsri merge(Terroresautorizacionsri detachedInstance);
	
	Terroresautorizacionsri findById(int id);
}
