package ec.com.doc.ele.util.log;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Extensi&oacute;n de PatternLayout del API Log4j para reconocer conversiones de caracteres
 * adicionales para el formato de los logs.
 * @author walvarez
 */
public class LogPatternLayout extends Layout {
	private static final String CONVERSION_PATTERN = "%d{yyyy-MM-dd HH:mm:ss.SS} [%s] [%5p] [%c] : %m%n";

	private String sistema;

	protected final int BUF_SIZE = 256;

	protected final int MAX_CAPACITY = 1024;

	private StringBuffer sbuf = new StringBuffer(BUF_SIZE);

	private String pattern;

	private PatternConverter head;

	public LogPatternLayout(String sistema) {
		this(sistema, CONVERSION_PATTERN);

	}

	/**
	 * Constructor que indica el patr&oacute;n de conversion de caracteres dado el sistema utilizado
	 */
	public LogPatternLayout(String sistema, String pattern) {
		this.sistema = sistema;
		this.pattern = pattern;
		head = createPatternParser(
				(pattern == null) ? CONVERSION_PATTERN : pattern).parse();
	}

	/**
	 * Establece la opci&oacute;n <b>ConversionPattern</b>. Esta es la cadena que controla
	 * el formato de los logs.
	 */
	public void setConversionPattern(String conversionPattern) {
		pattern = conversionPattern;
		head = createPatternParser(conversionPattern).parse();
	}

	/**
	 * Retorna la opci&oacute;n de <b>ConversionPattern</b>.
	 */
	public String getConversionPattern() {
		return pattern;
	}

	/**
	 * No existen opciones que activar 
	 */
	public void activateOptions() {}

	/**
	 * No manipular el contenido lanzado como error dentro de
	 * {@link LoggingEvent LoggingEvents}. Este retorna <code>true</code>.
	 * 
	 * @since 0.8.4
	 */
	public boolean ignoresThrowable() {
		return true;
	}
	
	

	/**
	 * Obtiene el nombre del sistema al que pertenece el patr&oacute;n de conversi&oacute;n
	 * @return el sistema
	 */
	public String getSistema() {
		return sistema;
	}

	/**
	 * Establece el nombre del sistema al que pertenece el patr&oacute;n de conversi&oacute;n
	 * @param sistema el sistema a establecer
	 */
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	/**
	 * Retorna PatternParser utilizado para resolver la conversi&oacute;n de cadenas
	 */
	protected PatternParser createPatternParser(String pattern) {
		return new LogPatternParser(sistema, pattern);
	}

	/**
	 * Produce cadenas de texto formateadas con el patr&oacute;n de conversi&oacute;n
	 */
	public String format(LoggingEvent event) {
		// Reset working stringbuffer
		if (sbuf.capacity() > MAX_CAPACITY) {
			sbuf = new StringBuffer(BUF_SIZE);
		} else {
			sbuf.setLength(0);
		}

		PatternConverter c = head;

		while (c != null) {
			c.format(sbuf, event);
			c = c.next;
		}
		return sbuf.toString();
	}

	public static void main(String[] args) {

		LogPatternLayout layout = new LogPatternLayout("CORP",
				"%d{yyyy-MM-dd HH:mm:ss.SS} [%s] [%5p] [%c] : %m%n");
		Logger logger = Logger.getLogger("some.cat");
		logger.addAppender(new ConsoleAppender(layout,
				ConsoleAppender.SYSTEM_OUT));
		logger.debug("Hello, log");
		logger.info("Hello again...");
	}
}
