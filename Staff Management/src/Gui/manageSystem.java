package Gui;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JComboBox;
import w_Photo.Photo;
import w_Photo.WorkInfo;


public class manageSystem {
    boolean packFrame = false;
    public manageSystem()
    {
        GUIMain frame = new GUIMain();
        if(packFrame){
            frame.pack();
        }
        else{
            frame.validate();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if(frameSize.height > screenSize.height){
            frameSize.height = screenSize.height;
        }
        if(frameSize.width > screenSize.width){
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width)/2,(screenSize.height - frameSize.height)/2);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new manageSystem();
    }
}



class GUIMain extends JFrame implements TreeSelectionListener{
    private static final long serialVersionUID = 1L;
    Dimension faceSize  = new Dimension(1000,600);
    Image icon = null;
    JTree tree = null;
    DefaultMutableTreeNode root = null;//人事管理系统
    DefaultMutableTreeNode InfoNode = null;//人员基本信息维护
    DefaultMutableTreeNode SalNode = null;//薪资管理
    TreePath treePath = null;


    public static JSplitPane splitPane = new JSplitPane();
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JLabel welcome = new JLabel();
    JScrollPane scrollPane;

    Manager man  = new Manager();

    //程序构造函数
    public GUIMain(){
        this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setSize(faceSize);
        this.setTitle("红浪漫KTV员工管理系统");
        icon = new ImageIcon("./beijing.jpeg").getImage();
        this.setIconImage(icon);
        this.setResizable(false);
        try{
            this.Init();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void Init() throws Exception{
        this.root = new DefaultMutableTreeNode("员工管理系统");
        this.InfoNode = new DefaultMutableTreeNode("基本信息管理");
        this.SalNode = new DefaultMutableTreeNode("工资管理");
        //人员基本信息
        root.add(InfoNode);
        InfoNode.add(new DefaultMutableTreeNode("添加人员信息"));
        InfoNode.add(new DefaultMutableTreeNode("修改人员信息"));
        InfoNode.add(new DefaultMutableTreeNode("删除人员信息"));
        InfoNode.add(new DefaultMutableTreeNode("查询人员信息"));
        //劳资管理
        root.add(SalNode);
        SalNode.add(new DefaultMutableTreeNode("劳资分配管理"));
        SalNode.add(new DefaultMutableTreeNode("工资结付"));




        tree = new JTree(root);
        scrollPane = new JScrollPane(tree);
        scrollPane.setPreferredSize(new Dimension(300,600));
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel1.add(scrollPane);
        welcome.setText("红浪漫KTV管理系统");
        welcome.setFont(new Font("Dialog",0,30));
        panel3.add(welcome);

        splitPane.setOneTouchExpandable(false);
        splitPane.setContinuousLayout(true);
        splitPane.setPreferredSize(new Dimension(320,600));
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(panel1);
        splitPane.setRightComponent(panel3);
        splitPane.setDividerSize(2);
        splitPane.setDividerLocation(161);
        //生成主界面

        tree.addTreeSelectionListener(this);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);//坚听到关闭时候直接退出虚拟机
            }
        });
        this.setContentPane(splitPane);
        this.setVisible(false);
        this.setVisible(true);
    }

    @Override
    public void valueChanged(TreeSelectionEvent tse) {
        DefaultMutableTreeNode dnode = (DefaultMutableTreeNode)tse.getPath().getLastPathComponent();
        System.out.println("dnode="+dnode);
        String node_str = dnode.toString();
        if(node_str == "员工管理系统"){
            splitPane.setRightComponent(panel3);
        }
        //信息树
        else if(node_str == "基本信息管理"){

            treePath = new TreePath(InfoNode.getPath());
            if(tree.isExpanded(treePath))
                tree.collapsePath(treePath);
            else
                tree.expandPath(treePath);
        }
        else if(node_str == "添加人员信息"){
            AddWorkerPanel AddWorkerPanel = new AddWorkerPanel(man);
            splitPane.setRightComponent(AddWorkerPanel);
        }
        else if(node_str == "修改人员信息"){
            ModifyWorkerPanel ModifyWorkerPanel = new ModifyWorkerPanel(man);
            splitPane.setRightComponent(ModifyWorkerPanel);
        }
        else if(node_str == "删除人员信息"){
            DelWorkerPanel DelWorkerPanel = new DelWorkerPanel(man);
            splitPane.setRightComponent(DelWorkerPanel);
        }
        else if(node_str == "查询人员信息"){
            QueryWorkerPanel QueryWorkerPanel = new QueryWorkerPanel(man);
            splitPane.setRightComponent(QueryWorkerPanel);
        }
        //劳资管理树
        else if(node_str == "劳资管理"){
            //当选中后展开或关闭叶子节点
            treePath = new TreePath(SalNode.getPath());
            if(tree.isExpanded(treePath))
                tree.collapsePath(treePath);
            else
                tree.expandPath(treePath);
        }
        else if(node_str == "劳资分配管理"){
            SalOperatePanel SalOperatePanel = new SalOperatePanel(man);
            splitPane.setRightComponent(SalOperatePanel);
        }
        else if(node_str == "工资结付"){
            SalPaidPanel SalPaidPanel = null;
            try {
                SalPaidPanel = new SalPaidPanel(man);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            splitPane.setRightComponent(SalPaidPanel);
        }

    }
}

