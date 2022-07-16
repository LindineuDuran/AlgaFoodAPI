package com.lduran.algafood.api.openapi.controller;

import com.lduran.algafood.api.exceptionHandler.Problem;
import com.lduran.algafood.api.model.PedidoModel;
import com.lduran.algafood.api.model.PedidoResumoModel;
import com.lduran.algafood.api.model.input.PedidoInputModel;
import com.lduran.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi
{
	@ApiOperation("Pesquisa os pedidos")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
					          name = "campos", paramType = "query", type = "string")
	})
	public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);

	@ApiOperation("Busca um pedido por código")
	@ApiResponses({@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)})
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
					          name = "campos", paramType = "query", type = "string")
	})
	public PedidoModel buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
	                          String codigoPedido);

	@ApiOperation("Registra um pedido")
	@ApiResponses({@ApiResponse(code = 201, message = "Pedido registrado"),})
	public PedidoModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true)
	                             PedidoInputModel pedidoInput);
}
