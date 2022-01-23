package com.lduran.algafood.infrastructure.service.email;

public class EmailException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public EmailException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public EmailException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

}