class search_node
{
    int B_type=1;//0
    JLabel search = new JLabel("搜索");
    JLabel pId = new JLabel("员工编号");         //员工编号
    JLabel pName = new JLabel("员工姓名");       //员工姓名
    JLabel pSex = new JLabel("员工性别");        //员工性别
    JLabel pAge = new JLabel("员工年龄");        //员工性别
    JLabel plv = new JLabel("员工等级");      //出生年月
    JLabel pPhone = new JLabel("联系方式");         //所属部门编号
    JLabel pPhoto = new JLabel("员工照片");      //出生年月
    JLabel pWorkInfo = new JLabel("员工考核信息");         //所属部门编号
    JLabel pSalary = new JLabel("工资");     //工资
    JLabel pIspay = new JLabel("是否结清工资");     //是否考核

    JTextField search_ = new JTextField(10);
    JTextField pId_ = new JTextField(10);         //员工编号
    JTextField pName_ = new JTextField(10);       //员工姓名
    JComboBox pSex_ = new JComboBox();        //员工性别
    JTextField pAge_=new JTextField(10);
    JComboBox plv_ = new JComboBox();        //员工性别
    JTextField pPhone_ = new JTextField(10);         //电话号
    JButton pPhoto_=new JButton("查看照片");
    JTextField pSalary_ = new JTextField(10);     //工资
    JButton pWorkInfo_=new JButton("查看员工详细状况");


    JComboBox pIspay_ = new JComboBox();        //员工性别

     public search_node()
     {
         pPhoto_.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e)
             {
                     Person pnow = new Person();
                     pnow.pId = Integer.parseInt(pId_.getText());
                     //pnow.pPhoto=pblob;
                     Photo.show_it(B_type,pnow);

             }
         });
         pWorkInfo_.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e)
             {
                     Person pnow = new Person();
                     pnow.pId = Integer.parseInt(pId_.getText());
                     //pnow.pPhoto=pblob;
                     WorkInfo.show_it(B_type,pnow.pId);

             }
         });
     }

    public JPanel add_msg(JPanel msg)
    {
        msg.setBounds(100,20,400,350);
        msg.setLayout(new GridLayout(12,2,-100,10));

        pSex_.addItem("男");//0
        pSex_.addItem("女");//1
        plv_.addItem("普通员工");//0
        plv_.addItem("部门副经理");//1
        plv_.addItem("部门经理");//2
        plv_.addItem("总经理");
        plv_.addItem("董事长");
        pIspay_.addItem("已结清");
        pIspay_.addItem("未结清");

        msg.add(search);msg.add(search_);search.setForeground(Color.RED);
        msg.add(pId);msg.add(pId_);
        msg.add(pName);msg.add(pName_);
        msg.add(pSex);msg.add(pSex_);
        msg.add(pAge);msg.add(pAge_);
        msg.add(plv);msg.add(plv_);
        msg.add(pPhone);msg.add(pPhone_);
        msg.add(pPhoto);msg.add(pPhoto_);
        msg.add(pWorkInfo);msg.add(pWorkInfo_);
        msg.add(pSalary);msg.add(pSalary_);
        msg.add(pIspay);msg.add(pIspay_);
        return msg;
    }
    public void set_info(Person person)
    {
        this.pId_.setText(Integer.toString(person.pId));
        this.pName_.setText(person.pName);

        this.pSex_.setSelectedItem(person.pSex);

        this.pAge_.setText(String.valueOf(person.pAge));
        this.plv_.setSelectedItem(person.plv);
        this.pPhone_.setText(person.pPhone);
        //this.pblob=person.pPhoto;
        //this.pclob=person.worker_info;
        //Blob
        //Clob
        this.pSalary_.setText(Float.toString(person.pSalary));
        this.pIspay_.setSelectedItem(person.ispay);
    }
}

