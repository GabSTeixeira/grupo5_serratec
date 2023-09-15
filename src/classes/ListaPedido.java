package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import conexao.Conexao;
import dao.PedidoDAO;
import dao.PedidoProdutoDAO;
import principal.Principal;

public class ListaPedido {
	private Conexao con;
	private String schema;
	
	private ArrayList<Pedido> pedidos = new ArrayList<>();
	private long quantidadePedido = 0;
	
	public ListaPedido(Conexao con, String schema) {
		this.con = con;
		this.schema = schema;
		
		carregarListaPedido();
		carregarProdutosPedido();
	}
	
	
	public ArrayList<Pedido> getListaPedido () {
		return this.pedidos;
	}
	
	public void adicionarPedidoLista(Pedido p) {
		this.pedidos.add(p);
		this.quantidadePedido = pedidos.size();
	}
	
	public Pedido localizarPedido(int idPedido) {
		Pedido localizado = null;
		
		for (Pedido p : pedidos) {
			if (p.getIdPedido() == idPedido) {
				localizado = p;
				break;
			}
		}		
	
		return localizado;
	}
	
	public ArrayList <Pedido> localizarPedido(LocalDate dataPedido) {	
		ArrayList <Pedido> pedidosEcontrados = new ArrayList<>();
		
		for (Pedido p : pedidos) {
			if (p.getData().equals(dataPedido)){
				pedidosEcontrados.add(p);
			}
		}			
		return pedidosEcontrados;
	}
	
	public void atualizarListaPedido() {
		carregarListaPedido();
		carregarProdutosPedido();
	}
	
	
 	private void carregarListaPedido() {
		PedidoDAO pedao = new PedidoDAO(con, schema);
		
		ResultSet tabela = pedao.carregarPedidos();
		this.pedidos.clear();
		
		try {
			tabela.beforeFirst();
			
			while (tabela.next()) {
				this.pedidos.add(dadosPedido(tabela));
			}
			
			this.quantidadePedido = pedidos.size();
			tabela.close();
		
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private Pedido dadosPedido(ResultSet tabela) { 
		Pedido ped = new Pedido();	

		try {
			ped.setIdPedido(tabela.getInt("idpedido"));
			ped.setCliente(Principal.clientes.localizarCliente(tabela.getInt("idcliente")));
			ped.setData(LocalDate.parse(tabela.getString("datapedido")));
			ped.setQtdItens(tabela.getLong("qtditens"));
			ped.setTotal(tabela.getDouble("total"));		
			return ped;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void carregarProdutosPedido () {
		
		for (Pedido p: this.pedidos) {
			p.setProdutos(carregarProdutos(p.getIdPedido()));
		}
	}
	
	private ArrayList<ProdutoVendido> carregarProdutos(int id) {
		ResultSet tabelaProdutos;
		PedidoProdutoDAO peprodao = new PedidoProdutoDAO(con, schema);
		
		ArrayList<ProdutoVendido> produtos = new ArrayList<>();
		
		tabelaProdutos = peprodao.buscarInfoPorIdPedidos(id);
		
		try {
			while (tabelaProdutos.next()) {
				produtos.add(
						new ProdutoVendido(
								principal.Principal.produtos.localizarProduto(tabelaProdutos.getInt("idproduto")),
								tabelaProdutos.getInt("qtdvendida")
								));
			}
			return produtos;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public long getQuantidadePedido() {
		return quantidadePedido;
	}
	
}
