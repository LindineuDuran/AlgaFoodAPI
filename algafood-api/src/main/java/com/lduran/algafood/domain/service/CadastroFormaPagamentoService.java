package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.FormaPagamentoNaoEncontradoException;
import com.lduran.algafood.domain.model.FormaPagamento;
import com.lduran.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService
{
	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento de código %d não pode ser removida, pois está em uso";

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	public List<FormaPagamento> listar()
	{
		return formaPagamentoRepository.findAll();
	}

	public FormaPagamento buscar(long formaPagamentoId)
	{
		return this.formaPagamentoRepository.findById(formaPagamentoId)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradoException(formaPagamentoId));
	}

	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento)
	{
		return formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void remover(long formaPagamentoId)
	{
		try
		{
			formaPagamentoRepository.deleteById(formaPagamentoId);

			// Libera todas as alterações pendentes no banco de dados.
			formaPagamentoRepository.flush();
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new FormaPagamentoNaoEncontradoException(formaPagamentoId);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}
	}
}
