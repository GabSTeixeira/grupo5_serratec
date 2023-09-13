package principal;

import java.time.LocalDate;

import java.util.Scanner;

import arquivo.ArquivoTxt;
import classes.Cliente;
import classes.ListaCliente;
import classes.ListaPedido;
import classes.ListaProduto;
import classes.Pedido;
import classes.Produto;
import classes.ProdutoVendido;
import conexao.Conexao;
import conexao.DadosConexao;
import constantes.Util;
import dao.ClienteDAO;
import dao.CreateDAO;
import dao.PedidoDAO;
import dao.PedidoProdutoDAO;
import dao.ProdutoDAO;

public class Principal {
	
	

	// id = size+1 muito ruim, trocar todos, já foi trocado em cadastrar pedido.
	
	
	
	// excluir pedido tem que devolver os produtos do pedido para o estoque da tabela produto? sim
	// FAZER ESTA MERDA URGENTE!!!!!
	
	
	
	// vai executar o codigo no cmd como jar ou no eclipse? vai executar pelo eclipse
	// fazer a consulta do relatorio usando join, ou baixar os dados do relatorio e fazer a consulta como
	// arrayList no java?

	// não entedemos o relatorio. por favor explicar.

	// perguntar se o menu esta legal, porque a versão dele estava confuso para mexer.
	
	
	public static Conexao con;
	public static DadosConexao dadosCon = new DadosConexao();
	
	public static final String BANCO = "grupofive";
	public static final String SCHEMA = "sistema";
	public static final String PATH = "C:\\temp\\";
	public static final String SFILE = "DadosConexao.ini";
	
	public static ListaCliente clientes;
	public static ListaProduto produtos;
	public static ListaPedido pedidos;
	
	public static void main(String[] args) {
		
		if (configInicial()) {
            if (CreateDAO.createBD(BANCO, SCHEMA, dadosCon)) {
                con = new Conexao(dadosCon); 
                con.conect();
                clientes = new ListaCliente(con, SCHEMA);
                produtos = new ListaProduto(con, SCHEMA);
                pedidos = new ListaPedido(con, SCHEMA);
            	
            	menu();
            } else {
                System.out.println(" ♦ Ocorreu um problema na criacao do banco de dados ♦ ");
            }
        } else
            System.out.println(" ♦ Não foi possível executar o sistema! ♦ ");
    }
	
	public static boolean configInicial() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		ArquivoTxt conexaoIni = new ArquivoTxt(PATH+SFILE);
		boolean abrirSistema = false;
		
