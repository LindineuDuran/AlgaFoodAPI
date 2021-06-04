package com.lduran.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
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
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId)
	{
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);

		if (restaurante != null)
		{
			return ResponseEntity.ok(restaurante);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante)
	{
		try
		{
			restaurante = cadastroRestaurante.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		}
		catch (EntidadeNaoEncontradaException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante)
	{
		try
		{
			Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);

			if (restauranteAtual != null)
			{
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco",
						"dataCadastro", "produtos");
				restauranteAtual = cadastroRestaurante.salvar(restauranteAtual);
				return ResponseEntity.ok(restauranteAtual);
			}

			return ResponseEntity.notFound().build();
		}
		catch (EntidadeNaoEncontradaException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos)
	{
		try
		{
			Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);

			if (restauranteAtual == null)
			{
				return ResponseEntity.notFound().build();
			}

			merge(campos, restauranteAtual);

			return atualizar(restauranteId, restauranteAtual);

		}
		catch (EntidadeNaoEncontradaException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * @param dadosOrigem
	 */
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino)
	{
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		System.out.println(restauranteOrigem);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) ->
		{
			Field campo = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			campo.setAccessible(true);

			Object novoValor = ReflectionUtils.getField(campo, restauranteOrigem);

			System.out.println(nomePropriedade + "=" + valorPropriedade + "=" + novoValor);

			ReflectionUtils.setField(campo, restauranteDestino, novoValor);
		});
	}

	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId)
	{
		try
		{
			cadastroRestaurante.remover(restauranteId);
			return ResponseEntity.noContent().build();
		}
		catch (EntidadeNaoEncontradaException e)
		{
			return ResponseEntity.notFound().build();
		}
		catch (EntidadeEmUsoException e)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}