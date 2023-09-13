package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import classes.Pedido;
import classes.ProdutoVendido;
import conexao.Conexao;

public class PedidoProdutoDAO {
	private Conexao conexao;
	private String schema;
	
	PreparedStatement pInclusao;
	PreparedStatement pAlteracao;
	PreparedStatement pExclusao;
	PreparedStatement pExclusaoTodos;
	
	public PedidoProdutoDAO(Conexao conexao, String schema) { 
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusao();
		prepararSqlAlteracao();
		prepararSqlExclusao();
		prepararSqlExclusaoTodos();
	}
	
	private void prepararSqlExclusao() {
		String sql = "delete from "+ this.schema + ".pedidoProduto";
		sql += " where idpedido = ? and idproduto = ?";
		
		try {
			this.pExclusaoTodos = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlExclusaoTodos() {
		String sql = "delete from "+ this.schema + ".pedidoProduto";
		sql += " where idpedido = ?";
		
		try {
			this.pExclusaoTodos = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlInclusao() {
		String sql = "insert into "+ this.schema + ".pedidoProduto";	
		sql += " (idproduto, idpedido, qtdvendida, subtotalitem)";
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
		String sql = "update "+ this.schema + ".pedidoproduto";	
		sql += " set idproduto = ?,";
		sql += " idpedido = ?,";
		sql += " qtdvendida = ?,";
		sql += " subtotalitem = ?";
		sql += " where idpedidoproduto = ?";
		
		try {
			this.pAlteracao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public int alterarPedidoProduto(Pedido p, int idProduto, int novaQtdVendida) {
		ProdutoVendido localizado = p.localizarProduto(idProduto);
		
		try {
			pAlteracao.setInt(1, localizado.getIdProduto());
			pAlteracao.setInt(2, p.getIdPedido());
			localizado.setQtdVendida(novaQtdVendida);
			pAlteracao.setLong(3, novaQtdVendida);
			pAlteracao.setDouble(4, localizado.calcularTotal());
			pAlteracao.setInt(5, p.getIdPedido());
			
			return pAlteracao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nProduto nao alterado.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public void incluirPedidoProduto(Pedido p) {
		
		for (ProdutoVendido produto: p.getProdutos()) {
			try {				
				
				pInclusao.setInt(1, produto.getIdProduto());
				pInclusao.setInt(2, p.getIdPedido());
				pInclusao.setInt(3, produto.getQtdVendida());
				pInclusao.setDouble(4, produto.getTotal());
				
				pInclusao.executeUpdate();
			} catch (Exception e) {
				if (e.getLocalizedMessage().contains("is null")) {
					System.err.println("\nProduto nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
				} else {				
					System.err.println(e);
					e.printStackTrace();
				}

			}
		}
	}
	
	public int excluirTodosPorPedido (Pedido p) {
		try {
			pExclusaoTodos.setInt(1, p.getIdPedido());
			
			return pExclusaoTodos.executeUpdate();
		} catch  (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nProduto nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	// fazer decrementa��o na tabela pedido no campo total e na quantidade de intens = fazer.
 	public int excluirPedidoProduto(Pedido p, int idProduto) {
		try {
			pExclusao.setInt(1, p.getIdPedido());
			pExclusao.setInt(2, p.localizarProduto(idProduto).getIdProduto());
			
			return pExclusao.executeUpdate();
		} catch  (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nProduto nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}

	public ResultSet carregarPedidoProduto() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".pedidoproduto order by idpedidoproduto";
		
		tabela = conexao.query(sql);
			
		return tabela;
	}
	
	public ResultSet buscarPedidosPorIdPedidos(int idPedido) {
		ResultSet tabela;
		String sql = "select * from " + this.schema + ".pedidoproduto where idpedido = "+idPedido+" order by idpedidoproduto";
		
		tabela = conexao.query(sql);
		
		return tabela;
	}
}