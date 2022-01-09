package com.lduran.algafood.infrastructure.service.storage;

public class StorageException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public StorageException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public StorageException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

}
