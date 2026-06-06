package br.edu.cs.poo.ac.bolsa.entidade;

import br.edu.cs.poo.ac.bolsa.util.Comparador;
import br.edu.cs.poo.ac.bolsa.util.Comparavel;

public class ComparadorInvestidorPessoaRenda implements Comparador{

    @Override
    public int comparar(Comparavel comp1, Comparavel comp2){
        if (!(comp1 instanceof InvestidorPessoa) || !(comp2
        instanceof InvestidorPessoa)){
            throw new RuntimeException("Pelo menos um dos argumentos nao e do tipo InvestidorPessoa");
        }

        InvestidorPessoa pessoa1 = (InvestidorPessoa) comp1;
        InvestidorPessoa pessoa2 = (InvestidorPessoa) comp2;

        if (pessoa1.getRenda() > pessoa2.getRenda()){
            return 1;
        }else if (pessoa1.getRenda() < pessoa2.getRenda()){
            return -1;
        }

        return 0;
    }
}
