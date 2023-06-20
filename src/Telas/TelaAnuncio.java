package Telas;

public class TelaAnuncio {
    public void CadastroAnuncio(){
        System.out.println("==========================");
        System.out.println("   Cadastrar Anuncio");
        System.out.println("==========================");
    }

    public void listarAnuncioAtivo(){
        System.out.println("==========================");
        System.out.println("   Lista de Anuncios");
        System.out.println("==========================");
        System.out.println("Anuncios em andamento");
    }

    public void listartAnunciodesativado(){
        System.out.println("----------------------");
        System.out.println("Anuncios pausados");
    }

    public void editarAnuncio(){
        System.out.println("==========================");
        System.out.println("     editar anuncio");
        System.out.println("==========================");
    }

    public void escolherOpcao(){
        System.out.println("------------------------");
        System.out.println("(1) - editar anuncio");
        System.out.println("(2) - excluir anuncio");
        System.out.println("(3) - voltar");
    }

    public void excluirAnuncio(){
        System.out.println("==========================");
        System.out.println("     excluir anuncio");
        System.out.println("==========================");
    }

    public void opcaoMostrarAnuncio(){
        System.out.println("(1) - adicionar ao carrinho  (2) - adicionar a lista de produtos  (3) - sair");
        System.out.println("------------------------------------------------------------------------------");
    }

    public void mostrarAnuncio(){
        System.out.println("==========================");
        System.out.println("        Anuncio");
        System.out.println("==========================");
    }
}
