package com.lduran.algafood.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lduran.algafood.api.assembler.RestauranteInputModelDisassembler;
import com.lduran.algafood.api.assembler.RestauranteModelAssembler;
import com.lduran.algafood.api.model.RestauranteModel;
import com.lduran.algafood.api.model.input.RestauranteInputModel;
import com.lduran.algafood.api.model.view.RestauranteView;
import com.lduran.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.lduran.algafood.domain.exception.*;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.service.CadastroRestauranteService;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController implements RestauranteControllerOpenApi
{
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private SmartValidator validator;

	@Autowired
	private RestauranteModelAssembler assembler;

	@Autowired
	private RestauranteInputModelDisassembler disassembler;

	@JsonView(RestauranteView.Resumo.class)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RestauranteModel>> listar()
	{
		return ResponseEntity.ok(assembler.toCollectionModel(cadastroRestaurante.listar()));
	}

	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RestauranteModel>> listarApenasNomes()
	{
		return ResponseEntity.ok(assembler.toCollectionModel(cadastroRestaurante.listar()));
	}

	@GetMapping(path = "/{restauranteId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RestauranteModel buscar(@PathVariable Long restauranteId)
	{
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);

		return assembler.toModel(restaurante);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInputModel restauranteInput)
	{
		try
		{
			Restaurante restaurante = disassembler.toDomainObject(restauranteInput);
			return assembler.toModel(cadastroRestaurante.salvar(restaurante));
		}
		catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e)
		{
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping(path = "/{restauranteId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInputModel restauranteInput)
	{
		try
		{
			Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);

			disassembler.copyToDomainObject(restauranteInput, restauranteAtual);

			return assembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		}
		catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e)
		{
			throw new NegocioException(e.getMessage());
		}
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

	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds)
	{
		try
		{
			cadastroRestaurante.ativar(restauranteIds);
		}
		catch (RestauranteNaoEncontradoException e)
		{
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{restauranteId}/inativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId)
	{
		cadastroRestaurante.inativar(restauranteId);
	}

	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds)
	{
		try
		{
			cadastroRestaurante.inativar(restauranteIds);
		}
		catch (RestauranteNaoEncontradoException e)
		{
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId)
	{
		cadastroRestaurante.abrir(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId)
	{
		cadastroRestaurante.fechar(restauranteId);
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