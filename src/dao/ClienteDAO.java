package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import classes.Cliente;
import conexao.Conexao;

public class ClienteDAO {
	private Conexao conexao;
	private String schema;
	
	PreparedStatement pInclusao;
	PreparedStatement pAlteracao;
	PreparedStatement pExclusao;
	
	
	public ClienteDAO(Conexao conexao, String schema) { 
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusao();
		prepararSqlAlteracao();
		prepararSqlExclusao();
	}
	
	private void prepararSqlExclusao() {
		String sql = "delete from "+ this.schema + ".cliente";
		sql += " where idcliente = ?";
		
		try {
			this.pExclusao = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlInclusao() {
		String sql = "insert into "+ this.schema + ".cliente";	
		sql += " (nome, cpf, endereco, dtnasc, sexo, telefone)";
		sql += " values ";
		sql += " (?, ?, ?, ?, ?, ?)";
		
		try {
			this.pInclusao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlAlteracao() {
		String sql = "update "+ this.schema + ".cliente";	
		sql += " set nome = ?,";
		sql += " cpf = ?,";
		sql += " endereco = ?,";
		sql += " dtnasc = ?,";
		sql += " sexo = ?,";
		sql += " telefone = ?";
		sql += " where idcliente = ?";
		
		try {
			this.pAlteracao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public int alterarCliente(Cliente cliente) {
		try {
			pAlteracao.setString(1, cliente.getNome());
			pAlteracao.setString(2, cliente.getCpf());
			pAlteracao.setString(3, cliente.getEndereco());
			pAlteracao.setDate(  4, Date.valueOf(cliente.getDataNascimento()));
			pAlteracao.setString(5, ""+cliente.getSexo());
			pAlteracao.setString(6, cliente.getTelefone());
			pAlteracao.setInt(7, cliente.getIdCliente());
			
			return pAlteracao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nCliente nao alterado.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public int incluirCliente(Cliente cliente) {
		try {				
			pInclusao.setString(1, cliente.getNome());
			pInclusao.setString(2, cliente.getCpf());
			pInclusao.setString(3, cliente.getEndereco());
			pInclusao.setDate(4, Date.valueOf(cliente.getDataNascimento()));
			pInclusao.setString(5, ""+cliente.getSexo());
			pInclusao.setString(6, cliente.getTelefone());
			
			return pInclusao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nCliente nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public int excluirCliente(Cliente cliente) {
		try {
			pExclusao.setInt(1, cliente.getIdCliente());
			
			return pExclusao.executeUpdate();
		} catch  (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nCliente nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public Cliente buscarCliente(int id) {
		ResultSet tabela;
		@SuppressWarnings("unused")
		Cliente cliente;
		
		String sql = "select * from " + this.schema + ".cliente where idcliente = " + id;
		
		tabela = conexao.query(sql);
		
		
		try {
			tabela.next();
			cliente = new Cliente(tabela.getString("nome"), tabela.getString("cpf"),
					LocalDate.parse(tabela.getString("dtnasc")), tabela.getString("sexo"), 
					tabela.getString("telefone"), tabela.getString("endereco"));
			cliente.setIdCliente(id);
			
			return cliente;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet carregarClientes() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".cliente order by idcliente";
		
		tabela = conexao.query(sql);
			
		return tabela;
	}
}
