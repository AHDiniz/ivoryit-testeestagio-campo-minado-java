package com.ivory.TesteEstagio.CampoMinado;

class Casa {

    private int indice;
    private boolean aberto, marcado;
    private int vizinhosBomba;

    private Solver solver;

    public Casa(Solver solver, int indice, boolean aberto, int vizinhosBomba) {

        this.solver = solver;
        this.indice = indice;
        this.aberto = aberto;
        this.vizinhosBomba = vizinhosBomba;

        marcado = false;
    }

    public void marcar() {

        this.marcado = true;
    }

    public boolean estaMarcado() {

        return marcado;
    }

    public void atualizar(boolean aberto, int vizinhosBomba) {

        this.aberto = aberto;
        this.vizinhosBomba = vizinhosBomba;
    }
}

public class Solver {

    private Casa[] casas;
    private CampoMinado campo;

    public Solver(String[] lines, CampoMinado campo) {

        this.campo = campo;
        casas = new Casa[lines.length * lines[0].length()];

        for (int i = 0; i < casas.length; ++i) {
            
            char c = lines[i / lines.length].charAt(i % lines.length);
            boolean isDigit = Character.isDigit(c);
            if (isDigit)
                casas[i] = new Casa(this, i, true, Character.getNumericValue(c));
            else casas[i] = new Casa(this, i, c == '0', 0);
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

    public void jogarTurno() {

        
    }
}
