package Bean;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;

public class Anuncio {

    String titulo="";
    String descricao="";
    public LocalDate data;
    int quantidade=0;
    ArrayList<Produto> produto;
    String categoria="";
    boolean entrega=false;
    boolean status = true;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        Date date = java.sql.Date.valueOf(data);
        return date;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public ArrayList<Produto> getProduto() {
        return produto;
    }

    public void setProduto(ArrayList<Produto> produto) {
        this.produto = produto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isEntrega() {
        return entrega;
    }

    public void setEntrega(boolean entrega) {
        this.entrega = entrega;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Anuncio(String titulo, String descricao, LocalDate data, int quantidade, ArrayList<Produto> produto, String categoria, boolean entrega, boolean status){
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.quantidade = quantidade;
        this.produto = produto;
        this.categoria = categoria;
        this.entrega = entrega;
        this.status = status;
    }

    
}
