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
			@ApiImplicitParam(value = "Nome da projeção de pedidos", name = "projecao",
					          paramType = "query", allowableValues = "apenas-nome", dataTypeClass = String.class)
	})
	@JsonView(RestauranteView.Resumo.class)
	ResponseEntity<List<RestauranteModel>> listar();

	@ApiOperation(value = "Lista restaurantes", hidden = true)
	ResponseEntity<List<RestauranteModel>> listarApenasNomes();

	@ApiOperation("Buscar um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteModel buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Cadastrar um restaurante")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Restaurante cadastrado"),
	})
	RestauranteModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
	 								  RestauranteInputModel restauranteInput);

	@ApiOperation("Atualizar um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Restaurante atualizado"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteModel atualizar( @ApiParam(value = "ID de um restaurante", example = "1", required = true)
	                                   Long restauranteId,

									   @ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", required = true)
			                           RestauranteInputModel restauranteInput);

	@ApiOperation("Remover um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante excluído"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void remover(Long restauranteId);

	@ApiOperation("Ativar um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Ativar múltiplos restaurantes")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	})
	void ativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
						         List<Long> restauranteIds);

	@ApiOperation("Inativar um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Inativar múltiplos restaurantes")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	})
	void inativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
							      List<Long> restauranteIds);

	@ApiOperation("Abrir um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true)
					  Long restauranteId);

	@ApiOperation("Fechar um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)
					   Long restauranteId);
}
