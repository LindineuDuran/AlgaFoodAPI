package com.lduran.algafoodapi.injdep.model;

/**
 * @author User
 *
 */
public class Cliente
{
	private String Nome;
	private String email;
	private String telefone;
	private boolean ativo = false;

	/**
	 * @param nome
	 * @param email
	 * @param telefone
	 * @param ativo
	 */
	public Cliente(String nome, String email, String telefone)
	{
		super();
		Nome = nome;
		this.email = email;
		this.telefone = telefone;
	}

	/**
	 * @return the nome
	 */
	public String getNome()
	{
		return Nome;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone()
	{
		return telefone;
	}

	/**
	 * @return the ativo
	 */
	public boolean isAtivo()
	{
		return ativo;
	}

	public void ativar()
	{
		this.ativo = true;
	}
}