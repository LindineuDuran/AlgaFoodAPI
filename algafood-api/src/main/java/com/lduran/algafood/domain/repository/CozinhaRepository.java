package com.lduran.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lduran.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>
{
	List<Cozinha> findByNome(String nome);

	List<Cozinha> findByNomeContaining(String nome);
}