		if (conexaoIni.criarArquivo()) {
			if (conexaoIni.alimentaDadosConexao()) {
				dadosCon = conexaoIni.getData();
				abrirSistema = true;
			} else {
				conexaoIni.apagarArquivo();
				System.out.println("Arquivo de configuração de conexão:\n");
				System.out.println("Local: ");
				String local = input.nextLine();
				System.out.println("Porta: ");
				String porta = input.nextLine();
				System.out.println("Usuário: ");
				String usuario = input.nextLine();
				System.out.println("Senha: ");
				String senha = input.nextLine();
				System.out.println("Database: ");
				String banco = input.nextLine();
				
				if (conexaoIni.criarArquivo()) {
					conexaoIni.escreverArquivo("bd=PostgreSql");
					conexaoIni.escreverArquivo("local="+local);
					conexaoIni.escreverArquivo("porta="+porta);
					conexaoIni.escreverArquivo("usuario="+usuario);
					conexaoIni.escreverArquivo("senha="+senha);
					conexaoIni.escreverArquivo("banco="+banco);
					
					if (conexaoIni.alimentaDadosConexao()) {
						dadosCon = conexaoIni.getData();
						abrirSistema = true;
					} else System.out.println("Não foi possível efetuar a configuração.\nVerifique");	
				}
			}
			
			return abrirSistema;
		} else {
			System.out.println("Houve um problema na criação do arquivo de configuração.");
			return abrirSistema;
		}		
	}
	//-------------------------------------menu-------------------------------------
	public static void menu() {
		boolean imprimirMenu = true;
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		do {
			
			System.out.println(
					"═════════════════════════════════════════════════════════════════════════════\n"+
					"                        ♣ Menu Principal ♣\n"+
					"═════════════════════════════════════════════════════════════════════════════\n"+
					" 1) adasdasdsadsadsadsadsadsad\nasdsadsadsadasdsada\nawdsadsadasdsadasdsa\nasdsadddddadad\nasdadsadasdsadaMenu Cliente\n"+			
					" 2) Menu Produto\n"+"\nasdsadsaedewadadasd"+
					" 3) Menu Pedido\n"+
					" 4) Sair\n"+
					"═════════════════════════════════════════════════════════════════════════════\n"+
					" ♦ Informe uma opção ♦ \n▸ "
					);		
			
			int opcao = Util.validarInteiro(in.nextLine());
		
			switch (opcao) {
				case 1: menuCliente(); break;
				case 2: menuProduto(); break;
				case 3: menuPedido(); break;
				case 4: System.out.println(" ♦ Sistema finalizado! ♦ "); imprimirMenu = false; break;
				default: System.out.println(" ♦ Opção inválida ♦ ");
			}
		
		} while (imprimirMenu);
	}
	
	public static void menuCliente() {
		boolean imprimirMenu = true;
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		do {
			System.out.println(
					"═════════════════════════════════════════════════════════════════════════════\n"+
					"                        ♣ Menu de Cliente ♣\n"+
					"═════════════════════════════════════════════════════════════════════════════\n"+
					" 1) Cadastrar\n"+			
					" 2) Alterar\n"+
					" 3) Excluir\n"+
					" 4) Listar\n"+
					" 5) voltar Menu Principal\n"+
					"═════════════════════════════════════════════════════════════════════════════\n"+
					" ♦ Informe uma opção ♦ \n▸ "
					);
			
			int opcao = Util.validarInteiro(in.nextLine());
		
		
			switch (opcao) {
				case 1: cadastrarCliente(); break;
				case 2: alterarCliente(); break;
				case 3: excluirCliente(); break;
				case 4: listarClientes(); break;
				case 5: imprimirMenu = false; break;
				default: System.out.println(" ♦ Opção inválida ♦ ");
			}
		
		} while (imprimirMenu);
		
		
	}
	
	public static void menuProduto() {
		boolean imprimirMenu = true;
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		do {
			System.out.println(
					"═════════════════════════════════════════════════════════════════════════════\n"+
					"                        ♣ Menu de Produto ♣\n"+
					"═════════════════════════════════════════════════════════════════════════════\n"+
					" 1) Cadastrar\n"+			
					" 2) Alterar\n"+
					" 3) Excluir\n"+
					" 4) Listar\n"+
					" 5) voltar Menu Principal\n"+
					"═════════════════════════════════════════════════════════════════════════════\n"+
					" ♦ Informe uma opção ♦ \n▸ "
					);
			
			int opcao = Util.validarInteiro(in.nextLine());
		
		
			switch (opcao) {
				case 1: cadastrarProduto(); break;
				case 2: alterarProduto(); break;
				case 3: excluirProduto(); break;
				case 4: listarProdutos(); break;
				case 5: imprimirMenu = false; break;
				default: System.out.println(" ♦ Opção inválida ♦ ");
			}
		
		} while (imprimirMenu);
		
		
	}
	
	public static void menuPedido() {
		boolean imprimirMenu = true;
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		do {
			System.out.println(
					"═════════════════════════════════════════════════════════════════════════════\n"+
					"                        ♣ Menu de Pedido ♣\n"+
					"═════════════════════════════════════════════════════════════════════════════\n"+
					" 1) Cadastrar\n"+			
					" 2) Alterar\n"+
					" 3) Excluir\n"+
					" 4) Listar\n"+
					" 5) voltar Menu Principal\n"+
					"═════════════════════════════════════════════════════════════════════════════\n"+
					" ♦ Informe uma opção ♦ \n▸ "
					);
			
			int opcao = Util.validarInteiro(in.nextLine());
		
			
			switch (opcao) {
				case 1: cadastrarPedido(); break;
				//case 2: alterarPedido(); break;
				case 3: excluirPedido(); break;
				case 4: listarPedidos(); break;
				case 5: imprimirMenu = false; break;
				default: System.out.println(" ♦ Opção inválida ♦ ");
			}
		
		} while (imprimirMenu);
		
		
	}
	//--------------------------------metodos-Cliente-------------------------------
	public static void cadastrarCliente() {
		Cliente c = new Cliente();
		ClienteDAO cdao = new ClienteDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		System.out.println("                        ♣ Cadastro de cliente ♣ ");
		System.out.println("═════════════════════════════════════════════════════════════════════════════\n");
		
		Util.br();
		
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		System.out.println(" ♦ Informe o nome ♦ ");
		System.out.print("▸ ");
		String s = in.nextLine();
		c.setNome(s);
	
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		System.out.println(" ♦ Informe o endereco ♦ ");
		System.out.print("▸ ");
		s = in.nextLine();
		c.setEndereco(s);
		
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		c.setDataNascimento(Util.validarData(" ♦ Informe a data de nascimento (dd/MM/yyyy) ♦  \n▸ "));
		
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		System.out.println(" ♦ Informe o CPF ♦ ");
		System.out.print("▸ ");
		s = in.nextLine();
		c.setCpf(s);
		
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		System.out.println(" ♦ Informe o sexo ♦ ");
		System.out.print("▸ ");
		s = in.nextLine();
		c.setSexo(s);
		
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		System.out.println(" ♦ Informe o telefone ♦ ");
		System.out.print("▸ ");
		String ch = in.nextLine();
		c.setTelefone(ch);
		
		clientes.adicionarClienteLista(c);
		cdao.incluirCliente(c);	
	}

	public static void alterarCliente() {
		
		Cliente cl = new Cliente();
		ClienteDAO cdao = new ClienteDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int idInputValido;
		
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		System.out.println("                        ♣ ALterar cliente ♣");
		System.out.println("═════════════════════════════════════════════════════════════════════════════\n");
		
		listarClientes();
		
		do {
			System.out.println(" ♦ Informe o id do cliente que deve ser alterado(0 para cancelar) ♦ ");
			System.out.print("▸ ");
			int id = Util.validarInteiro(sc.nextLine());
			
			if(id == 0||1 == clientes.getListaClientes()
			   .stream()
			   .filter(c -> id == c.getIdCliente())
			   .count()) {
				
				idInputValido = id;
				break;
			}
			
			System.out.println(" ♦ Informe um ID valido!! ♦ ");
		} while(true);
		
		if(idInputValido == 0) return;
		
		cl = clientes.localizarCliente(idInputValido);
		
		cl = menuAlterarCliente(cl);
		
		cdao.alterarCliente(cl);
	}
	
	public static Cliente menuAlterarCliente(Cliente cl) {
        @SuppressWarnings("resource")
        Scanner in = new Scanner(System.in);

        boolean imprimirMenu = true;
		do {
			System.out.println(
					"═════════════════════════════════════════════════════════════════════════════\n"+
					"                        ♣ Menu de alteração de Cliente ♣\n"+
					"═════════════════════════════════════════════════════════════════════════════\n"+
					" 1) Nome\n"+			
					" 2) CPF\n"+
					" 3) Endereço\n"+
					" 4) Data de nascimento\n"+
					" 5) Sexo\n"+
					" 6) Telefone\n"+
					" 7) Alterar tudo\n"+
					" 8) Voltar e salvar\n"+
					"═════════════════════════════════════════════════════════════════════════════\n"+
					" ♦ Informe uma opção ♦\n▸ "
					);
			
			int opcao = Util.validarInteiro(in.nextLine());
		
			switch (opcao) {
			case 1:
				System.out.println("═════════════════════════════════════════════════════════════════════════════");
				System.out.println(" ♦ Informe o novo nome ♦ ");
				System.out.print("▸ ");
				cl.setNome(in.nextLine());								
				 break;
			case 2: 
				System.out.println("═════════════════════════════════════════════════════════════════════════════");
				System.out.println(" ♦ Informe o novo CPF ♦");
				System.out.print("▸ ");
				cl.setCpf(in.nextLine());
				break;
			case 3: 
				System.out.println("═════════════════════════════════════════════════════════════════════════════");
				System.out.println(" ♦ Informe o novo endereço ♦");
				System.out.print("▸ ");
				cl.setEndereco(in.nextLine());
				break;
			case 4:
				System.out.println("═════════════════════════════════════════════════════════════════════════════");
				cl.setDataNascimento(Util.validarData(" ♦ Informe a nova data de nascimento (dd/MM/yyyy) ♦\n▸ "));
				break;			
			case 5:	
				System.out.println("═════════════════════════════════════════════════════════════════════════════");
				System.out.println(" ♦ Informe o novo sexo ♦");
				System.out.print("▸ ");
				cl.setSexo(in.nextLine());
				break;
			case 6:	
				System.out.println("═════════════════════════════════════════════════════════════════════════════");
				System.out.println(" ♦ Informe o novo telefone ♦");
				System.out.print("▸ ");
				cl.setTelefone(in.nextLine());
				break;
			case 7:	
				System.out.println("═════════════════════════════════════════════════════════════════════════════");
				System.out.println("                        ♣ Alteração de dados ♣ ");
				System.out.println("═════════════════════════════════════════════════════════════════════════════");
				System.out.println("═════════════════════════════════════════════════════════════════════════════");
		        System.out.println(" ♦ Informe o novo nome ♦ ");
		        System.out.print("▸ ");
		        String s = in.nextLine();

		        if (!s.isEmpty() && !s.isBlank() && s != null)
		            cl.setNome(s);

		        System.out.println("═════════════════════════════════════════════════════════════════════════════");
		        System.out.println(" ♦ Informe o novo CPF ♦ ");
		        System.out.print("▸ ");
		        s = in.nextLine();

		        if (!s.isEmpty() && !s.isBlank() && s != null)
		        	cl.setCpf(s);

		        System.out.println("═════════════════════════════════════════════════════════════════════════════");
		        System.out.println(" ♦ Sexo ♦ ");
		        System.out.print("▸ ");
		        s = in.nextLine();

		        if (!s.isEmpty() && !s.isBlank() && s != null)
		            cl.setSexo(s);

		        System.out.println("═════════════════════════════════════════════════════════════════════════════");
		        System.out.println(" ♦ Informe o novo sexo ♦ ");
		        System.out.print("▸ ");
		        s = in.nextLine();
		        
		        if (!s.isEmpty() && !s.isBlank() && s != null)
		        	cl.setEndereco(s);
		        
		        System.out.println("═════════════════════════════════════════════════════════════════════════════");
		        System.out.println(" ♦ Informe o novo telefone ♦ ");
		        System.out.print("▸ ");
		        s = in.nextLine();
		        
		        if (!s.isEmpty() && !s.isBlank() && s != null)
		        	cl.setTelefone(s);

		        System.out.println("═════════════════════════════════════════════════════════════════════════════");
		        cl.setDataNascimento(Util.validarData(" ♦ Informe a nova data de nascimento (dd/MM/yyyy) ♦\n▸ "));
		        break;
			case 8: imprimirMenu = false; break;
			default: System.out.println(" ♦ Opção inválida ♦ ");
		}
		
		} while (imprimirMenu);
        
       return cl;
	}

	public static void excluirCliente() {
		Cliente cl = new Cliente();
		ClienteDAO cdao = new ClienteDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int idInputValido;
		
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		System.out.println("                        ♣ Deletar cliente ♣ ");
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		
		listarClientes();	
		
		do {
			System.out.println(" ♦ Informe o id do cliente que deve ser excluido(0 para voltar) ♦");
			int id = Util.validarInteiro(sc.nextLine());
			
			if(id == 0||1 == clientes.getListaClientes()
			   .stream()
			   .filter(c -> id == c.getIdCliente())
			   .count()) {
				
				idInputValido = id;
				break;
			}
			
			System.out.println(" ♦ Informe um ID valido!! ♦ ");
		} while(true);
		
		if(idInputValido == 0) return;
		
		cl.setIdCliente(idInputValido);
		
		cdao.excluirCliente(cl);
		clientes.atualizarListaCliente();
	}

	public static void listarClientes() {
		if (clientes.getListaClientes().size() <= 0) {
			System.out.println("tem cliente não mano");
		}
		for (Cliente c : clientes.getListaClientes()) {
			System.out.println(
					"═════════════════════════════════════════════════════════════════════════════\n"+
					" ♦ idcliente ♦ "+c.getIdCliente()+" ♦"+
					" "+c.getNome()+" ♦"+
					" "+c.getSexo()+" ♦"+
					" "+c.getTelefone()+" ♦");
		}
	}	
	//--------------------------------metodos-Produto-------------------------------
	public static void cadastrarProduto() {
		
		Produto p = new Produto();
		ProdutoDAO pdao = new ProdutoDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		System.out.println("                        ♣ Cadastro de produto ♣ ");
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		
		Util.br();
		
		System.out.println(" ♦ Informe o nome ♦ ");
		System.out.print("▸ ");
		String s = in.nextLine();
		p.setNomeProduto(s);
		
		//listarProduto();
		System.out.println(" ♦ Informe o preço ♦ ");
		System.out.print("▸ ");
		double d = in.nextDouble();
		in.nextLine();
		p.setPreco(d);
		
		System.out.println(" ♦ Informe o descrição do produto ♦ ");
		System.out.print("▸ ");
		s = in.nextLine();
		p.setDesc(s);
		
		System.out.println(" ♦ Informe o estoque ♦ ");
		System.out.print("▸ ");
		int e = in.nextInt();
		in.nextLine();
		p.setEstoque(e);
		
		produtos.adicionarProdutoLista(p);
		pdao.incluirProduto(p);
	}
	
	public static void alterarProduto() {
		Produto pd = new Produto();
		ProdutoDAO pdao = new ProdutoDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int idInputValido;
		
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		System.out.println("                        ♣ ALterar produto ♣ ");
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		
		listarProdutos();
		
		do {
			System.out.println("\n ♦ Informe o id do produto que deve ser alterado(0 para cancelar) ♦ ");
			int id = Util.validarInteiro(sc.nextLine());
			
			if(id == 0||1 == produtos.getProdutos()
			   .stream()
			   .filter(c -> id == c.getIdProduto())
			   .count()) {
				
				idInputValido = id;
				break;
			}
			
			System.out.println(" ♦ Informe um ID valido!! ♦ ");
		} while(true);
		
		if(idInputValido == 0) return;
		
		pd = produtos.localizarProduto(idInputValido);
		
		pd = menuAlterarProduto(pd);
		
		pdao.alterarProduto(pd);
		produtos.atualizarListaProduto();
	}
	
    public static Produto menuAlterarProduto(Produto pd) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);

        boolean imprimirMenu = true;
		do {
			System.out.println(
				"═════════════════════════════════════════════════════════════════════════════\n"+
				"                       ♣ Menu de Alteração de Produto ♣  "+ pd.getNomeProduto()+
				"\n═════════════════════════════════════════════════════════════════════════════\n"+
				" 1) Nome\n"+
				" 2) Preço\n"+
				" 3) Descrição\n"+
				" 4) Estoque\n"+
				" 5) Alterar Tudo\n"+
				" 6) Voltar e salvar\n"+
				"═════════════════════════════════════════════════════════════════════════════\n"+
				" ♦ Informe uma opção ♦ "
				);
			
			int opcao = Util.validarInteiro(in.nextLine());
		
			switch (opcao) {
				case 1: 
					System.out.println(" ♦ Informe o novo nome ♦ ");
					System.out.print("▸ ");
					pd.setNomeProduto(in.nextLine());								
					 break;
				case 2: 
					System.out.println(" ♦ Informe o novo Preço ♦ ");
					System.out.print("▸ ");
					pd.setPreco(in.nextDouble());
					break;
				case 3: 
					System.out.println(" ♦ Informe o novo Descrição ♦ ");
					System.out.print("▸ ");
					pd.setDesc(in.nextLine());
					break;
				case 4:
					System.out.println(" ♦ Informe o novo estoque ♦ ");
					System.out.print("▸ ");
					pd.setEstoque(in.nextInt());
					break;								
				case 5:	
					System.out.println("═════════════════════════════════════════════════════════════════════════════");
				 	System.out.println("                      ♣ Alteração de dados do produto ♣ ");
				 	System.out.println("═════════════════════════════════════════════════════════════════════════════");
			        System.out.println(" ♦ Nome ♦ ");
			        System.out.print("▸ ");
			        String s = in.nextLine();

			        if (!s.isEmpty() && !s.isBlank() && s != null)
			            pd.setNomeProduto(s);

			        System.out.println(" ♦ Preço ♦ ");
			        System.out.print("▸ ");
			        double pr;
			        pr = in.nextDouble();
			        in.nextLine();

			        if (!s.isEmpty() && !s.isBlank() && s != null)
			        	pd.setPreco(pr);

			        System.out.println(" ♦ Descrição ♦ ");
			        System.out.print("▸ ");
			        s = in.nextLine();

			        if (!s.isEmpty() && !s.isBlank() && s != null)
			            pd.setDesc(s);

			        System.out.println(" ♦ Estoque ♦ ");
			        System.out.print("▸ ");
			        int est;
			        est = in.nextInt();
			        in.nextLine();
			        
			        if (!s.isEmpty() && !s.isBlank() && s != null)
			        	pd.setEstoque(est);
				       
				case 6: imprimirMenu = false; break;
				default: System.out.println(" ♦ Opção inválida ♦ ");
			}
		
		} while (imprimirMenu);
        
       return pd;
	}

	public static void excluirProduto() {
		Produto pd = new Produto();
		ProdutoDAO pdao = new ProdutoDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int idInputValido;
		
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		System.out.println("                       ♣ Deletar produto ♣ ");
		System.out.println("═════════════════════════════════════════════════════════════════════════════");
		
		listarProdutos();	
		
		do {
			System.out.println("\n ♦ Informe o id do produto que deve ser excluido(0 para voltar) ♦ ");
			int id = Util.validarInteiro(sc.nextLine());
			
			if(id == 0||1 == produtos.getProdutos()
			   .stream()
			   .filter(p -> id == p.getIdProduto())
			   .count()) {
				
				idInputValido = id;
				break;
			}
			
			System.out.println(" ♦ Informe um ID valido!! ♦ ");
		} while(true);
		
		if(idInputValido == 0) return;
		
		pd.setIdProduto(idInputValido);
		
		pdao.excluirProduto(pd);
		produtos.atualizarListaProduto();
    }
	
	public static void listarProdutos() {
		System.out.println("═════════════════════════════════════════════════════════════════════════════\n"+
						   "                       ♣ Todos os produtos ♣ \n"+
				           "═════════════════════════════════════════════════════════════════════════════");
		
		
		for (Produto prod : produtos.getProdutos()) {	
				System.out.println(	
					"\n ♦ idProduto ♦ "+prod.getIdProduto()+" ♦"+
					" "+prod.getNomeProduto()+" ♦"+
					" R$"+prod.getPreco()+" ♦"+
					" "+prod.getEstoque()+" ♦"+
					" "+prod.getDesc()+" ♦");
		}		
	}
	//--------------------------------metodos-Pedido--------------------------------
	public static void cadastrarPedido() {
		PedidoDAO pedao = new PedidoDAO(con, SCHEMA);
		PedidoProdutoDAO peProdao = new PedidoProdutoDAO(con, SCHEMA);
		
		Pedido ped = new Pedido();
		Cliente cl;
		ProdutoVendido pd;
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int idInputValido;
		
		System.out.println(Util.LINHA);
		System.out.println("Cadastro de pedido ");
		System.out.println(Util.LINHA);
		
		Util.br();
		
		//Informar clientes disponiveis
		listarClientes();
		
		//verificar se existe um cliente com o id informado
		do {
			System.out.println("\nInforme o id do cliente(0 para cancelar): ");
			int id = Util.validarInteiro(in.nextLine());
			
			if(id == 0||1 == clientes.getListaClientes()
			   .stream()
			   .filter(c -> id == c.getIdCliente())
			   .count()) {
				
				idInputValido = id;
				break;
			}
			
			System.out.println("Informe um ID valido!!");
		} while(true);
		
		if(idInputValido == 0) return;
		
		cl = clientes.localizarCliente(idInputValido);
		ped.setCliente(cl);
		ped.setData(LocalDate.now());
		
		do {
			Util.br();
			listarProdutos();
	
			do {
				System.out.println("\nInforme o id do produto(0 para voltar|-1 para confirmar pedido): ");
				int id = Util.validarInteiro(in.nextLine());
				
				if(id == -1 || id == 0||1 == produtos.getProdutos()
				   .stream()
				   .filter(prod -> id == prod.getIdProduto())
				   .count()) {
					
					idInputValido = id;
					break;
				}
				
				System.out.println("Informe um ID valido!!");
			} while(true);
			
			if (idInputValido == 0) {
				cadastrarPedido(); 
				break;
			}
			if (idInputValido == -1 && ped.getProdutos().size() > 0) break;
			
			if (idInputValido > 0) {
				Produto produto = produtos.localizarProduto(idInputValido);
				
				if (produto.getEstoque() <= 0) { 
					System.out.println("Selecione produtos com estoque");
				} else {
					
					System.out.println("Este Produto possui Estoque Disponivel de ("+produto.getEstoque()+")"
							+ "\nInforme a quantidade a ser vendida:");
					int qtdVendida = in.nextInt();
					in.nextLine();
					
					if (qtdVendida <= produto.getEstoque()) {
						
						pd = new ProdutoVendido (produto, qtdVendida);
						pd.setIdProduto(idInputValido);
						ped.adicionarProdutoLista(pd);
					} else {
						System.out.println("\nPedido invalido!! estoque insuficiente para esta venda");
					}
				}
			}
		} while(true);
		
		
		ped.calcularQtdItens();
		ped.calcularTotal();
		
		//inclusão no banco não precisa de id
		pedao.incluirPedido(ped);
	
		//id do pedido atual é igual a o tamanho da lista de pedidos +1.
		ped.setIdPedido(pedao.buscarIdPedidoMaisRecente());
		produtos.decrementarEstoqueProdutos(ped.getProdutos());
		pedidos.adicionarPedidoLista(ped);
		
		peProdao.incluirPedidoProduto(ped);
		
	}

	public static void alterarPedido() {
		
	}
	
	public static void excluirPedido() {
		PedidoDAO pedao = new PedidoDAO(con, SCHEMA);
		
		Pedido ped = new Pedido();
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int idInputValido;
		
		System.out.println(Util.LINHA);
		System.out.println("Deletar pedido");
		System.out.println(Util.LINHA);
		
		listarPedidos();	
		
		do {
			System.out.println("\nInforme o id do pedido que deve ser excluido(0 para voltar): ");
			int id = Util.validarInteiro(sc.nextLine());
			
			if(id == 0||1 == pedidos.getListaPedido()
			   .stream()
			   .filter(p -> id == p.getIdPedido())
			   .count()) {
				
				idInputValido = id;
				break;
			}
			
			System.out.println("Informe um ID valido!!");
		} while(true);
		
		if(idInputValido == 0) return;
		
		ped.setIdPedido(idInputValido);
		
		pedao.excluirPedido(ped);
		pedidos.atualizarListaPedido();
	}
	
	public static void listarPedidos() {
		for (Pedido p : pedidos.getListaPedido()) {
			System.out.println("idPedido: "+p.getIdPedido()+"|idCliente"+p.getCliente().getIdCliente()+
		"nome cliente: "+p.getCliente().getNome()+"|qtdItens: "+p.getQtdItens()+"|total: "+p.getTotal()+
		"data: "+p.getData());
		}	
	}
	

}
	
	
	
	
	
