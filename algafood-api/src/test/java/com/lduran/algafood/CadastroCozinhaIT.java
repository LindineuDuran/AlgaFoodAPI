package com.lduran.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.service.CadastroCozinhaService;
import com.lduran.algafood.domain.service.CadastroRestauranteService;
import com.lduran.algafood.util.DatabaseCleaner;
import com.lduran.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT
{
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@LocalServerPort
	private int port;

	private int quantidadeCozinhas;

	private String novaCozinha;

	private Cozinha cozinhaAmericana;

	private final int COZINHA_ID_INEXISTENTE = 100;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private ResourceUtils resourceUtils;

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
			cadastroCozinha.remover(100L);
		});
	}

	@BeforeEach
	public void setUp()
	{
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/cozinhas";
		RestAssured.port = port;

		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas()
	{
		RestAssured.given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.SC_OK);
	}

	@Test
	public void deveRetornarQuantidadeDeCozinhas_QuandoConsultarCozinhas()
	{
		RestAssured.given().accept(ContentType.JSON).when().get().then().body("", Matchers.hasSize(quantidadeCozinhas));
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas_EncontraCozinhasEspecificas()
	{
		RestAssured.given().accept(ContentType.JSON).when().get().then().body("", Matchers.hasSize(quantidadeCozinhas))
				.body("nome", Matchers.hasItems("Americana", "Tailandesa"));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinhass()
	{
		RestAssured.given().body(novaCozinha).contentType(ContentType.JSON).accept(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.SC_CREATED);
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente()
	{
		RestAssured.given().pathParam("cozinhaId", cozinhaAmericana.getId()).accept(ContentType.JSON).when()
				.get("/{cozinhaId}").then().statusCode(HttpStatus.SC_OK)
				.body("nome", equalTo(cozinhaAmericana.getNome()));
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente()
	{
		RestAssured.given().pathParam("cozinhaId", COZINHA_ID_INEXISTENTE).accept(ContentType.JSON).when()
				.get("/{cozinhaId}").then().statusCode(HttpStatus.SC_NOT_FOUND);
	}

	private void prepararDados()
	{
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cadastroCozinha.salvar(cozinha1);

		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cadastroCozinha.salvar(cozinha2);

		cozinhaAmericana = cozinha2;

		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Thai Delivery");
		restaurante1.setTaxaFrete(new BigDecimal("10.0"));
		restaurante1.setCozinha(cozinha1);
		cadastroRestaurante.salvar(restaurante1);

		var cozinhas = cadastroCozinha.listar();
		quantidadeCozinhas = cozinhas.size();

		novaCozinha = ResourceUtils.getContentFromResource("/json/correto/cozinha-chinesa.json");
	}
}