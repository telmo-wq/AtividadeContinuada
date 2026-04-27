package br.edu.cs.poo.ac.bolsa.entidade;
import java.io.Serializable;

public enum StatusTitulo implements Serializable {
	ATIVO(1, "Titulo ativo"),
	CANCELADO(2, "Titulo cancelado"),
	VENCIDO(3, "Titulo vencido");
	
	
	private int codigo;
	private String descricao;
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	private StatusTitulo(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
}


