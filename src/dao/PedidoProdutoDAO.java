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
	PreparedStatement pInclusaoUnicoProduto;
	PreparedStatement pAlteracao;
	PreparedStatement pExclusao;
	PreparedStatement pExclusaoTodos;
	
	public PedidoProdutoDAO(Conexao conexao, String schema) { 
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusao();
		prepararSqlInclusaoUnico();
		prepararSqlAlteracao();
		prepararSqlExclusao();
		prepararSqlExclusaoTodos();
	}
	
	private void prepararSqlExclusao() {
		String sql = "delete from "+ this.schema + ".pedidoproduto";
		sql += " where idpedido = ? and idproduto = ?";
		
		try {
			this.pExclusao = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	private void prepararSqlExclusaoTodos() {
		String sql = "delete from "+ this.schema + ".pedidoproduto";
		sql += " where idpedido = ?";
		
		try {
			this.pExclusaoTodos = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlInclusaoUnico() {
		String sql = "insert into "+ this.schema +".pedidoproduto";
		sql += " (idproduto, idpedido, qtdvendida, subtotalitem)";
		sql += " values ";
		sql += " (?, ?, ?, ?)";
		
		try {
			this.pInclusaoUnicoProduto = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nProduto nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
		}
		
	}
	
	private void prepararSqlInclusao() {
		String sql = "insert into "+ this.schema + ".pedidoproduto";	
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
		sql += " set qtdvendida = ?,";
		sql += " subtotalitem = ?";
		sql += " where idpedido = ? and idproduto = ?";
		
		try {
			this.pAlteracao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public int alterarPedidoProduto(Pedido p, ProdutoVendido prod) {
		int indiceProduto = p.getProdutos().indexOf(prod);
		
		try {
			
			pAlteracao.setInt(1, p.getProdutos().get(indiceProduto).getQtdVendida());
			pAlteracao.setDouble(2, p.getProdutos().get(indiceProduto).getTotal());
			pAlteracao.setLong(3, p.getIdPedido());
			pAlteracao.setInt(4, p.getProdutos().get(indiceProduto).getIdProduto());
			
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
	
	public void incluirProdutoUnico(ProdutoVendido p, int idPedido) {
		
		try {				
			
			pInclusaoUnicoProduto.setInt(1, p.getIdProduto());
			pInclusaoUnicoProduto.setInt(2, idPedido);
			pInclusaoUnicoProduto.setInt(3, p.getQtdVendida());
			pInclusaoUnicoProduto.setDouble(4, p.getTotal());
			
			pInclusaoUnicoProduto.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nProduto nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
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
	
 	public int excluirPedidoProduto(Pedido p, int idProduto) {
		try {
 			pExclusao.setInt(1, p.getIdPedido());
			pExclusao.setInt(2, p.localizarProduto(idProduto).getIdProduto());
			
			return pExclusao.executeUpdate();
		} catch  (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nExclusão não realizada.\nVerifique se foi chamado o conect:\n" + e);				
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
	
	public ResultSet buscarInfoPorIdPedidos(int idPedido) {
		ResultSet tabela;
		String sql = "select * from " + this.schema + ".pedidoproduto where idpedido = "+idPedido+" order by idpedidoproduto";
		
		tabela = conexao.query(sql);
		
		return tabela;
	}
}
