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
 * Ttipocomprobante generated by hbm2java
 */
@Entity
@Table(name = "ttipocomprobante", schema = "public")
public class Ttipocomprobante implements java.io.Serializable {

	private int idtipocomprobante;
	private Tusuario tusuario;
	private Byte codigocomprobante;
	private String nombrecomprobante;
	private String estadocomprobante;
	private String usuariomodificacion;
	private Date fecharegistro;
	private Date fechamodificacion;
	private Set<Tcomprobante> tcomprobantes = new HashSet<Tcomprobante>(0);

	public Ttipocomprobante() {
	}

	public Ttipocomprobante(int idtipocomprobante) {
		this.idtipocomprobante = idtipocomprobante;
	}

	public Ttipocomprobante(int idtipocomprobante, Tusuario tusuario, Byte codigocomprobante, String nombrecomprobante,
			String estadocomprobante, String usuariomodificacion, Date fecharegistro, Date fechamodificacion,
			Set<Tcomprobante> tcomprobantes) {
		this.idtipocomprobante = idtipocomprobante;
		this.tusuario = tusuario;
		this.codigocomprobante = codigocomprobante;
		this.nombrecomprobante = nombrecomprobante;
		this.estadocomprobante = estadocomprobante;
		this.usuariomodificacion = usuariomodificacion;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
		this.tcomprobantes = tcomprobantes;
	}

	@Id

	@Column(name = "idtipocomprobante", unique = true, nullable = false, precision = 9, scale = 0)
	public int getIdtipocomprobante() {
		return this.idtipocomprobante;
	}

	public void setIdtipocomprobante(int idtipocomprobante) {
		this.idtipocomprobante = idtipocomprobante;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioregistro")
	public Tusuario getTusuario() {
		return this.tusuario;
	}

	public void setTusuario(Tusuario tusuario) {
		this.tusuario = tusuario;
	}

	@Column(name = "codigocomprobante", precision = 2, scale = 0)
	public Byte getCodigocomprobante() {
		return this.codigocomprobante;
	}

	public void setCodigocomprobante(Byte codigocomprobante) {
		this.codigocomprobante = codigocomprobante;
	}

	@Column(name = "nombrecomprobante", length = 64)
	public String getNombrecomprobante() {
		return this.nombrecomprobante;
	}

	public void setNombrecomprobante(String nombrecomprobante) {
		this.nombrecomprobante = nombrecomprobante;
	}

	@Column(name = "estadocomprobante", length = 4)
	public String getEstadocomprobante() {
		return this.estadocomprobante;
	}

	public void setEstadocomprobante(String estadocomprobante) {
		this.estadocomprobante = estadocomprobante;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ttipocomprobante")
	public Set<Tcomprobante> getTcomprobantes() {
		return this.tcomprobantes;
	}

	public void setTcomprobantes(Set<Tcomprobante> tcomprobantes) {
		this.tcomprobantes = tcomprobantes;
	}

}
