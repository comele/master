package ec.com.doc.ele.util.log;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Extensi&oacute;n de PatternParser para reconocer conversiones de caractes
 * adicionales.
 * @author walvarez
 */
public class LogPatternParser extends PatternParser {

	int counter = 0;

	String sistema;

	/**
	 * Crea un resolvedor de conversi&oacute;n de caracteres dado un sistema.
	 * @param sistema
	 * @param pattern
	 */
	public LogPatternParser(String sistema, String pattern) {
		super(pattern);
		this.sistema = sistema;
	}

	/**
	 * Resuelve que cuando encuentre el patr&oacute;n %s, tiene que escribir
	 * el nombre del sistema.
	 * @see org.apache.log4j.helpers.PatternParser#finalizeConverter(char)
	 */
	public void finalizeConverter(char c) {
		if (c == 's') {
			addConverter(new SystemPatternConverter(formattingInfo));
			currentLiteral.setLength(0);
		} else {
			super.finalizeConverter(c);
		}
	}

	/**
	 * Convertidor de patrones de caracteres para un sistema
	 */
	private class SystemPatternConverter extends PatternConverter {
		SystemPatternConverter(FormattingInfo formattingInfo) {
			super(formattingInfo);
		}

		public String convert(LoggingEvent event) {
			return sistema;
		}
	}
}
