package com.lduran.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lduran.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
		JpaSpecificationExecutor<Restaurante>
{
	@Override
	@Query("from Restaurante r join r.cozinha")
	List<Restaurante> findAll();

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

//	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> buscarNomeCozinhaId(String nome, @Param("id") Long cozinhaID);

//	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaID);

	Optional<Restaurante> findFirstByNomeContaining(String nome);

	List<Restaurante> findTop2ByNomeContaining(String nome);
}
