package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:33 by Hibernate Tools 4.0.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Terroresautorizacionsri generated by hbm2java
 */
@Entity
@Table(name = "terroresautorizacionsri", schema = "public")
public class Terroresautorizacionsri implements java.io.Serializable {

	private int iderrorsri;
	private String codigoerrorsri;
	private String descripcionerrorsri;
	private String posiblesolucionerrorsri;
	private Integer usuarioregistro;
	private String usuariomodificacion;
	private Date fecharegistro;
	private Date fechamodificacion;
	private Set<Tautcomprobante> tautcomprobantes = new HashSet<Tautcomprobante>(0);

	public Terroresautorizacionsri() {
	}

	public Terroresautorizacionsri(int iderrorsri) {
		this.iderrorsri = iderrorsri;
	}

	public Terroresautorizacionsri(int iderrorsri, String codigoerrorsri, String descripcionerrorsri,
			String posiblesolucionerrorsri, Integer usuarioregistro, String usuariomodificacion, Date fecharegistro,
			Date fechamodificacion, Set<Tautcomprobante> tautcomprobantes) {
		this.iderrorsri = iderrorsri;
		this.codigoerrorsri = codigoerrorsri;
		this.descripcionerrorsri = descripcionerrorsri;
		this.posiblesolucionerrorsri = posiblesolucionerrorsri;
		this.usuarioregistro = usuarioregistro;
		this.usuariomodificacion = usuariomodificacion;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
		this.tautcomprobantes = tautcomprobantes;
	}

	@Id

	@Column(name = "iderrorsri", unique = true, nullable = false, precision = 9, scale = 0)
	public int getIderrorsri() {
		return this.iderrorsri;
	}

	public void setIderrorsri(int iderrorsri) {
		this.iderrorsri = iderrorsri;
	}

	@Column(name = "codigoerrorsri", length = 10)
	public String getCodigoerrorsri() {
		return this.codigoerrorsri;
	}

	public void setCodigoerrorsri(String codigoerrorsri) {
		this.codigoerrorsri = codigoerrorsri;
	}

	@Column(name = "descripcionerrorsri", length = 200)
	public String getDescripcionerrorsri() {
		return this.descripcionerrorsri;
	}

	public void setDescripcionerrorsri(String descripcionerrorsri) {
		this.descripcionerrorsri = descripcionerrorsri;
	}

	@Column(name = "posiblesolucionerrorsri", length = 400)
	public String getPosiblesolucionerrorsri() {
		return this.posiblesolucionerrorsri;
	}

	public void setPosiblesolucionerrorsri(String posiblesolucionerrorsri) {
		this.posiblesolucionerrorsri = posiblesolucionerrorsri;
	}

	@Column(name = "usuarioregistro", precision = 9, scale = 0)
	public Integer getUsuarioregistro() {
		return this.usuarioregistro;
	}

	public void setUsuarioregistro(Integer usuarioregistro) {
		this.usuarioregistro = usuarioregistro;
	}

	@Column(name = "usuariomodificacion", length = 64)
	public String getUsuariomodificacion() {
		return this.usuariomodificacion;
	}

	public void setUsuariomodificacion(String usuariomodificacion) {
		this.usuariomodificacion = usuariomodificacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecharegistro", length = 8)
	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechamodificacion", length = 8)
	public Date getFechamodificacion() {
		return this.fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "terroresautorizacionsri")
	public Set<Tautcomprobante> getTautcomprobantes() {
		return this.tautcomprobantes;
	}

	public void setTautcomprobantes(Set<Tautcomprobante> tautcomprobantes) {
		this.tautcomprobantes = tautcomprobantes;
	}

}