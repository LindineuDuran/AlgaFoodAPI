package com.lduran.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInputModel
{
	@NotBlank
	@ApiModelProperty(example = "Espetinho de Cupim", required = true)
	private String nome;

	@NotBlank
	@ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete", required = true)
	private String descricao;

	@NotNull
	@PositiveOrZero
	@ApiModelProperty(example = "12.50", required = true)
	private BigDecimal preco;

	@NotNull
	@ApiModelProperty(example = "true", required = true)
	private Boolean ativo;
}
