package com.lduran.algafood.api.openapi.controller;

import com.lduran.algafood.api.exceptionHandler.Problem;
import com.lduran.algafood.api.model.FormaPagamentoModel;
import com.lduran.algafood.api.model.input.FormaPagamentoInputModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi
{
	@ApiOperation("Listar as formas de pagamento")
	public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request);

	@ApiOperation("Buscar uma forma de pagamento por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	public ResponseEntity<FormaPagamentoModel> buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
													  long formaPagamentoId,
			                                          ServletWebRequest request);

	@ApiOperation("Adicionar uma nova forma de pagamento")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Forma de pagamento cadastrada"),
	})
	public FormaPagamentoModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true)
										 FormaPagamentoInputModel formaPagamentoInput);

	@ApiOperation("Atualizar uma forma de pagamento por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	public FormaPagamentoModel atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
	                                     long formaPagamentoId,

										 @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados", required = true)
			                             FormaPagamentoInputModel formaPagamentoInput);

	@ApiOperation("Excluir uma forma de pagamento por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Forma de pagamento excluída"),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	public void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) long formaPagamentoId);
}
