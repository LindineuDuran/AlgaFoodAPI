package com.lduran.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInputModel
{
	@ApiModelProperty(example = "Brasileira", required = true)
	@NotBlank
	private String nome;
}
