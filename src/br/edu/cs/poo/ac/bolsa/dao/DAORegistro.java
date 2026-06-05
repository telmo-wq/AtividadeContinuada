package br.edu.cs.poo.ac.bolsa.dao;

import br.edu.cs.poo.ac.bolsa.util.Registro;

public class DAORegistro extends DAOGenerico {
    public DAORegistro(Class<?> tipo) {
        inicializarCadastro(tipo);
    }

    public boolean incluir(Registro registro){
        try {
            cadastro.incluir(registro, registro.getIdentificador());
            return true;
        } catch (RuntimeException a) {
            return false;
        }
    }

    public Registro buscar(String identificador){
        return (Registro) cadastro.buscar(identificador);
    }

    public boolean alterar(Registro registro){
        try {
            cadastro.alterar(registro, registro.getIdentificador());
            return true;
        } catch (RuntimeException e){
            return false;
        }
    }

    public boolean excluir(String identificador){
        try {
            cadastro.excluir(identificador);
            return true;
        } catch (RuntimeException e){
            return false;
        }
    }
}

