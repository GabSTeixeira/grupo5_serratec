package principal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					"                                           ♣ Menu Principal ♣       GroupFive LTDA\n"+
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					" 1) Menu Cliente\n"+			
					" 2) Menu Produto\n"+
					" 3) Menu Pedido\n"+					
					" 4) Sair\n"+
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					" ♦ Informe uma opção ♦"
					);		
			System.out.print("▸ ");
			
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
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					"                                           ♣ Menu de Cliente ♣\n"+
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					" 1) Cadastrar\n"+			
					" 2) Alterar\n"+
					" 3) Excluir\n"+
					" 4) Listar\n"+
					" 5) Localizar Cliente\n"+
					" 6) voltar Menu Principal\n"+
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					" ♦ Informe uma opção ♦"
					);
			System.out.print("▸ ");
			
			int opcao = Util.validarInteiro(in.nextLine());
		
		
			switch (opcao) {
				case 1: cadastrarCliente(); break;
				case 2: alterarCliente(); break;
				case 3: excluirCliente(); break;
				case 4: listarClientes(); break;
				case 5: localizarCliente(); break;
				case 6: imprimirMenu = false; break;
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
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					"                                           ♣ Menu de Produto ♣\n"+
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					" 1) Cadastrar\n"+			
					" 2) Alterar\n"+
					" 3) Excluir\n"+
					" 4) Listar\n"+
					" 5) Localizar produto\n"+
					" 6) voltar Menu Principal\n"+
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					" ♦ Informe uma opção ♦"
					);
			System.out.print("▸ ");
			
			int opcao = Util.validarInteiro(in.nextLine());
		
		
			switch (opcao) {
				case 1: cadastrarProduto(); break;
				case 2: alterarProduto(); break;
				case 3: excluirProduto(); break;
				case 4: listarProdutos(); break;
				case 5: localizarProduto(); break;
				case 6: imprimirMenu = false; break;
				default: System.out.println(" ♦ Opção inválida ♦ ");
			}
		} while (imprimirMenu);		
	}
	
	public static void menuPedido() {
		boolean imprimirMenu = true;
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		if(clientes.getListaClientes().isEmpty() || produtos.getProdutos().isEmpty()) {
			System.out.println(" ♦ Não existe nenhum cliente ou produto cadastrado!! ♦ ");
			
		}else {
			do {
				System.out.println(
						"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
						"                                           ♣ Menu de Pedido ♣\n"+
						"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
						" 1) Cadastrar\n"+			
						" 2) Alterar\n"+
						" 3) Excluir\n"+
						" 4) Listar\n"+
						" 5) Localizar pedido\n"+
						" 6) voltar Menu Principal\n"+
						"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
						" ♦ Informe uma opção ♦"
						);
				System.out.print("▸ ");
				
				int opcao = Util.validarInteiro(in.nextLine());
			
				
				switch (opcao) {
					case 1: cadastrarPedido(); break;
					case 2: alterarPedido(); break;
					case 3: excluirPedido(); break;
					case 4: listarPedidos(); break;
					case 5: localizarPedido(); break;
					case 6: imprimirMenu = false; break;
					default: System.out.println(" ♦ Opção inválida ♦ ");
				}
			
			} while (imprimirMenu);
		}			
		
	}
	//--------------------------------metodos-Cliente-------------------------------
	public static void cadastrarCliente() {
		Cliente c = new Cliente();
		ClienteDAO cdao = new ClienteDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		System.out.println("                                           ♣ Cadastro de cliente ♣ ");
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		
		Util.br();
		
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		System.out.println(" ♦ Informe o primeiro nome ♦ ");
		System.out.print("▸ ");
		String s = in.nextLine();
		c.setNome(s);
	
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		System.out.println(" ♦ Informe o endereco ♦ ");
		System.out.print("▸ ");
		s = in.nextLine();
		c.setEndereco(s);
		
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		c.setDataNascimento(Util.validarDataNascimento(" ♦ Informe a data de nascimento (dd/MM/yyyy) ♦\n▸ "));
		
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		System.out.println(" ♦ Informe o CPF ♦ ");
		System.out.print("▸ ");
		s = in.nextLine();
		c.setCpf(s);
		
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		System.out.println(" ♦ Informe o sexo com ( M - F - I ) ♦ ");
		System.out.print("▸ ");
		s = in.nextLine();
		c.setSexo(s);
		
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		System.out.println(" ♦ Informe o telefone ♦ ");
		System.out.print("▸ ");
		String ch = in.nextLine();
		c.setTelefone(ch);
		// teste
		
		cdao.incluirCliente(c);
		
		c.setIdCliente(cdao.buscarIdClienteMaisRecente());
		
		clientes.adicionarClienteLista(c);
	}

	public static void alterarCliente() {
		
		if (clientes.getListaClientes().size() <= 0) {
			System.out.println(" ♦ Não há clientes cadastrados ♦ ");
			return;
			
		}
		
		Cliente cl = new Cliente();
		ClienteDAO cdao = new ClienteDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int idInputValido;
		
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
	
	private static Cliente menuAlterarCliente(Cliente cl) {
        @SuppressWarnings("resource")
        Scanner in = new Scanner(System.in);

        boolean imprimirMenu = true;
		do {
			System.out.println(
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					"                                           ♣ Menu de alteração de Cliente ♣\n"+
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					" 1) Nome\n"+			
					" 2) CPF\n"+
					" 3) Endereço\n"+
					" 4) Data de nascimento\n"+
					" 5) Sexo\n"+
					" 6) Telefone\n"+
					" 7) Alterar tudo\n"+
					" 8) Voltar e salvar\n"+
					"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
					" ♦ Informe uma opção ♦"
					);
			System.out.print("▸ ");
			
			int opcao = Util.validarInteiro(in.nextLine());
		
			switch (opcao) {
			case 1:
				System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
				System.out.println(" ♦ Informe o novo nome ♦ ");
				System.out.print("▸ ");
				cl.setNome(in.nextLine());								
				 break;
			case 2: 
				System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
				System.out.println(" ♦ Informe o novo CPF ♦");
				System.out.print("▸ ");
				cl.setCpf(in.nextLine());
				break;
			case 3: 
				System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
				System.out.println(" ♦ Informe o novo endereço ♦");
				System.out.print("▸ ");
				cl.setEndereco(in.nextLine());
				break;
			case 4:
				System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
				cl.setDataNascimento(Util.validarData(" ♦ Informe a nova data de nascimento (dd/MM/yyyy) ♦\n▸ "));
				break;			
			case 5:	
				System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
				System.out.println(" ♦ Informe o novo sexo ♦");
				System.out.print("▸ ");
				cl.setSexo(in.nextLine());
				break;
			case 6:	
				System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
				System.out.println(" ♦ Informe o novo telefone ♦");
				System.out.print("▸ ");
				cl.setTelefone(in.nextLine());
				break;
			case 7:	
				System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
				System.out.println("                                           ♣ Alteração de dados ♣ ");
				System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
				System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		        System.out.println(" ♦ Informe o novo nome ♦ ");
		        System.out.print("▸ ");
		        String s = in.nextLine();

		        if (!s.isEmpty() && !s.isBlank() && s != null)
		            cl.setNome(s);

		        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		        System.out.println(" ♦ Informe o novo CPF ♦ ");
		        System.out.print("▸ ");
		        s = in.nextLine();

		        if (!s.isEmpty() && !s.isBlank() && s != null)
		        	cl.setCpf(s);

		        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		        System.out.println(" ♦ Informe o novo Sexo ♦ ");
		        System.out.print("▸ ");
		        s = in.nextLine();

		        if (!s.isEmpty() && !s.isBlank() && s != null)
		            cl.setSexo(s);

		        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		        System.out.println(" ♦ Informe o novo endereço ♦ ");
		        System.out.print("▸ ");
		        s = in.nextLine();
		        
		        if (!s.isEmpty() && !s.isBlank() && s != null)
		        	cl.setEndereco(s);
		        
		        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		        System.out.println(" ♦ Informe o novo telefone ♦ ");
		        System.out.print("▸ ");
		        s = in.nextLine();
		        
		        if (!s.isEmpty() && !s.isBlank() && s != null)
		        	cl.setTelefone(s);

		        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		        cl.setDataNascimento(Util.validarData(" ♦ Informe a nova data de nascimento (dd/MM/yyyy) ♦\n▸ "));
		        break;
			case 8: imprimirMenu = false; break;
			default: System.out.println(" ♦ Opção inválida ♦ ");
		}
		
		} while (imprimirMenu);
        
       return cl;
	}

	public static void excluirCliente() {
		
		if (clientes.getListaClientes().size() <= 0) {
			System.out.println(" ♦ Não há clientes cadastrados ♦ ");
			return;
			
		}
		
		Cliente cl = new Cliente();
		ClienteDAO cdao = new ClienteDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int idInputValido;
		boolean encontrado = false;
		
		listarClientes();	
		
		do {
			encontrado = false;
			System.out.println(" ♦ Informe o id do cliente que deve ser excluido(0 para voltar) ♦");
			System.out.print("▸ ");
			int id = Util.validarInteiro(sc.nextLine());
			
			if(id == 0||1 == clientes.getListaClientes()
			   .stream()
			   .filter(c -> id == c.getIdCliente())
			   .count()) {
				
				idInputValido = id;
				
				for(Pedido p:pedidos.getListaPedido()) {
				
					if(p.getCliente().getIdCliente() == idInputValido) {
						encontrado = true;
						break;
					}
				}				
				if(encontrado) {
					System.out.println(" ♦ Não é possivel excluir pois já está cadastrado em um pedido! ♦ ");
				}else{
					break;
				}
			}
			if(!encontrado) {
				System.out.println(" ♦ Informe um ID valido!! ♦ ");
			}
			
		} while(true);
		
		if(idInputValido == 0) return;
		
		cl.setIdCliente(idInputValido);
		
		cdao.excluirCliente(cl);
		
		
		clientes.atualizarListaCliente();
	}

	public static void listarClientes() {
		if (clientes.getListaClientes().size() <= 0) {
			System.out.println(" ♦ Não há clientes cadastrados ♦ ");
			return;
			
		}
		
			System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
			System.out.printf("                                           ♣ Lista de Clientes ♣ %n");
			System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
			System.out.printf("═╦═════════════════╦══════════════════════╦═════════════════╦═════════════════╦═%n");
			System.out.printf(" ║ %-15s ║ %-20s ║ %-15s ║ %-15s ║%n","IdCliente" ,"Primeiro Nome","Sexo","Telefone");
			
			for (Cliente c : clientes.getListaClientes()) {
				System.out.printf(" ║ %15d ║ %20s ║ %15s ║ %15s ║%n",c.getIdCliente(),c.getNome(),c.getSexo(),c.getTelefone());
			}
			System.out.printf("═╩═════════════════╩══════════════════════╩═════════════════╩═════════════════╩═%n");	
		
	}
	
	public static void localizarCliente() {
		
		if (clientes.getListaClientes().size() <= 0) {
			System.out.println(" ♦ Não há clientes cadastrados! ♦ ");
			
			return;
		}
		
		ArrayList <Cliente> localizado;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		boolean pesquisaPorNome = false;
		int idInputValido = -1;
		String s;
		
		do {
			System.out.println(" ♦ Informe o id ou nome para localizar o cliente (0 para voltar) ♦ ");
			System.out.print("▸ ");
			s = sc.nextLine();
			
			
			if (!Util.isInteger(s)) {
				pesquisaPorNome = true;
				break;
			}
			
			int id = Util.validarInteiro(s);
				
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
		
		if(!pesquisaPorNome) {	
			localizado = new ArrayList<>();
			Cliente c;
			c = clientes.localizarCliente(idInputValido);	
			localizado.add(c);	
			
		}else if(s.length() > 1){			
			localizado = clientes.localizarCliente(s);
			
			if (localizado.size() <= 0) {
				System.out.println("♦ Nenhum cliente com esse nome encontrado ♦");
				return;
			}
		}else {	
			System.out.println(" ♦ Erro ♦ ");
			return;
		}	
		
		pedidos.atualizarListaPedido();
		
		for(Cliente cl : localizado) {
			System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
			System.out.printf("                                           ♣ Lista de clientes ♣ %n");
			System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
			System.out.printf("═╦═════════════════╦══════════════════════╦═%n");
			System.out.printf(" ║ %-15s ║ %-20s ║%n","IdCliente" ,"Nome do Cliente");	
			System.out.printf(" ║ %15d ║ %20s ║%n",cl.getIdCliente(),cl.getNome());						
			System.out.printf("═╩═════════════════╩══════════════════════╩═%n");
		
			for(Pedido pd : pedidos.getListaPedido()) {
				if(cl.getIdCliente() == pd.getCliente().getIdCliente()) {									
					System.out.printf("═╦═════════════════╦══════════════════════╦═%n");
					System.out.printf(" ║ %-15s ║ %-20s ║%n","IdPedido" ,"Data do Pedido");	
					System.out.printf(" ║ %15d ║ %20s ║%n",pd.getIdPedido(),pd.getData());						
					System.out.printf("═╩═════════════════╩══════════════════════╩═%n");
				}
			}	
		}				
	}
	//--------------------------------metodos-Produto-------------------------------
	public static void cadastrarProduto() {
		
		Produto p = new Produto();
		ProdutoDAO pdao = new ProdutoDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		System.out.println("                                           ♣ Cadastro de produto ♣ ");
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		
		Util.br();
		
		System.out.println(" ♦ Informe o nome ♦ ");
		System.out.print("▸ ");
		String s = in.nextLine();
		p.setNomeProduto(s);
		
		//listarProduto();
		System.out.println(" ♦ Informe o preço ♦ ");
		System.out.print("▸ ");
		String d = in.nextLine();
		p.setPreco(d);
		
		System.out.println(" ♦ Informe a descrição do produto ♦ ");
		System.out.print("▸ ");
		s = in.nextLine();
		p.setDesc(s);
		
		System.out.println(" ♦ Informe o estoque ♦ ");
		System.out.print("▸ ");
		String e  = in.nextLine();
		p.setEstoque(e);
		
		pdao.incluirProduto(p);
		p.setIdProduto(pdao.buscarIdProdutoMaisRecente());
		
		produtos.adicionarProdutoLista(p);
	}
	
	public static void alterarProduto() {
		Produto pd = new Produto();
		ProdutoDAO pdao = new ProdutoDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int idInputValido;		

		listarProdutos();
		
		do {
			System.out.println("\n ♦ Informe o id do produto que deve ser alterado(0 para cancelar) ♦ ");
			System.out.print("▸ ");
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
	
    private static Produto menuAlterarProduto(Produto pd) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);

        boolean imprimirMenu = true;
		do {
			System.out.println(
				"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
				"                            ♣ Menu de Alteração de Produto ♣  "+ pd.getNomeProduto()+
				"\n════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
				" 1) Nome\n"+
				" 2) Preço\n"+
				" 3) Descrição\n"+
				" 4) Estoque\n"+
				" 5) Alterar Tudo\n"+
				" 6) Voltar e salvar\n"+
				"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
				" ♦ Informe uma opção ♦"
				);
			System.out.print("▸ ");
			
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
					pd.setPreco(in.nextLine());
					break;
				case 3: 
					System.out.println(" ♦ Informe o novo Descrição ♦ ");
					System.out.print("▸ ");
					pd.setDesc(in.nextLine());
					break;
				case 4:
					System.out.println(" ♦ Informe o novo estoque ♦ ");
					System.out.print("▸ ");
					pd.setEstoque(in.nextLine());
					break;								
				case 5:	
					System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
				 	System.out.println("                                           ♣ Alteração de dados do produto ♣ ");
				 	System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
			        System.out.println(" ♦ Nome ♦ ");
			        System.out.print("▸ ");
			        String s = in.nextLine();

			        if (!s.isEmpty() && !s.isBlank() && s != null)
			            pd.setNomeProduto(s);

			        System.out.println(" ♦ Preço ♦ ");
			        System.out.print("▸ ");
			        String pr;
			        pr = in.nextLine();

			        if (!s.isEmpty() && !s.isBlank() && s != null)
			        	pd.setPreco(pr);

			        System.out.println(" ♦ Descrição ♦ ");
			        System.out.print("▸ ");
			        s = in.nextLine();

			        if (!s.isEmpty() && !s.isBlank() && s != null)
			            pd.setDesc(s);

			        System.out.println(" ♦ Estoque ♦ ");
			        System.out.print("▸ ");
			        String est;
			        est = in.nextLine();
			        
			        if (!s.isEmpty() && !s.isBlank() && s != null)
			        	pd.setEstoque(est);
				    
			        break;
				case 6: imprimirMenu = false; break;
				default: System.out.println(" ♦ Opção inválida ♦ ");
			}
		
		} while (imprimirMenu);
        
       return pd;
	}

	public static void excluirProduto() {
		if(produtos.getProdutos().size() <= 0) {
			System.out.println(" ♦ Não há produtos cadastrados! ♦ ");
			
			return;
		}
		
		Produto pd = new Produto();
		ProdutoDAO pdao = new ProdutoDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int idInputValido;
		
		listarProdutos();	
		
		do {
			System.out.println("\n ♦ Informe o id do produto que deve ser excluido(0 para voltar) ♦ ");
			System.out.print("▸ ");
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
		if(produtos.getProdutos().size() <= 0) {
			System.out.println(" ♦ Não há produtos cadastrados! ♦ ");
			
			return;
		}
		
		System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
		System.out.printf("                                           ♣ Lista de produtos ♣ %n");
		System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
		System.out.printf("═╦═════════════════╦══════════════════════╦═════════════════╦═════════════════╦═%n");
		System.out.printf(" ║ %-15s ║ %-20s ║ %-15s ║ %-15s ║%n","IdProduto" ,"Nome do Produto","Valor: R$","QtdEstoque: UN","Descrição");
		
		for (Produto prod : produtos.getProdutos()) {	
				System.out.printf(" ║ %15d ║ %20s ║ %15s ║ %15s ║%n",prod.getIdProduto(),prod.getNomeProduto(),prod.getPreco(),
					prod.getEstoque(),prod.getDesc());
				
		}		
		System.out.printf("═╩═════════════════╩══════════════════════╩═════════════════╩═════════════════╩═%n");
	}	
	
	public static void localizarProduto() {
		if(produtos.getProdutos().size() <= 0) {
			System.out.println(" ♦ Não há produtos cadastrados! ♦ ");
			
			return;
		}
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		Produto pr;

		
		System.out.println(" ♦ Informe o id ou nome para localizar o produto! ♦ ");
		System.out.print("▸ ");
		String s = in.nextLine();
		
		if(Util.isInteger(s)) {	
			int id = Integer.parseInt(s);
			pr = produtos.localizarProduto(id);			
			
		}else if(s.length() > 1){			
			pr = produtos.localizarProduto(s);
		}else {	
			System.out.println(" ♦ Erro ♦ ");
			return;
		}
		
		pedidos.atualizarListaPedido();
		
		System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
		System.out.printf("                                           ♣ Lista de produtos ♣ %n");
		System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
		System.out.printf("═╦═════════════════╦══════════════════════╦═%n");
		System.out.printf(" ║ %-15s ║ %-20s ║%n","IdProduto" ,"Nome do Produto");	
		System.out.printf(" ║ %15d ║ %20s ║%n",pr.getIdProduto(),pr.getNomeProduto());						
		System.out.printf("═╩═════════════════╩══════════════════════╩═%n");
				
		for(Pedido pd : pedidos.getListaPedido()) {
			for(Produto pro : pd.getProdutos()) {
				if(pd.getProdutos()
				   .stream()
				   .anyMatch(prod -> pr.getIdProduto() == pro.getIdProduto())) 
				{
				
					System.out.printf("═╦═════════════════╦══════════════════════╦═%n");
					System.out.printf(" ║ %-15s ║ %-20s ║%n","IdPedido" ,"Data do Pedido");	
					System.out.printf(" ║ %15d ║ %20s ║%n",pd.getIdPedido(),pd.getData());						
					System.out.printf("═╩═════════════════╩══════════════════════╩═%n");
				}
			}	
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
		
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		System.out.println("                                           ♣ Cadastro de pedido ♣ ");
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		
		Util.br();
		
		//Informar clientes disponiveis
		listarClientes();
		
		
		//verificar se existe um cliente com o id informado
		do {
			System.out.println("\n ♦ Informe o id do cliente(0 para cancelar) ♦ ");
			System.out.print("▸ ");
			int id = Util.validarInteiro(in.nextLine());
			
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
		ped.setCliente(cl);
		ped.setData(LocalDate.now());
		
		do {
			Util.br();
			listarProdutos();
	
			do {
				System.out.println("\n ♦ Informe o id do produto(0 para voltar|-1 para confirmar pedido) ♦ ");
				System.out.print("▸ ");
				int id = Util.validarInteiro(in.nextLine());
				
				if(id == -1 || id == 0||1 == produtos.getProdutos()
				   .stream()
				   .filter(prod -> id == prod.getIdProduto())
				   .count()) {
					
					idInputValido = id;
					break;
				}
				
				System.out.println(" ♦ Informe um ID valido!! ♦ ");
			} while(true);
			
			if (idInputValido == 0) {
				cadastrarPedido(); 
				break;
			}
			if (idInputValido == -1 && ped.getProdutos().size() > 0) break;
			
			if (idInputValido > 0) {
				Produto produto = produtos.localizarProduto(idInputValido);
								

				if (produto.getEstoque() <= 0) { 
					System.out.println(" ♦ Selecione produtos com estoque ♦ ");
					System.out.print("▸ ");
				} else {
				
					System.out.println(" ♦ Este Produto possui Estoque Disponivel de ("+produto.getEstoque()+") ♦ "
							+ "\n ♦ Informe a quantidade a ser vendida ♦ ");	
					System.out.print("▸ ");
					String st = in.nextLine();
					int qtdVendida = Util.validarInteiro(st);

					if (qtdVendida <= produto.getEstoque()) {
						
						pd = new ProdutoVendido (produto, qtdVendida);
						pd.setIdProduto(idInputValido);
						
						boolean produtoJaCadastrado = false;
						int produtoLocalizado = -1;
						
						for (ProdutoVendido pv: ped.getProdutos()) {
							if(pv.getIdProduto()==idInputValido) {
								produtoJaCadastrado = true;
								produtoLocalizado = ped.getProdutos().indexOf(pv);
							}
						}
						
						if (produtoJaCadastrado) {
							ped.getProdutos().get(produtoLocalizado).setEstoque(qtdVendida);
						} else {
							ped.adicionarProdutoLista(pd);							
						}
					} else {
						System.out.println("\n ♦ Pedido invalido!! estoque insuficiente para esta venda ♦ ");
					}
				}
			}
		} while(true);
		
		
		ped.calcularQtdItens();
		ped.calcularTotal();
		
		//inclusão no banco não precisa de id
		pedao.incluirPedido(ped);
	
		ped.setIdPedido(pedao.buscarIdPedidoMaisRecente());
		produtos.decrementarEstoqueProdutos(ped.getProdutos());
		pedidos.adicionarPedidoLista(ped);
		
		peProdao.incluirPedidoProduto(ped);
		
	}

	public static void alterarPedido() {
		
		if (pedidos.getListaPedido().isEmpty()) {
			System.out.println(" ♦ Não existe nenhum pedido cadastrado!! ♦ ");
		} else {
			PedidoDAO pedao = new PedidoDAO(con, SCHEMA);
			
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			int idInputValido;			
			
			listarPedidos();
			
			do {
				System.out.println("\n ♦ Informe o id do pedido que deve ser alterado(0 para cancelar) ♦ ");
				int id = Util.validarInteiro(sc.nextLine());
				
				if(id == 0||1 == pedidos.getListaPedido()
				   .stream()
				   .filter(pd -> id == pd.getIdPedido())
				   .count()) {
					
					idInputValido = id;
					break;
				}
				
				System.out.println(" ♦ Informe um ID valido!! ♦ ");
			} while(true);
			
			if(idInputValido == 0) return;
			
			
			Pedido p = pedidos.localizarPedido(idInputValido);
			
			p = menuAlterarPedido(p);
			
			pedao.alterarPedido(p);
			
			pedidos.atualizarListaPedido();
			produtos.atualizarListaProduto();
		}
	}
	
	private static Pedido menuAlterarPedido (Pedido ped) {
		PedidoProdutoDAO peprodao = new PedidoProdutoDAO(con, SCHEMA);
		ProdutoDAO pdao = new ProdutoDAO(con, SCHEMA);
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);

        boolean imprimirMenu = true;
		do {
			System.out.println(
				"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
				"                                     ♣ Menu de Alteração de Pedido ♣ id:"+ ped.getIdPedido()+
				"\n════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
				" 1) Cliente\n"+
				" 2) Incluir Produto\n"+
				" 3) Alterar Produto\n"+
				" 4) Data\n"+
				" 5) Voltar e salvar\n"+
				"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
				" ♦ Informe uma opção ♦"
				);
			System.out.print("▸ ");
			
			int opcao = Util.validarInteiro(in.nextLine());
		
			switch (opcao) {
				case 1: 
					listarClientes();
					int idInputValido;
					do {
						System.out.println(" ♦ Informe o id do novo cliente(0 para cancelar) ♦ ");
						System.out.print("▸ ");
						int id = Util.validarInteiro(in.nextLine());
						
						if(id == 0||1 == clientes.getListaClientes()
						   .stream()
						   .filter(c -> id == c.getIdCliente())
						   .count()) {
							
							idInputValido = id;
							break;
						}
						
						System.out.println(" ♦ Informe um ID valido!! ♦ ");
					} while(true);
					
					if(idInputValido == 0) break;
					
					ped.setCliente(clientes.localizarCliente(idInputValido));
								
					break;
				case 2:
					
					listarProdutos();
					int idProdutoValido;
					boolean produtoJaCadastrado = false;
					do {
						produtoJaCadastrado = false;
						System.out.println(" ♦ Informe o id do novo produto a ser incluido(0 para cancelar) ♦ ");
						System.out.print("▸ ");
						int id = Util.validarInteiro(in.nextLine());
						
						if(id == 0||1 == produtos.getProdutos()
						   .stream()
						   .filter(prod -> id == prod.getIdProduto())
						   .count()) {
							
							
							for (ProdutoVendido pv: ped.getProdutos()) {
								if (id == pv.getIdProduto()) {
									produtoJaCadastrado = true;
									break;
								}
							}
							
							if (!produtoJaCadastrado) {
								idProdutoValido = id;
								break;								
							} else {
								System.out.println(" ♦ Este produto já está cadastrado neste pedido, vá para alterar produtos ♦");
							}
							
						} else {
							System.out.println(" ♦ Informe um ID valido!! ♦ ");
						}
					} while(true);
					
					if(idProdutoValido == 0) break;
					
					Produto prod = produtos.localizarProduto(idProdutoValido);
					
					
					ResultSet tabela = pdao.buscarEstoqueIdProduto(idProdutoValido);
					int estoqueAtual;
					try {
						tabela.next();
					
						estoqueAtual = tabela.getInt("estoque");
					} catch (SQLException e) {
						e.printStackTrace();
						estoqueAtual = -1;
					}
					
					int novaQtdVendida;
					do {
						System.out.println(" ♦ Informe quantidade deste item a ser vendida ♦ ");
						novaQtdVendida = Util.validarInteiro(in.nextLine());
						
						if(novaQtdVendida <= 0) {
							System.out.println(" ♦ A nova quantidade vendida deve ser maior que zero ♦ ");
						}
						
						if(novaQtdVendida > estoqueAtual) {
							System.out.println(" ♦ Insira um valor menor que o estoque atual desse produto ("+estoqueAtual+") ♦ ");
						}
					} while(novaQtdVendida <= 0 || novaQtdVendida > estoqueAtual);
					
					
					ProdutoVendido p = new ProdutoVendido (prod, novaQtdVendida);
					
					peprodao.incluirProdutoUnico(p , ped.getIdPedido());
					
					ped.adicionarProdutoLista(p);
					
					ped.calcularQtdItens();
					ped.calcularTotal();
					
					p.setEstoque(estoqueAtual - novaQtdVendida);
					
					pdao.alterarProdutoEstoque(p);
					break;
				case 3: 
					for (ProdutoVendido pv : ped.getProdutos()) {

						System.out.println(	
							"\n ♦ idProduto ♦ "+pv.getIdProduto()+" ♦"+
							" nome: "+pv.getNomeProduto()+" ♦"+
							" qtdVendida: "+pv.getQtdVendida()+" ♦");
				
					}
					
					do {
						System.out.println("\n ♦ Informe o id do produto que deve ser alterado(0 para cancelar) ♦ ");
						System.out.print("▸ ");
						int id = Util.validarInteiro(in.nextLine());
						
						if(id == 0||1 == ped.getProdutos()
						   .stream()
						   .filter(prodList -> id == prodList.getIdProduto())
						   .count()) {
									
							idInputValido = id;
							break;
						}
						
						System.out.println(" ♦ Informe um ID valido!! ♦ ");
					} while(true);
					
					if(idInputValido == 0) break;
					
					ProdutoVendido produtoVendido = ped.localizarProduto(idInputValido);
					
					ped = menuAlterarProdutosPedido(ped, produtoVendido);
					
					ped.calcularQtdItens();
					ped.calcularTotal();
					
					peprodao.alterarPedidoProduto(ped, produtoVendido);
					
					pedidos.atualizarListaPedido();
					produtos.atualizarListaProduto();
					
					break;
				case 4:
					System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
					ped.setData(Util.validarData(" ♦ Informe a nova data deste pedido(dd/MM/yyyy) ♦ \n▸ "));
					
					break;								
				case 5: imprimirMenu = false; break;
				default: System.out.println(" ♦ Opção inválida ♦ ");
			}
		
		} while (imprimirMenu);
        
       return ped;
	}
		
	private static Pedido menuAlterarProdutosPedido (Pedido ped, ProdutoVendido p) {
		ProdutoDAO pdao = new ProdutoDAO(con, SCHEMA);
		PedidoProdutoDAO peprodao = new PedidoProdutoDAO(con, SCHEMA);
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		boolean imprimirMenu = true;
		
		ResultSet tabela = pdao.buscarEstoqueIdProduto(p.getIdProduto());
		int estoqueAtual;
		try {
			tabela.next();
		
			estoqueAtual = tabela.getInt("estoque");
		} catch (SQLException e) {
			e.printStackTrace();
			estoqueAtual = -1;
			return null;
		}
		
		int indice = ped.getProdutos().indexOf(p);
		int qtdVendida = ped.getProdutos().get(indice).getQtdVendida();
		int estoqueTotal = estoqueAtual + qtdVendida;
		
		System.out.println(
				"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
				"                           idPedido: "+ped.getIdPedido()+ " idProduto: "+p.getIdProduto()+
				"\n════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
				" 1) alterar Produto\n"+
				" 2) excluir Produto\n"+
				"════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"+
				" ♦ Informe uma opção ♦"
				);
			do {	
				System.out.print("▸ ");
				int opcao = Util.validarInteiro(in.nextLine());
					
				switch (opcao) {
					case 1:
						int novaQtdVendida;
						do {
							System.out.println(" ♦ Informe a nova quantidade vendida ♦ ");
							novaQtdVendida = Util.validarInteiro(in.nextLine());
							
							if(novaQtdVendida <= 0) {
								System.out.println(" ♦ A nova quantidade vendida deve ser maior que zero ♦ ");
							}
							
							if(novaQtdVendida > estoqueTotal) {
								System.out.println(" ♦ Não é possivel passar uma quantidade maior que ("+estoqueTotal+") ♦ ");
							}
						} while(novaQtdVendida <= 0 || novaQtdVendida > estoqueTotal);
						
						ped.getProdutos().get(indice).setQtdVendida(novaQtdVendida);
						
						if (novaQtdVendida != qtdVendida) {
							ped.getProdutos().get(indice).setEstoque(estoqueTotal);
							ped.getProdutos().get(indice).decrementarEstoque(novaQtdVendida);
							
							pdao.alterarProdutoEstoque(ped.getProdutos().get(indice));
						}
						
						ped.getProdutos().get(indice).calcularTotal();
						
						imprimirMenu = false;
						break;
					case 2:
						
						Produto produto = ped.getProdutos().get(indice);
						
						produto.setEstoque(estoqueTotal);
						pdao.alterarProdutoEstoque(produto);
						
						peprodao.excluirPedidoProduto(ped, ped.getProdutos().get(indice).getIdProduto());
						
						
						ped.getProdutos().get(indice).setQtdVendida(0);
						ped.getProdutos().get(indice).setTotal(0);
						
						imprimirMenu = false;
						break;
					default: System.out.println(" ♦ Opção inválida ♦ ");
				}
			}while(imprimirMenu);
			
			return ped;
	}
		
	public static void excluirPedido() {
		if(pedidos.getListaPedido().size() <= 0) {
			System.out.println(" ♦ Não há pedidos cadastrados! ♦ ");
			
			return;
		}
		
		PedidoDAO pedao = new PedidoDAO(con, SCHEMA);
		ProdutoDAO pdao = new ProdutoDAO(con, SCHEMA);
		
		Pedido ped = new Pedido();
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int idInputValido;
		
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		System.out.println("                                           ♣ Deletar pedido ♣ ");
		System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
		
		listarPedidos();
		
		do {
			System.out.println("\n ♦ Informe o id do pedido que deve ser excluido(0 para voltar) ♦");
			System.out.print("▸ ");
			int id = Util.validarInteiro(sc.nextLine());
			
			if(id == 0||1 == pedidos.getListaPedido()
			   .stream()
			   .filter(p -> id == p.getIdPedido())
			   .count()) {
				
				idInputValido = id;
				break;
			}
			System.out.println(" ♦ Informe um ID valido!! ♦ ");
		} while(true);
		
		if(idInputValido == 0) return;
		
		
		ped = pedidos.localizarPedido(idInputValido);
		
		for(ProdutoVendido p : ped.getProdutos()){
			p.setEstoque(p.getEstoque()+p.getQtdVendida());
			
			
			pdao.alterarProdutoEstoque(p);
		}
		
		
		pedao.excluirPedido(ped);
		pedidos.atualizarListaPedido();
	}

	public static void listarPedidos() {
		if(pedidos.getListaPedido().size() <= 0) {
			System.out.println(" ♦ Não há pedidos cadastrados! ♦ ");
			
			return;
		}
		
		System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
		System.out.printf("                                           ♣ Lista de pedidos ♣ %n");
		System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
		System.out.printf("═╦═════════════════╦═════════════════╦══════════════════════╦═════════════════╦═════════════════╦═════════════════╦═%n");
		System.out.printf(" ║ %-15s ║ %-15s ║ %-20s ║ %-15s ║ %-15s ║ %-15s ║ %n","IdPedido", "IdCliente" ,"Primeiro Nome","QtdItens: UN",
		"Valor Total", "Data");
			for (Pedido p : pedidos.getListaPedido()) {



					System.out.printf(" ║ %15d ║ %15d ║ %20s ║ %15d ║ %15.2f ║ %15s ║ %n",p.getIdPedido(),p.getCliente().getIdCliente(),
					p.getCliente().getNome(),p.getQtdItens(),p.getTotal(),p.getData());
		
			}
			System.out.printf("═╩═════════════════╩═════════════════╩══════════════════════╩═════════════════╩═════════════════╩═════════════════╩═%n");	
	}	
	
	public static void localizarPedido() {
		if(pedidos.getListaPedido().size() <= 0) {
			System.out.println(" ♦ Não há pedidos cadastrados! ♦ ");
			
			return;
		}
		
		
		int idInputValido = -1;
		
		boolean pesquisaPorData = false;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String data;
		do {
			System.out.println(" ♦ Informe o id ou a data (dd/mm/yyyy) para localizar o pedido!(0 para cancelar) ♦ ");
			data = sc.nextLine();
			
			if(Util.isInteger(data)) {
				pesquisaPorData = true;
				break;
			}
			
		
			int id = Util.validarInteiro(data);
			
			if(id == 0||1 == pedidos.getListaPedido()
			   .stream()
			   .filter(pd -> id == pd.getIdPedido())
			   .count()) {
				
				idInputValido = id;
				break;
			}
			
			System.out.println(" ♦ Informe um ID valido!! ♦ ");
		} while(true);
		
		if(idInputValido == 0) return;
		
		ArrayList <Pedido> localizado = new ArrayList<>();
		
		if(!pesquisaPorData) {	
			
			localizado.add(pedidos.localizarPedido(idInputValido));	
			
		}else if(data.length() > 1){	
			if(Util.isDateValid(data)) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate dt = LocalDate.parse(data, dtf);
				localizado = pedidos.localizarPedido(dt);
				
				
				if (localizado.size() <= 1) {
					System.out.println(" ♦ Nenhum produto Encontrado com esta data! ♦ ");
				}
			}
		}else {	
			System.out.println("Erro");
			return;
		}
		
		pedidos.atualizarListaPedido();
		
		for(Pedido p : localizado) {
			System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
			System.out.printf("                                           ♣ Lista de pedidos ♣ %n");
			System.out.printf("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════%n");
			System.out.printf("═╦═════════════════╦══════════════════════╦═%n");
			System.out.printf(" ║ %-15s ║ %-20s ║%n","IdPedido" ,"Data do Pedido");	
			System.out.printf(" ║ %15d ║ %20s ║%n", p.getIdPedido(), p.getData());						
			System.out.printf("═╩═════════════════╩══════════════════════╩═%n");
		
			Cliente c = p.getCliente();	
			System.out.printf("═╦═════════════════╦══════════════════════╦═%n");
			System.out.printf(" ║ %-15s ║ %-20s ║%n","IdCliente" ,"Nome do Cliente");	
			System.out.printf(" ║ %15d ║ %20s ║%n",c.getIdCliente(),c.getNome());						
			System.out.printf("═╩═════════════════╩══════════════════════╩═%n");
			
		
			for(ProdutoVendido pv : p.getProdutos()) {		
				System.out.printf("═╦═════════════════╦══════════════════════╦═════════════════╦═════════════════╦═════════════════╦═%n");
				System.out.printf(" ║ %-15s ║ %-20s ║ %-15s ║ %-15s ║ %-15s ║%n","IdProduto" ,"Nome do Produto","Valor: R$","Quantidade: ", "Total: R$");				
				System.out.printf(" ║ %15d ║ %20s ║ %15.2f ║ %15s ║ %15s ║%n",pv.getIdProduto(),pv.getNomeProduto(),pv.getPreco(),
					pv.getQtdVendida(),pv.getTotal());							
				System.out.printf("═╩═════════════════╩══════════════════════╩═════════════════╩═════════════════╩═════════════════╩═%n");
			}	
		
			System.out.printf("═╦══════════════════╦%n");
			System.out.printf(" ║ %-15s ║%n","Total pedido: R$");	
			System.out.printf(" ║ %16.2f ║%n",p.getTotal());						
			System.out.printf("═╩══════════════════╩%n");
		}
		
		System.out.println("Produtos vendidos por: GroupFive LTDA CNPJ: 34.126.361/0003-62 Rua Não sei oque, nº 2");						
	}
}

	