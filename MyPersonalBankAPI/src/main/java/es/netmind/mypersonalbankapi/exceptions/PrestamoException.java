package es.netmind.mypersonalbankapi.exceptions;

public class PrestamoException extends Exception{
    private final ErrorCode code;

	public PrestamoException(ErrorCode code) {
		super();
		this.code = code;
	}

	public PrestamoException(String message, ErrorCode code) {
		super(message);
		this.code = code;
	}

	public ErrorCode getCode() {
		return this.code;
	}
}
