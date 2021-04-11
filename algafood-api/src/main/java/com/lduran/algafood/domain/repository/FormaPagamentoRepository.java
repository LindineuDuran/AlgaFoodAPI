package com.lduran.algafood.domain.repository;

import java.util.List;

import com.lduran.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository
{
	List<FormaPagamento> todas();

	FormaPagamento porId(long id);

	FormaPagamento adicionar(FormaPagamento formaPagamento);

	void remover(FormaPagamento formaPagamento);
}
