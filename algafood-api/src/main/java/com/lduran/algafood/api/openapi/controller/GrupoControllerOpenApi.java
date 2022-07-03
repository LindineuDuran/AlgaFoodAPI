package com.lduran.algafood.api.openapi.controller;

import com.lduran.algafood.api.exceptionHandler.Problem;
import com.lduran.algafood.api.model.GrupoModel;
import com.lduran.algafood.api.model.input.GrupoInputModel;
import com.lduran.algafood.domain.model.Grupo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi
{
	@ApiOperation("Lista os grupos")
	public ResponseEntity<List<GrupoModel>> listar();

	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	public GrupoModel buscar(long grupoId);

	@ApiOperation("Cadastra um grupo")
	@ApiResponses({@ApiResponse(code = 201, message = "Grupo cadastrado")})
	public GrupoModel adicionar(GrupoInputModel grupoInput);

	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Grupo atualizado"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	public GrupoModel atualizar(long grupoId, GrupoInputModel grupoInput);

	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Grupo excluído"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	public void remover(long grupoId);
}