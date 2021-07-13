package com.lduran.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
public class CadastroRestauranteIT
{
	private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";

	private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

	private static final int RESTAURANTE_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private ResourceUtils resourceUtils;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	private String jsonRestauranteCorreto;
	private String jsonRestauranteSemFrete;
	private String jsonRestauranteSemCozinha;
	private String jsonRestauranteComCozinhaInexistente;

	private Restaurante burgerTopRestaurante;

	@BeforeEach
	public void setUp()
	{
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";

		jsonRestauranteCorreto = resourceUtils
				.getContentFromResource("/json/correto/restaurante-new-york-barbecue.json");

		jsonRestauranteSemFrete = resourceUtils
				.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-frete.json");

		jsonRestauranteSemCozinha = resourceUtils
				.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");

		jsonRestauranteComCozinhaInexistente = resourceUtils
				.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");

		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes()
	{
		RestAssured.given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.SC_OK);
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestaurante()
	{
		RestAssured.given().body(jsonRestauranteCorreto).contentType(ContentType.JSON).accept(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.SC_CREATED);
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete()
	{
		RestAssured.given().body(jsonRestauranteSemFrete).contentType(ContentType.JSON).accept(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.SC_BAD_REQUEST)
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha()
	{
		RestAssured.given().body(jsonRestauranteSemCozinha).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().post().then().statusCode(HttpStatus.SC_BAD_REQUEST)
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente()
	{
		RestAssured.given().body(jsonRestauranteComCozinhaInexistente).contentType(ContentType.JSON)
				.accept(ContentType.JSON).when().post().then().statusCode(HttpStatus.SC_BAD_REQUEST)
				.body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente()
	{
		RestAssured.given().pathParam("restauranteId", burgerTopRestaurante.getId()).accept(ContentType.JSON).when()
				.get("/{restauranteId}").then().statusCode(HttpStatus.SC_OK)
				.body("nome", equalTo(burgerTopRestaurante.getNome()));
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente()
	{
		RestAssured.given().pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE).accept(ContentType.JSON).when()
				.get("/{restauranteId}").then().statusCode(HttpStatus.SC_NOT_FOUND);
	}

	private void prepararDados()
	{
		Cozinha cozinhaBrasileira = new Cozinha();
		cozinhaBrasileira.setNome("Brasileira");
		cadastroCozinha.salvar(cozinhaBrasileira);

		Cozinha cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cadastroCozinha.salvar(cozinhaAmericana);

		burgerTopRestaurante = new Restaurante();
		burgerTopRestaurante.setNome("Burger Top");
		burgerTopRestaurante.setTaxaFrete(new BigDecimal("10.0"));
		burgerTopRestaurante.setCozinha(cozinhaBrasileira);
		cadastroRestaurante.salvar(burgerTopRestaurante);

		Restaurante comidaMineiraRestaurante = new Restaurante();
		comidaMineiraRestaurante.setNome("Comida Mineira");
		comidaMineiraRestaurante.setTaxaFrete(new BigDecimal("10.0"));
		comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
		cadastroRestaurante.salvar(comidaMineiraRestaurante);
	}
}
