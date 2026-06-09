package br.edu.cs.poo.ac.bolsa.dao;

import br.edu.cs.poo.ac.bolsa.entidade.InvestidorEmpresa;
import br.edu.cs.poo.ac.bolsa.entidade.InvestidorPessoa;

import java.io.Serializable;

public class DAOInvestidorPessoa extends DAOGenerico {
	public DAOInvestidorPessoa() {
		inicializarCadastro(InvestidorPessoa.class);
	}

	public InvestidorPessoa buscar(String cpf) {
		return (InvestidorPessoa)cadastro.buscar("" + cpf);
	}

	public boolean incluirInvestidorPessoa(InvestidorPessoa investidorPessoa) {
		if(buscar(investidorPessoa.getCpf()) == null) {
			cadastro.incluir(investidorPessoa, "" + investidorPessoa.getCpf());
			return true;
		}else {
			return false;
		}
	}

	public boolean alterar(InvestidorPessoa investidorPessoa) {
		if(buscar(investidorPessoa.getCpf()) != null) {
			cadastro.alterar(investidorPessoa, "" + investidorPessoa.getCpf());
			return true;
		}else {
			return false;
		}
	}

	public boolean excluir(String cpf) {
		if(buscar(cpf) != null) {
			cadastro.excluir("" + cpf);
			return true;
		}else {
			return false;
		}
	}

	public InvestidorPessoa[] consultarTodos(){
		Serializable[] resultado = cadastro.buscarTodos();

		if (resultado == null){
			return null;
		}
		InvestidorPessoa[] novoArray;
		novoArray = new InvestidorPessoa[resultado.length];

		for (int i = 0; i < resultado.length; i++){
			novoArray[i] = (InvestidorPessoa) resultado[i];
		}

		return novoArray;
	}

	public InvestidorPessoa buscarInvestidorPessoa(String cpf){
		return buscar(cpf);
	}
}
