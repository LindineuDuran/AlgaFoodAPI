package com.lduran.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService
{
	void armazenar(NovaFoto novaFoto);

	InputStream recuperar(String nomeArquivo);

	void remover(String nomeArquivo);

	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto)
	{
		this.armazenar(novaFoto);

		if (nomeArquivoAntigo != null)
		{
			this.remover(nomeArquivoAntigo);
		}
	}

	default String gerarNomeArquivo(String nomeOriginal)
	{
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}

	@Builder
	@Getter
	class NovaFoto
	{
		private String nomeArquivo;
		private InputStream inputStream;
	}
}
