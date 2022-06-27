package Dao;

import Connect_to_database.oracle_connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowItems {
    oracle_connect orc=new oracle_connect();
    Connection conn=orc.get_Connection();//获取连接
    public Object[][] getItems() throws SQLException {
        Statement stmt=conn.createStatement();
        String sql="select * from items";
        ResultSet rs=stmt.executeQuery(sql);
        int row=rs.getRow();
        int i=0;
        Object [][]str=new String[row][4];
        while(i<row){
            str[i][0]=rs.getString("id");
//            System.out.println(obj[i][0]);
            str[i][1]=rs.getInt("amount");
            str[i][2]=rs.getString("name");
            str[i][3]=rs.getFloat("PRICE");
//            System.out.println(obj[i][0].toString());
            i++;
        };
        return str;
    }


}
