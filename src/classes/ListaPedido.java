package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import conexao.Conexao;
import dao.PedidoDAO;
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
	
	public void atualizarListaPedido() {
		carregarListaPedido();
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

	public long getQuantidadePedido() {
		return quantidadePedido;
	}
	
}
