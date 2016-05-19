/**
 * 
 */
package ec.com.doc.ele.util.log;

import org.slf4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;


import ec.com.doc.ele.recursos.GenericoMensajes;


public class LogGenerico {
	private static final Logger LOGGER = createLogger();
	
	public LogGenerico(){
		//
	}
	
	/**
	 * 
	 * @return
	 */
	public static Logger getLogger(){
		return LOGGER;
	}

	
	/**
	 * 
	 * @param loggerName
	 * @return
	 */
	private static Logger createLogger(){
		Logger logger = null;
		
		try {
			logger = LogComun.getLog(GenericoMensajes.getString("log.sistema.ComEle"));
		} catch (Exception e){
			//
		} finally {
			if (logger == null){
				logger = new Log4jLoggerFactory().getLogger("COMELE");
			}
		}
		return logger;
	}
}
