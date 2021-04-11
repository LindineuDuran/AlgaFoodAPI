package com.lduran.algafood.domain.repository;

import java.util.List;

import com.lduran.algafood.domain.model.Permissao;

public interface PermissaoRepository
{
	List<Permissao> todas();

	Permissao porId(long id);

	Permissao adicionar(Permissao permissao);

	void remover(Permissao permissao);
}
