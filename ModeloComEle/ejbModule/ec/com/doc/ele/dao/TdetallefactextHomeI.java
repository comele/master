package ec.com.doc.ele.dao;

import ec.com.doc.ele.dto.Tdetallefactext;

public interface TdetallefactextHomeI {

	void persist(Tdetallefactext transientInstance);
	
	void remove(Tdetallefactext persistentInstance);
	
	Tdetallefactext merge(Tdetallefactext detachedInstance);
	
	Tdetallefactext findById(int id);
}
