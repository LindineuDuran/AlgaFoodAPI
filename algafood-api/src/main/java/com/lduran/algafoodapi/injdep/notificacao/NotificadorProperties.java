package com.lduran.algafoodapi.injdep.notificacao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("notificador.email")
public class NotificadorProperties
{
	private String hostServidor;
	private int portaServidor = 25;

	public String getHostServidor()
	{
		return hostServidor;
	}

	public void setHostServidor(String hostServidor)
	{
		this.hostServidor = hostServidor;
	}

	public int getPortaServidor()
	{
		return portaServidor;
	}

	public void setPortaServidor(int portaServidor)
	{
		this.portaServidor = portaServidor;
	}
}
