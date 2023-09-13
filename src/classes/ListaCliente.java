package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import conexao.Conexao;
import dao.ClienteDAO;

public class ListaCliente {
	private Conexao con;
	private String schema;
	
	private ArrayList<Cliente> listaClientes = new ArrayList<>();
	private long quantidadeCliente;
	
	
	public ListaCliente (Conexao con, String schema) {
		this.con = con;
		this.schema = schema;
		
		carregarListaClientes();
	}
	
	
	public ArrayList<Cliente> getListaClientes () {
		return this.listaClientes;
	}
	
	public void adicionarClienteLista(Cliente c) {
		this.listaClientes.add(c);
		this.quantidadeCliente = listaClientes.size();
	}
	
	public void atualizarListaCliente () {
		carregarListaClientes();
	}
	
	public Cliente localizarCliente(int idCliente) {
		Cliente localizado = null;
		
		for (Cliente c : listaClientes) {
			if (c.getIdCliente() == idCliente) {
				localizado = c;
				break;
			}
		}		
	
		return localizado;
	}
	
 	private void carregarListaClientes() {
		ClienteDAO cdao = new ClienteDAO(con, schema);
		
		ResultSet tabela = cdao.carregarClientes();
		this.listaClientes.clear();
		
		try {
			tabela.beforeFirst();
			
			while (tabela.next()) {							
				this.listaClientes.add(dadosCliente(tabela));
			}
			
			this.quantidadeCliente = listaClientes.size();
			tabela.close();
		
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private Cliente dadosCliente(ResultSet tabela) { 
		Cliente c = new Cliente();
		String dtNasc; 
		
		try {
			dtNasc = tabela.getString("dtnasc");
			if (dtNasc != null)
				c.setDataNascimento(LocalDate.parse(dtNasc));
			c.setNome(tabela.getString("nome"));
			c.setEndereco(tabela.getString("endereco"));
			c.setSexo(tabela.getString("sexo"));
			c.setCpf(tabela.getString("cpf"));			
			c.setTelefone(tabela.getString("telefone"));
			c.setIdCliente(tabela.getInt("idcliente"));
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	public long getQuantidadeCliente() {
		return quantidadeCliente;
	}
	
	
}
