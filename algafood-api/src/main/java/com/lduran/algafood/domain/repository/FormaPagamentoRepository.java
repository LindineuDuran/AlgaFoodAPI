package com.lduran.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lduran.algafood.domain.model.FormaPagamento;

import java.time.OffsetDateTime;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>
{
	@Query("select max(dataAtualizacao) from FormaPagamento")
	OffsetDateTime getDataUltimaAtualizacao();

	@Query("select dataAtualizacao from FormaPagamento where id = :formaPagamentoId")
	OffsetDateTime getDataUltimaAtualizacaoById(Long formaPagamentoId);
}
