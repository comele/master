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
 * Ttipodeducible generated by hbm2java
 */
@Entity
@Table(name = "ttipodeducible", schema = "public")
public class Ttipodeducible implements java.io.Serializable {

	private int idtipodeducible;
	private Tusuario tusuario;
	private String nombrededucible;
	private String estadodeducible;
	private String usuariomodificacion;
	private Date fecharegistro;
	private Date fechamodificacion;
	private Set<Tdetallefactext> tdetallefactexts = new HashSet<Tdetallefactext>(0);
	private Set<Tdeduciblefactura> tdeduciblefacturas = new HashSet<Tdeduciblefactura>(0);
	private Set<Tdeducibles> tdeducibleses = new HashSet<Tdeducibles>(0);

	public Ttipodeducible() {
	}

	public Ttipodeducible(int idtipodeducible) {
		this.idtipodeducible = idtipodeducible;
	}

	public Ttipodeducible(int idtipodeducible, Tusuario tusuario, String nombrededucible, String estadodeducible,
			String usuariomodificacion, Date fecharegistro, Date fechamodificacion,
			Set<Tdetallefactext> tdetallefactexts, Set<Tdeduciblefactura> tdeduciblefacturas,
			Set<Tdeducibles> tdeducibleses) {
		this.idtipodeducible = idtipodeducible;
		this.tusuario = tusuario;
		this.nombrededucible = nombrededucible;
		this.estadodeducible = estadodeducible;
		this.usuariomodificacion = usuariomodificacion;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
		this.tdetallefactexts = tdetallefactexts;
		this.tdeduciblefacturas = tdeduciblefacturas;
		this.tdeducibleses = tdeducibleses;
	}

	@Id

	@Column(name = "idtipodeducible", unique = true, nullable = false, precision = 9, scale = 0)
	public int getIdtipodeducible() {
		return this.idtipodeducible;
	}

	public void setIdtipodeducible(int idtipodeducible) {
		this.idtipodeducible = idtipodeducible;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioregistro")
	public Tusuario getTusuario() {
		return this.tusuario;
	}

	public void setTusuario(Tusuario tusuario) {
		this.tusuario = tusuario;
	}

	@Column(name = "nombrededucible", length = 64)
	public String getNombrededucible() {
		return this.nombrededucible;
	}

	public void setNombrededucible(String nombrededucible) {
		this.nombrededucible = nombrededucible;
	}

	@Column(name = "estadodeducible", length = 64)
	public String getEstadodeducible() {
		return this.estadodeducible;
	}

	public void setEstadodeducible(String estadodeducible) {
		this.estadodeducible = estadodeducible;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ttipodeducible")
	public Set<Tdetallefactext> getTdetallefactexts() {
		return this.tdetallefactexts;
	}

	public void setTdetallefactexts(Set<Tdetallefactext> tdetallefactexts) {
		this.tdetallefactexts = tdetallefactexts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ttipodeducible")
	public Set<Tdeduciblefactura> getTdeduciblefacturas() {
		return this.tdeduciblefacturas;
	}

	public void setTdeduciblefacturas(Set<Tdeduciblefactura> tdeduciblefacturas) {
		this.tdeduciblefacturas = tdeduciblefacturas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ttipodeducible")
	public Set<Tdeducibles> getTdeducibleses() {
		return this.tdeducibleses;
	}

	public void setTdeducibleses(Set<Tdeducibles> tdeducibleses) {
		this.tdeducibleses = tdeducibleses;
	}

}
