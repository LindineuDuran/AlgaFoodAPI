package com.lduran.algafood.domain.repository;

import java.util.List;

import com.lduran.algafood.domain.model.Cidade;

public interface CidadeRepository
{
	List<Cidade> todas();

	Cidade porId(long id);

	Cidade adicionar(Cidade cidade);

	void remover(Cidade cidade);
}
