package com.lduran.algafood.api.openapi.controller;

import com.lduran.algafood.api.exceptionHandler.Problem;
import com.lduran.algafood.api.model.PedidoModel;
import com.lduran.algafood.api.model.PedidoResumoModel;
import com.lduran.algafood.api.model.input.PedidoInputModel;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.algafood.domain.exception.NegocioException;
import com.lduran.algafood.domain.filter.PedidoFilter;
import com.lduran.algafood.domain.model.Pedido;
import com.lduran.algafood.domain.model.Usuario;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
	public PedidoModel buscar(String codigoPedido);

	@ApiOperation("Registra um pedido")
	@ApiResponses({@ApiResponse(code = 201, message = "Pedido registrado"),})
	public PedidoModel adicionar(PedidoInputModel pedidoInput);
}
