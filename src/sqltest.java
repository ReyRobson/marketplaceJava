// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;


// public class sqltest{
//     public static void main(String[] args){
//         String url = "jdbc:mysql://127.0.0.1:3306/test";
//         String user = "java";
//         String pass = "java123";

//         Connection conn = null;

//         try {
//             Class.forName("com.mysql.cj.jdbc.Driver");
//             conn = DriverManager.getConnection(url, user, pass);
//             String select = "select nome from music";
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(select);
//             while(rs.next()){
//                 String nome = rs.getString("nome");
//                 System.out.println(nome);
//             }
//         } catch (Exception e) {
//             System.out.println(e);
//         }
//     }
// }