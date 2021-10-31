package com.lduran.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInputModel
{
	@NotBlank
	private String nome;
}
