package com.lduran.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInputModel
{
	@Valid
	@NotNull
	private RestauranteIdInput restaurante;

	@Valid
	@NotNull
	private EnderecoInputModel enderecoEntrega;

	@Valid
	@NotNull
	private FormaPagamentoIdInput formaPagamento;

	@Valid
	@Size(min = 1)
	@NotNull
	private List<ItemPedidoInputModel> itens;
}
