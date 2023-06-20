package DAO;

import java.util.Scanner;

import Telas.TelaCadastro;
import factory.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cadastro {

    ConexaoFactory conexao = new ConexaoFactory();
    Connection connection = conexao.conectar();

    public void cadastrar(){
        int x = 0;
        String tipo = "";
        Scanner value = new Scanner(System.in);
    
        new TelaCadastro().CadastroOrgao();

        System.out.println("Nome:");
        String nome = value.nextLine();

        System.out.println("CNPJ:");
        String CNPJ = value.nextLine();

        System.out.println("cidade:");
        String cidade = value.nextLine();

        System.out.println("estado:");
        String estado = value.nextLine();

        System.out.println("endereco:");
        String endereco = value.nextLine();

        System.out.println("CEP:");
        String CEP = value.nextLine();

        System.out.println("telefone:");
        String telefone = value.nextLine();

        while(x != 1){
            System.out.println("esse cadastro pertence a uma empresa ou organizacao? E = Empresa | O = Organizacao");
            tipo = value.nextLine();
            if(tipo.equals("E") || tipo.equals("O")){
                x=1;
            }else{
                System.out.println("digite as opcoes corretas");
            }
        }
        
        System.out.println("Digite o email:");
        String email = value.nextLine();

        System.out.println("Digite a senha:");
        String senha = value.nextLine();

        //value.close();

        // PessoaJuridica PJ = new PessoaJuridica(nome, telefone, email, endereco, cidade, estado, senha, CEP, CNPJ, tipo);
        // ArrayList<PessoaJuridica> array =  App.PessoasJuridicas;
        // array.add(PJ);

        if(tipo.equals("E")){
            String sql = "INSERT INTO empresa(nome, telefone, email, endereco, cidade, estado, senha, CEP, CNPJ) VALUES(?,?,?,?,?,?,?,?,?)";
            
            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, nome);
                pstmt.setString(2, telefone);
                pstmt.setString(3, email);
                pstmt.setString(4, endereco);
                pstmt.setString(5, cidade);
                pstmt.setString(6, estado);
                pstmt.setString(7, senha);
                pstmt.setString(8, CEP);
                pstmt.setString(9, CNPJ);
                pstmt.execute();
                pstmt.close();
                System.out.println("empresa cadastrada");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            String sql = "INSERT INTO organizacao(Nome, Telefone, Email, Endereco, Cidade, Estado, Senha, CEP, CNPJ) VALUES(?,?,?,?,?,?,?,?,?)";
            
            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, nome);
                pstmt.setString(2, telefone);
                pstmt.setString(3, email);
                pstmt.setString(4, endereco);
                pstmt.setString(5, cidade);
                pstmt.setString(6, estado);
                pstmt.setString(7, senha);
                pstmt.setString(8, CEP);
                pstmt.setString(9, CNPJ);
                pstmt.execute();
                pstmt.close();
                System.out.println("Organizacao cadastrada");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
