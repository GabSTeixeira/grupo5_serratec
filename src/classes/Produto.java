package classes;

import java.util.Scanner;

import constantes.Util;

public class Produto {
	private int idProduto;
	private String nome;
	private double preco;
	private String desc;
	private int estoque;
	
	public Produto (){}
	
	public Produto (String nome, double preco, String desc, int estoque) {
		this.nome = nome;
		this.preco = preco;
		this.desc = desc;
		this.estoque = estoque;
	}
	
	
	public Produto (int id, String nome, double preco, String desc, int estoque) {
		this.idProduto = id;
		this.nome = nome;
		this.preco = preco;
		this.desc = desc;
		this.estoque = estoque;
	}
	
	public void decrementarEstoque(int qtd) {
		this.estoque -= qtd;
	}
	public int getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(String preco) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		boolean sair = false;
		double	precod;
		do {
			if(Util.isDouble(preco)) {
				precod = Double.parseDouble(preco);
				this.preco = precod;
				sair = true;	
			} else {
				System.out.println("\nInforme um valor válido!\n");
				preco = sc.nextLine();
			}
		
		}while(!sair);
		
	} 
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getEstoque() {
		return estoque;
	}
	public void setEstoque(String estoque) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		boolean sair = false;
		int	estoqueint;
		do {
			if(Util.isInteger(estoque)) {
				estoqueint = Integer.parseInt(estoque);
				this.estoque = estoqueint;
				sair = true;	
			} else {
				System.out.println("\nInforme um valor válido!\n");
				estoque = sc.nextLine();
			}
		
		}while(!sair);
	}
	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
	public String getNomeProduto() {
		return nome;
	}
	public void setNomeProduto (String nome) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String opcao = nome;
		
		while(!validarNomeProduto(opcao)) {
			System.out.println("\nErro nome do produto invalido. Digite um nome com até 100 caracteres.");
			opcao = sc.nextLine();
		}
		this.nome = opcao;
	}	
	private boolean validarNomeProduto (String nome) {
		if(nome.length() <= 100 && !nome.isBlank()) {
			return true;
		}
		return false;
	}
}
