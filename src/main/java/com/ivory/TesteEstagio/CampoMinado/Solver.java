package com.ivory.TesteEstagio.CampoMinado;

import java.util.List;
import java.util.ArrayList;

class Casa {

    private int indice;
    private float probabilidade;
    private boolean aberto, marcado;
    private int vizinhosBomba;

    public Casa(int indice, boolean aberto, int vizinhosBomba) {

        this.indice = indice;
        this.aberto = aberto;
        this.vizinhosBomba = vizinhosBomba;
        this.probabilidade = 0.0f;

        marcado = false;
    }

    public void marcar() {

        this.marcado = true;
    }

    public boolean estaMarcado() {

        return marcado;
    }

    public boolean estaAberto() {

        return aberto;
    }

    public int getIndice() {

        return indice;
    }

    public int getVizinhosBomba() {

        return vizinhosBomba;
    }

    public void atualizar(boolean aberto, int vizinhosBomba) {

        this.aberto = aberto;
        this.vizinhosBomba = vizinhosBomba;
    }

    public void setProbabilidade(float probabilidade) {

        this.probabilidade = probabilidade;
    }

    public float getProbabilidade() {

        return probabilidade;
    }
}

public class Solver {

    private Casa[] casas;
    private CampoMinado campo;
    private int colunas, linhas;

    public Solver(String[] lines, CampoMinado campo) {

        this.campo = campo;
        casas = new Casa[lines.length * lines[0].length()];

        linhas = lines.length;
        colunas = lines[0].length();

        for (int i = 0; i < casas.length; ++i) {
            
            char c = lines[i / lines.length].charAt(i % lines.length);
            boolean isDigit = Character.isDigit(c);
            if (isDigit)
                casas[i] = new Casa(i, true, Character.getNumericValue(c));
            else casas[i] = new Casa(i, c == '0', 0);
        }
    }

    public void atualizarCasas(String[] lines) {

        for (int i = 0; i < casas.length; ++i) {

            if (casas[i].estaMarcado())
                continue;

            char c = lines[i / lines.length].charAt(i % lines.length);
            boolean isDigit = Character.isDigit(c);
            if (isDigit)
                casas[i].atualizar(true, Character.getNumericValue(c));
            else casas[i].atualizar(c == '0', 0);
        }
    }

    public Coordenada[] jogarTurno() {

        List<Coordenada> pontosParaAbrir = new ArrayList<Coordenada>();

        // Passe para calcular probabilidades de cada casa fechada possuir uma bomba:
        for (Casa casa : casas) {
            
            if (casa.estaMarcado() || !casa.estaAberto() || casa.getVizinhosBomba() == 0)
                continue;
            
            Casa[] vizinhos = casasVizinhas(casa);
            Casa[] fechados = vizinhosFechados(casa, vizinhos);

            if (fechados.length <= casa.getVizinhosBomba()) {

                for (Casa fechada : fechados) {

                    fechada.marcar();
                }
            } else {

                for (Casa fechada : fechados) {

                    fechada.setProbabilidade(fechada.getProbabilidade() + casa.getVizinhosBomba() / 8);
                }
            }
        }

        // Passe para marcar casas com probabilidade maior ou igual a um:
        for (Casa casa : casas) {

            if (!casa.estaAberto() && !casa.estaMarcado()) {

                if (casa.getProbabilidade() >= 1.0f) {

                    casa.marcar();
                }
            }
        }

        // Passe para abrir as casas que podem ser abertas:
        for (Casa casa : casas) {

            if (casa.estaMarcado() || !casa.estaAberto() || casa.getVizinhosBomba() == 0)
                continue;
            
            Casa[] vizinhos = casasVizinhas(casa);
            Casa[] fechados = vizinhosFechados(casa, vizinhos);
            Casa[] marcados = vizinhosMarcados(casa, vizinhos);

            if (marcados.length >= casa.getVizinhosBomba()) {

                for (Casa fechada : fechados) {

                    int x = fechada.getIndice() % linhas;
                    int y = fechada.getIndice() / linhas;

                    Coordenada c = new Coordenada(x, y);
                    pontosParaAbrir.add(c);
                }
            }
        }

        Coordenada[] resultado = new Coordenada[pontosParaAbrir.size()];

        for (int i = 0; i < pontosParaAbrir.size(); ++i)
            resultado[i] = pontosParaAbrir.get(i);

        return resultado;
    }

    private Casa[] vizinhosMarcados(Casa casa, Casa[] vizinhos) {

        List<Casa> marcados = new ArrayList<Casa>();

        for (Casa vizinho : vizinhos) {

            if (vizinho.estaMarcado())
                marcados.add(vizinho);
        }

        Casa[] resultado = new Casa[marcados.size()];

        for (int i = 0; i < marcados.size(); ++i) {
            resultado[i] = marcados.get(i);
        }

        return resultado;
    }

    private Casa[] vizinhosFechados(Casa casa, Casa[] vizinhos) {

        List<Casa> fechados = new ArrayList<Casa>();

        for (Casa vizinho : vizinhos) {

            if (!vizinho.estaMarcado() && !vizinho.estaAberto()) {

                fechados.add(vizinho);
            }
        }

        Casa[] resultado = new Casa[fechados.size()];

        for (int i = 0; i < fechados.size(); ++i) {
            resultado[i] = fechados.get(i);
        }

        return resultado;
    }

    private Casa[] vizinhosAbertos(Casa casa, Casa[] vizinhos) {

        List<Casa> abertos = new ArrayList<Casa>();

        for (Casa vizinho : vizinhos) {

            if (vizinho.estaAberto() && vizinho.getVizinhosBomba() != 0) {

                abertos.add(vizinho);
            }
        }

        Casa[] resultado = new Casa[abertos.size()];

        for (int i = 0; i < abertos.size(); ++i) {
            resultado[i] = abertos.get(i);
        }

        return resultado;
    }

    private Casa[] casasVizinhas(Casa casa) {

        List<Casa> vizinhos = new ArrayList<Casa>();

        for (Casa c : casas) {
            
            // Vizinhos laterais:
            if (Math.abs(c.getIndice() - casa.getIndice()) == 1) {
                
                vizinhos.add(c);
                continue;
            }
            
            // Vizinhos de baixo:
            if (c.getIndice() <= casa.getIndice() + (colunas + 1) && c.getIndice() >= casa.getIndice() + (colunas - 1)) {

                vizinhos.add(c);
                continue;
            }

            // Vizinhos de cima:
            if (c.getIndice() <= casa.getIndice() - (colunas + 1) && c.getIndice() >= casa.getIndice() - (colunas - 1)) {

                vizinhos.add(c);
                continue;
            }
        }

        Casa[] resultado = new Casa[vizinhos.size()];

        for (int i = 0; i < vizinhos.size(); ++i) {
            resultado[i] = vizinhos.get(i);
        }

        return resultado;
    }
}
