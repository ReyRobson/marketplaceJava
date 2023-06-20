package Bean;

public class Produto {
    String nome;
    String subCategoria;
    boolean disponibilidade = true;
    String caracteristicas;

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
    
    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Produto(String nome, String subcategoria, String caracteristicas){
        this.nome = nome;
        this.subCategoria = subcategoria;
        this.disponibilidade = true;
        this.caracteristicas = caracteristicas;
    }
}
