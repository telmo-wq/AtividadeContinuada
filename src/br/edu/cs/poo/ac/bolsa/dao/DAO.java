package br.edu.cs.poo.ac.bolsa.dao;

import br.edu.cs.poo.ac.bolsa.util.Registro;
import br.edu.cs.poo.ac.bolsa.util.ExcecaoObjetoJaExistente;
import br.edu.cs.poo.ac.bolsa.util.ExcecaoOobjetoNaoExistente;


import java.io.Serializable;

public class DAO<T extends Registro> extends DAOGenerico{
    public DAO(Class<T> classe){
        inicializarCadastro(classe);
    }

    public boolean incluir(T entidade) throws ExcecaoObjetoJaExistente{
        try {
            cadastro.incluir(entidade, entidade.getIdentificador());
            return true;
        }catch (RuntimeException a) {
            throw new ExcecaoObjetoJaExistente("Objeto ja existente");
        }
    }

    public T buscar(String identificador){
        return (T) cadastro.buscar(identificador);
    }

    public boolean alterar(T entidade) throws ExcecaoOobjetoNaoExistente{
        try {
            cadastro.alterar(entidade, entidade.getIdentificador());
            return true;
        }catch (RuntimeException e){
            throw new ExcecaoOobjetoNaoExistente("Objeto nao existente");
        }
    }

    public boolean excluir(String identificador) throws ExcecaoOobjetoNaoExistente{
        try {
            cadastro.excluir(identificador);
            return true;
        } catch (RuntimeException e){
            throw new ExcecaoOobjetoNaoExistente("Objeto nao existente");
        }
    }

    public T[] buscarTodos(){
        Serializable[] resultado = cadastro.buscarTodos();

        if (resultado == null){
            return null;
        }

        T[] novoArray = (T[]) new Serializable[resultado.length];

        for (int i = 0; i < resultado.length; i++){
            novoArray[i] = (T) resultado[i];
        }

        return novoArray;
    }
}
