package com.ivory.TesteEstagio.CampoMinado;

public class Program {
	
	public void executar() {
		
		CampoMinado campoMinado = new CampoMinado();
		
		System.out.println("Início do jogo\n=======");
		System.out.println(campoMinado.Tabuleiro());
		
		while (campoMinado.JogoStatus() == StatusTipo.Aberto) {
			
			String[] lines = campoMinado.Tabuleiro().split("\\r?\\n|\\r");

			// Não usando foreach pois a coordenada vai ser importante para verificar casas vizinhas:
			for (int i = 0; i < lines.length; ++i) {

				String line = lines[i];

				for (int j = 0; j < line.length(); ++j) {

					char c = line.charAt(j);
				}
			}
		}
	}

}
