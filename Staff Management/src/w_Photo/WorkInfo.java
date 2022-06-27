package w_Photo;

import Gui.Manager;
import Gui.Person;
import oracle.jdbc.rowset.OracleSerialClob;
import oracle.sql.CLOB;

import javax.sql.rowset.serial.SerialClob;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.SQLException;

public class WorkInfo extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea;
    Clob curClob=null;
    int id;
    int flag=1;
    Manager man= new Manager();

    public String clobToString(Clob clob){
        String re = "";
        Reader is = null;
        BufferedReader br = null;
        try {
            // 得到流
            is = clob.getCharacterStream();
            br = new BufferedReader(is);
            String s = br.readLine();
            StringBuffer sb = new StringBuffer();
            // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            while (s != null) {
                sb.append(s);
                s = br.readLine();
            }
            re = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return re;
    }



    public WorkInfo(int flag,int id) {
        Person p=man.searchById(id);
        this.id=p.pId;
        this.flag=flag;
        curClob=p.worker_info;

        //textArea.setPreferredSize(new Dimension(300, 360));


        setContentPane(contentPane);
        contentPane.setPreferredSize(new Dimension(600, 800));
        textArea.setText(clobToString(p.worker_info));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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

    private void onOK() throws SQLException {
        // add your code here

        if(flag==1)
        man.insertClob(this.id,textArea.getText());
        else
        {
            JOptionPane.showMessageDialog(null,"此处不可更改，请移步修改界面！！");
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void show_it(int flag,int id){
        WorkInfo dialog = new WorkInfo(flag,id);
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }
}
