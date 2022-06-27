package KTV;

import oracle.jdbc.OracleTypes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class PayUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JTable table1;
    private JLabel total;
    private JLabel G_idInput;
    private JLabel RoomID_input;
    private JPanel jpanel2;
    private JPanel jpanel3;
    private JButton confirmB;
    Manager man=new Manager();

    public PayUI() {
        setContentPane(contentPane);
        contentPane.setPreferredSize(new Dimension(500,600));
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

        confirmB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String custId=textField1.getText();
                ArrayList<String> resid_arr=new ArrayList<String>();
                try {
                    String sql0="Select ID from RESGUESTROOMID where g_id=? AND r_id=?";
                    PreparedStatement ps= man.conn.prepareStatement(sql0);
                    ps.setString(1,custId);
                    ps.setString(2, textField2.getText());
                    ResultSet rs=ps.executeQuery();
                    while(rs.next())
                    {
                        resid_arr.add(rs.getString("id"));
                    }
                    rs.close();
                    ps.close();

                    JScrollPane tableContainer = new JScrollPane();

                    final DefaultTableModel tableModel = new DefaultTableModel();
                    tableModel.getDataVector().clear();    //清除tableModel
                    Object[] columnTitle = new Object[]{"订单编号", "待支付金额"};//列名
                    Object[][] rowData = new Object[resid_arr.size()+1][2];
                    rowData[0]=columnTitle;
                    int k=1;

                    for(int i=0;i<resid_arr.size();i++) {
                        String sql = "{?=call RoomIsPaid(?)}";
                        CallableStatement pstmt = man.conn.prepareCall(sql);
                        pstmt.setString(2, resid_arr.get(i));
                        pstmt.registerOutParameter(1, OracleTypes.FLOAT);
                        pstmt.execute();

                        if(Float.parseFloat(pstmt.getObject(1).toString())<=0)
                        {
                            resid_arr.remove(i);
                        }
                        else
                        {
                            rowData[k]=new Object[]{resid_arr.get(i),pstmt.getObject(1).toString()};
                            k++;
                        }
                        pstmt.close();
                    }

                    tableModel.setDataVector(rowData, columnTitle);
                    table1.setModel(tableModel);

                    tableContainer.setViewportView(table1);    //这句很重要
                    //panelT.setLayout(null);
                    jpanel3.add(tableContainer);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });
    }

    private void onOK() {
        // add your code here
        int row=table1.getSelectedRow();
        String resid=table1.getValueAt(row,0).toString();

        String sql="DELETE FROM RESERVE_INFO WHERE ID=?";
        PreparedStatement ps= null;
        try {
            ps = man.conn.prepareStatement(sql);
            ps.setString(1,resid);
            ps.execute();
            JOptionPane.showMessageDialog(null,"已结付！");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void show_it() {
        PayUI dialog = new PayUI();
        dialog.pack();
        dialog.setVisible(true);

    }

    public static void main(String[] args) {
        PayUI dialog = new PayUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
