package com.lduran.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lduran.algafood.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>
{

}
