package com.lduran.algafood.domain.repository;

import java.util.List;

import com.lduran.algafood.domain.model.Cozinha;

public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>
{
	List<Cozinha> findByNome(String nome);

	List<Cozinha> findByNomeContaining(String nome);
}
