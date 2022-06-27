package control_gui;

import Dao.LoginValition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Panel1 {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //在主函数中，实例化Login类的对象，调用初始化界面的方法
        Panel1 login = new Panel1();
        login.initUI();

    }

    //在类中定义初始化界面的方法
    public void initUI() {
        //在initUI中实例化JFrame类的对象
        JFrame frame = new JFrame();
        //设置窗体对象的属性值
        frame.setTitle("KTV点餐系统");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(3);
        frame.setResizable(true);
        frame.setFont(new Font("宋体",Font.PLAIN,20));
        frame.setLayout(null);


        JLabel jb0=new JLabel("红浪漫KTV点餐系统");
        jb0.setFont(new Font("宋体",Font.PLAIN,30));
        jb0.setBounds(400, 10, 300, 60);
        frame.add(jb0);


        JLabel jb1=new JLabel("账号: ");
        jb1.setFont(new Font("宋体",Font.PLAIN,20));
        jb1.setBounds(250, 100, 80, 50);
        frame.add(jb1);

        JTextField jtf1=new JTextField(10);
        jtf1.setBounds(410, 100, 300, 50);
//        jtf1.setVisible(true);
//        jtf1.setBackground(new Color(192,192,192));
        frame.add(jtf1);


        JLabel jb2=new JLabel("密码: ");
        jb2.setFont(new Font("宋体",Font.PLAIN,20));
        jb2.setBounds(250, 200, 80, 50);
        frame.add(jb2);

        JTextField jtf2=new JTextField(10);
        jtf2.setBounds(410, 200, 300, 50);
        frame.add(jtf2);

        JButton button1=new JButton("登录");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s1=jtf1.getText();
                String s2=jtf2.getText();
                LoginValition lv=new LoginValition();
                int va= 0;
                try {
                    va = lv.Judge(s1, s2);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (va==1){
                    //
                    try {
                        Panel2 p2=new Panel2();
                        frame.setVisible(false);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
                else {
                    jtf1.setText("");
                    jtf2.setText("");
                    JOptionPane.showMessageDialog(null, "您的账号或密码可能出现错误，请重新输入！", "错误提示", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        button1.setBounds(300, 400, 100, 80);
        frame.add(button1);///////////////登录验证功能


        JButton button2=new JButton("重置");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtf1.setText("");
                jtf2.setText("");
            }
        });
        button2.setBounds(500, 400, 100, 80);
        frame.add(button2);

        frame.setVisible(true);
    }

}//登录页面
