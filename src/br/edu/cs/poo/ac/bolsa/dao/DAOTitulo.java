package br.edu.cs.poo.ac.bolsa.dao;

import br.edu.cs.poo.ac.bolsa.entidade.Titulo;
import br.edu.cs.poo.ac.bolsa.entidade.Ativo;

public class DAOTitulo extends DAOGenerico {
	public DAOTitulo() {
		inicializarCadastro(Titulo.class);
	}
	
	public Titulo buscar(Ativo ativo) {
		return (Titulo)cadastro.buscar("" + ativo);
	}
	
	public boolean incluir(Titulo titulo) {
		if (buscar(titulo.getAtivo()) == null) {
			cadastro.incluir(titulo, "" + titulo.getAtivo());
			return true;
		}else {
			return false;
		}
	}
	
	public boolean alterar(Titulo titulo) {
		if (buscar(titulo.getAtivo()) != null) {
			cadastro.alterar(titulo, "" + titulo.getAtivo());
			return true;
		}else {
			return false;
		}
	}
	
	public boolean excluir(Ativo ativo) {
		if(buscar(ativo) != null) {
			cadastro.excluir("" + ativo);
			return true;
		}else {
			return false;
		}
	}
}
