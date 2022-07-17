package com.lduran.algafood.api.openapi.controller;

import com.lduran.algafood.api.exceptionHandler.Problem;
import com.lduran.algafood.api.model.EstadoModel;
import com.lduran.algafood.api.model.input.EstadoInputModel;
import com.lduran.algafood.domain.model.Estado;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi
{
	@ApiOperation("Lista os estados")
	ResponseEntity<List<EstadoModel>> listar();

	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	EstadoModel buscar(@ApiParam(value = "ID de um estado", example = "1", required = true) long estadoId);

	@ApiOperation("Cadastra um estado")
	@ApiResponses({@ApiResponse(code = 201, message = "Estado cadastrado")})
	ResponseEntity<EstadoModel> adicionar(@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
	                                      EstadoInputModel estadoInput);

	@ApiOperation("Atualiza um estado por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Estado atualizado"),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	EstadoModel atualizar(@ApiParam(value = "ID de um estado", example = "1", required = true) long estadoId,
			              @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true)
						  EstadoInputModel estadoInput);

	@ApiOperation("Exclui um estado por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Estado excluído"),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID de um estado", example = "1", required = true) long estadoId);
}
