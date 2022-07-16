package com.lduran.algafood.api.openapi.controller;

import com.lduran.algafood.api.exceptionHandler.Problem;
import com.lduran.algafood.api.model.GrupoModel;
import com.lduran.algafood.api.model.input.GrupoInputModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi
{
	@ApiOperation("Lista os grupos")
	ResponseEntity<List<GrupoModel>> listar();

	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	GrupoModel buscar(@ApiParam(value = "ID de um grupo", example = "1", required = true) long grupoId);

	@ApiOperation("Cadastra um grupo")
	@ApiResponses({@ApiResponse(code = 201, message = "Grupo cadastrado")})
	GrupoModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true)
	                     GrupoInputModel grupoInput);

	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Grupo atualizado"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	GrupoModel atualizar(@ApiParam(value = "ID de um grupo", example = "1", required = true) long grupoId,
			             @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados", required = true)
			             GrupoInputModel grupoInput);

	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Grupo excluído"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) long grupoId);
}