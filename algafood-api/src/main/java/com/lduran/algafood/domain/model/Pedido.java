package com.lduran.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.lduran.algafood.domain.event.PedidoCanceladoEvent;
import com.lduran.algafood.domain.event.PedidoConfirmadoEvent;
import com.lduran.algafood.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido>
{
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;

	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;

	@Embedded
	private Endereco enderecoEntrega;

	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;

	@CreationTimestamp
	private OffsetDateTime dataCriacao;

	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;

	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();

	public void calcularValorTotal()
	{
		getItens().forEach(ItemPedido::calcularPrecoTotal);

		this.subtotal = getItens().stream().map(item -> item.getPrecoTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);

		this.valorTotal = this.subtotal.add(this.taxaFrete);
	}

	public void confirmar()
	{
		this.setStatus(StatusPedido.CONFIRMADO);
		this.setDataConfirmacao(OffsetDateTime.now());

		registerEvent(new PedidoConfirmadoEvent(this));
	}

	public void cancelar()
	{
		this.setStatus(StatusPedido.CANCELADO);
		this.setDataCancelamento(OffsetDateTime.now());

		registerEvent(new PedidoCanceladoEvent(this));
	}

	public void entregar()
	{
		this.setStatus(StatusPedido.ENTREGUE);
		this.setDataEntrega(OffsetDateTime.now());

	}

	private void setStatus(StatusPedido novoStatus)
	{
		if (getStatus().naoPodeAlterarPara(novoStatus))
		{
			throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s",
					this.getCodigo(), this.getStatus().getDescricao(), novoStatus.getDescricao()));
		}

		this.status = novoStatus;
	}

	@PrePersist
	private void gerarCodigo()
	{
		setCodigo(UUID.randomUUID().toString());
	}
}
