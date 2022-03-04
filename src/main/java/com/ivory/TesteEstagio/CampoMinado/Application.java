package com.ivory.TesteEstagio.CampoMinado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		Program jogo = new Program();
		jogo.executar();
	}

}
