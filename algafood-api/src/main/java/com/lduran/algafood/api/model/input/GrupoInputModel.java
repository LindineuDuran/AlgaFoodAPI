package com.lduran.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GrupoInputModel
{
	@NotBlank
	@ApiModelProperty(example = "Gerente", required = true)
	private String nome;
}
