package ec.com.doc.ele.dto;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import ec.com.doc.ele.pojo.BuscadorDTO;

@SuppressWarnings("serial")
@Entity
public class VistaModulosUsuarios extends BuscadorDTO{

	@Id
	private String idacceso;
	private String labelacceso;
	private String descripcionacceso;
	private Short orden;
	private String idaccesopadre;
	private String estado;
	private Long idusuario;
	private String url;
	private String mostrarenpanel;
	private String estiloimagen;

	@Transient
	private Collection<VistaModulosUsuarios> vistaAccesosDTOCol;
	@Transient
	private VistaModulosUsuarios vistaAccesosPadre;

	/**
	 * @return the vistaAccesosDTOCol
	 */
	public Collection<VistaModulosUsuarios> getVistaAccesosDTOCol() {
		return vistaAccesosDTOCol;
	}
	/**
	 * @param vistaAccesosDTOCol the vistaAccesosDTOCol to set
	 */
	public void setVistaAccesosDTOCol(
			Collection<VistaModulosUsuarios> vistaAccesosDTOCol) {
		this.vistaAccesosDTOCol = vistaAccesosDTOCol;
	}

	/**
	 * @return the vistaAccesosPadre
	 */
	public VistaModulosUsuarios getVistaAccesosPadre() {
		return vistaAccesosPadre;
	}
	/**
	 * @param vistaAccesosPadre the vistaAccesosPadre to set
	 */
	public void setVistaAccesosPadre(VistaModulosUsuarios vistaAccesosPadre) {
		this.vistaAccesosPadre = vistaAccesosPadre;
	}
	public String getIdacceso() {
		return idacceso;
	}
	public void setIdacceso(String idacceso) {
		this.idacceso = idacceso;
	}
	public String getLabelacceso() {
		return labelacceso;
	}
	public void setLabelacceso(String labelacceso) {
		this.labelacceso = labelacceso;
	}
	public String getDescripcionacceso() {
		return descripcionacceso;
	}
	public void setDescripcionacceso(String descripcionacceso) {
		this.descripcionacceso = descripcionacceso;
	}
	public Short getOrden() {
		return orden;
	}
	public void setOrden(Short orden) {
		this.orden = orden;
	}
	public String getIdaccesopadre() {
		return idaccesopadre;
	}
	public void setIdaccesopadre(String idaccesopadre) {
		this.idaccesopadre = idaccesopadre;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Long getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(Long idusuario) {
		this.idusuario = idusuario;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMostrarenpanel() {
		return mostrarenpanel;
	}
	public void setMostrarenpanel(String mostrarenpanel) {
		this.mostrarenpanel = mostrarenpanel;
	}
	public String getEstiloimagen() {
		return estiloimagen;
	}
	public void setEstiloimagen(String estiloimagen) {
		this.estiloimagen = estiloimagen;
	}
}
