package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:33 by Hibernate Tools 4.0.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Testablecimiento generated by hbm2java
 */
@Entity
@Table(name = "testablecimiento", schema = "public")
public class Testablecimiento implements java.io.Serializable {

	private int idestablecimiento;
	private Tusuario tusuario;
	private String nombreestablecimiento;
	private String codigoestablecimiento;
	private String usuariomodificacion;
	private Date fecharegistro;
	private Date fechamodificacion;
	private Set<Tpuntoemision> tpuntoemisions = new HashSet<Tpuntoemision>(0);

	public Testablecimiento() {
	}

	public Testablecimiento(int idestablecimiento, String codigoestablecimiento) {
		this.idestablecimiento = idestablecimiento;
		this.codigoestablecimiento = codigoestablecimiento;
	}

	public Testablecimiento(int idestablecimiento, Tusuario tusuario, String nombreestablecimiento,
			String codigoestablecimiento, String usuariomodificacion, Date fecharegistro, Date fechamodificacion,
			Set<Tpuntoemision> tpuntoemisions) {
		this.idestablecimiento = idestablecimiento;
		this.tusuario = tusuario;
		this.nombreestablecimiento = nombreestablecimiento;
		this.codigoestablecimiento = codigoestablecimiento;
		this.usuariomodificacion = usuariomodificacion;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
		this.tpuntoemisions = tpuntoemisions;
	}

	@Id

	@Column(name = "idestablecimiento", unique = true, nullable = false, precision = 9, scale = 0)
	public int getIdestablecimiento() {
		return this.idestablecimiento;
	}

	public void setIdestablecimiento(int idestablecimiento) {
		this.idestablecimiento = idestablecimiento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioregistro")
	public Tusuario getTusuario() {
		return this.tusuario;
	}

	public void setTusuario(Tusuario tusuario) {
		this.tusuario = tusuario;
	}

	@Column(name = "nombreestablecimiento", length = 128)
	public String getNombreestablecimiento() {
		return this.nombreestablecimiento;
	}

	public void setNombreestablecimiento(String nombreestablecimiento) {
		this.nombreestablecimiento = nombreestablecimiento;
	}

	@Column(name = "codigoestablecimiento", nullable = false, length = 64)
	public String getCodigoestablecimiento() {
		return this.codigoestablecimiento;
	}

	public void setCodigoestablecimiento(String codigoestablecimiento) {
		this.codigoestablecimiento = codigoestablecimiento;
	}

	@Column(name = "usuariomodificacion", length = 64)
	public String getUsuariomodificacion() {
		return this.usuariomodificacion;
	}

	public void setUsuariomodificacion(String usuariomodificacion) {
		this.usuariomodificacion = usuariomodificacion;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecharegistro", length = 4)
	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechamodificacion", length = 4)
	public Date getFechamodificacion() {
		return this.fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "testablecimiento")
	public Set<Tpuntoemision> getTpuntoemisions() {
		return this.tpuntoemisions;
	}

	public void setTpuntoemisions(Set<Tpuntoemision> tpuntoemisions) {
		this.tpuntoemisions = tpuntoemisions;
	}

}
