package Connect_to_database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
    public static void main(String[] args) throws SQLException {
        oracle_connect orc = new oracle_connect();
        Connection conn = orc.get_Connection();//获取连接
        String sql="select ID from items";
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        int i=0;
        while(rs.next())
            i++;
        System.out.println(i);
    }
}
