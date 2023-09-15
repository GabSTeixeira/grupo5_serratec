package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexao.Conexao;
import dao.ProdutoDAO;

public class ListaProduto {
	private Conexao con;
	private String schema;
	
	private ArrayList<Produto> produtos = new ArrayList<>();
	private int qtdProdCadastrados;
	
	public ListaProduto (Conexao con, String schema) {
		this.con = con;
		this.schema = schema;
		
		carregarListaProdutos();
	}
	
	
	
	public ArrayList<Produto> getProdutos() {
		return produtos;
	}
	public void adicionarProdutoLista(Produto produto) {
		produto.setIdProduto(produtos.size()+1);
		
		this.produtos.add(produto);
		this.qtdProdCadastrados = produtos.size();
	}
	
	public void atualizarListaProduto  () {
		carregarListaProdutos();
	}
	
	
	private void carregarListaProdutos() {
		ProdutoDAO pdao = new ProdutoDAO(con, schema);
		
		ResultSet tabela = pdao.carregarProdutos();
		this.produtos.clear();
		
		try {
			tabela.beforeFirst();
			
			while (tabela.next()) {							
				this.produtos.add(dadosProduto(tabela));
				
			}
			
			this.qtdProdCadastrados = produtos.size();
			tabela.close();
		
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	
	public Produto localizarProduto(int idProduto) {
        Produto localizado = null;

        for (Produto p : produtos) {
            if (p.getIdProduto() == idProduto) {
                localizado = p;
                break;
            }
        }

        return localizado;
    }
	
	public ArrayList <Produto> localizarProduto(String nomeProduto) {
		 ArrayList <Produto> produtosEncontrados = new ArrayList<>();

        for (Produto p : produtos) {
            if (p.getNomeProduto().equals(nomeProduto)) {
                produtosEncontrados.add(p);
            }
        }
        return produtosEncontrados;
	        
	}
	
	private Produto dadosProduto(ResultSet tabela) { 
		Produto prod = new Produto();
		
		try {
			prod.setNomeProduto(tabela.getString("nome"));
			prod.setPreco(tabela.getDouble("preco"));
			prod.setDesc(tabela.getString("descricao"));
			prod.setEstoque(tabela.getInt("estoque"));			
			prod.setIdProduto(tabela.getInt("idproduto"));
			return prod;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void decrementarEstoqueProdutos (ArrayList<ProdutoVendido> produtos) {
		ProdutoDAO pdao = new ProdutoDAO(con, schema);
		
		for (ProdutoVendido pv: produtos) {
			for(Produto p: this.produtos) {
			
				if(p.getIdProduto() == pv.getIdProduto()) {
					int prodEncontrado = this.produtos.indexOf(p);
					
					this.produtos.get(prodEncontrado)
							.decrementarEstoque(pv.getQtdVendida());
					
					pdao.alterarProdutoEstoque(this.produtos.get(prodEncontrado));
					
					continue;
				}
			}
		}
	}
	
	public int getQtdProdCadastrados() {
		return qtdProdCadastrados;
	}
}
