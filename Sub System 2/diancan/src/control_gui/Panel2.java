package control_gui;

import Connect_to_database.oracle_connect;
import entity.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Panel2 {
    private ArrayList<Item> shopcar=new ArrayList<Item>();
    Panel2() throws SQLException {
        oracle_connect orc=new oracle_connect();
        Connection conn=orc.get_Connection();//获取连接

        JFrame frame=new JFrame();
        Container contentPane=frame.getContentPane();
        frame.setTitle("商品页面");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(3);
        frame.setResizable(true);
        frame.setFont(new Font("宋体",Font.PLAIN,20));
        frame.setLayout(new BorderLayout());
        String[] columnNames={"ID","剩余量","名字","价格"};
        Statement stmt=conn.createStatement();
        String sql="select * from items";
        ResultSet rs=stmt.executeQuery(sql);


        int row=0;
        while(rs.next())
            row++;
        Object [][]str=new Object[row][4];
        int h=0;
        rs=stmt.executeQuery(sql);
        while(rs.next()){
            str[h][0]=rs.getString("id");
            str[h][1]=rs.getInt("amount");
            str[h][2]=rs.getString("name");
            str[h][3]=rs.getFloat("PRICE");
            h++;
        };





        JTable jt =new JTable(str,columnNames);
        jt.setPreferredScrollableViewportSize(new Dimension(1000,300));
        contentPane.add(new JScrollPane(jt),BorderLayout.CENTER);

        Panel pl=new Panel();
        JLabel label1=new JLabel("请输入商品编号：");
        label1.setFont(new Font("宋体",Font.PLAIN,15));
        label1.setBounds(50, 500, 150, 20);
        pl.add(label1);
        JTextField jtf1=new JTextField(10);//输入商品编号
        jtf1.setBounds(220, 500, 200, 20);
        pl.add(jtf1);

        JLabel label2=new JLabel("请输入购买数量：");
        label2.setFont(new Font("宋体",Font.PLAIN,15));
        label2.setBounds(50, 550, 150, 20);
        pl.add(label2);
        JTextField jtf2=new JTextField(4);//输入购买数量
        jtf2.setBounds(220, 550, 200, 20);
        pl.add(jtf2);

        JButton button1=new JButton("提交");//提交
        button1.setBounds(500, 400, 100, 80);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s1=jtf1.getText().toString();
//                System.out.println(s1);
                String s2=jtf2.getText().toString();
                if(s1.equals("")||s2.equals("")){
                    JOptionPane.showMessageDialog(null, "您的输入可能存在空值", "错误提示",JOptionPane.ERROR_MESSAGE);
                }
                try {
                    if(Judge(s1)==0){
                        JOptionPane.showMessageDialog(null, "您的输入可能不合法", "错误提示",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //排除输入不合法


                try {
                    Item it1=chaxun(s1,s2);
                    shopcar.add(it1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }



            }
        });
        pl.add(button1);
        JButton button2=new JButton("查看购物车");//购物车
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(shopcar.isEmpty()){
                    JOptionPane.showMessageDialog(null, "您的购物车为空", "错误提示",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    JFrame frame1=new JFrame();
                    frame1.setTitle("购物车");
                    frame1.setSize(500, 300);
                    frame1.setDefaultCloseOperation(3);
                    frame1.setResizable(true);
                    frame1.setFont(new Font("宋体",Font.PLAIN,14));
                    frame1.setLayout(new BorderLayout());
                    String[] coulnname={"ID","剩余量","名字","价格","点餐数目"};
                    Container contentPane1=frame1.getContentPane();
                    int row1=shopcar.size();
                    Object[][] ooo=new Object[row1][5];
                    for(int m=0;m<row1;m++) {
                        ooo[m][0] = shopcar.get(m).getID();
                        ooo[m][1] = shopcar.get(m).getAmount();
                        ooo[m][2] = shopcar.get(m).getName();
                        ooo[m][3] = shopcar.get(m).getPrice();
                        ooo[m][4] = shopcar.get(m).getorder();
                    }

                    JTable jt1 =new JTable(ooo,coulnname);
                    jt1.setPreferredScrollableViewportSize(new Dimension(500,250));
                    contentPane1.add(new JScrollPane(jt1),BorderLayout.CENTER);

                    frame1.setVisible(true);


                }
            }
        });
        button2.setBounds(550, 450, 100, 80);
        pl.add(button2);






        JButton button3=new JButton("支付");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame2=new JFrame();
                frame2.setTitle("支付页面");
                frame2.setSize(1000, 700);
                frame2.setDefaultCloseOperation(3);
                frame2.setResizable(true);
                frame2.setFont(new Font("宋体",Font.PLAIN,14));
                frame2.setLayout(new BorderLayout());
                Container contentPane2=frame2.getContentPane();


                JLabel label1=new JLabel();
                ImageIcon img1=new ImageIcon("src/picture/picture1.jpg");
                Image img11=img1.getImage();
                img11 = img11.getScaledInstance(400, 600, Image.SCALE_DEFAULT);
                img1.setImage(img11);
                label1.setIcon(img1);
                label1.setBounds(200, 300, 50, 100);
                contentPane2.add(label1,BorderLayout.WEST);

                String zhifu="您本次点餐共消费"+jisuan()+"元";
                JLabel label2=new JLabel(zhifu);
                label2.setBounds(400, 300, 50, 100);
                contentPane2.add(label2,BorderLayout.CENTER);


//                JLabel label3=new JLabel("请您扫描左边二维码进行付款，祝您玩得愉快！");
//                label2.setBounds(400, 400, 50, 100);
//                contentPane2.add(label3);








                frame2.setVisible(true);


            }
        });
        button2.setBounds(65, 450, 100, 80);
        pl.add(button3);

        contentPane.add(pl,BorderLayout.SOUTH);









        frame.setVisible(true);

    }

    public static void main(String[] args) throws SQLException {
        Panel2 p2=new Panel2();
    }

    public int Judge(String str) throws SQLException {
        oracle_connect orc = new oracle_connect();
        Connection conn = orc.get_Connection();//获取连接

        String sql="select id from items";
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()){
            String s=rs.getString("id");
            if(s.equals(str))
                return 1;
        }
        return 0;
    }
    public Item chaxun(String id, String order) throws SQLException {
        oracle_connect orc = new oracle_connect();
        Connection conn = orc.get_Connection();//获取连接
        String sql="select * from items where id="+id;
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()) {
            String s = rs.getString("id");
            int amount=rs.getInt("amount");
            String name = rs.getString("name");
            String price = rs.getString("price");
            int order1=Integer.parseInt(order);
            Item it=new Item(s,amount,name,price,order1);
            return it;
        }
        return null;
    }

    public float jisuan(){
        int row=shopcar.size();
        float sum=0;
        for(int i=0;i<row;i++){
            String s1=shopcar.get(i).getPrice();
            int s2=shopcar.get(i).getorder();
            float s=Float.valueOf(s1);
            sum=sum+s*s2;
        }
        return sum;
    }



}
