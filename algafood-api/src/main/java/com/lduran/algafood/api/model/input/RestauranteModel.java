package com.lduran.algafood.api.model.input;

import java.math.BigDecimal;

import com.lduran.algafood.api.model.CozinhaModel;
import com.lduran.algafood.api.model.EnderecoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel
{
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaModel cozinha;
	private Boolean ativo = Boolean.TRUE;
	private Boolean aberto = Boolean.FALSE;
	private EnderecoModel endereco;
}
