package com.lduran.algafood.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lduran.algafood.domain.model.Estado;

public abstract class CidadeMixin
{
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
}
