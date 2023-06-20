package Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class sqltest{
    public static void main(String[] args) throws ClassNotFoundException{
        String url = "jdbc:mysql://127.0.0.1:3306/marketplace";
        String user = "java";
        String pass = "java123";

        Connection conn = null;

        String insert = "INSERT INTO produtos(Nome, Caracteristicas, Disponibilidade, Subcategoria) VALUES(?,?,?,?)";
        String delete = "DELETE FROM produtos where Nome=?";
        String resetID = "ALTER TABLE produtos AUTO_INCREMENT = 1";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement pstmt = conn.prepareStatement(insert);
            pstmt.setString(1, "arroz");
            pstmt.setString(2, "Arroz de marca 10 kg");
            pstmt.setBoolean(3, true);
            pstmt.setString(4, "Arroz");
            pstmt.execute();
            System.out.println("dado adicionado");
            pstmt.close();
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setString(1, "arroz");
            stmt.execute();
            System.out.println("Dado removido");
            stmt.close();
            Statement rstmt = conn.createStatement();
            rstmt.execute(resetID);
            rstmt.close();
            System.out.println("tudo funcionando com o banco de dados!");
        } catch (SQLException e) {
            System.out.println("Erro durante a execucao das diretivas sql");
            e.printStackTrace();
        }
    }
}