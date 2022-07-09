package com.lduran.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInputModel
{
	@NotBlank
	@ApiModelProperty(example = "38400-000", required = true)
	private String cep;

	@NotBlank
	@ApiModelProperty(example = "Rua Floriano Peixoto", required = true)
	private String logradouro;

	@NotBlank
	@ApiModelProperty(example = "1500", required = true)
	private String numero;

	@ApiModelProperty(example = "Apto 901")
	private String complemento;

	@NotBlank
	@ApiModelProperty(example = "Centro", required = true)
	private String bairro;

	@Valid
	@NotNull
	private CidadeIdInput cidade;
}
