package com.lduran.algafood.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInputModel
{
	@NotNull
	@ApiModelProperty(example = "1", required = true)
	private Long produtoId;

	@NotNull
	@PositiveOrZero
	@ApiModelProperty(example = "2", required = true)
	private Integer quantidade;

	@ApiModelProperty(example = "Menos picante, por favor")
	private String observacao;
}
