package com.lduran.algafood.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;
import com.lduran.algafood.api.model.view.RestauranteView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel
{
	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
	@ApiModelProperty(example = "1")
	private Long id;

	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
	@ApiModelProperty(example = "Thai Gourmet")
	private String nome;

	@JsonView(RestauranteView.Resumo.class)
	@ApiModelProperty(example = "12.00")
	private BigDecimal taxaFrete;

	@JsonView(RestauranteView.Resumo.class)
	private CozinhaModel cozinha;

	private Boolean ativo = Boolean.TRUE;
	private Boolean aberto = Boolean.FALSE;
	private EnderecoModel endereco;
}
