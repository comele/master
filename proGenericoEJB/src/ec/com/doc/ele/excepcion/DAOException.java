package ec.com.doc.ele.excepcion;

@SuppressWarnings("serial")
public class DAOException extends Exception{
	/**
	 * 
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(String message,Throwable cause) {
		super(message,cause);
	}
	
	public DAOException(Throwable cause) {
		super(cause);
	}
}
