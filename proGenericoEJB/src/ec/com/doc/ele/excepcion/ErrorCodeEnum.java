package ec.com.doc.ele.excepcion;

import ec.com.doc.ele.recursos.GenericoMensajes;

public enum ErrorCodeEnum {
	CONSTRAINT_ERROR(GenericoMensajes.getString("genericoDao.mensajes.error.dataIntegrityConstraint")),
	UNIQUE_CONSTRAINT_ERROR(GenericoMensajes.getString("genericoDao.mensajes.error.uniqueConstraint"));
	ErrorCodeEnum(String message){
		this.message = message;
	}

	public String message;
}
