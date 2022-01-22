package com.lduran.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.lduran.algafood.core.storage.StorageProperties.TipoStorage;
import com.lduran.algafood.domain.service.FotoStorageService;
import com.lduran.algafood.infrastructure.service.storage.LocalFotoStorageService;
import com.lduran.algafood.infrastructure.service.storage.S3FotoStorageService;
import com.lduran.algafood.infrastructure.service.storage.StorageException;

@Configuration
public class StorageConfig
{
	@Autowired
	private StorageProperties storageProperties;

	@Bean
	@ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
	public AmazonS3 amazonS3()
	{
		try
		{
			var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
					storageProperties.getS3().getChaveAcessoSecreta());

			return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(storageProperties.getS3().getRegiao()).build();
		}
		catch (Exception e)
		{
			throw new StorageException("Não foi possível conectar no S3 da Amazon.", e);
		}
	}

	@Bean
	public FotoStorageService fotoStorageService()
	{
		if (TipoStorage.S3.equals(storageProperties.getTipo()))
		{
			return new S3FotoStorageService();
		}
		else
		{
			return new LocalFotoStorageService();
		}
	}
}
