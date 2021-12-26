package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.exception.NegocioException;
import com.lduran.algafood.domain.exception.PedidoNaoEncontradoException;
import com.lduran.algafood.domain.model.Cidade;
import com.lduran.algafood.domain.model.FormaPagamento;
import com.lduran.algafood.domain.model.Pedido;
import com.lduran.algafood.domain.model.Produto;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.model.Usuario;
import com.lduran.algafood.domain.repository.PedidoRepository;
import com.lduran.algafood.domain.repository.filter.PedidoFilter;
import com.lduran.algafood.infrastructure.repository.spec.PedidoSpecs;

@Service
public class EmissaoPedidoService
{
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	public List<Pedido> listar()
	{
		return pedidoRepository.findAll();
	}

	public Page<Pedido> pesquisar(PedidoFilter filtro, Pageable pageable)
	{
		return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
	}

	public Pedido buscar(String codigoPedido)
	{
		return pedidoRepository.findByCodigo(codigoPedido)
				.orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}

	@Transactional
	public Pedido emitir(Pedido pedido)
	{
		validarPedido(pedido);
		validarItens(pedido);

		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();

		return pedidoRepository.save(pedido);
	}

	private void validarPedido(Pedido pedido)
	{
		Cidade cidade = cadastroCidade.buscar(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario cliente = cadastroUsuario.buscar(pedido.getCliente().getId());
		Restaurante restaurante = cadastroRestaurante.buscar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscar(pedido.getFormaPagamento().getId());

		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);

		if (restaurante.naoAceitaFormaPagamento(formaPagamento))
		{
			throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por este restaurante.",
					formaPagamento.getDescricao()));
		}
	}

	private void validarItens(Pedido pedido)
	{
		pedido.getItens().forEach(item ->
		{
			Produto produto = cadastroProduto.buscar(pedido.getRestaurante().getId(), item.getProduto().getId());

			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}
}
