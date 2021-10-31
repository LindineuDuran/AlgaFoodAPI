package com.lduran.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lduran.algafood.api.assembler.RestauranteInputModelDisAssembler;
import com.lduran.algafood.api.assembler.RestauranteModelAssembler;
import com.lduran.algafood.api.model.RestauranteModel;
import com.lduran.algafood.api.model.input.RestauranteInputModel;
import com.lduran.algafood.core.validation.ValidacaoException;
import com.lduran.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.lduran.algafood.domain.exception.NegocioException;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController
{
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private SmartValidator validator;

	@Autowired
	private RestauranteModelAssembler assembler;

	@Autowired
	private RestauranteInputModelDisAssembler disassembler;

	@GetMapping
	public ResponseEntity<List<RestauranteModel>> listar()
	{
		return ResponseEntity.ok(assembler.toCollectionModel(cadastroRestaurante.listar()));
	}

	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId)
	{
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);

		return assembler.toModel(restaurante);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInputModel restauranteInput)
	{
		try
		{
			Restaurante restaurante = disassembler.toDomainObject(restauranteInput);
			return assembler.toModel(cadastroRestaurante.salvar(restaurante));
		}
		catch (CozinhaNaoEncontradaException e)
		{
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInputModel restauranteInput)
	{
		try
		{
			Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);

			disassembler.copyToDomainObject(restauranteInput, restauranteAtual);

			return assembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		}
		catch (CozinhaNaoEncontradaException e)
		{
			throw new NegocioException(e.getMessage());
		}
	}

	@PatchMapping("/{restauranteId}")
	public RestauranteModel atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos,
			HttpServletRequest request)
	{
		Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);

		merge(campos, restauranteAtual, request);

		validate(restauranteAtual, "restaurante");

		return atualizar(restauranteId, assembler.toInputModel(restauranteAtual));
	}

	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId)
	{
		cadastroRestaurante.remover(restauranteId);
	}

	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId)
	{
		cadastroRestaurante.ativar(restauranteId);
	}

	@DeleteMapping("/{restauranteId}/inativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId)
	{
		cadastroRestaurante.inativar(restauranteId);
	}

	private void validate(Restaurante restaurante, String objectName)
	{
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);

		validator.validate(restaurante, bindingResult);

		if (bindingResult.hasErrors())
		{
			throw new ValidacaoException(bindingResult);
		}
	}

	/**
	 * @param dadosOrigem
	 */
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request)
	{
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

		try
		{
			ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

			dadosOrigem.forEach((nomePropriedade, valorPropriedade) ->
			{
				Field campo = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				campo.setAccessible(true);

				Object novoValor = ReflectionUtils.getField(campo, restauranteOrigem);

				System.out.println(nomePropriedade + "=" + valorPropriedade + "=" + novoValor);

				ReflectionUtils.setField(campo, restauranteDestino, novoValor);
			});
		}
		catch (IllegalArgumentException e)
		{
			Throwable rootCause = ExceptionUtils.getRootCause(e);

			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
}