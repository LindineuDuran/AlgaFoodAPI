package com.lduran.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
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

	@GetMapping
	public ResponseEntity<List<Restaurante>> listar()
	{
//		List<Restaurante> restaurantes = cadastroRestaurante.listar();
//
//		System.out.println(restaurantes.get(0).getNome());
//		restaurantes.get(0).getFormasPagamento().forEach(System.out::println);
//		System.out.println("O nome da cozinha é ");
//		System.out.println(restaurantes.get(0).getCozinha().getNome());

//		return ResponseEntity.ok(restaurantes);

		return ResponseEntity.ok(cadastroRestaurante.listar());
	}

	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId)
	{
		if (true)
		{
			throw new IllegalArgumentException("Teste");
		}

		return cadastroRestaurante.buscar(restauranteId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante)
	{
		try
		{
			return cadastroRestaurante.salvar(restaurante);
		}
		catch (CozinhaNaoEncontradaException e)
		{
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante)
	{
		try
		{
			Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);

			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
					"produtos");

			return cadastroRestaurante.salvar(restauranteAtual);
		}
		catch (CozinhaNaoEncontradaException e)
		{
			throw new NegocioException(e.getMessage());
		}
	}

	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos,
			HttpServletRequest request)
	{
		Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);

		merge(campos, restauranteAtual, request);

		return atualizar(restauranteId, restauranteAtual);
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
//		System.out.println(restauranteOrigem);

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

	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId)
	{
		cadastroRestaurante.remover(restauranteId);
	}
}