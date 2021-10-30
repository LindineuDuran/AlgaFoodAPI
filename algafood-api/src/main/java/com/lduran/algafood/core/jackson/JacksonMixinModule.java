package com.lduran.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lduran.algafood.api.model.mixin.CidadeMixin;
import com.lduran.algafood.api.model.mixin.CozinhaMixin;
import com.lduran.algafood.domain.model.Cidade;
import com.lduran.algafood.domain.model.Cozinha;

@Component
public class JacksonMixinModule extends SimpleModule
{
	private static final long serialVersionUID = 1L;

	public JacksonMixinModule()
	{
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}
}
