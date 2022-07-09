package com.lduran.algafood.api.controller;

import com.lduran.algafood.api.assembler.PedidoInputModelDisassembler;
import com.lduran.algafood.api.assembler.PedidoModelAssembler;
import com.lduran.algafood.api.assembler.PedidoResumoModelAssembler;
import com.lduran.algafood.api.model.PedidoModel;
import com.lduran.algafood.api.model.PedidoResumoModel;
import com.lduran.algafood.api.model.input.PedidoInputModel;
import com.lduran.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.lduran.algafood.core.data.PageableTranslator;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.algafood.domain.exception.NegocioException;
import com.lduran.algafood.domain.filter.PedidoFilter;
import com.lduran.algafood.domain.model.Pedido;
import com.lduran.algafood.domain.model.Usuario;
import com.lduran.algafood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerOpenApi
{
	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;

	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;

	@Autowired
	private PedidoInputModelDisassembler pedidoInputModelDisassembler;

	@GetMapping
	public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable)
	{
		pageable = traduzirPageable(pageable);

		Page<Pedido> pedidosPage = emissaoPedido.pesquisar(filtro, pageable);

		List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler
				.toCollectionModel(pedidosPage.getContent());

		Page<PedidoResumoModel> pedidoResumoModelPage = new PageImpl<>(pedidosResumoModel, pageable,
				pedidosPage.getTotalElements());

		return pedidoResumoModelPage;
	}

	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido)
	{
		return pedidoModelAssembler.toModel(emissaoPedido.buscar(codigoPedido));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInputModel pedidoInput)
	{
		try
		{
			Pedido novoPedido = pedidoInputModelDisassembler.toDomainObject(pedidoInput);

			// TODO pegar usuário autenticado
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);

			novoPedido = emissaoPedido.emitir(novoPedido);

			return pedidoModelAssembler.toModel(novoPedido);
		}
		catch (EntidadeNaoEncontradaException e)
		{
			throw new NegocioException(e.getMessage(), e);
		}
	}

	private Pageable traduzirPageable(Pageable apiPageable)
	{
		var mapeamento = Map.of("codigo", "codigo", "restaurante.nome", "restaurante.nome", "nomeCliente",
				"cliente.nome", "valorTotal", "valorTotal");

		return PageableTranslator.translate(apiPageable, mapeamento);
	}
}
