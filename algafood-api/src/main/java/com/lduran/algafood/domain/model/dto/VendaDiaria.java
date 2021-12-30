package com.lduran.algafood.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VendaDiaria
{
	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturado;
}
