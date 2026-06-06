package br.edu.cs.poo.ac.bolsa.negocio;

import br.edu.cs.poo.ac.bolsa.dao.*;
import br.edu.cs.poo.ac.bolsa.util.*;
import br.edu.cs.poo.ac.bolsa.entidade.*;

import java.time.LocalDate;
import java.math.BigDecimal;

public class InvestidorMediator {
	private DAOInvestidorEmpresa daoInvEmp = new DAOInvestidorEmpresa();
	private DAOInvestidorPessoa daoInvPes = new DAOInvestidorPessoa();
	
	public static boolean temApenasNumeros(String s) {
	    for (int i = 0; i < s.length(); i++) {
	        if (!Character.isDigit(s.charAt(i))) {
	            return false;
	        }
	    }
	    return true;
	}
	
	private MensagensValidacao validarEndereco(Endereco endereco) {
		MensagensValidacao msgs = new MensagensValidacao();
		
		if (endereco.getLogradouro().isBlank()) {
			msgs.adicionar("Logradouro é obrigatório.");
		}
		
		if (endereco.getCep().isBlank()) {
			msgs.adicionar("Cep é obrigatório.");
		}
		
		if(endereco.getNumero().isBlank()) {
			msgs.adicionar("Número é obrigatório.");
		}
		
		if (endereco.getPais().isBlank()) {
			msgs.adicionar("País é obrigatório.");
		}
		
		if (endereco.getEstado().isBlank()) {
			msgs.adicionar("Estado é obrigatório.");
		}
		
		if (endereco.getCidade().isBlank()) {
			msgs.adicionar("Cidade é obrigatório.");
		}
		return msgs;
	}
	
	private MensagensValidacao validarContatos(Contatos contatos, 
			boolean ehPessoaJuridica) {
		MensagensValidacao msgs = new MensagensValidacao();
		
		if(contatos.getEmail().isBlank()) {
			msgs.adicionar("E-mail é obrigatório.");
		}else if (!contatos.getEmail().contains("@") || 
				!contatos.getEmail().contains(".com")) {
			msgs.adicionar("E-mail inválido.");
		}
		
		if (contatos.getCelular().isBlank() && contatos.getTelefoneFixo().isBlank()
				&& contatos.getWhats().isBlank()) {
			msgs.adicionar("Pelo menos um telefone deve ser informado.");
		}
		
		if (!contatos.getCelular().isBlank()) {
			if (!temApenasNumeros(contatos.getCelular())) {
				msgs.adicionar("Telefone celular deve conter apenas números.");
			}
		}
		
		if (!contatos.getTelefoneFixo().isBlank()) {
			if (!temApenasNumeros(contatos.getTelefoneFixo())) {
				msgs.adicionar("Telefone fixo deve conter apenas números.");
			}
		}
		
		if (!contatos.getWhats().isBlank()) {
			if (!temApenasNumeros(contatos.getWhats())) {
				msgs.adicionar("Telefone WhatsApp deve conter apenas números.");
			}
		}
		
		if (contatos.getContato().isBlank()) {
			if(ehPessoaJuridica) {
				msgs.adicionar("Nome para contato é obrigatório para pessoa jurídica.");
			}
		}
		
		return msgs;	
	}
	
	private MensagensValidacao validarDadosInvestidor(DadosInvestidor dadosInv) {
		MensagensValidacao msgs = new MensagensValidacao();
		
		if (dadosInv.getNome().isBlank()) {
			msgs.adicionar("Nome é obrigatório.");
		}
		
		if(dadosInv.getEndereco() == null) {
			msgs.adicionar("Endereço é obrigatório.");
		}
		
		if (dadosInv.getContatos() == null) {
			msgs.adicionar("Contatos são obrigatórios.");
		}
		if(dadosInv.getEndereco() != null) {
			msgs.adicionar(validarEndereco(dadosInv.getEndereco()));
		}
		
		if (dadosInv.getContatos() != null) {
			msgs.adicionar(validarContatos(dadosInv.getContatos(), dadosInv.ehInvestidorEmpresa()));
		}
		return msgs;
	}
	
	
	private MensagensValidacao validarInvestidorEmpresa(InvestidorEmpresa ie) {
		MensagensValidacao msgs = new MensagensValidacao();
		DadosInvestidor dadosInv = new DadosInvestidor(ie, null);
		msgs = validarDadosInvestidor(dadosInv);
		
		if (ValidadorCpfCnpj.validarCnpj(ie.getCnpj()) != null) {
			msgs.adicionar("CNPJ inválido.");
		}
		
		if (ie.getFaturamento() < 100000.0) {
			msgs.adicionar("Faturamento deve ser maior ou igual a 100000.0");
		}
		
		return msgs;
	}
	
