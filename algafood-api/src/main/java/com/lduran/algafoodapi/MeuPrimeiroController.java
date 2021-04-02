package com.lduran.algafoodapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lduran.algafoodapi.injdep.model.Cliente;
import com.lduran.algafoodapi.injdep.service.AtivacaoClienteService;

@Controller
public class MeuPrimeiroController
{
	private AtivacaoClienteService ativacaoCliente;

	/**
	 * @param ativacaoClienteService
	 */
	public MeuPrimeiroController(AtivacaoClienteService ativacaoCliente)
	{
		this.ativacaoCliente = ativacaoCliente;
	}

	@GetMapping("/hello")
	@ResponseBody
	public String helloWorld()
	{
		Cliente joao = new Cliente("João", "joao@algaworks.com.br", "91234-5678");

		ativacaoCliente.ativar(joao);

		return "Hello World!";
	}
}
