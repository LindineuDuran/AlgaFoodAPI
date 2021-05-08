package com.lduran.algafood.domain.repository;

import java.util.List;

import com.lduran.algafood.domain.model.Cozinha;

public interface CozinhaRepository
{
	List<Cozinha> listar();

	Cozinha buscar(long id);

	boolean existe(long id);

	Cozinha salvar(Cozinha cozinha);

	void remover(long id);
}
