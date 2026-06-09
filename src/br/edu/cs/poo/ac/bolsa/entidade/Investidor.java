package br.edu.cs.poo.ac.bolsa.entidade;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.io.Serializable;
import br.edu.cs.poo.ac.bolsa.util.Registro;

public abstract class Investidor extends Registro implements Serializable {
	private String nome;
	private Endereco endereco;
	private LocalDate dataCriacao;
	private BigDecimal bonus;
	private Contatos contatos;

	public String getNome() {
		return nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public Contatos getContatos() {
		return contatos;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public void setContatos(Contatos contatos) {
		this.contatos = contatos;
	}

	protected LocalDate getDataCriacao() {
		return dataCriacao;
	}

	protected void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	protected void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

	public Investidor(String nome, Endereco endereco, LocalDate dataCriacao, BigDecimal bonus, Contatos contatos) {
		this.nome = nome;
		this.endereco = endereco;
		this.dataCriacao = dataCriacao;
		this.bonus = bonus;
		this.contatos = contatos;
	}

	public Investidor() {
	}

	public int getIdade() {
		LocalDate hoje = LocalDate.now();
		return hoje.getYear() - dataCriacao.getYear();
	}

	public void creditarBonus(BigDecimal valor) {
		if (valor == null || valor.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}
		if (bonus == null){
			bonus = BigDecimal.ZERO;
		}
		bonus = bonus.add(valor);
	}

	public void debitarBonus(BigDecimal valor) {
		if(valor == null || valor.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}
		if (bonus == null){
			bonus = BigDecimal.ZERO;
		}
		bonus = bonus.subtract(valor);
	}

	public abstract BigDecimal getEntradaFinanceira();

	public abstract String getIdentificador();
}
