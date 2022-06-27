package KTV;

import HANDEL_RESFLAG.HandelFlag;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBox1;
    private JLabel label1;//可用房间ID
    private JTextField G_IDInput;
    private JLabel label2;//客户ID
    private JLabel label3;//可用时段表示
    private JButton ConfirmRoom;
    private JTable table;
    private JPanel panelT;
    private JTextField resTextField;
    private JLabel reslab;
    Manager man=new Manager();

    public ResUI() {


        Connection conn=man.conn;
        String sql="SELECT ID,RSIZE,PRICE FROM ROOM_INFO";
        try {
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql);

            while(rs.next())
            {
                comboBox1.addItem(rs.getString("ID"));
            }
            rs.close();
            stmt.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }


        setContentPane(contentPane);
        contentPane.setPreferredSize(new Dimension(450,600));
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ConfirmRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedRID=comboBox1.getSelectedItem().toString();


                Date day=new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-M-dd");
                String today=df.format(day);

                Connection conn=man.conn;
                String sql="SELECT ID,RESERVE_FLAG FROM SHOWRESERVE R WHERE R_ID=? AND TIME=?";
                try {
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,selectedRID);
                    pstmt.setString(2,today);//查询今日此房间预约信息
                    ResultSet rs=pstmt.executeQuery();
                    int pre=8000000;

                    while(rs.next())
                    {
                        pre=HandelFlag.put_together(pre,rs.getInt("RESERVE_FLAG"));

                    }
                    rs.close();
                    pstmt.close();

                    JScrollPane tableContainer = new JScrollPane();


                    final DefaultTableModel tableModel = new DefaultTableModel();

                    tableModel.getDataVector().clear();    //清除tableModel

                    table = new JTable(tableModel){
                        private static final long serialVersionUID = 1L;
                        public boolean isCellEditable(int row, int column){
                            if (column != 2) {
                                return false;
                            }
                            return autoCreateColumnsFromModel;
                        }
                    };


                    Object[] columnTitle = new Object[]{"时间段", "是否占用？"};//列名

                    Object[][] rowData = new Object[6][2];

                    int i=0;
                    while(pre>10){
                        if(pre%10==1)
                        rowData[i] = new Object[]{"时间段"+i, Boolean.TRUE};
                        else
                            rowData[i] = new Object[]{"时间段"+i, Boolean.FALSE};
                        pre=pre/10;
                        i++;
                    }
                    tableModel.setDataVector(rowData, columnTitle);
                    table.setModel(tableModel);

                    //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    table.getColumnModel().getColumn(0).setPreferredWidth(100);
                    table.getColumnModel().getColumn(1).setCellEditor(table.getDefaultEditor(Boolean.class));
                    table.getColumnModel().getColumn(1).setCellRenderer(table.getDefaultRenderer(Boolean.class));

                    DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
                    cr.setHorizontalAlignment(JTextField.CENTER);
                    table.getColumnModel().getColumn(0).setCellRenderer(cr);


                    tableContainer.setViewportView(table);    //这句很重要
                    //panelT.setLayout(null);
                    panelT.add(tableContainer);
                    table.repaint();
                    table.updateUI();
                    panelT.updateUI();

                    //panelT.add(table);
                } catch (SQLException exp) {
                    exp.printStackTrace();
                }






            }
        });
    }

    private void onOK() {
        // add your code here
        String res=resTextField.getText();
        String customId=G_IDInput.getText();
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-dd");
        String today=df.format(day);

        String[] strs = res.split(";");//根据，切分字符串
        int sum=8000000;
        for(int i = 0;i < strs.length; i++){

            if(table.getValueAt(Integer.parseInt(strs[i]), 1).toString().compareTo("true") == 0)
            {
                JOptionPane.showMessageDialog(null,"禁止重复预定！");
                return ;
            }
            switch(Integer.parseInt(strs[i]))
            {
                case 0:
                    sum=sum+1;
                    break;
                case 1:
                    sum=sum+10;
                    break;
                case 2:
                    sum=sum+100;
                    break;
                case 3:
                    sum=sum+1000;
                    break;
                case 4:
                    sum=sum+10000;
                    break;
                case 5:
                    sum=sum+100000;
                    break;
                default:
                    break;

            }

        }


        String sql="insert into RESERVE_INFO(G_ID,R_ID,TIME,RESERVE_FLAG,TOTAL_PRICE,DEPOSIT) VALUES(?,?,?,?,100,20)";

        try {
            PreparedStatement pstmt=man.conn.prepareStatement(sql);
            pstmt.setString(1,customId);
            pstmt.setString(2,comboBox1.getSelectedItem().toString());
            pstmt.setString(3,today);
            pstmt.setInt(4,sum);
            pstmt.execute();
            JOptionPane.showMessageDialog(null,"已预定！");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //dispose();
    }

    private void onCancel() {
        // add your code here if necessary

        dispose();
    }

    public void show_it( ) {
        ResUI dialog = new ResUI();
        dialog.pack();
        dialog.setVisible(true);

    }
    public static void main(String[] args) {
        ResUI dialog = new ResUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
