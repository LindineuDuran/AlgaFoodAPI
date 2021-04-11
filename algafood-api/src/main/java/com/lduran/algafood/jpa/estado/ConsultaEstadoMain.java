package com.lduran.algafood.jpa.estado;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.lduran.algafood.AlgafoodApiApplication;
import com.lduran.algafood.domain.model.Estado;
import com.lduran.algafood.domain.repository.EstadoRepository;

public class ConsultaEstadoMain
{
	public static void main(String[] args)
	{
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		EstadoRepository estados = applicationContext.getBean(EstadoRepository.class);

		List<Estado> todosEstados = estados.todos();

		todosEstados.forEach(e ->
		{
			System.out.println(e.getNome());
		});
	}

}
