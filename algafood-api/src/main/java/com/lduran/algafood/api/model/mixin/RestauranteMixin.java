package com.lduran.algafood.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.model.Endereco;
import com.lduran.algafood.domain.model.FormaPagamento;
import com.lduran.algafood.domain.model.Produto;

public abstract class RestauranteMixin
{
	@JsonIgnore
	private OffsetDateTime dataCadastro;

	@JsonIgnore
	private OffsetDateTime dataAtualizacao;

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;

	@JsonIgnore
	private Endereco endereco;

	@JsonIgnore
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();
}
