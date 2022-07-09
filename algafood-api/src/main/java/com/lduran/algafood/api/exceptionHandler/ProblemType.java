package com.lduran.algafood.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType
{
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"), ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro invalido"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado");

	private final String title;
	private final String uri;

	/**
	 * @param title
	 * @param uri
	 */
	ProblemType(String path, String title)
	{
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}
}
