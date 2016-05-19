package ec.com.doc.ele.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

@SuppressWarnings("serial")
public class BaseVO<x extends Serializable> extends BuscadorDTO{

	private x baseDTO;
	
	private x baseDTOAnterior;
	
	private String evento;
	
	private String filaModificada;
	
	private Collection<String> mensajesError;
	
	private Collection<String> mensajesInfo;
	
	
	public BaseVO() {
		super();
	}

	public BaseVO(x baseDTO) {
		super();
		this.baseDTO = baseDTO;
	}


	/**
	 * @return the baseDTO
	 */
	public x getBaseDTO() {
		return baseDTO;
	}

	/**
	 * @param baseDTO the baseDTO to set
	 */
	public void setBaseDTO(x baseDTO) {
		this.baseDTO = baseDTO;
	}

	/**
	 * @return the evento
	 */
	public String getEvento() {
		return evento;
	}

	/**
	 * @param evento the evento to set
	 */
	public void setEvento(String evento) {
		this.evento = evento;
	}
	
	

	/**
	 * @return the mensajesError
	 */
	public Collection<String> getMensajesError() {
		return mensajesError;
	}

	/**
	 * @param mensajesError the mensajesError to set
	 */
	public void setMensajesError(Collection<String> mensajesError) {
		this.mensajesError = mensajesError;
	}

	/**
	 * @return the mensajesInfo
	 */
	public Collection<String> getMensajesInfo() {
		return mensajesInfo;
	}

	/**
	 * @param mensajesInfo the mensajesInfo to set
	 */
	public void setMensajesInfo(Collection<String> mensajesInfo) {
		this.mensajesInfo = mensajesInfo;
	}

	public Serializable getPOJO(){
		return this.getBaseDTO();
	}

	/**
	 * @return the baseDTOAnterior
	 */
	public x getBaseDTOAnterior() {
		return baseDTOAnterior;
	}

	/**
	 * @param baseDTOAnterior the baseDTOAnterior to set
	 */
	public void setBaseDTOAnterior(x baseDTOAnterior) {
		this.baseDTOAnterior = baseDTOAnterior;
	}
	
	public void addError(String error){
		if(mensajesError == null){
			mensajesError = new LinkedHashSet<String>();
		}
		mensajesError.add(error);
	}
	
	public boolean getHayMensajesErrores(){
		return mensajesError != null && (!mensajesError.isEmpty());
	}
	
	public boolean getHayMensajesInformativos(){
		return mensajesInfo != null && (!mensajesInfo.isEmpty());
	}
	
	
	public void addInfo(String info){
		if(mensajesInfo == null){
			mensajesInfo = new LinkedHashSet<String>();
		}
		mensajesInfo.add(info);
	}

	public String getFilaModificada() {
		return filaModificada;
	}

	public void setFilaModificada(String filaModificada) {
		this.filaModificada = filaModificada;
	}
}
