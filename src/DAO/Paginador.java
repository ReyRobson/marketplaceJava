package DAO;
import Telas.TelaPrincipal;
import java.util.Scanner;

import java.util.Map;

public class Paginador extends PaginadorAbstrato{

    public void selecionador(){
        if(Login.login){
            Map<String, String> map = Login.loginData;
            String val = map.get("tipo");
            if(val.equals("organizacao")){
                paginaOrganizacao();
            }else{
                paginaEmpresa();
            }   
           }else{
            paginaPrincipal();
           }
        }
    public void paginaPrincipal(){
        new TelaPrincipal().Home();
        Scanner choose = new Scanner(System.in);
        boolean wrongOption = false;
        
        do{
            System.out.println("Escolha sua opcao");
            switch (choose.nextInt()) {
                case 1:
                    Pesquisa.pesquisarAnuncio();
                    break;
                case 2:
                    System.out.println("faca login como organizacao para ter acesso a sua lista de produtos");
                    break;
                case 3:
                    System.out.println("faca login como organizacao para poder adicionar produtos ao carrinho");
                    break;
                case 4:
                    new Login().realizarLogin();
                    break;
                case 5:
                    new Cadastro().cadastrar();
                    break;
                case 6:
                    metodosPadrao();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Essa opcao não existe");
                    wrongOption = true;
            }
        }while(wrongOption);

    }

    public void paginaOrganizacao(){
        new TelaPrincipal().HomeOrganizacao();
        Scanner choose = new Scanner(System.in);
        boolean wrongOption = false;
        
        do{
            System.out.println("Escolha sua opcao");
            switch (choose.nextInt()) {
                case 1:
                    Pesquisa.pesquisarAnuncio();
                    break;
                case 2:
                    System.out.println("lista de produtos");
                    break;
                case 3:
                    Carrinho.visualizaCarrinho();
                    break;
                case 4:
                    new Perfil().mostrarPerfil();
                    break;
                case 5:
                    metodosPadrao();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Essa opcao não existe");
                    wrongOption = true;
            }
        }while(wrongOption);
    }

    public void paginaEmpresa(){
        new TelaPrincipal().HomeEmpresa();
        Scanner choose = new Scanner(System.in);
        boolean wrongOption = false;
        
        do{
            System.out.println("Escolha sua opcao");
            switch (choose.nextInt()) {
                case 1:
                    Pesquisa.pesquisarAnuncio();
                    break;
                case 2:
                    AnuncioDAO.criarAnuncio();
                    break;
                case 3:
                    AnuncioDAO.listarAnuncioAtivoDesativo();
                    break;
                case 4:
                    new Perfil().mostrarPerfil();
                    break;
                case 5:
                    metodosPadrao();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Essa opcao não existe");
                    wrongOption = true;
            }
        }while(wrongOption);
    }

}
