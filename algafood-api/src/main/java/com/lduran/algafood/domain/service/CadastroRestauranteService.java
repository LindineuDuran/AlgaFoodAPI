package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.lduran.algafood.domain.model.Cidade;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.model.FormaPagamento;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.model.Usuario;
import com.lduran.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService
{
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso";

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	public List<Restaurante> listar()
	{
		return restauranteRepository.findAll();
	}

	public Restaurante buscar(Long restauranteId)
	{
		return this.restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}

	@Transactional
	public Restaurante salvar(Restaurante restaurante)
	{
		long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);

		restaurante.setCozinha(cozinha);

		long cidadeId = restaurante.getEndereco().getCidade().getId();
		Cidade cidade = cadastroCidade.buscar(cidadeId);

		restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void ativar(Long restauranteId)
	{
		Restaurante restauranteAtual = buscar(restauranteId);
		restauranteAtual.ativar();
	}

	@Transactional
	public void ativar(List<Long> restauranteIds)
	{
		restauranteIds.forEach(this::ativar);
	}

	@Transactional
	public void inativar(Long restauranteId)
	{
		Restaurante restauranteAtual = buscar(restauranteId);
		restauranteAtual.inativar();
	}

	@Transactional
	public void inativar(List<Long> restauranteIds)
	{
		restauranteIds.forEach(this::inativar);
	}

	@Transactional
	public void abrir(Long restauranteId)
	{
		Restaurante restauranteAtual = buscar(restauranteId);
		restauranteAtual.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId)
	{
		Restaurante restauranteAtual = buscar(restauranteId);
		restauranteAtual.fechar();
	}

	@Transactional
	public void remover(Long restauranteId)
	{
		try
		{
			restauranteRepository.deleteById(restauranteId);

			// Libera todas as alterações pendentes no banco de dados.
			restauranteRepository.flush();
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new RestauranteNaoEncontradoException(restauranteId);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
	}

	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId)
	{
		Restaurante restaurante = buscar(restauranteId);

		FormaPagamento formaPagamento = cadastroFormaPagamento.buscar(formaPagamentoId);

		restaurante.adicionarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId)
	{
		Restaurante restaurante = buscar(restauranteId);

		FormaPagamento formaPagamento = cadastroFormaPagamento.buscar(formaPagamentoId);

		restaurante.removerFormaPagamento(formaPagamento);
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId)
	{
		Restaurante restaurante = buscar(restauranteId);

		Usuario usuario = cadastroUsuario.buscar(usuarioId);

		restaurante.adicionarResponsavel(usuario);
	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId)
	{
		Restaurante restaurante = buscar(restauranteId);

		Usuario usuario = cadastroUsuario.buscar(usuarioId);

		restaurante.removerResponsavel(usuario);
	}
}