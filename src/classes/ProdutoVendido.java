package classes;

public class ProdutoVendido extends Produto {
	private int qtdVendida = 0;
	private double total;
	
	public ProdutoVendido (){}
	
	public ProdutoVendido (Produto p, int qtdVendido) {
		super(p.getIdProduto(), p.getNomeProduto(), p.getPreco(), p.getNomeProduto(), p.getEstoque());
		this.qtdVendida = qtdVendido;
		
		this.total = this.qtdVendida * super.getPreco();
	}
	
	public ProdutoVendido (Produto p, int qtdVendido, double total) {
		super(p.getNomeProduto(), p.getPreco(), p.getNomeProduto(), p.getEstoque());
		this.qtdVendida = qtdVendido;
		this.total = total;
	}
	
	public double calcularTotal () {
		return this.total = this.qtdVendida * super.getPreco();
	}
	
	public int getQtdVendida() {
		return qtdVendida;
	}

	public void setQtdVendida(int qtdVendida) {
		this.qtdVendida = qtdVendida;
	}
	
	public void setTotal (double total) {
		this.total = total;
	}

	public double getTotal() {
		return total;
	}
	
}
