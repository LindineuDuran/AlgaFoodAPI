package com.lduran.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lduran.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>
{

}
