package com.ivory.TesteEstagio.CampoMinado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampoMinado {
	
	private StatusTipo status = StatusTipo.Aberto;
	
	// O tabuleiro inicia com 34 casas abertas. Ao abrir 71 casas(casas que não possuem bombas) o jogo é ganho.
	private int jogoTotalDeCasasAbertas = 34;
	
	private static String Solucao[][] =
		{
			{ "0", "0", "0", "0", "0", "0", "0", "1", "*" },
            { "0", "0", "0", "0", "0", "0", "0", "1", "1" },
            { "1", "2", "2", "1", "0", "0", "0", "0", "0" },
            { "1", "*", "*", "3", "2", "2", "1", "1", "0" },
            { "1", "2", "3", "*", "*", "4", "*", "2", "1" },
            { "0", "0", "1", "3", "*", "*", "3", "*", "2" },
            { "0", "0", "0", "1", "2", "2", "2", "2", "*" }, 
            { "0", "0", "0", "0", "0", "0", "0", "1", "1" },
            { "0", "0", "0", "0", "0", "0", "0", "0", "0" }
		};
	
	
	private static String Jogo[][] =
		{
			{ "0", "0", "0", "0", "0", "0", "0", "1", "-" },
            { "0", "0", "0", "0", "0", "0", "0", "1", "1" },
            { "1", "2", "2", "1", "0", "0", "0", "0", "0" },
            { "-", "-", "-", "3", "2", "2", "1", "1", "0" },
            { "-", "-", "-", "-", "-", "-", "-", "2", "1" },
            { "-", "-", "-", "-", "-", "-", "-", "-", "-" },
            { "-", "-", "-", "-", "-", "-", "-", "-", "-" },
            { "-", "-", "-", "-", "-", "-", "-", "-", "-" },
            { "-", "-", "-", "-", "-", "-", "-", "-", "-" }
		};
	

	/**
	 * @return enum do tipo: StatusTipo
	 * 0 - Jogo em aberto, continue procurando as casas que não possuem bombas;
	 * 1 - Vitoria, você encontrou todas as casas que não possui bomba;
	 * 2 - GameOver, você encontrou uma bomba.
	 */
	public StatusTipo JogoStatus()
	{
		return status;
	}
	
	/**
	 * Retorna o tabuleiro atual em modo texto.
	 */
	public String Tabuleiro() 
	{
		String tabuleiro = "";
		
		for (int i = 0; i < 9; i++) {
			for (int c = 0; c < 9; c++) {
				tabuleiro += Jogo[i][c];
			}
			if(i != 8) {
				tabuleiro += "\r\n";
			}
		}
		
		return tabuleiro;
	}
	
	/**
	 * Método responsável por abrir uma casa do tabuleiro e alterar o status do jogo para:
	 * - GameOver(2) caso você abra uma bomba;
	 * - Aberto(0) caso ainda não tenha encontrado todas as casas que não possui uma bomba e você deve continuar procurando;
	 * - Vitoria (1) caso encontre todas as casas que não possuem bomba.
	 * Como nosso jogo é baseado em um tabuleiro 9 x 9, informe valores dentro deste intervalo.
	 * @param linha
	 * @param coluna
	 */
	public void Abrir(int linha, int coluna) {
		if (linha >= 1 && linha <= 9 && coluna >= 1 && coluna <= 9)
        {
            linha--;
            coluna--;
            
            if (status == StatusTipo.GameOver || Solucao[linha][coluna] == "*")
            {
                Jogo[linha][coluna] = Solucao[linha][coluna];
                status = StatusTipo.GameOver;
            } else {
            	if (Jogo[linha][coluna] == "-" && Solucao[linha][coluna] != "0") 
            	{
                    Jogo[linha][coluna] = Solucao[linha][coluna];
                    jogoTotalDeCasasAbertas++;
                } 
            	else if (Jogo[linha][coluna] == "-" && Solucao[linha][coluna] == "0") 
                {
                	if (Jogo[4][0] == "-")
                    {
                        Jogo[4][0] = Solucao[4][0];
                        jogoTotalDeCasasAbertas++;
                    }

                    if (Jogo[4][1] == "-")
                    {
                        Jogo[4][1] = Solucao[4][1];
                        jogoTotalDeCasasAbertas++;
                    }

                    if (Jogo[4][2] == "-")
                    {
                        Jogo[4][2] = Solucao[4][2];
                        jogoTotalDeCasasAbertas++;
                    }

                    if (Jogo[5][2] == "-")
                    {
                        Jogo[5][2] = Solucao[5][2];
                        jogoTotalDeCasasAbertas++;
                    }

                    if (Jogo[5][3] == "-")
                    {
                        Jogo[5][3] = Solucao[5][3];
                        jogoTotalDeCasasAbertas++;
                    }

                    if (Jogo[6][3] == "-")
                    {
                        Jogo[6][3] = Solucao[6][3];
                        jogoTotalDeCasasAbertas++;
                    }

                    if (Jogo[6][4] == "-")
                    {
                        Jogo[6][4] = Solucao[6][4];
                        jogoTotalDeCasasAbertas++;
                    }

                    if (Jogo[6][5] == "-")
                    {
                        Jogo[6][5] = Solucao[6][5];
                        jogoTotalDeCasasAbertas++;
                    }

                    if (Jogo[6][6] == "-")
                    {
                        Jogo[6][6] = Solucao[6][6];
                        jogoTotalDeCasasAbertas++;
                    }

                    if (Jogo[6][7] == "-")
                    {
                        Jogo[6][7] = Solucao[6][7];
                        jogoTotalDeCasasAbertas++;
                    }

                    if (Jogo[7][7] == "-")
                    {
                        Jogo[7][7] = Solucao[7][7];
                        jogoTotalDeCasasAbertas++;
                    }

                    if (Jogo[7][8] == "-")
                    {
                        Jogo[7][8] = Solucao[7][8];
                        jogoTotalDeCasasAbertas++;
                    }

                    Jogo[5][0] = "0";
                    Jogo[5][1] = "0";
                    Jogo[6][0] = "0";
                    Jogo[6][1] = "0";
                    Jogo[6][2] = "0";
                    Jogo[7][0] = "0";
                    Jogo[7][1] = "0";
                    Jogo[7][2] = "0";
                    Jogo[7][3] = "0";
                    Jogo[7][4] = "0";
                    Jogo[7][5] = "0";
                    Jogo[7][6] = "0";
                    Jogo[8][0] = "0";
                    Jogo[8][1] = "0";
                    Jogo[8][2] = "0";
                    Jogo[8][3] = "0";
                    Jogo[8][4] = "0";
                    Jogo[8][5] = "0";
                    Jogo[8][6] = "0";
                    Jogo[8][7] = "0";
                    Jogo[8][8] = "0";
                    jogoTotalDeCasasAbertas += 21;
                	
                }
            	
            	if (jogoTotalDeCasasAbertas == 71)
                {
                    status = StatusTipo.Vitoria;
                }
            	
            }
        }
		
	}
}
