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
        if (aberto)
            this.probabilidade = 0;
    }

    public void setProbabilidade(float probabilidade) {

        if (probabilidade >= 1.0f)
            probabilidade = 1.0f;
        if (probabilidade <= 0.0f)
            probabilidade = 0.0f;
        this.probabilidade = probabilidade;
    }

    public float getProbabilidade() {

        return probabilidade;
    }
}

public class Solver {

    private Casa[] casas;
    private int colunas, linhas;

    public Solver(String[] lines) {

        casas = new Casa[lines.length * lines[0].length()];

        linhas = lines.length;
        colunas = lines[0].length();

        for (int i = 0; i < casas.length; ++i) {

            int linha = i / lines.length, coluna = i % lines.length;
            
            char c = lines[linha].charAt(coluna);
            boolean isDigit = Character.isDigit(c);
            if (isDigit)
                casas[i] = new Casa(i + 1, true, Character.getNumericValue(c));
            else casas[i] = new Casa(i + 1, false, 0);
        }

        Casa[] v = casasVizinhas(casas[0]);
        System.out.println(v.length);
    }

    public void atualizarCasas(String[] lines) {

        for (int i = 0; i < casas.length; ++i) {

            if (casas[i].estaMarcado()) {
                continue;
            }

            char c = lines[i / lines.length].charAt(i % lines.length);
            boolean isDigit = Character.isDigit(c);
            if (isDigit)
                casas[i].atualizar(true, Character.getNumericValue(c));
            else casas[i].atualizar(false, 0);
        }
    }

    public void imprimirProbabilidades() {

        for (int i = 0; i < casas.length; ++i) {

            System.out.print(casas[i].getProbabilidade() + " ");

            if ((i + 1) % linhas == 0) {

                System.out.print("\n");
            }
        }
    }

    public void imprimirMarcados() {

        for (int i = 0; i < casas.length; ++i) {

            System.out.print(casas[i].estaMarcado() ? 1 : 0);

            if ((i + 1) % linhas == 0) {

                System.out.print("\n");
            }
        }

        System.out.print("\n");
    }

    public Coordenada[] jogarTurno() {

        List<Coordenada> pontosParaAbrir = new ArrayList<Coordenada>();

        int casasMarcadas = 0;

        for (Casa casa : casas) {

            if (casa.getVizinhosBomba() == 1) {

                Casa[] vizinhos = casasVizinhas(casa);
                Casa[] fechados = vizinhosFechados(casa, vizinhos);
                Casa[] marcados = vizinhosMarcados(casa, vizinhos);

                if (fechados.length == 1 && marcados.length == 0)
                    fechados[0].marcar();
            }

            if (casa.estaAberto() && casa.getVizinhosBomba() > 0) {

                Casa[] vizinhos = casasVizinhas(casa);
                Casa[] fechados = vizinhosFechados(casa, vizinhos);

                for (Casa fechado : fechados) {

                    Casa[] vizinhosDoFechado = casasVizinhas(fechado);
                    Casa[] abertosDoFechado = vizinhosAbertos(fechado, vizinhosDoFechado);

                    float probabilidade = 1.0f;

                    for (Casa a : abertosDoFechado) {

                        Casa[] vizinhosA = casasVizinhas(a);
                        Casa[] fechadosA = vizinhosFechados(a, vizinhosA);
                        Casa[] marcadosA = vizinhosMarcados(a, vizinhosA);

                        probabilidade *= (float)(a.getVizinhosBomba() - marcadosA.length) / (float)(fechadosA.length - marcadosA.length);
                    }

                    fechado.setProbabilidade(probabilidade);

                    if (fechado.getProbabilidade() >= 1.0f) {
                        fechado.marcar();
                        ++casasMarcadas;
                    }
                }
            }
        }

        for (Casa casa : casas) {

            if (casa.estaAberto() && casa.getVizinhosBomba() > 0) {

                Casa[] vizinhos = casasVizinhas(casa);
                Casa[] marcados = vizinhosMarcados(casa, vizinhos);

                if (marcados.length >= casa.getVizinhosBomba()) {

                    Casa[] fechados = vizinhosFechados(casa, vizinhos);

                    for (Casa fechado : fechados) {

                        if (fechado.getProbabilidade() <= 0.0f) {

                            int linha = fechado.getIndice() / linhas;
                            int coluna = fechado.getIndice() % linhas;

                            Coordenada c = new Coordenada(linha, coluna);
                            pontosParaAbrir.add(c);
                        }
                    }
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

        int linha = casa.getIndice() / linhas;
        int coluna = casa.getIndice() % linhas;

        for (Casa c : casas) {

            if (c.getIndice() == casa.getIndice())
                continue;

            int linhaC = c.getIndice() / linhas;
            int colunaC = c.getIndice() % linhas;

            if (Math.abs(linha - linhaC) <= 1 && Math.abs(coluna - colunaC) <= 1)
                vizinhos.add(c);
        }

        Casa[] resultado = new Casa[vizinhos.size()];

        for (int i = 0; i < vizinhos.size(); ++i) {
            resultado[i] = vizinhos.get(i);
        }

        return resultado;
    }
}
