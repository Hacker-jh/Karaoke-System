package ORAconnect;

import java.sql.*;

public class oracle_connect {
    Connection con;
   public Connection get_Connection() {
       try {
           Class.forName("oracle.jdbc.driver.OracleDriver");
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
       String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String uname ="homework";
        String pwd = "123456";
       try {
           con = DriverManager.getConnection(url,uname,pwd);
       } catch (SQLException e) {
           e.printStackTrace();
       }
       System.out.println(con.getClass().getName());
       return con;
    }

}
