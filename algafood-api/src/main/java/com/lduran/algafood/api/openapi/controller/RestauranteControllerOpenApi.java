package com.lduran.algafood.api.openapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lduran.algafood.api.exceptionHandler.Problem;
import com.lduran.algafood.api.model.RestauranteModel;
import com.lduran.algafood.api.model.input.RestauranteInputModel;
import com.lduran.algafood.api.model.view.RestauranteView;
import com.lduran.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi
{
	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
					          name = "projecao", paramType = "query", type = "string")
	})
	@JsonView(RestauranteView.Resumo.class)
	public ResponseEntity<List<RestauranteModel>> listar();

	//@JsonView(RestauranteView.ApenasNome.class)
	@ApiOperation(value = "Lista restaurantes", hidden = true)
	public ResponseEntity<List<RestauranteModel>> listarApenasNomes();

	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	public RestauranteModel buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Cadastra um restaurante")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Restaurante cadastrado"),
	})
	public RestauranteModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
	 								  RestauranteInputModel restauranteInput);

	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Restaurante atualizado"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	public RestauranteModel atualizar( @ApiParam(value = "ID de um restaurante", example = "1", required = true)
	                                   Long restauranteId,

									   @ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", required = true)
			                           RestauranteInputModel restauranteInput);

	@ApiOperation("Remover um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante excluído"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	public void remover(Long restauranteId);

	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	public void ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Ativa múltiplos restaurantes")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	})
	public void ativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
						         List<Long> restauranteIds);

	@ApiOperation("Inativa um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	public void inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Inativa múltiplos restaurantes")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	})
	public void inativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
							      List<Long> restauranteIds);

	@ApiOperation("Abre um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	public void abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true)
					  Long restauranteId);

	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	public void fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)
					   Long restauranteId);
}
