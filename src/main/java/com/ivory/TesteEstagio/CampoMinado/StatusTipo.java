package com.ivory.TesteEstagio.CampoMinado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public enum StatusTipo {
		
	Aberto(0),
	Vitoria(1),
	GameOver(2);
	
	@SuppressWarnings("unused")
	private final int tipo;
	
	StatusTipo(int status) {
		this.tipo = status;
	}
		
}
