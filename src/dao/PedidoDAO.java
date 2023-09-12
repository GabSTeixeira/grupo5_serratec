package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import classes.Pedido;
import classes.ProdutoVendido;
import conexao.Conexao;

public class PedidoDAO {
	private Conexao conexao;
	private String schema;
	
	PreparedStatement pInclusao;
	PreparedStatement pAlteracao;
	PreparedStatement pExclusao;
	
	public PedidoDAO(Conexao conexao, String schema) { 
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusao();
		prepararSqlAlteracao();
		prepararSqlExclusao();
	}
	
	private void prepararSqlExclusao() {
		String sql = "delete from "+ this.schema + ".pedido";
		sql += " where idpedido = ?";
		
		try {
			this.pExclusao = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlInclusao() {
		String sql = "insert into "+ this.schema + ".pedido";	
		sql += " (idcliente, datapedido, qtditens, total)";
		sql += " values ";
		sql += " (?, ?, ?, ?)";
		
		try {
			this.pInclusao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlAlteracao() {
		String sql = "update "+ this.schema + ".pedido";	
		sql += " set idcliente = ?,";
		sql += " datapedido = ?,";
		sql += " qtditens = ?,";
		sql += " total = ?";
		sql += " where idpedido = ?";
		try {
			this.pAlteracao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public int alterarPedido(Pedido pedido) {
		try {
			pAlteracao.setInt(1, pedido.getCliente().getIdCliente());
			pAlteracao.setDate(2, Date.valueOf(pedido.getData()));
			pAlteracao.setLong(3, pedido.getQtdItens());
			pAlteracao.setDouble(4, pedido.getTotal());
			pAlteracao.setInt(5, pedido.getIdPedido());
			return pAlteracao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nPedido nao alterado.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public int incluirPedido(Pedido pedido) {
		try {				
			pInclusao.setInt(1, pedido.getCliente().getIdCliente());
			pInclusao.setDate(2, Date.valueOf(pedido.getData()));
			pInclusao.setLong(3, pedido.getQtdItens());
			pInclusao.setDouble(4, pedido.getTotal());
			return pInclusao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nPedido nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public int excluirPedido(Pedido pedido) {
		try {
			pExclusao.setInt(1, pedido.getIdPedido());
			
			PedidoProdutoDAO pedao = new PedidoProdutoDAO(conexao, schema);
			
			pedao.excluirTodosPorPedido(pedido);
			
			
			return pExclusao.executeUpdate();
		} catch  (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nPedido nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public int buscarIdPedidoMaisRecente () {
		ResultSet tabela;
		
		String sql = "select idpedido from " + this.schema +".pedido order by idpedido desc limit 1";
		
		tabela = conexao.query(sql);
		
		try {
			tabela.next();
			return tabela.getInt("idpedido");
		} catch (SQLException e) {
			System.out.println("não foi possivel buscar o idpedido mais recente");
			e.printStackTrace();
			return -1;
		}
	}
	
	
	public Pedido buscarPedido(int id) {
		ResultSet tabela;
		ResultSet tabelaProdutos;
		@SuppressWarnings("unused")
		Pedido pedido;
		ClienteDAO cdao = new ClienteDAO(conexao, schema);
		ProdutoDAO pdao = new ProdutoDAO(conexao, schema);
		PedidoProdutoDAO peprodao = new PedidoProdutoDAO(conexao, schema);
		
		String sql = "select * from " + this.schema + ".pedido where idpedido = " + id;
		
		tabela = conexao.query(sql);
		
		ArrayList<ProdutoVendido> produtos = new ArrayList<>();
		
		tabelaProdutos = peprodao.buscarPedidosPorIdPedidos(id);
		
		
		try {
			if(tabelaProdutos.next()) {
				ProdutoVendido prod = new ProdutoVendido(pdao.buscarProduto(id),
						tabelaProdutos.getInt("qtdvendida"), tabelaProdutos.getDouble("subtotalitem"));
				
				produtos.add(prod);
			}
		
			pedido = new Pedido(cdao.buscarCliente(tabela.getInt("idcliente")), produtos,
					LocalDate.parse(tabela.getString("datapedido")), tabela.getLong("qtditens"),
					tabela.getDouble("total"));
			pedido.setIdPedido(tabela.getInt("idpedido"));
			
			return pedido;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet carregarPedidos() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".pedido order by idpedido";
		
		tabela = conexao.query(sql);
			
		return tabela;
	}
}