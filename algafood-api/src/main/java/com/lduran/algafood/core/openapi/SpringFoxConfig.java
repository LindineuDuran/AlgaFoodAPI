package com.lduran.algafood.core.openapi;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lduran.algafood.api.exceptionHandler.Problem;
import com.lduran.algafood.api.model.CozinhaModel;
import com.lduran.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.lduran.algafood.api.openapi.model.PageableModelOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Configuration @Import(BeanValidatorPluginsConfiguration.class) public class SpringFoxConfig
{
	@Bean
	public Docket apiDocket()
	{
		var typeResolver = new TypeResolver();

		return new Docket(DocumentationType.OAS_30).select()
				.apis(RequestHandlerSelectors.basePackage("com.lduran.algafood.api")).paths(PathSelectors.any()).build()
				.useDefaultResponseMessages(false).globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.globalRequestParameters(Collections.singletonList(new RequestParameterBuilder()
						.name("campos")
						.description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
						.in(ParameterType.QUERY)
						.required(true)
						.query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
						.build()))
				.additionalModels(typeResolver.resolve(Problem.class)).apiInfo(apiInfo())
				.ignoredParameterTypes(ServletWebRequest.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, CozinhaModel.class),
						                                       CozinhasModelOpenApi.class))
				.tags(new Tag("Cidades", "Gerencia as Cidades"),
					  new Tag("Grupos", "Gerencia os grupos de usuários"),
					  new Tag("Cozinhas", "Gerencia as Cozinhas"),
					  new Tag("Formas de pagamento", "Gerencia as Formas de Pagamento"));
	}

	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig()
	{
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}

	private List<Response> globalGetResponseMessages()
	{
		return Arrays.asList(new ResponseBuilder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					                              .description("Erro interno do Servidor")
						                          .representation( MediaType.APPLICATION_JSON )
						                          .apply(getProblemaModelReference())
						                          .build(),
		                     new ResponseBuilder().code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
					                              .description("Recurso não possui representação que pode ser aceita pelo consumidor").build());
	}

	private List<Response> globalPostPutResponseMessages()
	{
		return Arrays.asList(new ResponseBuilder().code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
						                          .description("Requisição inválida (erro do cliente)")
						                          .representation( MediaType.APPLICATION_JSON )
						                          .apply(getProblemaModelReference())
						                          .build(),
				             new ResponseBuilder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
						                          .description("Erro interno no servidor")
									              .representation( MediaType.APPLICATION_JSON )
									              .apply(getProblemaModelReference())
									              .build(),
				             new ResponseBuilder().code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
						                          .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
									              .build(),
				             new ResponseBuilder().code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
									              .description("Requisição recusada porque o corpo está em um formato não suportado")
									              .representation( MediaType.APPLICATION_JSON )
									              .apply(getProblemaModelReference())
									              .build());
	}

	private List<Response> globalDeleteResponseMessages()
	{
		return Arrays.asList(new ResponseBuilder().code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
						                          .description("Requisição inválida (erro do cliente)")
						                          .representation( MediaType.APPLICATION_JSON )
						                          .apply(getProblemaModelReference())
						                          .build(),
				             new ResponseBuilder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
						                          .description("Erro interno no servidor")
									              .representation( MediaType.APPLICATION_JSON )
									              .apply(getProblemaModelReference())
									              .build());
	}

	private ApiInfo apiInfo()
	{
		return new ApiInfoBuilder().title("AlgaFood API").description("API aberta para clientes e restaurantes")
				.version("1").contact(new Contact("Lindineu Duran", "https://github.com/LindineuDuran?tab=repositories",
						"lduran355@gmail.com")).build();
	}

	private Consumer<RepresentationBuilder> getProblemaModelReference()
	{
		return r -> r.model(m -> m.name("Problema")
				.referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
						q -> q.name("Problema").namespace("com.lduran.algafood.api.exceptionHandler")))));
	}
}