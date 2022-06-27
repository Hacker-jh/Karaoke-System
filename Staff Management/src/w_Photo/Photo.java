package w_Photo;

import Gui.Manager;
import Gui.Person;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Photo extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton selectPhotoButton;
    private JPanel Pic;
    private JLabel realPic;
    private String imgPath;
    private ImageIcon icon=null;
    private int id;
    int flag=0;
    int flag2=1;
    Manager man=new Manager();


    public Photo(int flagcur,Person p) {
        id=p.pId;
        flag2=flagcur;
        p=man.searchById(id);
        realPic=new JLabel();
        Pic.add(realPic);
        setContentPane(contentPane);
        contentPane.setPreferredSize(new Dimension(350, 420));
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Blob pic = p.pPhoto;
        if(p.pPhoto!=null) {

            Date day=new Date();
            SimpleDateFormat df = new SimpleDateFormat("MM-dd_HH@mm@ss");
            System.out.println(df.format(day));

            String filepath = "./" + df.format(day) + ".jpg";
            System.out.println("输出文件路径为:" + filepath);
            try {
                InputStream in = pic.getBinaryStream(); // 建立输出流
                FileOutputStream file = new FileOutputStream(filepath);
                int len = (int) pic.length();
                byte[] buffer = new byte[len]; // 建立缓冲区
                while ((len = in.read(buffer)) != -1) {
                    file.write(buffer, 0, len);
                }
                file.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("I/O Exception.");
            }

            icon = new ImageIcon(filepath);
            icon.setImage(icon.getImage().getScaledInstance(210,280, Image.SCALE_DEFAULT));
            realPic.setIcon(icon);
            }



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
        selectPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==selectPhotoButton)
                {
                    JFileChooser jfc=new JFileChooser();
                    jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );
                    jfc.showDialog(new JLabel(), "选择");
                    File file=jfc.getSelectedFile();
                    if(file.isFile()){
                        System.out.println("文件:"+file.getAbsolutePath());
                        imgPath=file.getAbsolutePath();
                        BufferedImage img = null;

                        try {
                            img = ImageIO.read(new File(imgPath));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        ImageIcon icon = new ImageIcon(img);
                        icon.setImage(icon.getImage().getScaledInstance(180,240, Image.SCALE_DEFAULT));

                        realPic.setIcon(icon);
                        contentPane.updateUI();
                        flag=1;

                    }else
                    {
                        System.out.println("???"+file.getAbsolutePath());

                    }
                    System.out.println(jfc.getSelectedFile().getName());
                }
            }
        });
    }

    private void onOK() {
        // add your code here

        if (flag==1&&flag2==1) {


            man = new Manager();
            man.insertBlob(id, imgPath);
            flag=0;
        }else
        {

            if(flag2==0)
            JOptionPane.showMessageDialog(null,"此处无法更改，请移步修改界面！！");
            else
            {
                JOptionPane.showMessageDialog(null,"未选择文件");
            }
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void show_it(int flag,Person p)
    {

        Photo dialog = new Photo(flag,p);
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }

    public static void main(String[] args) {

    }
}
