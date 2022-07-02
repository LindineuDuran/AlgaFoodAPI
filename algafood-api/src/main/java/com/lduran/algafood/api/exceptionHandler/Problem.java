package com.lduran.algafood.api.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@ApiModel("Problema")
@Getter
@Builder
public class Problem
{
	@ApiModelProperty(example = "400", position = 1)
	private Integer status;

	@ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = 5)
	private OffsetDateTime timestamp;

	@ApiModelProperty(example = "https://github.com/LindineuDuran?tab=repositories", position = 10)
	private String type;

	@ApiModelProperty(example = "Dados inválidos", position = 15)
	private String title;

	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto.", position = 20)
	private String detail;

	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto.", position = 25)
	private String userMessage;

	@ApiModelProperty(example = "Lista de objetos ou campos que geraram o erro (optional)", position = 30)
	private List<Object> objects;

	@Getter
	@Builder
	@ApiModel("ObjetoProblema")
	public static class Object
	{
		@ApiModelProperty(example = "preço")
		private String name;

		@ApiModelProperty(example = "O preço é obrigatório")
		private String userMessage;
	}
}
