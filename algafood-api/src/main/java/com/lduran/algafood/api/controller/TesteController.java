package com.lduran.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.repository.CozinhaRepository;
import com.lduran.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController
{
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@GetMapping("/cozinhas/por-nome")
	public ResponseEntity<List<Cozinha>> cozinhasPorNome(@RequestParam String nome)
	{
		return ResponseEntity.ok(cozinhaRepository.findByNome(nome));
	}

	@GetMapping("/cozinhas/por-nome-parcial")
	public ResponseEntity<List<Cozinha>> buscarPorNomeParcial(@RequestParam String nome)
	{
		return ResponseEntity.ok(cozinhaRepository.findByNomeContaining(nome));
	}

	@GetMapping("/restaurantes/por-taxa-frete")
	public ResponseEntity<List<Restaurante>> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal)
	{
		return ResponseEntity.ok(restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal));
	}

	@GetMapping("/restaurantes/por-nome-e-cozinha")
	public ResponseEntity<List<Restaurante>> restaurantesPorNomeECozinha(String nome, Long cozinhaId)
	{
		return ResponseEntity.ok(restauranteRepository.buscarNomeCozinhaId(nome, cozinhaId));
	}

	@GetMapping("/restaurantes/primeiro-por-nome")
	public ResponseEntity<Restaurante> restaurantePrimeiroPorNome(String nome)
	{
		return ResponseEntity.ok(restauranteRepository.findFirstByNomeContaining(nome).get());
	}

	@GetMapping("/restaurantes/top2-por-nome")
	public ResponseEntity<List<Restaurante>> restaurantesTop2PorNome(String nome)
	{
		return ResponseEntity.ok(restauranteRepository.findTop2ByNomeContaining(nome));
	}

	@GetMapping("/restaurantes/por-nome-e-frete")
	public List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal)
	{
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}

	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome)
	{
		return restauranteRepository.findComFreteGratis(nome);
	}

	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantesPrimeiro()
	{
		return restauranteRepository.buscarPrimeiro();
	}

	@GetMapping("/cozinhas/primeira")
	public Optional<Cozinha> cozinhasPrimeira()
	{
		return cozinhaRepository.buscarPrimeiro();
	}
}
