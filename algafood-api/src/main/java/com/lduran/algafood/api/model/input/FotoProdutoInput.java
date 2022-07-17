package com.lduran.algafood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.lduran.algafood.core.validation.FileContentType;
import com.lduran.algafood.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput
{
	@NotNull
	@ApiModelProperty(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)", required = true)
	@FileSize(max = "500KB")
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	private MultipartFile arquivo;

	@NotBlank
	@ApiModelProperty(value = "Descrição da foto do produto", required = true)
	private String descricao;
}