	private MensagensValidacao validarInvestidorPessoa(InvestidorPessoa ip) {
		MensagensValidacao msgs = new MensagensValidacao();
		DadosInvestidor dadosInv = new DadosInvestidor(null, ip);
		msgs = validarDadosInvestidor(dadosInv);
		
		if (ValidadorCpfCnpj.validarCpf(ip.getCpf()) != null) {
			msgs.adicionar("Cpf inválido.");
		}
		
		if (ip.getRenda() < 10000.0) {
			msgs.adicionar("Renda deve ser maior ou igual a 10000.0");
		}
		
		return msgs;
	}
	
	public MensagensValidacao incluirInvestidorEmpresa(InvestidorEmpresa ie) {
		MensagensValidacao msgs = new MensagensValidacao();
		msgs = validarInvestidorEmpresa(ie);
		
		if (msgs.estaVazio()== true) {
			if (daoInvEmp.incluirInvestidorEmpresa(ie) == false) {
				msgs.adicionar("Investidor Empresa já existente.");
			}
		}
		
		return msgs;
	}
	
	public MensagensValidacao alterarInvestidorEmpresa(InvestidorEmpresa ie) {
		MensagensValidacao msgs = new MensagensValidacao();
		msgs = validarInvestidorEmpresa(ie);
		
		if(msgs.estaVazio() == true) {
			if(daoInvEmp.alterarInvestidorEmpresa(ie) == false) {
				msgs.adicionar("Investidor Empresa não existente.");
			}
		}
		return msgs;
	}
	
	public MensagensValidacao excluirInvestidorEmpresa(String cnpj) {
		MensagensValidacao msgs = new MensagensValidacao();
		
		if (ValidadorCpfCnpj.validarCnpj(cnpj) != null) {
			msgs.adicionar("Cnpj inválido.");
		}
		
		if(msgs.estaVazio() == true) {
			if(daoInvEmp.excluirInvestidorEmpresa(cnpj) == false) {
				msgs.adicionar("Investidor Empresa não existente.");
			}
		}
		
		return msgs;
	}
	
	public InvestidorEmpresa buscarInvestidorEmpresa(String cnpj) {
		if (ValidadorCpfCnpj.validarCnpj(cnpj) == null) {
			return daoInvEmp.buscar(cnpj);
		}else {
			return null;
		}
	}
	
	public MensagensValidacao incluirInvestidorPessoa(InvestidorPessoa ip) {
		MensagensValidacao msgs = new MensagensValidacao();
		msgs = validarInvestidorPessoa(ip);
		
		if(msgs.estaVazio() == true) {
			if (daoInvPes.incluirInvestidorPessoa(ip) == false) {
				msgs.adicionar("Investidor Pessoa já existente.");
			}
		}
		return msgs;
	}
	
	public MensagensValidacao alterarInvestidorPessoa(InvestidorPessoa ip) {
		MensagensValidacao msgs = new MensagensValidacao();
		msgs = validarInvestidorPessoa(ip);
		
		if(msgs.estaVazio() == true) {
			if(daoInvPes.alterar(ip) == false) {
				msgs.adicionar("Investidor Pessoa não existente.");
			}
		}
		return msgs;
	}
	
	public MensagensValidacao excluirInvestidorPessoa(String cpf) {
		MensagensValidacao msgs = new MensagensValidacao();
		
		if(ValidadorCpfCnpj.validarCpf(cpf) != null) {
			msgs.adicionar("Cpf inválido.");
		}
		
		if(msgs.estaVazio() == true) {
			if(daoInvPes.excluir(cpf) == false) {
				msgs.adicionar("Investidor Pessoa não existente.");
			}
		}
		return msgs;
	}
	
	public InvestidorPessoa buscarInvestidorPessoa(String cpf) {
		if (ValidadorCpfCnpj.validarCpf(cpf) != null) {
			return null;
		}else {
			return daoInvPes.buscar(cpf);
		}
	}

	public InvestidorPessoa[] consultarInvestidorPessoa(
			OrdenacaoInvestidorPessoa criterio
	){
		InvestidorPessoa[] array = daoInvPes.consultarTodos();

		if (criterio.getCodigo() == 1){
			ComparadorGenerico compGenerico = new ComparadorGenerico();
			Ordenador.ordenar(array, compGenerico);
			return array;
		}else if (criterio.getCodigo() == 2){
			ComparadorInvestidorPessoaRenda compRenda =
					new ComparadorInvestidorPessoaRenda();
			Ordenador.ordenar(array, compRenda);
			return array;
		}

		return null;
	}
}
