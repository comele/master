package ec.com.doc.ele.util.log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.helpers.LogLog;
import org.slf4j.ILoggerFactory;
import org.slf4j.impl.Log4jLoggerFactory;

import ec.com.doc.ele.recursos.GenericoMensajes;


/**
 * Utilitario para realizar logs por sistema, los sistemas admitidos
 * tienen que estar registrados en el archivo de propiedades Logging.properties
 * @author walvarez
 *
 */
public class LogComun {
	private static Map<String,org.slf4j.Logger> logMap = new HashMap<String,org.slf4j.Logger>();	
	private static final String FORMATO_GLOBAL_LAYOUT = "%d{yyyy-MM-dd HH:mm:ss.SS} [%p] [%C.%M(#%L)] : %m%n";
	private static final String FORMATO_SYSTEMA_LAYOUT = "%d{yyyy-MM-dd HH:mm:ss.SS} [%s] [%p] [%C.%M(#%L)] : %m%n";
	private static final String RUTA_LOGS_PREDETERMINADA = System.getProperty("user.home")+"/Logs";
	private static final ILoggerFactory logFactory = new Log4jLoggerFactory();
	
	/**
	 * Configura el log global que utiliza el log4j, entre las configuraciones globales estan
	 * el archivo de log utilizado, el nivel de log global, tama&ntilde;o del archivo, n&uacute;mero de backups de los logs
	 */
	protected static void configureLog(){
		Logger logger = Logger.getRootLogger();
		
		PatternLayout layout = new PatternLayout(FORMATO_GLOBAL_LAYOUT);
		
		//Agregar logs en la consola estandar
		
		//Agregar logs en un archivo
		String nombreLogSistema = GenericoMensajes.getString("log.sistema.ComEle");
		String tamanioArchivoLog = "2MB";		
		Integer numeroArchivosLog = 5;
		String rutaArchivosLog =null;
		
		rutaArchivosLog =   GenericoMensajes.getString("log.parametro.rutaArchivosLog");
		if(rutaArchivosLog == null || "".equals(rutaArchivosLog)){
			rutaArchivosLog = RUTA_LOGS_PREDETERMINADA;
		}
		
		String nivelLogArchivo =  "info";
		
		//Ruta donde se encontrara el archivo de log global
		String rutaArchivoSistema =rutaArchivosLog + "/"+nombreLogSistema+"/"+nombreLogSistema+".log";
		
		//establece el nivel de log por defecto para todas las categorias de log4j
		Level level = Level.toLevel(nivelLogArchivo);
		logger.setLevel(level);
		
		//Crea un log que escribe a un archivo
		RollingFileAppender fileAppender = createRollingFileAppender(nombreLogSistema,rutaArchivoSistema,numeroArchivosLog,tamanioArchivoLog);		
		fileAppender.setLayout(layout);
		fileAppender.setThreshold(level);
		fileAppender.activateOptions();		
		logger.addAppender(fileAppender);
		LogLog.debug("Nivel de log Global:" + fileAppender.getThreshold());
		
		//crear los directorios necesarios para los logs
		crearDirectorios(rutaArchivoSistema);
		
	}

	/**
	 * Crea los directorios donde se ubicar&aacute;n los logs
	 * @param rutaArchivoSistema
	 */
	private static void crearDirectorios(String rutaArchivoSistema) {
		LogLog.debug("====================================================================");
		LogLog.debug("Ruta sistema: " + rutaArchivoSistema);
		String parentName = new File(rutaArchivoSistema).getParent();
        if (parentName != null) {
           File parentDir = new File(parentName);
           if(!parentDir.exists() && parentDir.mkdirs()) {
        	   LogLog.debug("La ruta de ubicacion de logs ha sido creada");
           }
           else{
        	   LogLog.debug("La ruta de ubicacion de logs existe");
           }
        }
        LogLog.debug("====================================================================");
	}
	
	/**
	 * Obtener el generador de logs dado el sistema
	 * @param sistema, indica en que archivo debe imprimirse los logs seg&uacute;n el nombre del sistema
	 * @return Log de commons loggin de apache para poder realizar los logs
	 */
	public static synchronized org.slf4j.Logger getLog(String sistema){
		//obtener el nombre del log dado el sistema
		org.slf4j.Logger log = null;
		if(sistema == null){
			log = logFactory.getLogger("");
		}
		else {
			//obtener el log del sistema
			log = logMap.get(sistema);
			//si no existe, procede a crear el log
			if(log == null){
				Logger logger = Logger.getLogger(sistema);
				logger.setAdditivity(false);
				//obtiene el tamanio maximo permitido en un archivo de log del sistema
				String tamanioArchivoLog = "2MB";
				//establece el numero maximo de backups de los para el sistema
				Integer numeroArchivosLog = 5;
				
				//Establece el lugar donde se deben ubicar los logs del sistema
				String rutaArchivosLog = GenericoMensajes.getString("log.parametro.rutaArchivosLog");
				
				//Establece el nivel de log del sistema
				String nivelLog ="info";
				
				try {
					String rutaArchivoSistema =rutaArchivosLog + "/"+sistema+"/"+sistema+".log";
					//Crear un formato para imprimir los logs del sistema
					LogPatternLayout layout = new LogPatternLayout(sistema,FORMATO_SYSTEMA_LAYOUT);
					
					//obtener el nivel de logs
					Level level = Level.toLevel(nivelLog);					
					
					//Crear el manejador de logs por archivo
					RollingFileAppender fileAppender = createRollingFileAppender(sistema,rutaArchivoSistema,numeroArchivosLog,tamanioArchivoLog);				
					fileAppender.setLayout(layout);				
					fileAppender.setThreshold(level);
					fileAppender.activateOptions();
					logger.addAppender(fileAppender);
					
					logger.setLevel(level);
					//Crea los directorios de los los
					crearDirectorios(rutaArchivoSistema); 
					
					log =  logFactory.getLogger(sistema);

					logMap.put(sistema,log);
					
				} catch (SecurityException e) {	
					logFactory.getLogger("").error("No se puede crear el log para el sistema:" + sistema,e);
					LogLog.error("No se puede crear el log para el sistema:" + sistema,e);
					
				} 
				
			}
		}
		return log;
	}
	
	/**
	 * Crea un manejador de logs por archivo
	 * @param nombreAppender Nombre del manejador de logs
	 * @param rutaArchivoSistema Ruta de los archivos de logs
	 * @param numeroArchivosLog n&uacute;mero de backups de los archivos de log
	 * @param tamanioArchivoLog Tama&ntilde;o m&aacute;ximo de cada archivo de log
	 * @return
	 */
	private static RollingFileAppender createRollingFileAppender(String nombreAppender,String rutaArchivoSistema,Integer numeroArchivosLog,String tamanioArchivoLog){
		RollingFileAppender fileAppender = new RollingFileAppender();
		fileAppender.setName(nombreAppender);				
		fileAppender.setAppend(true);
		fileAppender.setFile(rutaArchivoSistema);
//		fileAppender.setImmediateFlush(true);
		fileAppender.setMaxBackupIndex(numeroArchivosLog);
		fileAppender.setMaxFileSize(tamanioArchivoLog);			
		return fileAppender;
	}
}
