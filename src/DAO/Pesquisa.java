package DAO;
import java.util.Scanner;

public class Pesquisa {
    public static void pesquisarAnuncio(){
        Scanner valor = new Scanner(System.in);

        System.out.println("Digite o que deseja pesquisar");
        String texto = valor.nextLine();
        AnuncioDAO.listarAnuncio(texto);
    }
}
