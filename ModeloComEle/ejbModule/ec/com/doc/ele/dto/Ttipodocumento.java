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
 * Ttipodocumento generated by hbm2java
 */
@Entity
@Table(name = "ttipodocumento", schema = "public")
public class Ttipodocumento implements java.io.Serializable {

	private int idtipodocumento;
	private Tusuario tusuario;
	private String tipodocumento;
	private String descripcion;
	private String usuariomodificacion;
	private Date fecharegistro;
	private Date fechamodificacion;
	private Set<Tcliente> tclientes = new HashSet<Tcliente>(0);

	public Ttipodocumento() {
	}

	public Ttipodocumento(int idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}

	public Ttipodocumento(int idtipodocumento, Tusuario tusuario, String tipodocumento, String descripcion,
			String usuariomodificacion, Date fecharegistro, Date fechamodificacion, Set<Tcliente> tclientes) {
		this.idtipodocumento = idtipodocumento;
		this.tusuario = tusuario;
		this.tipodocumento = tipodocumento;
		this.descripcion = descripcion;
		this.usuariomodificacion = usuariomodificacion;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
		this.tclientes = tclientes;
	}

	@Id

	@Column(name = "idtipodocumento", unique = true, nullable = false, precision = 9, scale = 0)
	public int getIdtipodocumento() {
		return this.idtipodocumento;
	}

	public void setIdtipodocumento(int idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioregistro")
	public Tusuario getTusuario() {
		return this.tusuario;
	}

	public void setTusuario(Tusuario tusuario) {
		this.tusuario = tusuario;
	}

	@Column(name = "tipodocumento", length = 64)
	public String getTipodocumento() {
		return this.tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	@Column(name = "descripcion", length = 64)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ttipodocumento")
	public Set<Tcliente> getTclientes() {
		return this.tclientes;
	}

	public void setTclientes(Set<Tcliente> tclientes) {
		this.tclientes = tclientes;
	}

}
