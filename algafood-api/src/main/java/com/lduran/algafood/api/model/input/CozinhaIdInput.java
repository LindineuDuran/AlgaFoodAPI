package com.lduran.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaIdInput
{
	@NotNull
	@ApiModelProperty(example = "1", required = true)
	private Long id;
}
