package com.lduran.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInputModel
{
	@NotBlank
	@ApiModelProperty(example = "Thai Gourmet", required = true)
	private String nome;

	@NotNull
	@PositiveOrZero
	@ApiModelProperty(example = "12.00", required = true)
	private BigDecimal taxaFrete;

	@Valid
	@NotNull
	private CozinhaIdInput cozinha;

	@Valid
	@NotNull
	private EnderecoInputModel endereco;
}
