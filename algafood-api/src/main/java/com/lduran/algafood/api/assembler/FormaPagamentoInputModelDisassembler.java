package com.lduran.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.input.FormaPagamentoInputModel;
import com.lduran.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputModelDisassembler
{
	@Autowired
	private ModelMapper modelMapper;

	public FormaPagamento toDomainObject(FormaPagamentoInputModel formaPagamentoInput)
	{
		return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
	}

	public void copyToDomainObject(FormaPagamentoInputModel formaPagamentoInput, FormaPagamento formaPagamento)
	{
		modelMapper.map(formaPagamentoInput, formaPagamento);
	}

}
