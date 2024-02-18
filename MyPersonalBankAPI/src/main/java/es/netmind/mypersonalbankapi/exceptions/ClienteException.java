package es.netmind.mypersonalbankapi.exceptions;

public class ClienteException extends Exception{
    private final ErrorCode code;

	public ClienteException(ErrorCode code) {
		super();
		this.code = code;
	}

	public ClienteException(String message, ErrorCode code) {
		super(message);
		this.code = code;
	}

	public ErrorCode getCode() {
		return this.code;
	}
}
