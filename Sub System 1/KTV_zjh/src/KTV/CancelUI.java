package KTV;

import oracle.jdbc.OracleTypes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class CancelUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel G_idInput;
    private JPanel jpanel3;
    private JTable table1;
    private JButton Confirmbutton;
    Manager man=new Manager();


    public CancelUI() {
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
        Confirmbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String custId = textField1.getText();
                ArrayList<String> resid_arr = new ArrayList<String>();
                try {
                    String sql = "Select ID,R_ID,TIME,TOTAL_PRICE,DEPOSIT from RESERVE_INFO where g_id='"+custId+"'";
                    Statement stmt = man.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery(sql);
                    rs.last();
                    Object[][] rowData = new Object[rs.getRow()][4];

                    JScrollPane tableContainer = new JScrollPane();
                    final DefaultTableModel tableModel = new DefaultTableModel();
                    tableModel.getDataVector().clear();    //清除tableModel
                    Object[] columnTitle = new Object[]{"订单编号", "开房时间","房间编号", "应付金额"};//列名
                    //rowData[0] = columnTitle;
                    int k = 0;
                    rs.beforeFirst();
                    while (rs.next()) {
                        rowData[k++] = new Object[]{rs.getString("id"), rs.getString("time"), rs.getString("R_ID"),rs.getFloat("total_price") - rs.getFloat("deposit")};

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
            JOptionPane.showMessageDialog(null,"已取消！");
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
        CancelUI dialog = new CancelUI();
        dialog.pack();
        dialog.setVisible(true);

    }
    public static void main(String[] args) {
        CancelUI dialog = new CancelUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
