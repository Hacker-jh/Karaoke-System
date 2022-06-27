package KTV;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowRoomUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table1;
    private JPanel jpanel2;
    Manager man=new Manager();

    public ShowRoomUI() {
        try {

            setContentPane(contentPane);
            contentPane.setPreferredSize(new Dimension(450,600));
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        JScrollPane tableContainer = new JScrollPane();
        final DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.getDataVector().clear();    //清除tableModel
        Object[] columnTitle = new Object[]{"房间id", "房间大小","开房价格"};//列名

        Statement stmt= null;
        stmt = this.man.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        String sql="select ID,RSIZE,PRICE from ROOM_INFO";
        ResultSet rs=stmt.executeQuery(sql);
        rs.last();
        Object[][] rowData = new Object[rs.getRow()+1][3];
        rowData[0]=columnTitle;

        int i=1;
        rs.beforeFirst();
        while(rs.next()){
            rowData[i] = new Object[]{rs.getString("ID"), rs.getInt("RSIZE"),rs.getFloat("PRICE")};
            i++;

        }
        rs.close();
        stmt.close();

        tableModel.setDataVector(rowData, columnTitle);
        table1.setModel(tableModel);

            tableContainer.setViewportView(table1);    //这句很重要
            //panelT.setLayout(null);
            jpanel2.add(tableContainer);
           // rowData[0]=columnTitle;
            //table1=new JTable(rowData,columnTitle);

    } catch (SQLException e) {
        e.printStackTrace();
    }


        jpanel2.add(table1);
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
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void show_it() {
        ShowRoomUI dialog = new ShowRoomUI();
        dialog.pack();
        dialog.setVisible(true);

    }
    public static void main(String[] args) {
        ShowRoomUI dialog = new ShowRoomUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
