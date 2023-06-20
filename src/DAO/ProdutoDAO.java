package DAO;

import java.util.ArrayList;
import java.util.Scanner;

import Bean.Produto;
import factory.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoDAO {
        public static ArrayList<Produto> adicionarProduto(){
        Scanner scan = new Scanner(System.in);

        System.out.println("adicionar produto");

        System.out.println("Nome: ");
        String nome = scan.nextLine();

        System.out.println("subCategoria: ");
        String subcategoria = scan.nextLine();

        System.out.println("caracteristicas");
        String caracteristicas = scan.nextLine();

        ArrayList<Produto> produto = new ArrayList<Produto>();

        Produto prod = new Produto(nome, subcategoria, caracteristicas);

        produto.add(prod);

        addProduto(prod);

        return produto;
    }

    private static void addProduto(Produto produto){
        ConexaoFactory conexao = new ConexaoFactory();
        Connection connection = conexao.conectar();

        String sql = "INSERT INTO produtos(nome, subcategoria, disponibilidade, caracteristicas)VALUES(?,?,?,?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getSubCategoria());
            pstmt.setBoolean(3, true);
            pstmt.setString(4, produto.getCaracteristicas());
            pstmt.execute();
            pstmt.close();
            System.out.println("produto adicionado com sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
