package com.lduran.algafood.client.model.inputs;

import lombok.Data;

@Data
public class EnderecoInput
{
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private CidadeIdInput cidade;
}