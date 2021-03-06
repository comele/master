package ec.com.doc.ele.dto;
// Generated 11-may-2016 8:40:33 by Hibernate Tools 4.0.0.Final

import java.math.BigDecimal;
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
 * Tcatalogoarticulos generated by hbm2java
 */
@Entity
@Table(name = "tcatalogoarticulos", schema = "public")
public class Tcatalogoarticulos implements java.io.Serializable {

	private int idarticulo;
	private Tusuario tusuario;
	private BigDecimal codigobarras;
	private String descripcionart;
	private BigDecimal preciounisiniva;
	private String aplicaiva;
	private String porivaaplicar;
	private String tipodeducible;
	private String usuariomodificacion;
	private Date fecharesgistro;
	private Date fechamodificacion;
	private Set<Tdetallecomprobante> tdetallecomprobantes = new HashSet<Tdetallecomprobante>(0);

	public Tcatalogoarticulos() {
	}

	public Tcatalogoarticulos(int idarticulo) {
		this.idarticulo = idarticulo;
	}

	public Tcatalogoarticulos(int idarticulo, Tusuario tusuario, BigDecimal codigobarras, String descripcionart,
			BigDecimal preciounisiniva, String aplicaiva, String porivaaplicar, String tipodeducible,
			String usuariomodificacion, Date fecharesgistro, Date fechamodificacion,
			Set<Tdetallecomprobante> tdetallecomprobantes) {
		this.idarticulo = idarticulo;
		this.tusuario = tusuario;
		this.codigobarras = codigobarras;
		this.descripcionart = descripcionart;
		this.preciounisiniva = preciounisiniva;
		this.aplicaiva = aplicaiva;
		this.porivaaplicar = porivaaplicar;
		this.tipodeducible = tipodeducible;
		this.usuariomodificacion = usuariomodificacion;
		this.fecharesgistro = fecharesgistro;
		this.fechamodificacion = fechamodificacion;
		this.tdetallecomprobantes = tdetallecomprobantes;
	}

	@Id

	@Column(name = "idarticulo", unique = true, nullable = false, precision = 9, scale = 0)
	public int getIdarticulo() {
		return this.idarticulo;
	}

	public void setIdarticulo(int idarticulo) {
		this.idarticulo = idarticulo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioregistro")
	public Tusuario getTusuario() {
		return this.tusuario;
	}

	public void setTusuario(Tusuario tusuario) {
		this.tusuario = tusuario;
	}

	@Column(name = "codigobarras", precision = 64, scale = 0)
	public BigDecimal getCodigobarras() {
		return this.codigobarras;
	}

	public void setCodigobarras(BigDecimal codigobarras) {
		this.codigobarras = codigobarras;
	}

	@Column(name = "descripcionart", length = 128)
	public String getDescripcionart() {
		return this.descripcionart;
	}

	public void setDescripcionart(String descripcionart) {
		this.descripcionart = descripcionart;
	}

	@Column(name = "preciounisiniva", precision = 10, scale = 4)
	public BigDecimal getPreciounisiniva() {
		return this.preciounisiniva;
	}

	public void setPreciounisiniva(BigDecimal preciounisiniva) {
		this.preciounisiniva = preciounisiniva;
	}

	@Column(name = "aplicaiva", length = 2)
	public String getAplicaiva() {
		return this.aplicaiva;
	}

	public void setAplicaiva(String aplicaiva) {
		this.aplicaiva = aplicaiva;
	}

	@Column(name = "porivaaplicar", length = 2)
	public String getPorivaaplicar() {
		return this.porivaaplicar;
	}

	public void setPorivaaplicar(String porivaaplicar) {
		this.porivaaplicar = porivaaplicar;
	}

	@Column(name = "tipodeducible", length = 64)
	public String getTipodeducible() {
		return this.tipodeducible;
	}

	public void setTipodeducible(String tipodeducible) {
		this.tipodeducible = tipodeducible;
	}

	@Column(name = "usuariomodificacion", length = 64)
	public String getUsuariomodificacion() {
		return this.usuariomodificacion;
	}

	public void setUsuariomodificacion(String usuariomodificacion) {
		this.usuariomodificacion = usuariomodificacion;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecharesgistro", length = 4)
	public Date getFecharesgistro() {
		return this.fecharesgistro;
	}

	public void setFecharesgistro(Date fecharesgistro) {
		this.fecharesgistro = fecharesgistro;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechamodificacion", length = 4)
	public Date getFechamodificacion() {
		return this.fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tcatalogoarticulos")
	public Set<Tdetallecomprobante> getTdetallecomprobantes() {
		return this.tdetallecomprobantes;
	}

	public void setTdetallecomprobantes(Set<Tdetallecomprobante> tdetallecomprobantes) {
		this.tdetallecomprobantes = tdetallecomprobantes;
	}

}
