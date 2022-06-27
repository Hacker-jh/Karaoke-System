package Connect_to_database;
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
        String uname ="yangxu";
        String pwd = "123456";
        try {
            con = DriverManager.getConnection(url,uname,pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void Close() throws SQLException {
        if(con!=null) {
            con.close();
        }
    }

}

