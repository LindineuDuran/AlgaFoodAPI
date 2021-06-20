package com.lduran.algafood.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType
{
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"), ERRO_NEGOCIO("/erro-negocio", "Erro de negócio"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel");

	private String title;
	private String uri;

	/**
	 * @param title
	 * @param uri
	 */
	private ProblemType(String path, String title)
	{
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}
}
