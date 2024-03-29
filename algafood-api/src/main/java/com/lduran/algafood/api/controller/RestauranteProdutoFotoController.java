package com.lduran.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import com.lduran.algafood.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.net.HttpHeaders;
import com.lduran.algafood.api.assembler.FotoProdutoModelAssembler;
import com.lduran.algafood.api.model.FotoProdutoModel;
import com.lduran.algafood.api.model.input.FotoProdutoInput;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.algafood.domain.model.FotoProduto;
import com.lduran.algafood.domain.model.Produto;
import com.lduran.algafood.domain.service.CadastroProdutoService;
import com.lduran.algafood.domain.service.CatalogoFotoProdutoService;
import com.lduran.algafood.domain.service.FotoStorageService;
import com.lduran.algafood.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi
{
	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;

	@Autowired
	FotoStorageService fotoStorage;

	@Autowired
	private FotoProdutoModelAssembler assembler;

	@GetMapping
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId)
	{
		FotoProduto fotoProduto = catalogoFotoProduto.buscar(restauranteId, produtoId);

		return assembler.toModel(fotoProduto);
	}

	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException
	{
		try
		{
			FotoProduto fotoProduto = catalogoFotoProduto.buscar(restauranteId, produtoId);

			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

			FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoProduto.getNomeArquivo());

			if (fotoRecuperada.temURL())
			{
				return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			}
			else
			{
				return ResponseEntity.ok().contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
		}
		catch (EntidadeNaoEncontradaException e)
		{
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput) throws IOException
	{
		Produto produto = cadastroProduto.buscar(restauranteId, produtoId);

		MultipartFile arquivo = fotoProdutoInput.getArquivo();

		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());

		FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

		return assembler.toModel(fotoSalva);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId)
	{
		catalogoFotoProduto.excluir(restauranteId, produtoId);
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas)
			throws HttpMediaTypeNotAcceptableException
	{
		boolean compativel = mediaTypesAceitas.parallelStream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

		if (!compativel)
		{
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}
}
