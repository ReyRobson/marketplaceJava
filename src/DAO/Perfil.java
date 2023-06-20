package DAO;

import java.util.Map;
import java.util.Scanner;

import Telas.TelaPerfil;
import factory.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Perfil implements SwitchInterface{
    Map<String, String> map = Login.loginData;
    public void mostrarPerfil(){
        new TelaPerfil().perfil();
        new TelaPerfil().Acompanhamento();
        System.out.println("nome: " + map.get("nome"));
        System.out.println("endereco: " + map.get("endereco") + " " + map.get("cidade"));
        System.out.println("telefone: " + map.get("telefone"));
        System.out.println("email: " + map.get("email")+"\n");

        menuOptions();
    }

    private void editarPerfil(){
        Scanner obj = new Scanner(System.in);
        boolean editado = false;
        do {
            System.out.println("Digite o nome de um dos objetos acima que deseja alterar");
            String value = obj.nextLine();
    
            for (String iterable_element : map.keySet()) {
                if(value.equals(iterable_element)){
                    System.out.println("digite um novo " + iterable_element);
                    String newElement = obj.nextLine();
                    map.replace(value, newElement);
                    alteraDados(value, newElement);
                    editado=true;
                    //changeArray(value, newElement, map.get("email"));
                }
            }
    
            if(editado){
                System.out.println("objeto alterado com sucesso");
            }else{
                System.out.println("o nome informado não pode ser alterado tente novamente");
            }
        } while (!editado);

    }

    private void centralTransacoes(){
        new TelaPerfil().PedidosAndamento();
        System.out.println("em construcao");
    }

    private void notificacoes(){
        new TelaPerfil().Notificacoes();
        System.out.println("em construcao");
    }

    private void acompanhamentoPedido(){
        new TelaPerfil().acompanhamentoPedido();
        System.out.println("em construcao");
    }

    public void alteraDados(String value, String element){
        ConexaoFactory conexao = new ConexaoFactory();
        Connection connection = conexao.conectar();

        String tipo = map.get("tipo");
        String val = value.substring(0, 1).toUpperCase() + value.substring(1);

        String sql = "UPDATE "+ tipo + " SET "+ val + '='+ "? WHERE Email = ?";
        String email = map.get("email");

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, element);
            pstmt.setString(2, email);
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void menuOptions(){
        Scanner choose = new Scanner(System.in);
        boolean wrongOption = false;
        do{
            System.out.println("Escolha sua opcao");
            switch (choose.nextInt()) {
                case 1:
                    editarPerfil();
                    break;
                case 2:
                    centralTransacoes();
                    break;
                case 3:
                    notificacoes();
                    break;
                case 4:
                    acompanhamentoPedido();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Essa opcao não existe");
                    wrongOption = true;
            }
        }while(wrongOption);
    }
}
