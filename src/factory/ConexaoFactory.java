package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory{
    public Connection conectar(){
        String url = "jdbc:mysql://127.0.0.1:3306/marketplace";
        String user = "java";
        String pass = "java123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException el) {
            el.printStackTrace();
        }
        try {
            return DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}