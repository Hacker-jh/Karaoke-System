package Gui;

import ORAconnect.oracle_connect;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.*;

public class Manager{

    oracle_connect Dbconn=new oracle_connect();
    Connection conn=Dbconn.get_Connection();
    public boolean insert(Person p){

        String sql;

        sql = "insert into workers(name,age,lv,phone,salary,is_pay,sex,photo,work_info) values (?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,p.pName);//name
            ps.setInt(2,p.pAge);
            ps.setString(3,p.plv);
            ps.setString(4,p.pPhone);
            ps.setFloat(5,p.pSalary);
            ps.setString(6,p.ispay);
            ps.setString(7,p.pSex);
            ps.setBlob(8,p.pPhoto);
            ps.setClob(9,p.worker_info);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    };

    public boolean update(Person p)
    {

        String sql = "update workers set name=?,age=?,lv=?,phone=?,salary=?,is_pay=?,sex=? where id=?";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,p.pName);//name
            ps.setInt(2,p.pAge);
            ps.setString(3,p.plv);
            ps.setString(4,p.pPhone);
            ps.setFloat(5,p.pSalary);
            ps.setString(6,p.ispay);
            ps.setString(7,p.pSex);
            //ps.setBlob(8,p.pPhoto);
            //ps.setClob(9,p.worker_info);
            ps.setInt(8,p.pId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return true;
    };

    public boolean updateSal(Person hp)
    {

        String sql = "update workers set salary=? where id=?";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setFloat(1,hp.pSalary);
            ps.setInt(2,hp.pId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return true;
    };

    public boolean delete_p(int id)
    {
        String sql="delete from workers where id =?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    };

    public Person searchById(int id)
    {
        Person p=new Person();
        String sql="select * from workers w where w.id="+String.valueOf(id);
        try {
            Statement stmt=conn.createStatement();
            stmt.execute(sql);
            ResultSet rs= stmt.getResultSet();
            if(rs.next())
            {
                p.pId=id;
                p.pName=rs.getString("NAME");
                p.pAge=rs.getInt("AGE");
                p.pSex=rs.getString("SEX");
                p.plv=rs.getString("LV");
                p.pPhone=rs.getString("PHONE");
                p.pSalary=rs.getFloat("SALARY");
                p.ispay=rs.getString("IS_PAY");
                p.pPhoto= rs.getBlob("PHOTO");
                p.worker_info=rs.getClob("WORK_INFO");
            }

            rs.close();stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return p;
    }

    public Person searchSal(int id)
    {
        Person p=new Person();
        String sql="select * from idNameOrSal w where w.id="+String.valueOf(id);
        try
        {
            Statement stmt=conn.createStatement();
            stmt.execute(sql);
            ResultSet rs= stmt.getResultSet();
            if(rs.next())
            {
                p.pId=id;
                p.pName=rs.getString("NAME");
                p.pSalary=rs.getFloat("SALARY");
                p.ispay=rs.getString("IS_PAY");
            }

            rs.close();stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }


    public boolean insertBlob(int id,String filepath)
    {
        PreparedStatement ps = null;

        String sql = "update  workers set photo=? where id=?";
        try {
            ps =conn.prepareStatement(sql);

        File file=new File(filepath);

        java.io.BufferedInputStream imageInput = new java.io.BufferedInputStream(
                new java.io.FileInputStream(file));


        ps.setBinaryStream(1, imageInput,(int) file.length());
        ps.setInt(2,id);
        ps.executeUpdate();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return true;
    };

    public boolean insertClob(int id,String text)
    {

        PreparedStatement ps = null;

        String sql = "update  workers set work_info=? where id=?";
        try {
            ps =conn.prepareStatement(sql);

            Reader clobReader = new StringReader(text); // 将 text转成流形式
            ps.setCharacterStream(1, clobReader, text.length());// 替换sql语句中的？
            System.out.println("manager:199::"+text+"  "+id);

            //ps.setClob(1,clob);
            ps.setInt(2,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;
    };


}
