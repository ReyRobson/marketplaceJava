package DAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import Bean.Anuncio;
import Bean.Produto;
import Telas.TelaAnuncio;
import factory.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AnuncioDAO {
    static String[] graos = {"arroz", "feijao", "granola", "milho", "cereal"};
    static String[] bebidas = {"leite", "cafe", "cha", "agua", "suco", "refrigerante"};
    static String[] Higiene = {"papel higienico", "creme dental", "sabonete", "shampoo"};
    static String[] frutas_legumes = {"banana", "maca", "laranja", "tomate", "alface", "melao", "uva", "batata", "cenoura", "mamao", "morango"};
    static String[] proteinas = {"carne bovina", "peixe", "frango"};

    private static ArrayList<Integer> anuncioIds = new ArrayList<Integer>();

    public static void criarAnuncio(){

        int quantidade = 0;
        Scanner value = new Scanner(System.in);

        new TelaAnuncio().CadastroAnuncio();

        System.out.println("Nome do Anuncio: ");
        String titulo = value.nextLine();

        System.out.println("descricao do anuncio: ");
        String descricao = value.nextLine();

        System.out.println("voce será o responsavel pela entrega? Digite true para sim e false para não");
        boolean entrega = value.nextBoolean();

        ArrayList<Produto> produto = ProdutoDAO.adicionarProduto();

        do {
            System.out.println("quantidade: ");
            quantidade = value.nextInt();
        } while (quantidade <= 0);

        String categoria = defineCategoria(produto);

        LocalDate data = LocalDate.now();

        boolean status = true;

        Anuncio anuncio = new Anuncio(titulo, descricao, data, quantidade, produto, categoria, entrega, status);

        addAnuncio(anuncio);

    }

    private static String defineCategoria(ArrayList<Produto> produto){
        
        String res="";
        for(int i=0;i<graos.length;i++){
            if(graos[i].equals(produto.get(0).getSubCategoria())){
                res = "graos";
            }
        }

        for(int i=0;i<bebidas.length;i++){
            if(bebidas[i].equals(produto.get(0).getSubCategoria())){
                res = "bebidas";
            }
        }

        for(int i=0;i<Higiene.length;i++){
            if(Higiene[i].equals(produto.get(0).getSubCategoria())){
                res = "Higiene";
            }
        }

        for(int i=0;i<frutas_legumes.length;i++){
            if(frutas_legumes[i].equals(produto.get(0).getSubCategoria())){
                res = "frutas_legumes";
            }
        }

        for(int i=0;i<proteinas.length;i++){
            if(proteinas[i].equals(produto.get(0).getSubCategoria())){
                res = "proteinas";
            }
        }

        if(res.isEmpty()){
            System.out.println("nenhuma categoria encontrada");
        }

        return res;
    }

    private static void addAnuncio(Anuncio anuncio){
        ConexaoFactory conexao = new ConexaoFactory();
        Connection connection = conexao.conectar();
        int produtoId=0;
        int empresaId=0;
        int anuncioId=0;

        String sqlGetIdProduto = "SELECT id from produtos ORDER BY id DESC LIMIT 1";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlGetIdProduto);
            while(rs.next()){
                produtoId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Map<String, String> map = Login.loginData;
        String sqlInsert = "INSERT INTO anuncio(Titulo, Descricao, Data, Quantidade, fk_Produtos_id, Categoria, Status, Entrega) VALUES(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
            pstmt.setString(1, anuncio.getTitulo());
            pstmt.setString(2, anuncio.getDescricao());
            pstmt.setDate(3, anuncio.getData());
            pstmt.setInt(4, anuncio.getQuantidade());
            pstmt.setInt(5, produtoId);
            pstmt.setString(6, anuncio.getCategoria());
            pstmt.setBoolean(7, anuncio.isStatus());
            pstmt.setBoolean(8, anuncio.isEntrega());
            pstmt.execute();
            pstmt.close();
            System.out.println("anuncio adicionado com sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlGetIdEmpresa = "SELECT id from empresa WHERE Email = ?";
        String sqlGetIdAnuncio = "SELECT id from anuncio ORDER BY id DESC LIMIT 1";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlGetIdEmpresa);
            pstmt.setString(1, map.get("email"));
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                empresaId = rs.getInt("id");
            }
            Statement stmt = connection.createStatement();
            ResultSet rs2 = stmt.executeQuery(sqlGetIdAnuncio);
            while(rs2.next()){
                anuncioId = rs2.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlInsertRelacionamento = "INSERT INTO cria(fk_Empresa_Id, fk_Anuncio_Id	) VALUES(?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlInsertRelacionamento);
            pstmt.setInt(1, empresaId);
            pstmt.setInt(2, anuncioId);
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }  

    private static ArrayList<Anuncio> selectAnuncioEmpresa(){
        ConexaoFactory conexao = new ConexaoFactory();
        Connection connection = conexao.conectar();

        ArrayList<Anuncio> arrayAnuncio = new ArrayList<Anuncio>();
        Map<String, String> map = Login.loginData;

        String sql = "select anuncio.id,titulo,descricao,status,quantidade,entrega,categoria from cria join anuncio on cria.fk_Anuncio_Id=anuncio.Id join empresa on cria.fk_Empresa_Id=empresa.id where Email=?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, map.get("email"));
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("Id");
                String titulo = rs.getString("Titulo");
                String descricao = rs.getString("Descricao");
                String categoria = rs.getString("Categoria");
                int quantidade = rs.getInt("Quantidade");
                Boolean entrega = rs.getBoolean("Entrega");
                Boolean status = rs.getBoolean("Status");
                
                anuncioIds.add(id);

                Anuncio anuncio = new Anuncio(titulo, descricao, null, quantidade, null, categoria, entrega, status);
                arrayAnuncio.add(anuncio);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayAnuncio;
    }

    public static void listarAnuncioAtivoDesativo(){
        int x=1;
        int y=1;

        ArrayList<Anuncio> array = selectAnuncioEmpresa();

        new TelaAnuncio().listarAnuncioAtivo();
        
        for (Anuncio anuncio : array) {
            if(anuncio.isStatus()){
                System.out.println("----------------------");
                System.out.println("anuncio " + x);
                System.out.println("Nome: "+ anuncio.getTitulo());
                System.out.println("Descricao: " + anuncio.getDescricao() + " quantidade " + anuncio.getQuantidade());
            }
            x++;
        }

        new TelaAnuncio().listartAnunciodesativado();
        for (Anuncio anuncio : array) {
            if(!anuncio.isStatus()){
                System.out.println("----------------------");
                System.out.println("anuncio " + y);
                System.out.println("Nome: "+ anuncio.getTitulo());
                System.out.println("Descricao: " + anuncio.getDescricao() + " quantidade " + anuncio.getQuantidade());
            }
            y++;
        }

        escolherOpcao();

    }

    private static ArrayList<Anuncio> selectAnuncio(){
        ConexaoFactory conexao = new ConexaoFactory();
        Connection connection = conexao.conectar();
        ArrayList<Anuncio> arrayAnuncio = new ArrayList<Anuncio>();
        ArrayList<Produto> arrayProduto = new ArrayList<Produto>();

        String sql = "select * from anuncio left join produtos on anuncio.fk_Produtos_id=produtos.Id;";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String titulo = rs.getString("Titulo");
                String descricao = rs.getString("Descricao");
                String categoria = rs.getString("Categoria");
                int quantidade = rs.getInt("Quantidade");
                Boolean entrega = rs.getBoolean("Entrega");
                Boolean status = rs.getBoolean("Status");
                String pnome = rs.getString("Nome");
                String psubcategoria = rs.getString("Subcategoria");
                String pcaracteristicas = rs.getString("Caracteristicas");
                
                Produto produto = new Produto(pnome, psubcategoria, pcaracteristicas);
                arrayProduto.add(produto);

                Anuncio anuncio = new Anuncio(titulo, descricao, null, quantidade, arrayProduto, categoria, entrega, status);
                arrayAnuncio.add(anuncio);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayAnuncio;
    }

    public static void listarAnuncio(String nome){
        System.out.println("----------------------");
        System.out.println("       " + nome + "       ");

        ArrayList<Anuncio> array = selectAnuncio();
        boolean findResult = false;
        int y=0;

        for (Anuncio anuncio : array) {
            if(nome.equals(anuncio.getProduto().get(y).getNome()) && anuncio.isStatus()){
                System.out.println("----------------------");
                System.out.println("anuncio " + y);
                System.out.println("Nome: "+ anuncio.getTitulo());
                System.out.println("Descricao: " + anuncio.getDescricao() + " quantidade " + anuncio.getQuantidade());
                findResult = true;
            }

            if(nome.equals(anuncio.getProduto().get(y).getSubCategoria()) && anuncio.isStatus()){
                System.out.println("----------------------");
                System.out.println("anuncio " + y);
                System.out.println("Nome: "+ anuncio.getTitulo());
                System.out.println("Descricao: " + anuncio.getDescricao() + " quantidade " + anuncio.getQuantidade());
                findResult = true;   
            }
            y++;
        }
        for (Anuncio anuncio : array) {
            if(nome.equals(anuncio.getCategoria()) && anuncio.isStatus()){
                System.out.println("----------------------");
                System.out.println("anuncio " + y);
                System.out.println("Nome: "+ anuncio.getTitulo());
                System.out.println("Descricao: " + anuncio.getDescricao() + " quantidade " + anuncio.getQuantidade());
                y++;
                findResult = true;
            }
        }

        if(!findResult){
            System.out.println("nao foi possivel listar um anuncio");
        }else{
            boolean wrongOption=false;
            Scanner choose = new Scanner(System.in);
            do {
                System.out.println("(1) visualizar anuncio (2) voltar");
                switch (choose.nextInt()) {
                    case 1:
                        mostrarAnuncio();
                        break;
                    case 2:
                        break;
                    default:
                        System.out.println("essa opcao nao existe");
                        wrongOption = true;
                        break;
                }
            } while (wrongOption);
        }
    }

    private static void escolherOpcao(){

        Scanner option = new Scanner(System.in);
        boolean wrongOption = false;
        new TelaAnuncio().escolherOpcao();
        do {
            System.out.println("Escolha sua opcao");
            switch (option.nextInt()) {
                case 1:
                    editarAnuncio();
                    break;
                case 2:
                    excluirAnuncio();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("essa opcao não existe");
                    wrongOption = true;
            } 
        } while (wrongOption);
        
    }

    private static void excluirAnuncio() {
        boolean deleted=false;
        Scanner scan = new Scanner(System.in);

        ArrayList<Anuncio> array = selectAnuncioEmpresa();

        ConexaoFactory conexao = new ConexaoFactory();
        Connection connection = conexao.conectar();

        String sqldeleteAnuncio="delete from anuncio where Titulo=? and id=?";
        String sqldeleteRelacao="delete from cria where fk_Anuncio_id is null";

        do{
            System.out.println("Digite o nome do anuncio que deseja excluir");
            String anuncioName = scan.nextLine();
            for (Anuncio anuncio : array) {
                if(anuncio.getTitulo().equals(anuncioName)){
                    for(int x=0; x<anuncioIds.size();x++){
                        try {
                            PreparedStatement pstmt = connection.prepareStatement(sqldeleteAnuncio);
                            pstmt.setString(1, anuncioName);
                            pstmt.setInt(2, anuncioIds.get(x));
                            pstmt.execute();
                            pstmt.close();
                            deleted=true;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }while(!deleted);

        if(deleted){
            try {
               Statement stmt = connection.createStatement();
                stmt.execute(sqldeleteRelacao); 
                System.out.println("anuncio excluido");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void editarAnuncio(){
        boolean editado=false;
        Scanner scan = new Scanner(System.in);
        ArrayList<Anuncio> array = selectAnuncioEmpresa();

        new TelaAnuncio().editarAnuncio();

        do {
            System.out.println("Digite o nome do anuncio que deseja editar");
            String anuncioName = scan.nextLine();

            for (Anuncio anuncio : array) {
                if(anuncio.getTitulo().equals(anuncioName)){
                    System.out.println("digite o nome do que deseja alterar, podem ser alterados: titulo, descricao, quantidade e status");
                    String changeOption = scan.nextLine();
        
                    if(changeOption.equals("titulo")){
                        System.out.println("Digite o valor");
                        String value = scan.nextLine();
                        alteraDados(value, "titulo", anuncioName);
                        editado=true;
                    }else if(changeOption.equals("descricao")){
                        System.out.println("Digite o valor");
                        String value = scan.nextLine();
                        alteraDados(value, "descricao", anuncioName);
                        editado=true;
                    }else if(changeOption.equals("quantidade")){
                        System.out.println("digite o valor");
                        int value = scan.nextInt();
                        String val = Integer.toString(value);
                        alteraDados(val, "quantidade", anuncioName);
                        editado=true;
                    }else if(changeOption.equals("status")){
                        System.out.println("digite o valor");
                        boolean value = scan.nextBoolean();
                        String val = Boolean.toString(value);
                        alteraDados(val, "status", anuncioName);
                        editado=true;
                    }
                }
            }
            if(!editado){
                System.out.println("O anuncio ou o nome que deseja alterar nao existe, tente novamente");
            }
        }while (!editado);
    }

    private static void alteraDados(String value, String tipo, String titulo){
        ConexaoFactory conexao = new ConexaoFactory();
        Connection connection = conexao.conectar();
        
        String sql = "UPDATE anuncio SET "+ tipo + '='+ "? where titulo = ? and id=?";

        if(tipo.equals("quantidade")){
            for(int x=0; x<anuncioIds.size(); x++){
            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                int val = Integer.parseInt(value);
                pstmt.setInt(1, val);
                pstmt.setString(2, titulo);
                pstmt.setInt(3, anuncioIds.get(x));
                pstmt.execute();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        }else if(tipo.equals("status")){
            for(int x=0; x<anuncioIds.size(); x++){
            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                Boolean val = Boolean.parseBoolean(value);
                pstmt.setBoolean(1, val);
                pstmt.setString(2, titulo);
                pstmt.setInt(3, anuncioIds.get(x));
                pstmt.execute();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        }else{
            for(int x=0; x<anuncioIds.size(); x++){
            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, value);
                pstmt.setString(2, titulo);
                pstmt.setInt(3, anuncioIds.get(x));
                pstmt.execute();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }
    }

    public static void mostrarAnuncio(){

        ArrayList<Anuncio> array = selectAnuncio();

        Scanner scanner = new Scanner(System.in);

        System.out.println("digite o nome do anuncio que deseja visualizar");
        String nome = scanner.nextLine();
        int x=0;

        new TelaAnuncio().mostrarAnuncio();
        
        for (Anuncio anuncio : array) {
            if(nome.equals(anuncio.getTitulo())){
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("Nome: "+ anuncio.getTitulo());
                System.out.println("quantidade " + anuncio.getQuantidade());
                System.out.println("Descricao: " + anuncio.getDescricao());
                System.out.println("responsavel pela entrega? " + anuncio.isEntrega());
                System.err.println("data do anuncio: " + anuncio.data);
                System.out.println("-------------------------------------------");
                System.out.println("produto: " + anuncio.getProduto().get(x).getNome());
                System.err.println("caracteristicas do produto: " + anuncio.getProduto().get(x).getCaracteristicas());
                System.out.println("------------------------------------------------------------------------------");
                break;
            }else{
                x++;
            }
        }

        if(Login.login){
            Map<String, String> map = Login.loginData;
            String val = map.get("tipo");
            if(val.equals("organizacao")){
                new TelaAnuncio().opcaoMostrarAnuncio();
                Scanner choose = new Scanner(System.in);
                boolean wrongOption = false;
                do {
                    System.out.println("escolha uma opcao");
                    switch (choose.nextInt()) {
                        case 1:
                            Carrinho.adicionaAoCarrinho(array.get(x).getTitulo());
                            break;
                        case 2:
                            System.out.println("adicionar a lista de produtos");
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("essa opcao nao existe");
                            wrongOption = true;
                            break;
                    }
                } while (wrongOption);
            }   
        }
    }
}
