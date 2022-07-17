package com.lduran.algafood.api.controller;

import com.lduran.algafood.api.assembler.UsuarioModelAssembler;
import com.lduran.algafood.api.model.UsuarioModel;
import com.lduran.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi
{
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private UsuarioModelAssembler assembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UsuarioModel>> listar(@PathVariable Long restauranteId)
	{
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);

		return ResponseEntity.ok(assembler.toCollectionModel(restaurante.getResponsaveis()));
	}

	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId)
	{
		cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
	}

	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId)
	{
		cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
	}
}