//添加人员信息
class AddWorkerPanel extends JPanel implements ActionListener{
    private static final long serialVersionUID = 1L;
   // JLabel pId = new JLabel("员工编号");         //员工编号 为了使用触发器和序列
    JLabel pName = new JLabel("员工姓名");       //员工姓名
    JLabel pSex = new JLabel("员工性别");        //员工性别
    JLabel pAge = new JLabel("员工年龄");        //员工性别
    JLabel plv = new JLabel("员工等级");      //出生年月
    JLabel pPhone = new JLabel("联系方式");         //所属部门编号
    JLabel pPhoto = new JLabel("员工照片");      //出生年月
    JLabel pWorkInfo = new JLabel("员工考核信息");         //所属部门编号
    JLabel pSalary = new JLabel("工资");     //工资
    JLabel pIspay = new JLabel("是否结清工资");     //是否考核

  //  JTextField pId_ = new JTextField(10);         //员工编号
    JTextField pName_ = new JTextField(10);       //员工姓名
    JComboBox pSex_ = new JComboBox();        //员工性别
    JTextField pAge_=new JTextField(10);
    //JTextField plv_ = new JTextField(10);      //等级
    JComboBox plv_ = new JComboBox();        //员工性别
    JTextField pPhone_ = new JTextField(10);         //电话号
    //************************************************************
    JTextField pPhoto_ = new JTextField(10);      //照片


    JTextField pWorkInfo_ = new JTextField(10);         //考核信息
    //************************************************************

    JTextField pSalary_ = new JTextField(10);     //工资
    //JTextField pIspay_ = new JTextField(10);     //是否考核
    JComboBox pIspay_ = new JComboBox();
    JPanel msg = new JPanel();
    JButton ad = new JButton("添加");

    Manager man = null;

    public AddWorkerPanel(Manager man){
        this.man = man;
        this.setLayout(null);
        msg.setBounds(100,20,400,300);
        msg.setLayout(new GridLayout(10,2,-100,10));
      //  msg.add(pId);msg.add(pId_);

        pSex_.addItem("男");
        pSex_.addItem("女");
        plv_.addItem("普通员工");
        plv_.addItem("部门副经理");
        plv_.addItem("部门经理");
        plv_.addItem("总经理");
        plv_.addItem("董事长");
        pIspay_.addItem("已结清");
        pIspay_.addItem("未结清");

        msg.add(pName);msg.add(pName_);
        msg.add(pSex);msg.add(pSex_);
        msg.add(pAge);msg.add(pAge_);
        msg.add(plv);msg.add(plv_);
        msg.add(pPhone);msg.add(pPhone_);
        msg.add(pPhoto);msg.add(pPhoto_);
        msg.add(pWorkInfo);msg.add(pWorkInfo_);
        msg.add(pSalary);msg.add(pSalary_);
        msg.add(pIspay);msg.add(pIspay_);

        ad.setBounds(250,350,250,60);
        this.add(msg);this.add(ad);
        this.ad.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {//向数据库录入一条员工信息
        if (e.getSource() == ad){
            Person person = new Person();
         //   person.pId=Integer.parseInt(pId_.getText());
            person.pName=pName_.getText();
            //*****************************************

            person.pSex=pSex_.getSelectedItem().toString();
            person.plv=plv_.getSelectedItem().toString();

            //*****************************************
            person.pAge=Integer.parseInt(pAge_.getText());
            person.pPhone=pPhone_.getText();
            //person.pPhoto=pPhoto_.;
            //person.worker_info
            person.pSalary=Float.parseFloat(pSalary_.getText());
            person.ispay=pIspay_.getSelectedItem().toString();

            //man.append(person);//
            //*************************************************************
            //*************************************************************
            if (man.insert(person)){
                JOptionPane.showMessageDialog(this,person.pName+"添加成功");
            }else{
                JOptionPane.showMessageDialog(this,person.pName+"添加失败");
            }
        }
    }
}

//修改人员信息
class ModifyWorkerPanel extends JPanel implements KeyListener,ActionListener{
    private static final long serialVersionUID = 1L;
    search_node s_p=new search_node();

