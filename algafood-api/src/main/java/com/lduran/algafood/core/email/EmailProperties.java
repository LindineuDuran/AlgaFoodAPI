package com.lduran.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties
{
	private Implementacao impl = Implementacao.FAKE;
	private Sandbox sandbox = new Sandbox();

	@NotNull
	private String remetente;

	public enum Implementacao
	{
		SMTP, FAKE, SANDBOX
	}

	@Getter
	@Setter
	public class Sandbox
	{
		private String destinatario;
	}
}
