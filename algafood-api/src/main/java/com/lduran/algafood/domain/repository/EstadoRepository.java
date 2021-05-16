package com.lduran.algafood.domain.repository;

import java.util.List;

import com.lduran.algafood.domain.model.Estado;

public interface EstadoRepository
{
	List<Estado> listar();

	Estado buscar(long id);

	Estado salvar(Estado estado);

	void remover(long id);
}
