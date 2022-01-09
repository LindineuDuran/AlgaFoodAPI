package com.lduran.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FotoProduto
{
	@Id
	@Column(name = "produto_id")
	@EqualsAndHashCode.Include
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Produto produto;

	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;

	public Long getRestauranteId()
	{
		if (getProduto() != null)
		{
			return getProduto().getRestaurante().getId();
		}

		return null;
	}

	public Long getProdutoId()
	{
		if (getProduto() != null)
		{
			return getProduto().getId();
		}

		return null;
	}
}
