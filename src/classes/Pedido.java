package classes;

import java.time.LocalDate;
import java.util.ArrayList;


public class Pedido {
	private int idPedido;
	private Cliente cliente;
	private ArrayList<ProdutoVendido> produtos = new ArrayList<>();
	private LocalDate data;
	private long qtdItens;
	private double total;
	
	
	public Pedido (){};
	
	public Pedido (Cliente cliente, ArrayList<ProdutoVendido> produtos, LocalDate data, long qtdItens, double total) {
		this.cliente = cliente;
		this.produtos = produtos;
		this.data = data;
		this.qtdItens = qtdItens;
		this.total = total;
	}
	
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public ArrayList<ProdutoVendido> getProdutos() {
		return produtos;
	}
	
	public void setProdutos (ArrayList<ProdutoVendido> produtos) {
		this.produtos = produtos;
	}
	public void adicionarProdutoLista(ProdutoVendido produtos) {
		this.produtos.add(produtos);
	}
	public ProdutoVendido localizarProduto (int id) {
		ProdutoVendido localizado = null;
		
		for (ProdutoVendido p : produtos) {
			if (id == p.getIdProduto()) {
				localizado = p;
				break;
			}
		}	
	
		return localizado;
	}
	public void setQtdItens(long qtdItens) {
		this.qtdItens = qtdItens;
	}
	
	public long getQtdItens() {
		return qtdItens;
	}
	
	public void setTotal (double total) {
		this.total = total;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void calcularQtdItens () {
		long somaQtdItens = 0;
		for (ProdutoVendido p : this.produtos) {
			somaQtdItens += p.getQtdVendida();
		}
		
		this.qtdItens = somaQtdItens;
	}
	
	public void calcularTotal () {
		double somaTotal = 0;
		for (ProdutoVendido p: this.produtos) {
			somaTotal += p.getTotal();
		}
		
		this.total = somaTotal;
	}
	
	
	
	
}
