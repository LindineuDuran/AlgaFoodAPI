package com.lduran.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.lduran.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>
{

}