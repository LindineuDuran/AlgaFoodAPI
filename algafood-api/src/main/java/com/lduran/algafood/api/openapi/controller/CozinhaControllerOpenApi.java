package com.lduran.algafood.api.openapi.controller;

import com.lduran.algafood.api.exceptionHandler.Problem;
import com.lduran.algafood.api.model.CozinhaModel;
import com.lduran.algafood.api.model.input.CozinhaInputModel;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi
{
	@ApiOperation("Lista as cozinhas com paginação")
	Page<CozinhaModel> listarPage(Pageable pageable);

	@ApiOperation("Buscar uma cozinha por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModel buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) long cozinhaId);

	@ApiOperation("Adicionar uma nova cozinha")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Cozinha cadastrada"),
	})
	CozinhaModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true)
	                       CozinhaInputModel cozinhaInput);

	@ApiOperation("Atualizar uma cozinha por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Cozinha atualizada"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModel atualizar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) long cozinhaId,
			               @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados")
						   CozinhaInputModel cozinhaInput);

	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Cozinha excluída"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) long cozinhaId);
}
