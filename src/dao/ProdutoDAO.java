package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import classes.Produto;
import conexao.Conexao;

public class ProdutoDAO {
	private Conexao conexao;
	private String schema;
	
	PreparedStatement pInclusao;
	PreparedStatement pAlteracao;
	PreparedStatement pAlteracaoEstoque;
	PreparedStatement pExclusao;
	
	public ProdutoDAO(Conexao conexao, String schema) { 
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusao();
		prepararSqlAlteracao();
		prepararSqlAlteracaoEstoque();
		prepararSqlExclusao();
	}
	
	private void prepararSqlExclusao() {
		String sql = "delete from "+ this.schema + ".produto";
		sql += " where idproduto = ?";
		
		try {
			this.pExclusao = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlInclusao() {
		String sql = "insert into "+ this.schema + ".produto";	
		sql += " (nome, preco, descricao, estoque)";
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
		String sql = "update "+ this.schema + ".produto";	
		sql += " set nome = ?,";
		sql += " preco = ?,";
		sql += " descricao = ?,";
		sql += " estoque = ?";
		sql += " where idproduto = ?";
		
		try {
			this.pAlteracao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlAlteracaoEstoque() {
		String sql = "update "+ this.schema + ".produto";	
		sql += " set estoque = ?";
		sql += " where idproduto = ?";
		
		try {
			this.pAlteracaoEstoque =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public int alterarProduto(Produto produto) {
		try {
			pAlteracao.setString(1, produto.getNomeProduto());
			pAlteracao.setDouble(2, produto.getPreco());
			pAlteracao.setString(3, produto.getDesc());
			pAlteracao.setInt(4, produto.getEstoque());
			pAlteracao.setInt(5, produto.getIdProduto());
			
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
	
	public int alterarProdutoEstoque(Produto produto) {
		try {
			pAlteracaoEstoque.setInt(1, produto.getEstoque());
			pAlteracaoEstoque.setInt(2, produto.getIdProduto());
			
			return pAlteracaoEstoque.executeUpdate();
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
	
	public int incluirProduto(Produto produto) {
		try {				
			pInclusao.setString(1, produto.getNomeProduto());
			pInclusao.setDouble(2, produto.getPreco());
			pInclusao.setString(3, produto.getDesc());
			pInclusao.setInt(4, produto.getEstoque());
			
			return pInclusao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nProduto nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public int excluirProduto(Produto produto) {
		try {
			pExclusao.setInt(1, produto.getIdProduto());
			
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
	
	public Produto buscarProduto(int id) {
		ResultSet tabela;
		Produto produto;
		
		String sql = "select * from " + this.schema + ".produto where idproduto = " + id;
		
		tabela = conexao.query(sql);
		
		try {
			produto = new Produto(tabela.getString("nome"), tabela.getDouble("preco"),
					tabela.getString("descricao"), tabela.getInt("estoque"));
			produto.setIdProduto(id);
			
			return produto;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet carregarProdutos() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".produto order by idproduto";
		
		tabela = conexao.query(sql);
			
		return tabela;
	}
}