package br.edu.cs.poo.ac.bolsa.util;

public enum OrdenacaoInvestidorPessoa {
    NOME(1, "Ordenacao por nome"),
    RENDA(2, "Ordenacao por renda");

    private int codigo;
    private String mensagem;

    private OrdenacaoInvestidorPessoa(int codigo, String mensagem){
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public int getCodigo() {
        return codigo;
    }
}
