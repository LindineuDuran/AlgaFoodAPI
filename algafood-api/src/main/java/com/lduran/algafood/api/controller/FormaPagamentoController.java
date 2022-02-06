package com.lduran.algafood.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.algafood.api.assembler.FormaPagamentoInputModelDisassembler;
import com.lduran.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.lduran.algafood.api.model.FormaPagamentoModel;
import com.lduran.algafood.api.model.input.FormaPagamentoInputModel;
import com.lduran.algafood.domain.exception.EstadoNaoEncontradoException;
import com.lduran.algafood.domain.exception.NegocioException;
import com.lduran.algafood.domain.model.FormaPagamento;
import com.lduran.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController
{
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private FormaPagamentoModelAssembler assembler;

	@Autowired
	private FormaPagamentoInputModelDisassembler disassembler;

	@GetMapping
	public ResponseEntity<List<FormaPagamentoModel>> listar()
	{
		List<FormaPagamento> todasFormasPagamentos = cadastroFormaPagamento.listar();

		List<FormaPagamentoModel> formasPagamentoModel = assembler.toCollectionModel(todasFormasPagamentos);

		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS)).body(formasPagamentoModel);
	}

	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable long formaPagamentoId)
	{
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscar(formaPagamentoId);

		FormaPagamentoModel formaPagamentoModel = assembler.toModel(formaPagamento);

		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS)).body(formaPagamentoModel);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInputModel formaPagamentoInput)
	{
		try
		{
			FormaPagamento formaPagamento = disassembler.toDomainObject(formaPagamentoInput);
			return assembler.toModel(cadastroFormaPagamento.salvar(formaPagamento));
		}
		catch (EstadoNaoEncontradoException e)
		{
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoModel atualizar(@PathVariable long formaPagamentoId,
			@RequestBody @Valid FormaPagamentoInputModel formaPagamentoInput)
	{
		try
		{
			FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscar(formaPagamentoId);

			disassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

			return assembler.toModel(cadastroFormaPagamento.salvar(formaPagamentoAtual));
		}
		catch (EstadoNaoEncontradoException e)
		{
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{formaPagamentoId}")
	public void remover(@PathVariable long formaPagamentoId)
	{
		cadastroFormaPagamento.remover(formaPagamentoId);
	}
}
