package com.lduran.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.algafood.api.assembler.UsuarioInputModelDisassembler;
import com.lduran.algafood.api.assembler.UsuarioModelAssembler;
import com.lduran.algafood.api.model.UsuarioModel;
import com.lduran.algafood.api.model.input.SenhaInputModel;
import com.lduran.algafood.api.model.input.UsuarioComSenhaInputModel;
import com.lduran.algafood.api.model.input.UsuarioInputModel;
import com.lduran.algafood.domain.exception.CidadeNaoEncontradaException;
import com.lduran.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.lduran.algafood.domain.exception.NegocioException;
import com.lduran.algafood.domain.model.Usuario;
import com.lduran.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController
{
	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private UsuarioModelAssembler assembler;

	@Autowired
	private UsuarioInputModelDisassembler disassembler;

	@GetMapping
	public ResponseEntity<List<UsuarioModel>> listar()
	{
		return ResponseEntity.ok(assembler.toCollectionModel(cadastroUsuario.listar()));
	}

	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId)
	{
		Usuario usuario = cadastroUsuario.buscar(usuarioId);

		return assembler.toModel(usuario);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInputModel usuarioInput)
	{
		try
		{
			Usuario usuario = disassembler.toDomainObject(usuarioInput);
			return assembler.toModel(cadastroUsuario.salvar(usuario));
		}
		catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e)
		{
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{usuarioId}")
	public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputModel usuarioInput)
	{
		try
		{
			Usuario usuarioAtual = cadastroUsuario.buscar(usuarioId);

			disassembler.copyToDomainObject(usuarioInput, usuarioAtual);

			return assembler.toModel(cadastroUsuario.salvar(usuarioAtual));
		}
		catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e)
		{
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInputModel senha)
	{
		cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
	}

	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long usuarioId)
	{
		cadastroUsuario.remover(usuarioId);
	}
}