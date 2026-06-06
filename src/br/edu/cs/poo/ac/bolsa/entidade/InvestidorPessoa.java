package br.edu.cs.poo.ac.bolsa.entidade;
import br.edu.cs.poo.ac.bolsa.util.Comparavel;

import java.time.LocalDate;
import java.math.BigDecimal;

public class InvestidorPessoa extends Investidor implements Comparavel {
	private String cpf;
	private double renda;
	private FaixaRenda faixaRenda;
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public double getRenda() {
		return renda;
	}
	
	public void setRenda(double renda) {
		this.renda = renda;
	}
	
	public FaixaRenda getFaixaRenda() {
		return faixaRenda;
	}
	
	public void setFaixaRenda(FaixaRenda faixaRenda) {
		this.faixaRenda = faixaRenda;
	}
	
	public LocalDate getDataNascimento() {
		return super.getDataCriacao();
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		super.setDataCriacao(dataNascimento);
	}
	public InvestidorPessoa(String nome, Endereco endereco, LocalDate dataNascimento, BigDecimal bonus, Contatos contatos, String cpf, double renda, FaixaRenda faixaRenda) {
		super(nome, endereco, dataNascimento, bonus, contatos);
		this.cpf = cpf;
		this.renda = renda;
		this.faixaRenda = faixaRenda;
	}
	
	public InvestidorPessoa() {
	}

	public int comparar(Comparavel comp){
		if (!(comp instanceof InvestidorPessoa)){
			throw new RuntimeException("O argumento nao e do tipo InvestidorPessoa");

		}
		InvestidorPessoa inv1 = (InvestidorPessoa) comp;

		if (this.getNome().compareTo(inv1.getNome()) > 0){
			return 1;
		}else if (this.getNome().compareTo(inv1.getNome()) < 0){
			return -1;
		}

		return 0;


	}
}
