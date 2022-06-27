package KTV;
import ORAconnect.oracle_connect;
import java.sql.*;

public class Manager{

    oracle_connect Dbconn=new oracle_connect();
    Connection conn=Dbconn.get_Connection();
}
