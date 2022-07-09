package com.lduran.algafood.domain.filter;

import java.time.OffsetDateTime;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoFilter
{
	@ApiModelProperty(example = "1", value = "ID do cliente para filtro da pesquisa")
	private Long clienteId;

	@ApiModelProperty(example = "1", value = "ID do restaurante para filtro da pesquisa")
	private Long restauranteId;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	@ApiModelProperty(example = "2019-10-30T00:00:00Z",
			          value = "Data/hora de criação inicial para filtro da pesquisa")
	private OffsetDateTime dataCriacaoInicio;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	@ApiModelProperty(example = "2019-11-01T10:00:00Z",
			          value = "Data/hora de criação final para filtro da pesquisa")
	private OffsetDateTime dataCriacaoFim;
}
