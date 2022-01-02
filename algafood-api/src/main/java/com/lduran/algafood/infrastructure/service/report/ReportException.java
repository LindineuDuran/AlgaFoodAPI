package com.lduran.algafood.infrastructure.service.report;

public class ReportException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public ReportException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ReportException(String message)
	{
		super(message);
	}
}
