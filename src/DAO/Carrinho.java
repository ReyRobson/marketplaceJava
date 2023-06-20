package DAO;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import Bean.Anuncio;
import Telas.TelaCarrinho;
import factory.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Carrinho implements SwitchInterface {

    public static void adicionaAoCarrinho(String nomeAnuncio){
        ConexaoFactory conexao = new ConexaoFactory();
        Connection connection = conexao.conectar();
        
        Map<String, String> map = Login.loginData;
        String email = map.get("email");

        String sqlGetOrgID = "select id from organizacao where email=?";
        String sqlGetAnuncioID = "select id from anuncio where titulo = ?";
        String sqlInsertIntoCarrinho = "INSERT INTO carrinho(fk_Organizacao_Id, fk_Anuncio_Id) VALUES(?,?)";

        int orgID=0;
        int anuncioID=0;

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlGetOrgID);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                orgID = rs.getInt("Id");
            }

            PreparedStatement pstmt1 = connection.prepareStatement(sqlGetAnuncioID);
            pstmt1.setString(1, nomeAnuncio);
            ResultSet rs1 = pstmt1.executeQuery();
            while(rs1.next()){
                anuncioID = rs1.getInt("Id");
            }

            PreparedStatement pstmti = connection.prepareStatement(sqlInsertIntoCarrinho);
            pstmti.setInt(1, orgID);
            pstmti.setInt(2, anuncioID);
            pstmti.execute();
            pstmti.close();
            System.out.println("adicionado ao carrinho");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void visualizaCarrinho(){

        new TelaCarrinho().mostrarCarrinho();

        ArrayList<Anuncio> array = itensCarrinho();
        int y=0;

        for (Anuncio anuncio : array) {
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("item: " + y);
            System.out.println("Nome: " + array.get(y).getTitulo());
            System.out.println("Descricao: " + array.get(y).getDescricao() + " Entrega? " + array.get(y).isEntrega());
            System.out.println("Categoria: " + array.get(y).getCategoria());            
            System.out.println("------------------------------------------------------------------------------");
            y++;
        }

        System.out.println("seu carrinho possui "+y+" itens");

        new Carrinho().menuOptions();
    }

    private static ArrayList<Anuncio> itensCarrinho(){
        ConexaoFactory conexao = new ConexaoFactory();
        Connection connection = conexao.conectar();

        ArrayList<Anuncio> arrayAnuncio = new ArrayList<Anuncio>();
        Map<String, String> map = Login.loginData;
        String email = map.get("email");

        String sql = "select anuncio.id, titulo, descricao, entrega, categoria, quantidade, status from carrinho join anuncio on carrinho.fk_Anuncio_Id=anuncio.Id join organizacao on carrinho.fk_Organizacao_Id=organizacao.Id where organizacao.Email=?";
    
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String titulo = rs.getString("Titulo");
                String descricao = rs.getString("Descricao");
                String categoria = rs.getString("Categoria");
                boolean entrega = rs.getBoolean("entrega");
                Boolean status = rs.getBoolean("Status");
                int quantidade = rs.getInt("Quantidade");

                Anuncio anuncio = new Anuncio(titulo, descricao, null, quantidade, null, categoria, entrega, status);
                arrayAnuncio.add(anuncio);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayAnuncio;
    }

    private static void removeQuantidade() {
    ConexaoFactory conexao = new ConexaoFactory();
    Connection connection = conexao.conectar();

    ArrayList<Anuncio> array = itensCarrinho();
    int y=0;

    String sqlUpdateAnuncio = "UPDATE anuncio set quantidade = ? where id = ?";
    String sqlGetAnuncioID = "select id from anuncio where titulo = ?";
    int anuncioID = 0;

        for (Anuncio anuncio : array) {
            int quantidade = anuncio.getQuantidade();
            int novaQuantidade = quantidade -1;
            try {
                PreparedStatement pstmt1 = connection.prepareStatement(sqlGetAnuncioID);
                pstmt1.setString(1, anuncio.getTitulo());
                ResultSet rs = pstmt1.executeQuery();
                while(rs.next()){
                    anuncioID = rs.getInt("Id");
                }
                pstmt1.close();
                PreparedStatement pstsmt = connection.prepareStatement(sqlUpdateAnuncio);
                pstsmt.setInt(1, novaQuantidade);
                pstsmt.setInt(2, anuncioID);
                pstsmt.execute();
                pstsmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(novaQuantidade == 0){
                anuncio.setStatus(false);
            }
            y++;
        }
    }

    private static void apagaCarrinho() {
        ConexaoFactory conexao = new ConexaoFactory();
        Connection connection = conexao.conectar();

        Map<String, String> map = Login.loginData;
        String email = map.get("email");

        int id=0;
        String sqlGetOrgID = "select id from carrinho join organizacao on carrinho.fk_Organizacao_Id=organizacao.Id where email=?";
        String sqlRemoveCarrinhoOrg = "delete from carrinho where carrinho.fk_Organizacao_Id=?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlGetOrgID);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                id = rs.getInt("Id");
            }
            pstmt.close();

            PreparedStatement pstmt1 = connection.prepareStatement(sqlRemoveCarrinhoOrg);
            pstmt1.setInt(1, id);
            pstmt1.execute();
            pstmt1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void menuOptions(){
        boolean wrongOption=false;
        Scanner choose = new Scanner(System.in);

        do{
            System.out.println("deseja finalizar a compra? (1) - Sim (2) - Nao (3) - apagar carrinho");
            switch(choose.nextInt()){
                case 1:
                    System.out.println("aguarde, estamos finalizando seu pedido...");
                    removeQuantidade();
                    apagaCarrinho();
                    System.out.println("Compra realizada com sucesso!");
                    break;
                case 2:
                    break;
                case 3:
                    apagaCarrinho();
                    break;
                default:
                    System.out.println("essa opcao nao existe");
                    wrongOption=true;
                    break;
            }
        }while(wrongOption);
    }
}

