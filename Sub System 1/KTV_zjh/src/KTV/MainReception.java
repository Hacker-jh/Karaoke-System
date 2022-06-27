package KTV;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class MainReception extends JDialog {
    private JPanel contentPane;
    private JLabel TITLE;
    private JButton ResButton;
    private JButton CancelButton;
    private JButton PayButton;
    private JButton checkRoomButton;

    public MainReception() {
        setContentPane(contentPane);
        contentPane.setPreferredSize(new Dimension(600,400));
        setModal(true);

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
        ResButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResUI ru=new ResUI();
                ru.show_it();
            }
        });
        CancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CancelUI cu=new CancelUI();
                cu.show_it();
            }
        });
        PayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PayUI pu=new PayUI();
                pu.show_it();
            }
        });
        checkRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowRoomUI shu= null;
                    shu = new ShowRoomUI();
                shu.show_it();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        MainReception dialog = new MainReception();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
