package com.lduran.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.repository.CozinhaRepository;
import com.lduran.algafood.domain.service.CadastroCozinhaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTests
{
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void contextLoads()
	{
		assertTrue(true);
	}

	@Test
	public void testarCadastroCozinhaComSucesso()
	{
		// cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		// ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		// validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void testarCadastroCozinhaSemNome()
	{
		assertThrows(ConstraintViolationException.class, () ->
		{
			// cenário
			Cozinha novaCozinha = new Cozinha();
			novaCozinha.setNome(null);

			// ação
			novaCozinha = cadastroCozinha.salvar(novaCozinha);
		});
	}

	@Test
	public void deveFalharQuandoExcluirCozinhaEmUso()
	{
		assertThrows(EntidadeEmUsoException.class, () ->
		{
			cadastroCozinha.remover(1L);
		});
	}

	@Test
	public void deveFalharQuandoExcluirCozinhaInexistente()
	{
		assertThrows(EntidadeNaoEncontradaException.class, () ->
		{
			cadastroCozinha.remover(10L);
		});
	}
}