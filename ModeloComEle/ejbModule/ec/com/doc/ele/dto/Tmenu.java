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

import ec.com.doc.ele.pojo.BuscadorDTO;

/**
 * Tmenu generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tmenu", schema = "public")
public class Tmenu extends BuscadorDTO {

	private Long idmenu;
	private Tusuario tusuario;
	private String descripcionmenu;
	private String nombremenu;
	private String estadomenu;
	private String usuariomodificacion;
	private Date fecharegistro;
	private Date fechamodificacion;
	private Set<Tusuariomenu> tusuariomenus = new HashSet<Tusuariomenu>(0);
	private Tmenu menu;
	public Tmenu() {
	}

	public Tmenu(Long idmenu) {
		this.idmenu = idmenu;
	}

	public Tmenu(Long idmenu, Tusuario tusuario, String descripcionmenu, String nombremenu, String estadomenu,
			String usuariomodificacion, Date fecharegistro, Date fechamodificacion, Set<Tusuariomenu> tusuariomenus) {
		this.idmenu = idmenu;
		this.tusuario = tusuario;
		this.descripcionmenu = descripcionmenu;
		this.nombremenu = nombremenu;
		this.estadomenu = estadomenu;
		this.usuariomodificacion = usuariomodificacion;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
		this.tusuariomenus = tusuariomenus;
	}

	@Id

	@Column(name = "idmenu", unique = true, nullable = false, precision = 9, scale = 0)
	public Long getIdmenu() {
		return this.idmenu;
	}

	public void setIdmenu(Long idmenu) {
		this.idmenu = idmenu;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioregistro")
	public Tusuario getTusuario() {
		return this.tusuario;
	}

	public void setTusuario(Tusuario tusuario) {
		this.tusuario = tusuario;
	}

	@Column(name = "descripcionmenu", length = 128)
	public String getDescripcionmenu() {
		return this.descripcionmenu;
	}

	public void setDescripcionmenu(String descripcionmenu) {
		this.descripcionmenu = descripcionmenu;
	}

	@Column(name = "nombremenu", length = 128)
	public String getNombremenu() {
		return this.nombremenu;
	}

	public void setNombremenu(String nombremenu) {
		this.nombremenu = nombremenu;
	}

	@Column(name = "estadomenu", length = 128)
	public String getEstadomenu() {
		return this.estadomenu;
	}

	public void setEstadomenu(String estadomenu) {
		this.estadomenu = estadomenu;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tmenu")
	public Set<Tusuariomenu> getTusuariomenus() {
		return this.tusuariomenus;
	}

	public void setTusuariomenus(Set<Tusuariomenu> tusuariomenus) {
		this.tusuariomenus = tusuariomenus;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigomodulopadre")
	public Tmenu getTmenu() {
		return menu;
	}

	public void setTmenu(Tmenu menu) {
		this.menu = menu;
	}

}
