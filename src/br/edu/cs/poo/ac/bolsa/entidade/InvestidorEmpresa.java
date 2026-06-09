package br.edu.cs.poo.ac.bolsa.entidade;
import java.time.LocalDate;
import java.math.BigDecimal;

public class InvestidorEmpresa extends Investidor {
	private String cnpj;
	private double faturamento;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public double getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(double faturamento) {
		this.faturamento = faturamento;
	}

	public LocalDate getDataAbertura() {
		return super.getDataCriacao();
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		super.setDataCriacao(dataAbertura);
	}

	public InvestidorEmpresa(String nome, Endereco endereco, LocalDate dataAbertura, BigDecimal bonus, Contatos contatos, String cnpj, double faturamento) {
		super(nome, endereco, dataAbertura, bonus, contatos);
		this.cnpj = cnpj;
		this.faturamento = faturamento;
	}

	public InvestidorEmpresa() {
	}

	@Override
	public String getIdentificador(){
		return cnpj;
	}

	@Override
	public BigDecimal getEntradaFinanceira() {
		return BigDecimal.valueOf(faturamento);
	}
}
	
	
