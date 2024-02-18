package es.netmind.mypersonalbankapi.exceptions;

public class CuentaException extends Exception{
    private final ErrorCode code;

	public CuentaException(ErrorCode code) {
		super();
		this.code = code;
	}

	public CuentaException(String message, ErrorCode code) {
		super(message);
		this.code = code;
	}

	public ErrorCode getCode() {
		return this.code;
	}
}
