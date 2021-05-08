package com.lduran.algafood.domain.repository;

import java.util.List;

import com.lduran.algafood.domain.model.Cozinha;

public interface CozinhaRepository
{
	List<Cozinha> todas();

	Cozinha porId(long id);

	boolean existe(long id);

	Cozinha adicionar(Cozinha cozinha);

	void remover(Cozinha cozinha);
}
