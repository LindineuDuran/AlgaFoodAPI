package com.lduran.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.model.FotoProduto;
import com.lduran.algafood.domain.repository.ProdutoRepository;
import com.lduran.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService
{
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FotoStorageService fotoStorageService;

	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo)
	{
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProdutoId();

		String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;

		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		if (fotoExistente.isPresent())
		{
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}

		foto.setNomeArquivo(nomeNovoArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();

		NovaFoto novaFoto = NovaFoto.builder().nomeArquivo(foto.getNomeArquivo()).inputStream(dadosArquivo).build();

		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

		return foto;
	}
}
