package com.lduran.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lduran.algafood.domain.model.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long>
{
	List<Cozinha> findByNome(String nome);

	List<Cozinha> findByNomeContaining(String nome);
}
