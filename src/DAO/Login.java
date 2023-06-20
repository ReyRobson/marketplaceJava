package DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Bean.PessoaJuridica;
import Telas.TelaLogin;
import factory.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login {

    ConexaoFactory conexao = new ConexaoFactory();
    Connection connection = conexao.conectar();

    private int x;
    public static boolean login = false;
    public static Map<String, String> loginData = new HashMap<>();

    public void realizarLogin(){
        String userSenha;
        String email;

        do{
            new TelaLogin().Login();
            
            Scanner myObj = new Scanner(System.in); 

            System.out.println("Digite seu email");

            email = myObj.nextLine();

            System.out.println("Digite sua senha");
            
            userSenha = myObj.nextLine();

            if(userSenha.equals("1")){
                esqueciASenha();
            }

        }while(!verificaLogin(email, userSenha));

        login = true;
    }
    
    private boolean verificaLogin(String email, String senha){
        this.x=0;
        String login = "";
        
        if(email.equals("organizacao@mail.com") || email.equals("empresa@mail.com")){
            if(senha.equals("123456")){
                System.out.println("logado com sucesso");
                login = "sucesso";
            }
        }else{
            ArrayList<PessoaJuridica> empresa =  getEmaileSenha("empresa");
            for (PessoaJuridica pessoaJuridica : empresa) {
                if(empresa.get(x).getEmail().contains(email)){
                    String pass = empresa.get(this.x).getSenha();
                    if(pass.equals(senha)){
                        System.out.println("logado com sucesso");
                        login = "sucesso";
                        whoLogin(x, "empresa");
                    }
                }
                x++;
            }
        }

        if(!login.equals("sucesso")){
            this.x=0;
            ArrayList<PessoaJuridica> organizacao =  getEmaileSenha("organizacao");
            for (PessoaJuridica pessoaJuridica : organizacao) {
                if(organizacao.get(x).getEmail().contains(email)){
                    String pass = organizacao.get(this.x).getSenha();
                    if(pass.equals(senha)){
                        System.out.println("logado com sucesso");
                        login = "sucesso";
                        whoLogin(x, "organizacao");
                    }
                }
                x++;
            }
        }

        if(login.equals("sucesso")){
            return true;
        }else{
            System.out.println("login falhou");
            return false;
        }
    }

        private ArrayList<PessoaJuridica> getEmaileSenha(String tipo){
        String sql = "select * from " + tipo;

        ArrayList<PessoaJuridica> login = new ArrayList<PessoaJuridica>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String nome = rs.getString("Nome");
                String telefone = rs.getString("Telefone");
                String email = rs.getString("Email");
                String endereco = rs.getString("Endereco");
                String cidade = rs.getString("Cidade");
                String estado = rs.getString("Estado");
                String senha= rs.getString("Senha");
                String CEP = rs.getString("CEP");
                String CNPJ = rs.getString("CNPJ");
                PessoaJuridica pessoaJuridica = new PessoaJuridica(nome,telefone,email,endereco,cidade,estado,senha,CEP,CNPJ,tipo);
                login.add(pessoaJuridica);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return login;
    }

    private void esqueciASenha(){
        String newUserSenha;
        boolean senhaAlterada=false;

        new TelaLogin().esqueciSenha();

        Scanner myObj = new Scanner(System.in); 

        System.out.println("digite seu email");

        String email = myObj.nextLine();

        System.out.println("digite a nova senha");
        
        newUserSenha = myObj.nextLine();

        ArrayList<PessoaJuridica> empresa =  getEmaileSenha("empresa");

        for (PessoaJuridica pessoaJuridica : empresa) {
            if(pessoaJuridica.getEmail().contains(email)){
                alteraSenha(email, newUserSenha, "empresa");
                senhaAlterada = true;
            }
        }

        if(!senhaAlterada){
            ArrayList<PessoaJuridica> organizacao =  getEmaileSenha("organizacao");
            for (PessoaJuridica pessoaJuridica : organizacao) {
                if(pessoaJuridica.getEmail().contains(email)){
                    alteraSenha(email, newUserSenha, "organizacao");
                    senhaAlterada = true;
                }
            }
        }

        System.out.println("Se o email digitado for correto a senha para este email foi trocada com sucesso!");
    }

    private void alteraSenha(String email, String novaSenha, String tipo){
        String sql = "UPDATE "+ tipo + " SET Senha = ? WHERE Email = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, novaSenha);
            pstmt.setString(2, email);
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void whoLogin(int x, String tipo){
        ArrayList<PessoaJuridica> array =  getEmaileSenha(tipo);

        loginData.put("nome", array.get(x).getNome());
        loginData.put("email", array.get(x).getEmail());
        loginData.put("tipo", tipo);
        loginData.put("endereco", array.get(x).getEndereco());
        loginData.put("cidade", array.get(x).getCidade());
        loginData.put("telefone", array.get(x).getTelefone());
        loginData.put("CNPJ", array.get(x).getCNPJ());
    }
}
