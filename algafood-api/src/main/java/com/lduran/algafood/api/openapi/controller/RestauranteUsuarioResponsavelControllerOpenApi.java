package com.lduran.algafood.api.openapi.controller;

import com.lduran.algafood.api.exceptionHandler.Problem;
import com.lduran.algafood.api.model.UsuarioModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi
{
	@ApiOperation("Lista os usuários responsáveis associados a restaurante")
	@ApiResponses({@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	ResponseEntity<List<UsuarioModel>> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true)
											  Long restauranteId);

	@ApiOperation("Desassociação de restaurante com usuário responsável")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", response = Problem.class)
	})
	void desassociar(@ApiParam(value = "ID do restaurante", example = "1", required = true)
					 Long restauranteId,
					 @ApiParam(value = "ID do usuário", example = "1", required = true)
					 Long usuarioId);

	@ApiOperation("Associação de restaurante com usuário responsável")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Associação realizada com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", response = Problem.class)
	})
	void associar(@ApiParam(value = "ID do restaurante", example = "1", required = true)
							Long restauranteId,
							@ApiParam(value = "ID do usuário", example = "1", required = true)
							Long usuarioId);
}
