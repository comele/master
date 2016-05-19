package ec.com.doc.ele.servicio;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Remote(ServiceRemote.class)
@Local(ServiceLocal.class)
public class ComEleServicioImpl extends EjbServiceImpl {
	@Override
	@PersistenceContext(unitName = "prjJPA")
	public void setEntityManager(EntityManager em) {
		super.setEntityManager(em);
	}
}
