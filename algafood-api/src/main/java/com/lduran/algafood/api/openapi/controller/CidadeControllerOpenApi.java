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
	CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) long cidadeId);

	@ApiOperation("Adicionar uma nova cidade")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cidade cadastrada") })
	CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
	                      CidadeInputModel cidadeInput);

	@ApiOperation("Atualizar uma cidade por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModel atualizar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) long cidadeId,
			              @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
			              CidadeInputModel cidadeInput);

	@ApiOperation("Remover uma cidade por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Cidade excluida"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true) long cidadeId);
}
