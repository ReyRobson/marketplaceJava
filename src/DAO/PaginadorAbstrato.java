package DAO;

import java.util.Scanner;

import Telas.TelaPrincipal;

public abstract class PaginadorAbstrato {
    public abstract void paginaPrincipal();

    public abstract void paginaOrganizacao();

    public abstract void paginaEmpresa();

    public void metodosPadrao(){
            new TelaPrincipal().outrasOpcoes();
            Scanner choose = new Scanner(System.in);
            boolean wrongOption = false;

            do {
                System.out.println("escolha sua opcao");
                switch (choose.nextInt()) {
                    case 11:
                        AnuncioDAO.listarAnuncio("graos");
                        break;
                    case 12:
                        AnuncioDAO.listarAnuncio("bebidas");
                        break;
                    case 13:
                        AnuncioDAO.listarAnuncio("Higiene");
                        break;
                    case 14:
                        AnuncioDAO.listarAnuncio("frutas_legumes");
                        break;
                    case 15:
                        AnuncioDAO.listarAnuncio("proteinas");
                        break;
                    case 16:
                        break;      
                    default:
                    System.out.println("essa opcao nao existe");
                        break;
                }   
            }while (wrongOption);                
    }
}
