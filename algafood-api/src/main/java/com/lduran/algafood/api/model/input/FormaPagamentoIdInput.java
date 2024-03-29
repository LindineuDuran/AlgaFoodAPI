package com.lduran.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoIdInput
{
	@NotNull
	@ApiModelProperty(example = "1", required = true)
	private Long id;
}
