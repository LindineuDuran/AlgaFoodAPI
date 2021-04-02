package com.lduran.algafoodapi.injdep.notificacao;

import com.lduran.algafoodapi.injdep.model.Cliente;

public interface Notificador
{

	void notificar(Cliente cliente, String mensagem);

}