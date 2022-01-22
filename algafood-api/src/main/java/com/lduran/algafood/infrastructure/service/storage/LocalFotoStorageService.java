package com.lduran.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.lduran.algafood.core.storage.StorageProperties;
import com.lduran.algafood.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService
{
	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto)
	{
		try
		{
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		}
		catch (Exception e)
		{
			throw new StorageException("Não foi possível armazenar arquivo.", e);
		}
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo)
	{
		Path arquivoPath = getArquivoPath(nomeArquivo);

		try
		{
			FotoRecuperada fotoRecuperada = FotoRecuperada.builder().inputStream(Files.newInputStream(arquivoPath))
					.build();
			return fotoRecuperada;
		}
		catch (Exception e)
		{
			throw new StorageException("Não foi possível recuperar arquivo.", e);
		}
	}

	@Override
	public void remover(String nomeArquivo)
	{
		Path arquivoPath = getArquivoPath(nomeArquivo);

		try
		{
			Files.deleteIfExists(arquivoPath);
		}
		catch (Exception e)
		{
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}
	}

	private Path getArquivoPath(String nomeArquivo)
	{
		return storageProperties.getLocal().getDiretorioLocal().resolve(Path.of(nomeArquivo));
	}
}
