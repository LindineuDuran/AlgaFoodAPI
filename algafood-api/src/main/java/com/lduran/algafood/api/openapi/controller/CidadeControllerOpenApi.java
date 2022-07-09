package com.lduran.algafood.api.openapi.controller;

import com.lduran.algafood.api.exceptionHandler.Problem;
import com.lduran.algafood.api.model.CidadeModel;
import com.lduran.algafood.api.model.input.CidadeInputModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi
{
	@ApiOperation("Listar as cidades")
	ResponseEntity<List<CidadeModel>> listar();

	@ApiOperation("Buscar uma cidade por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModel buscar(@ApiParam("ID de uma cidade") long cidadeId);

	@ApiOperation("Adicionar uma nova cidade")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cidade cadastrada") })
	CidadeModel adicionar(CidadeInputModel cidadeInput);

	@ApiOperation("Atualizar uma cidade por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModel atualizar(@ApiParam("ID de uma cidade") long cidadeId, CidadeInputModel cidadeInput);

	@ApiOperation("Remover uma cidade por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Cidade excluida"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	void remover(@ApiParam("ID de uma cidade") long cidadeId);
}
