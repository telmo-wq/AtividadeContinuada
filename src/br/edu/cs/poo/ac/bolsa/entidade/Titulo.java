package br.edu.cs.poo.ac.bolsa.entidade;
import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.Period;
import java.lang.Math;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.io.Serializable;
import br.edu.cs.poo.ac.bolsa.util.Registro;



public class Titulo extends Registro implements Serializable {
	private Investidor investidor;
	private Ativo ativo;
	private BigDecimal valorInvestido;
	private BigDecimal valorAtual;
	private BigDecimal taxaDiaria;
	private LocalDate dataAplicacao;
	private LocalDate dataVencimento;
	private LocalDate dataUltimoRendimento;
	private StatusTitulo status;
	private InvestidorEmpresa empresa;

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}

	public void setInvestidor(Investidor investidor){
		this.investidor = investidor;
	}

	public Investidor getInvestidor(){
		return investidor;
	}

	public BigDecimal getValorInvestido() {
		return valorInvestido;
	}
	public void setValorInvestido(BigDecimal valorInvestido) {
		this.valorInvestido = valorInvestido;
	}
	public BigDecimal getValorAtual() {
		return valorAtual;
	}
	public void setValorAtual(BigDecimal valorAtual) {
		this.valorAtual = valorAtual;
	}
	public BigDecimal getTaxaDiaria() {
		return taxaDiaria;
	}
	public void setTaxaDiaria(BigDecimal taxaDiaria) {
		this.taxaDiaria = taxaDiaria;
	}
	public LocalDate getDataAplicacao() {
		return dataAplicacao;
	}
	public void setDataAplicacao(LocalDate dataAplicacao) {
		this.dataAplicacao = dataAplicacao;
	}
	public LocalDate getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public LocalDate getDataUltimoRendimento() {
		return dataUltimoRendimento;
	}
	public void setDataUltimoRendimento(LocalDate dataUltimoRendimento) {
		this.dataUltimoRendimento = dataUltimoRendimento;
	}
	public StatusTitulo getStatus() {
		return status;
	}
	public void setStatus(StatusTitulo status) {
		this.status = status;
	}

	public Titulo(Investidor investidor, Ativo ativo,
	              BigDecimal valorInvestido, BigDecimal valorAtual, BigDecimal taxaDiaria, LocalDate dataAplicacao,
	              LocalDate dataVencimento, LocalDate dataUltimoRendimento, StatusTitulo status) {
		this.investidor = investidor;
		this.ativo = ativo;
		this.valorInvestido = valorInvestido;
		this.valorAtual = valorAtual;
		this.taxaDiaria = taxaDiaria;
		this.dataAplicacao = dataAplicacao;
		this.dataVencimento = dataVencimento;
		this.dataUltimoRendimento = dataUltimoRendimento;
		this.status = status;
	}

	public InvestidorEmpresa getEmpresa(){
		return empresa;
	}

	public void setEmpresa(InvestidorEmpresa empresa){
		this.empresa = empresa;
	}

	public boolean render() {
		if (status.getCodigo() != 1 || !LocalDate.now().isBefore(dataVencimento) || !LocalDate.now().isAfter(dataAplicacao)) {
			return false;
		}

		LocalDate referencia;

		if(dataUltimoRendimento != null) {
			referencia = dataUltimoRendimento;
		}else {
			referencia = dataAplicacao;
		}

		long dias = java.time.temporal.ChronoUnit.DAYS.between(referencia, LocalDate.now());

		if (dias == 0) {
			return false;
		}

		if(dataUltimoRendimento != null && !LocalDate.now().isAfter(dataUltimoRendimento)) {
			return false;
		}

		BigDecimal fator = BigDecimal.ONE.add(taxaDiaria.divide(BigDecimal.valueOf(100)));
		valorAtual = valorAtual.multiply(fator.pow((int) dias));
		dataUltimoRendimento = LocalDate.now();
		return true;

	}

	public String getNumero(){
		if (investidor.getIdentificador().length() == 11){
			return "000" + investidor.getIdentificador() + ativo.getCodigo() +
					dataAplicacao.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "0000";
		}else if (investidor.getIdentificador().length() == 14){
			return investidor.getIdentificador() + ativo.getCodigo() +
					dataAplicacao.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "0000";
		}

		return null;
	}

	@Override
	public String getIdentificador(){
		return getNumero();
	}

}