    JPanel msg =new JPanel();

    JButton ad = new JButton("修改");

    Person person = null;

    Manager man = null;
    public ModifyWorkerPanel(Manager man){
        this.man = man;
        this.setLayout(null);
        s_p.add_msg(msg);
        ad.setBounds(250,400,250,60);
        this.add(msg);this.add(ad);
        this.ad.addActionListener(this);
        s_p.search_.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {//修改人员信息
        if (keyEvent.getKeyChar() == KeyEvent.VK_ENTER){
            Person person = null;
            if((person = man.searchById(Integer.parseInt(s_p.search_.getText())))!= null){////修改人员信息
                this.person = person;
                s_p.set_info(person);

            }else{
                JOptionPane.showMessageDialog(this,"没有该员工");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ad){
            Person person = new Person();
            person.pId=Integer.parseInt(s_p.pId_.getText());
            person.pName=s_p.pName_.getText();
            person.pSex=s_p.pSex_.getSelectedItem().toString();
            person.plv=s_p.plv_.getSelectedItem().toString();
            person.pAge=Integer.parseInt(s_p.pAge_.getText());
            person.pPhone=s_p.pPhone_.getText();

            //person.pPhoto=this.person.pPhoto;
            //person.worker_info=this.person.worker_info;

            person.pSalary=Float.parseFloat(s_p.pSalary_.getText());
            person.ispay=s_p.pIspay_.getSelectedItem().toString();



            if (this.person != null){
                //man.modify(this.person,person);
                if (man.update(person)){
                    this.person=man.searchById(person.pId);
                    JOptionPane.showMessageDialog(this,this.person.pName+"修改成功");
                }else{
                    JOptionPane.showMessageDialog(this,this.person.pName+"修改失败");
                }
            }else{
                JOptionPane.showMessageDialog(this,"没有员工");
            }


        }


    }
}

//删除人员信息
class DelWorkerPanel extends JPanel implements KeyListener,ActionListener{
    private static final long serialVersionUID = 1L;
    /*
    JLabel search = new JLabel("搜索");
    JLabel pId = new JLabel("员工编号");         //员工编号
    JLabel pName = new JLabel("员工姓名");       //员工姓名
    JLabel pSex = new JLabel("员工性别");        //员工性别
    JLabel pAge = new JLabel("员工年龄");        //员工性别
    JLabel plv = new JLabel("员工等级");      //出生年月
    JLabel pPhone = new JLabel("联系方式");         //所属部门编号
    JLabel pPhoto = new JLabel("员工照片");      //出生年月
    JLabel pWorkInfo = new JLabel("员工考核信息");         //所属部门编号
    JLabel pSalary = new JLabel("工资");     //工资
    JLabel pIspay = new JLabel("是否结清工资");     //是否考核

    JTextField search_ = new JTextField(10);
    JTextField pId_ = new JTextField(10);         //员工编号
    JTextField pName_ = new JTextField(10);       //员工姓名
    JTextField pSex_ = new JTextField(10);        //员工性别
    JTextField pAge_=new JTextField(10);
    JTextField plv_ = new JTextField(10);      //等级
    JTextField pPhone_ = new JTextField(10);         //电话号
    JTextField pPhoto_ = new JTextField(10);      //照片
    JTextField pWorkInfo_ = new JTextField(10);         //考核信息
    JTextField pSalary_ = new JTextField(10);     //工资
    JTextField pIspay_ = new JTextField(10);     //是否考核
*/
    search_node s_p=new search_node();
    JPanel msg = s_p.add_msg(new JPanel());

    JButton ad = new JButton("删除");

    Person person = null;

    Manager man = null;
    public DelWorkerPanel(Manager man){
        this.man = man;
        s_p.B_type=0;
        this.setLayout(null);

        //s_p.add_msg(msg);

        ad.setBounds(250,400,250,60);
        this.add(msg);this.add(ad);
        this.ad.addActionListener(this);
        s_p.search_.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == KeyEvent.VK_ENTER){
            Person person = null;
            if((person = man.searchById(Integer.parseInt(s_p.search_.getText())))!= null){////修改人员信息
                //*****************************************
                this.person = person;
                //*****************************************
               s_p.set_info(person);
            }else{
                JOptionPane.showMessageDialog(this,"找不到该员工");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ad){
            if (this.person != null){
                if ( man.delete_p(this.person.pId)){
                    JOptionPane.showMessageDialog(this,this.person.pName+"删除成功");
                }else{
                    JOptionPane.showMessageDialog(this,this.person.pName+"删除失败");
                }
            }else{
                JOptionPane.showMessageDialog(this,"没有员工");
            }


        }
    }
}

//查询人员信息
class QueryWorkerPanel extends JPanel  implements KeyListener{
    private static final long serialVersionUID = 1L;
   /* JLabel search = new JLabel("搜索");
    JLabel pId = new JLabel("员工编号");         //员工编号
    JLabel pName = new JLabel("员工姓名");       //员工姓名
    JLabel pSex = new JLabel("员工性别");        //员工性别
    JLabel pAge = new JLabel("员工年龄");        //员工性别
    JLabel plv = new JLabel("员工等级");      //出生年月
    JLabel pPhone = new JLabel("联系方式");         //所属部门编号
    JLabel pPhoto = new JLabel("员工照片");      //出生年月
    JLabel pWorkInfo = new JLabel("员工考核信息");         //所属部门编号
    JLabel pSalary = new JLabel("工资");     //工资
    JLabel pIspay = new JLabel("是否结清工资");     //是否考核

    JTextField search_ = new JTextField(10);
    JTextField pId_ = new JTextField(10);         //员工编号
    JTextField pName_ = new JTextField(10);       //员工姓名
    JTextField pSex_ = new JTextField(10);        //员工性别
    JTextField pAge_=new JTextField(10);
    JTextField plv_ = new JTextField(10);      //等级
    JTextField pPhone_ = new JTextField(10);         //电话号
    JTextField pPhoto_ = new JTextField(10);      //照片
    JTextField pWorkInfo_ = new JTextField(10);         //考核信息
    JTextField pSalary_ = new JTextField(10);     //工资
    JTextField pIspay_ = new JTextField(10);     //是否考核
*/
    search_node s_p=new search_node();
    JPanel msg = s_p.add_msg(new JPanel());

    Person person = null;

    Manager man = null;
    public QueryWorkerPanel(Manager man){
        this.man = man;
        s_p.B_type=0;
        this.setLayout(null);

        this.add(msg);
        s_p.search_.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == KeyEvent.VK_ENTER){
            Person person = null;
            if((person = man.searchById(Integer.parseInt(s_p.search_.getText())))!= null){////修改人员信息
                //*****************************************
                this.person = person;
                //*****************************************
               s_p.set_info(person);
            }else{
                JOptionPane.showMessageDialog(this,"找不到该员工");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

//劳资分配管理F
class SalOperatePanel extends JPanel implements KeyListener,ActionListener{
    private static final long serialVersionUID = 1L;
    JLabel search = new JLabel("搜索");
    JTextField search_ = new JTextField(10);

    JLabel part = new JLabel("现有月工资()");
    JLabel part_show = new JLabel();

    JLabel part_new = new JLabel("修改后");
    JTextField part_to = new JTextField(10);
    JPanel msg = new JPanel();

    JButton ad = new JButton("确认修改");

    Person person = null;

    Manager man = null;
    public SalOperatePanel(Manager man){
        this.man = man;
        this.setLayout(null);
        msg.setBounds(100,150,400,150);
        msg.setLayout(new GridLayout(3,2,-100,10));
        msg.add(search);msg.add(search_);msg.add(part);msg.add(part_show);msg.add(part_new);msg.add(part_to);
        this.add(msg);
        ad.setBounds(250,400,250,60);
        this.add(msg);this.add(ad);
        this.ad.addActionListener(this);
        search_.addKeyListener(this);
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == KeyEvent.VK_ENTER){
            Person halfPerson = null;
            if((halfPerson = man.searchSal(Integer.parseInt(search_.getText())))!= null){
                this.person = halfPerson;
                this.part_show.setText(Float.toString(person.pSalary));
            }else{
                JOptionPane.showMessageDialog(this,"找不到该员工");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ad){
            if (this.person != null){
                Person person = new Person(this.person);
                person.pSalary = Float.parseFloat(part_to.getText());
                if (man.updateSal(person)){
                    JOptionPane.showMessageDialog(this,this.person.pName+"工资变动成功");
                }else{
                    JOptionPane.showMessageDialog(this,this.person.pName+"工资变动失败");
                }
            }else{
                JOptionPane.showMessageDialog(this,"没有员工");
            }


        }
    }
}

//结清工资：：先查看所有未结清的工人工资。再选择对某些进行工资结付。
class SalPaidPanel extends JPanel{
    private ArrayList<Person> shopcar=new ArrayList<Person>();
    DefaultTableModel obj = null;

    Manager man=null;
    public SalPaidPanel(Manager man) throws SQLException {
        this.man=man;

        final JFrame jf = new JFrame("请确认");
        jf.setSize(705, 450);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setResizable(false);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch (Exception e1) {
            e1.printStackTrace();
        }

        JPanel jp = new JPanel();

        JScrollPane jscrollpane = new JScrollPane();

        final DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.getDataVector().clear();    //清除tableModel

        final JTable table = new JTable(tableModel){
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column){
                if (column != 4) {
                    return false;
                }
                return autoCreateColumnsFromModel;
            }
        };

        Object[] columnTitle = new Object[]{"ID", "姓名", "SALARY", "ISPAY", "是否现在结清？"};//列名




        Statement stmt=this.man.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        String sql="select ID,NAME,SALARY,IS_PAY from WORKERS  w where w.IS_PAY='未结清'";
        ResultSet rs=stmt.executeQuery(sql);
        rs.last();
        Object[][] rowData = new Object[rs.getRow()][4];

        rs.beforeFirst();
        int i=0;
        while(rs.next()){
            String[] str = new String[4];
            str[0] = rs.getString("ID");
            str[1] = rs.getString("NAME");
            str[2] = rs.getString("SALARY");
            str[3] = rs.getString("IS_PAY");
            rowData[i] = new Object[]{str[0], str[1], str[2], str[3], Boolean.TRUE};
            i++;
        }
        rs.close();
        stmt.close();

        tableModel.setDataVector(rowData, columnTitle);
        table.setModel(tableModel);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);

        table.getColumnModel().getColumn(4).setCellEditor(table.getDefaultEditor(Boolean.class));
        table.getColumnModel().getColumn(4).setCellRenderer(table.getDefaultRenderer(Boolean.class));

        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JTextField.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(cr);

        jscrollpane.setBounds(0, 0, 700, 330);
        jscrollpane.setViewportView(table);    //这句很重要

        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(165, 360, 80, 30);
        cancelButton.setFocusPainted(false);
        JButton okButton = new JButton("确定");
        okButton.setBounds(405, 360, 80, 30);
        okButton.setFocusPainted(false);

        jp.setLayout(null);
        // 将各个组件加入到JFrame
        jp.add(cancelButton);
        jp.add(okButton);

        jp.add(jscrollpane);
        jf.setContentPane(jp);

        //取消按钮监听
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });

        //确定按钮监听
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.getSelectedRowCount();
                int sum=table.getRowCount();
                if(sum!=0) {
                    System.out.println("行数::" + sum);
                    int confirm=0;

                    for (int i = 0; i < sum; i++) {
                        if (table.getValueAt(i, 4).toString().compareTo("true") == 0) {
                            confirm++;

                            String sql = "UPDATE WORKERS w SET IS_PAY='已结清' where w.ID=" + table.getValueAt(i, 0);
                            try {
                                Statement stmt1 = man.conn.createStatement();
                                stmt1.execute(sql);
                                stmt1.close();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }
                    String str="已完成结算，本次操作人数：："+confirm;
                    JOptionPane.showMessageDialog(null,str);

                }else
                {
                    JOptionPane.showMessageDialog(null,"已全部结清");
                }
            }

        });

        jf.setVisible(true);



    }

}


