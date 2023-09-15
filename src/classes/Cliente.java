package classes;

import java.time.LocalDate;
import java.util.Scanner;

public class Cliente {
	
	private int idCliente;
	private String nome;	
	private String cpf;
	private LocalDate dataNascimento;
	private char sexo;
	private String telefone;
	private String endereco;
	
	public Cliente () {
		
	}
	
	public Cliente (String nome, String cpf, LocalDate dataNasc, String sexo, String tel, String endereco) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNasc;
		this.setSexo(sexo);
		this.telefone = tel;
		this.endereco = endereco;
	}
	
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNome() {
		return nome;
	}
	public void setNome (String nome) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String opcao = nome;
		
		while(!validarNome(opcao)) {
			System.out.println("\n ♦ Erro - Digite um nome válido ♦ ");
			System.out.print("▸ ");
			opcao = sc.nextLine();
		}
		this.nome = opcao;
	}		
	private boolean validarNome (String nome) {
		if(nome == "") {
			return false;
		}else if(nome.length() <= 100) {
			return true;
		}
		return false;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf (String cpf) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String opcao = cpf;
		
		while(!validarCpf(opcao)) {
			System.out.println("\n ♦ Erro - Digite um CPF válido ♦ ");
			System.out.print("▸ ");
			opcao = sc.nextLine();
		}
		this.cpf = opcao.replaceAll("\\D", "");
	}
	private boolean validarCpf (String cpf) {
		if(cpf.length() == 11) {
			return true;
		}
		return false;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone (String telefone) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String opcao = telefone;
		
		while(!validarTelefone(opcao)) {
			System.out.println("\n ♦ Erro - Digite um telefone válido ♦ ");
			System.out.print("▸ ");
			opcao = sc.nextLine();
		}
		this.telefone = opcao.replaceAll("\\D", "");
		
	}	
	private boolean validarTelefone (String telefone) {
		if(telefone.length() == 11) {
			return true;
		}
		return false;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco (String endereco) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String opcao = endereco;
		
		while(!validarEndereco(opcao)) {
			System.out.println("\n ♦ Erro endereço invalido. Digite um endereço com até 150 caracteres ♦ ");
			System.out.print("▸ ");
			opcao = sc.nextLine();
		}
		this.endereco = opcao;
	}	
	private boolean validarEndereco (String endereco) {
		if(endereco == "") {
			return false;
		}else if(endereco.length() <= 150) {
			return true;
		}
		return false;
	}
	private boolean validarSexo (String sexo) {
		char letraValida = sexo.toUpperCase().charAt(0);
		
		if(sexo == "") {
			return false;
		}else if(letraValida == 'M' || letraValida == 'F' || letraValida == 'I') {
			return true;	
		}
		return false;
	}
	public char getSexo() {
		return this.sexo;
	}
	public void setSexo(String sexo) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String opcao = sexo;
		
		while (!validarSexo(opcao)) {
			System.out.println("\n ♦ Erro sexo invalido. DIGITE M, F OU I para indefinido ♦ ");
			System.out.print("▸ ");
			opcao = sc.nextLine();
		};
		
		this.sexo = opcao.toUpperCase().charAt(0);
	}
}