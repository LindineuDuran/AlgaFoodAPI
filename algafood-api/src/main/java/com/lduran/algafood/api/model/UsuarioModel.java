package com.lduran.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel
{
	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty("João da Silva")
	private String nome;

	@ApiModelProperty(example = "joao.ger@algafood.com.br")
	private String email;
}
