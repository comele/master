package ec.com.doc.ele.locator;

import org.slf4j.Logger;

import ec.com.doc.ele.recursos.GenericoMensajes;
import ec.com.doc.ele.util.log.LogComun;


public interface ComEleLogger {
	Logger log = LogComun.getLog(GenericoMensajes.getString("log.sistema.Caritas"));

}
