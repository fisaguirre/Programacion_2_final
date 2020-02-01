package edu.um.ar.programacion2.ventas.jwt.exceptions;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String msg;

	public ValidationException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}
