package com.ivory.TesteEstagio.CampoMinado;

public class Program {
	
	public void executar() {
		
		CampoMinado campoMinado = new CampoMinado();
		
		System.out.println("Início do jogo\n=======");
		System.out.println(campoMinado.Tabuleiro());
		
		Solver solver = null;
		
		while (campoMinado.JogoStatus() == StatusTipo.Aberto) {
			
			String[] lines = campoMinado.Tabuleiro().split("\\r?\\n|\\r");
			for (String line : lines)
				System.out.println(line);

			if (solver == null)
				solver = new Solver(lines);
			else solver.atualizarCasas(lines);

			Coordenada[] pontosParaAbrir = solver.jogarTurno();

			for (Coordenada ponto : pontosParaAbrir) {

				campoMinado.Abrir(ponto.X(), ponto.Y());
			}

			System.out.println("Status = " + campoMinado.JogoStatus());
			System.out.println(campoMinado.Tabuleiro());
		}
	}

}
