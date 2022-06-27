package Dao;

import Connect_to_database.oracle_connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginValition {
    oracle_connect orc=new oracle_connect();
    Connection conn=orc.get_Connection();//获取连接

    public int Judge(String id,String password) throws SQLException {
        if(id==null||password==null)
            return 0;//为空返回0
        String sql="select * from GUEST_INFO ";
        Statement stmt=conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            String id1=rs.getString("id");
            String password1=rs.getString("password");
            if(id1.equals(id) && password1.equals(password))
                return 1;//验证成功返回1
        }
        return 2;//错误返回2

    }


}
