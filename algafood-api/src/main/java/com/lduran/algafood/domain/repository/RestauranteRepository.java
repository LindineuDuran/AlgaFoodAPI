package com.lduran.algafood.domain.repository;

import java.util.List;

import com.lduran.algafood.domain.model.Restaurante;

public interface RestauranteRepository
{
	List<Restaurante> todos();

	Restaurante porId(long id);

	Restaurante adicionar(Restaurante restaurante);

	void remover(Restaurante restaurante);
}
