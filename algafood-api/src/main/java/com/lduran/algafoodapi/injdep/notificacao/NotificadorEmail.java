package com.lduran.algafoodapi.injdep.notificacao;

import com.lduran.algafoodapi.injdep.model.Cliente;

public class NotificadorEmail implements Notificador
{
	private boolean caixaAlta;
	private String hostServidorSmtp;

	public NotificadorEmail(String hostServidorSmtp)
	{
		this.hostServidorSmtp = hostServidorSmtp;
		System.out.println("NotificadorEmail");
	}

	@Override
	public void notificar(Cliente cliente, String mensagem)
	{
		if (caixaAlta)
		{
			mensagem = mensagem.toUpperCase();
		}

		System.out.printf("Notificando %s através do e-mail %s usando smtp %s: %s\n", cliente.getNome(),
				cliente.getEmail(), this.hostServidorSmtp, mensagem);
	}

	/**
	 * @param caixaAlta the caixaAlta to set
	 */
	public void setCaixaAlta(boolean caixaAlta)
	{
		this.caixaAlta = caixaAlta;
	}

}
