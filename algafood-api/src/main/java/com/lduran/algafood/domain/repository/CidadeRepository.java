package com.lduran.algafood.domain.repository;

import java.util.List;

import com.lduran.algafood.domain.model.Cidade;

public interface CidadeRepository
{
	List<Cidade> listar();

	Cidade buscar(long id);

	Cidade salvar(Cidade cidade);

	void remover(long id);
}
