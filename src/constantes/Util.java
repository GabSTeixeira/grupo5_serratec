package constantes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Util {
	public static Scanner in = new Scanner(System.in);
	public static final String CABECALHO = "SISTEMA BIBLIOTECA NACIONAL";
	public static final String LINHA = "----------------------------------";
	public static final String LINHAD = "==================================";
	
	public enum CRUD {
		CADASTRAR,
		ALTERAR,
		EXCLUIR,
		IMPRIMIR
	}

	public static void br() {
		System.out.println("");
	}
	
	public static void escrever(String mensagem) {
		System.out.println(mensagem);
	}
	
	public static LocalDate validarData(String mensagem) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataConvertida = null;
		String sData; 
		boolean dataValidada = false;
		
		do {
			System.out.print(mensagem);
			sData = in.nextLine();
			
			try {
				dataConvertida = LocalDate.parse(sData, dtf);
				
				dataValidada = true;
				return dataConvertida;
			} catch (Exception e) {
				System.out.println(" ♦ Data invalida!! ♦ \n");
			}
		} while (!dataValidada);
		
		return null;
	}
	
	public static LocalDate validarDataNascimento(String mensagem) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataConvertida = null;
		String sData; 
		
		LocalDate dataPermitida = LocalDate.parse("01/01/2009", dtf);
		
		boolean dataValidada = false;
		
		do {
			System.out.print(mensagem);
			sData = in.nextLine();
			
			try {
				dataConvertida = LocalDate.parse(sData, dtf);
				
				if (dataConvertida.isBefore(dataPermitida)) {
					dataValidada = true;
					return dataConvertida;
				}
				
				
				System.out.println(" ♦ Só permitimos Clientes com 14 anos ou mais!! ♦ \n");
				
			} catch (Exception e) {
				System.out.println(" ♦ Data invalida!! ♦ \n");
			}
		} while (!dataValidada);
		
		return null;
	}
	
	public static boolean isDateValid(String date) {
		try {
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    @SuppressWarnings("unused")
		    LocalDate d = LocalDate.parse(date, formatter);    
		    return true;
		} catch (DateTimeParseException e) {
		    return false;
		}   
   }
	
	public static int validarInteiro(String StringNumero) {
		int numero = 0;
		boolean validado = false;	
		
		do {
			try {
				numero = Integer.parseInt(StringNumero);
				validado = true;
			} catch (Exception e) {
				System.out.println(" ♦ Informe um Numero valido!! ♦ ");
				System.out.print("▸ ");
				StringNumero = in.nextLine();
				
			}
		} while (!validado);
		
		return numero;
	}
	
	public static boolean isInteger(String str) {
		try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	/*
	public static double validarDouble(String StringNumero) {
		String s;
		double numero = 0.0;
		boolean validado = false;
		Scanner in = new Scanner(System.in);
		
		do {
			try {
				s = in.next();			
				numero = Double.parseDouble(s);
				validado = true;
			} catch (Exception e) {
				System.out.println(" ♦ Informe um numero valido ♦ \n" + e.getMessage());
			}
		} while (!validado);
		
		in.close();
		
		return numero;
	}
	*/
	
	public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
