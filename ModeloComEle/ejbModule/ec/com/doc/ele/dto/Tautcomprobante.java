package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:33 by Hibernate Tools 4.0.0.Final

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ec.com.doc.ele.pojo.BuscadorDTO;

/**
 * Tautcomprobante generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tautcomprobante", schema = "public")
public class Tautcomprobante extends BuscadorDTO {

	private int idautcomp;
	private Terroresautorizacionsri terroresautorizacionsri;
	private Tcomprobante tcomprobante;
	private Tclavescontingencia tclavescontingencia;
	private Tusuario tusuario;
	private Tfirmaelectronica tfirmaelectronica;
	private BigDecimal clavedeacceso;
	private BigDecimal numautorizacion;
	private String estado;
	private String usuariomodificacion;
	private Date fecharegistro;
	private Date fechamodificacion;

	public Tautcomprobante() {
	}

	public Tautcomprobante(int idautcomp) {
		this.idautcomp = idautcomp;
	}

	public Tautcomprobante(int idautcomp, Terroresautorizacionsri terroresautorizacionsri, Tcomprobante tcomprobante,
			Tclavescontingencia tclavescontingencia, Tusuario tusuario, Tfirmaelectronica tfirmaelectronica,
			BigDecimal clavedeacceso, BigDecimal numautorizacion, String estado, String usuariomodificacion,
			Date fecharegistro, Date fechamodificacion) {
		this.idautcomp = idautcomp;
		this.terroresautorizacionsri = terroresautorizacionsri;
		this.tcomprobante = tcomprobante;
		this.tclavescontingencia = tclavescontingencia;
		this.tusuario = tusuario;
		this.tfirmaelectronica = tfirmaelectronica;
		this.clavedeacceso = clavedeacceso;
		this.numautorizacion = numautorizacion;
		this.estado = estado;
		this.usuariomodificacion = usuariomodificacion;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
	}

	@Id

	@Column(name = "idautcomp", unique = true, nullable = false, precision = 9, scale = 0)
	public int getIdautcomp() {
		return this.idautcomp;
	}

	public void setIdautcomp(int idautcomp) {
		this.idautcomp = idautcomp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iderrorsri")
	public Terroresautorizacionsri getTerroresautorizacionsri() {
		return this.terroresautorizacionsri;
	}

	public void setTerroresautorizacionsri(Terroresautorizacionsri terroresautorizacionsri) {
		this.terroresautorizacionsri = terroresautorizacionsri;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcomprobante")
	public Tcomprobante getTcomprobante() {
		return this.tcomprobante;
	}

	public void setTcomprobante(Tcomprobante tcomprobante) {
		this.tcomprobante = tcomprobante;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idclavescontingencia")
	public Tclavescontingencia getTclavescontingencia() {
		return this.tclavescontingencia;
	}

	public void setTclavescontingencia(Tclavescontingencia tclavescontingencia) {
		this.tclavescontingencia = tclavescontingencia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioregistro")
	public Tusuario getTusuario() {
		return this.tusuario;
	}

	public void setTusuario(Tusuario tusuario) {
		this.tusuario = tusuario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idfirmaelectronica")
	public Tfirmaelectronica getTfirmaelectronica() {
		return this.tfirmaelectronica;
	}

	public void setTfirmaelectronica(Tfirmaelectronica tfirmaelectronica) {
		this.tfirmaelectronica = tfirmaelectronica;
	}

	@Column(name = "clavedeacceso", precision = 49, scale = 0)
	public BigDecimal getClavedeacceso() {
		return this.clavedeacceso;
	}

	public void setClavedeacceso(BigDecimal clavedeacceso) {
		this.clavedeacceso = clavedeacceso;
	}

	@Column(name = "numautorizacion", precision = 37, scale = 0)
	public BigDecimal getNumautorizacion() {
		return this.numautorizacion;
	}

	public void setNumautorizacion(BigDecimal numautorizacion) {
		this.numautorizacion = numautorizacion;
	}

	@Column(name = "estado", length = 4)
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

}
