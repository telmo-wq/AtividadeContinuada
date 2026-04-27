package br.edu.cs.poo.ac.bolsa.util;
import br.edu.cs.poo.ac.bolsa.util.ResultadoValidacao;

public class ValidadorCpfCnpj {
	
	public static String converterCpf(String cpf) {
		String novoCpf = "";
		
		for (int i = 0; i < cpf.length(); i++) {
			char c = cpf.charAt(i);
			
			if (c != '.' && c != '-') {
				novoCpf = novoCpf + c;
			}
		}
		return novoCpf;
	}
	
	public static String converterCnpj(String cnpj) {
		String novoCnpj = "";
		
		for (int i = 0; i < cnpj.length(); i++) {
			char c = cnpj.charAt(i);
			
			if (c != '.' && c != '/' && c != '-') {
				novoCnpj = novoCnpj + c;
			}
				
		}
		return novoCnpj;
	}
	
	private static boolean dvCnpjValido(String cnpj) {
	    // cnpj já deve ter 14 dígitos, sem pontuação
	    
	    String base = cnpj.substring(0, 12);
	    
	    // Pesos para o primeiro DV
	    int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	    int dv1 = calcularDvCnpj(base, pesos1);
	    
	    // Pesos para o segundo DV
	    int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	    int dv2 = calcularDvCnpj(base + dv1, pesos2);
	    
	    int dvInformado1 = cnpj.charAt(12) - '0';
	    int dvInformado2 = cnpj.charAt(13) - '0';
	    
	    return (dv1 == dvInformado1 && dv2 == dvInformado2);
	}

	private static int calcularDvCnpj(String numeros, int[] pesos) {
	    int soma = 0;
	    
	    for (int i = 0; i < numeros.length(); i++) {
	        int digito = numeros.charAt(i) - '0';
	        soma += digito * pesos[i];
	    }
	    
	    int resto = soma % 11;
	    
	    if (resto < 2) {
	        return 0;
	    } else {
	        return 11 - resto;
	    }
	}
	
	public static boolean verificarNumerosRepetidos(String cpf) {
		char caractere = cpf.charAt(0);
		int contador = 0;
		
		for (int i = 0; i < cpf.length(); i++) {
			if(cpf.charAt(i) == caractere) {
				contador++;
			}
		}
		
		if(contador == cpf.length()) {
			return true;  //tem números repetidos em todas as casa
		}else {
			return false; //não tem números repetidos
		}
	}
	
	public static boolean verificarDvValidoCpf(String cpf) {
		int peso = 10;
		int soma = 0;
		String dv = "";
		
		for (int i = 0; i < 9; i++) {
			soma = soma + (cpf.charAt(i) - '0') * peso;
			peso = peso - 1;
		}
		
		int resto;
		
		if (soma % 11 >= 2) {
			resto = 11 - (soma % 11);
		}else {
			resto = 0;
		}
		dv = dv + resto;
		
		soma = 0;
		peso = 11;
		
		for (int i = 0; i < 10; i++) {
			soma = soma + (cpf.charAt(i) - '0') * peso;
			peso = peso - 1;
		}
		
		if (soma % 11 >= 2) {
			resto = 11 - (soma % 11);
		}else {
			resto = 0;
		}
		
		dv = dv + resto;
		
		String dvReal = cpf.substring(9, 11);
		
		if (dv.equals(dvReal)) {
			return true;  //o dv é válido
		}else {
			return false;  //o dv não é válido
		}
	}
	
	public static ResultadoValidacao validarCpf(String cpf) {
		if (cpf != null)
			cpf = converterCpf(cpf);
		
		if (cpf == null || cpf == "") {
			return ResultadoValidacao.NAO_INFORMADO;
		}
		
		if (cpf.length() != 11) {
			return ResultadoValidacao.FORMATO_INVALIDO;
		}
		
		if (verificarNumerosRepetidos(cpf)) {
			return ResultadoValidacao.FORMATO_INVALIDO;
		}
		
		if (!verificarDvValidoCpf(cpf)) {
			return ResultadoValidacao.DV_INVALIDO;
		}
		
		return null;
	}
	
	public static ResultadoValidacao validarCnpj(String cnpj) {
		if (cnpj != null)
			cnpj = converterCnpj(cnpj);
		
		if (cnpj == null || cnpj == "") {
			return ResultadoValidacao.NAO_INFORMADO;
		}
		
		if (cnpj.length() != 14) {
			return ResultadoValidacao.FORMATO_INVALIDO;
		}
		
		if (verificarNumerosRepetidos(cnpj)) {
			return ResultadoValidacao.FORMATO_INVALIDO;
		}
		
		if (!dvCnpjValido(cnpj)) {
			return ResultadoValidacao.DV_INVALIDO;
		}
		
		
		return null;
	}

}
