package Telas;

public class TelaPerfil {
    public void perfil(){
        System.out.println("=====================================");
        System.out.println("      Perfil e Acompanhamento");
        System.out.println("=====================================");
    }
    public void Acompanhamento(){
        System.out.println("(1) - Editar informacoes do perfil");
        System.out.println("-------------------------------------------------------------");
        System.out.println("(2) - Central de Transações: Consulte os pedidos em andamento");
        System.out.println("-------------------------------------------------------------");
        System.out.println("(3) - Notificacoes: Verifique suas ultimas notificacoes e mensagens");
        System.out.println("-------------------------------------------------------------");
        System.out.println("(4) - Acompanhamento de pedido: Acompanhe a entrega de um pedido");
        System.out.println("-------------------------------------------------------------");
        System.out.println("(5) - voltar para a pagina anterior");
    }

    public void PedidosAndamento(){
        System.out.println("==========================");
        System.out.println("  Central de Transações");
        System.out.println("==========================");
        System.out.println("------------------------------");
        System.out.println("Pedidos em andamento");
        System.out.println("------------------------------");
    }

    public void Notificacoes(){
        System.out.println("==========================");
        System.out.println("     Notificacoes");
        System.out.println("==========================");
        System.out.println("------------------------------");
    }

    public void acompanhamentoPedido(){
        System.out.println("==========================");
        System.out.println("Acompanhamento de Pedido");
        System.out.println("==========================");
        System.out.println("------------------------------");
    }

}
