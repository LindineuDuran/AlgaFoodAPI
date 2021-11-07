package com.lduran.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.NegocioException;
import com.lduran.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.lduran.algafood.domain.model.Grupo;
import com.lduran.algafood.domain.model.Usuario;
import com.lduran.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService
{
	private static final String MSG_USUARIO_EM_USO = "Usuario de código %d não pode ser removido, pois está em uso";

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroGrupoService cadastroGrupo;

	public List<Usuario> listar()
	{
		return usuarioRepository.findAll();
	}

	public Usuario buscar(Long usuarioId)
	{
		return this.usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}

	@Transactional
	public Usuario salvar(Usuario usuario)
	{
		// Tratar: Caused by: javax.persistence.NonUniqueResultException: query did not
		// return a unique result: 2
		usuarioRepository.detach(usuario);

		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

		if (usuarioExistente.isPresent() && !usuario.equals(usuarioExistente.get()))
		{
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}

		return usuarioRepository.save(usuario);
	}

	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha)
	{
		Usuario usuario = buscar(usuarioId);

		if (usuario.senhaNaoCoincideCom(senhaAtual))
		{
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}

		usuario.setSenha(novaSenha);
	}

	@Transactional
	public void remover(Long usuarioId)
	{
		try
		{
			usuarioRepository.deleteById(usuarioId);

			// Libera todas as alterações pendentes no banco de dados.
			usuarioRepository.flush();
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new UsuarioNaoEncontradoException(usuarioId);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
		}
	}

	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId)
	{
		Usuario usuario = buscar(usuarioId);
		Grupo grupo = cadastroGrupo.buscar(grupoId);

		usuario.removerGrupo(grupo);
	}

	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId)
	{
		Usuario usuario = buscar(usuarioId);
		Grupo grupo = cadastroGrupo.buscar(grupoId);

		usuario.adicionarGrupo(grupo);
	}
}