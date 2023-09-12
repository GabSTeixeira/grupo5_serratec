package classes;

import java.util.Scanner;

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
		if(nome.length() <= 100) {
			return true;
		}
		return false;
	}
}
