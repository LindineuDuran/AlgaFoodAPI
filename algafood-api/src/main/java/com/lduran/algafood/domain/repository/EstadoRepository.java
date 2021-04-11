package com.lduran.algafood.domain.repository;

import java.util.List;

import com.lduran.algafood.domain.model.Estado;

public interface EstadoRepository
{
	List<Estado> todos();

	Estado porId(long id);

	Estado adicionar(Estado estado);

	void remover(Estado estado);
}
