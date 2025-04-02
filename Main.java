import java.sql.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main {
    private static JFrame frame;  // 修改此处为 JFrame 类型

    public static void main(String args[]) {
        // 连接数据库
        Connection con = GetDBConnection.connectDB("team", "root", "123456");
        Login login = new Login();
        login.connect(con);
        // 初始化主界面
        frame = new JFrame("社团管理系统");
        frame.setSize(1000, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("欢迎登录社团管理系统");
        titleLabel.setFont(new Font("华文行楷", Font.PLAIN, 40));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 10, 10);

        JLabel userLabel = new JLabel("账号:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(userLabel, gbc);

        JTextField idField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(idField, gbc);

        JLabel passwordLabel = new JLabel("密码:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("登录");
        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(loginButton, gbc);

        JButton breakButton = new JButton("退出");
        gbc.gridx = 3;
        gbc.gridy = 3;
        centerPanel.add(breakButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String password = new String(passwordField.getPassword());
                if (!login.selectid(id)) {
                    JOptionPane.showMessageDialog(frame, "账号不存在！");
                } else if (!login.selectpassword(id).equals(password)) {
                    JOptionPane.showMessageDialog(frame, "密码错误！");
                } else if ("教师".equals(login.selectposition(id))) {
                    //判断职位，如果是教师，进入教师端
                    showteacher();
                } else if ("副社长".equals(login.selectposition(id)) || "社长".equals(login.selectposition(id))) {
                    //判断职位，如果是社长或副社长，进入干部端
                    showleader(id);
                } else {
                    //如果都不是，进入社员端
                    showmember(id);
                }
            }
        });
        // 为"退出"按钮添加动作监听器
        breakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // 设置窗口为可见
        frame.setVisible(true);
    
}

    public static void changeleader(){
        // 连接到数据库
        Connection con = GetDBConnection.connectDB("team", "root", "123456");
        // 创建一个新的教师对象
        teacher teacher=new teacher();
        // 连接到教师
        teacher.connect(con);
        // 创建一个新的窗口用于更改社长
        frame = new JFrame("更改社长");
        // 设置窗口大小
        frame.setSize(1000, 900);
        // 设置窗口关闭操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 创建一个新的面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        // 将面板添加到窗口中
        frame.add(mainPanel);
        // 创建一个新的网格包布局约束
        GridBagConstraints gbc = new GridBagConstraints();
        // 设置组件之间的间距
        gbc.insets = new Insets(20, 10, 10, 10);
        // 创建一个新的面板，使用网格包布局
        JPanel centerPanel = new JPanel(new GridBagLayout());
        // 将面板添加到主面板中
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 创建一个新的标签，显示"前社长姓名:"
        JLabel text1 = new JLabel("前社长姓名:");
        // 设置标签的位置
        gbc.gridx = 0;
        gbc.gridy = 0;
        // 将标签添加到面板中
        centerPanel.add(text1, gbc);

        // 创建一个新的文本字段，用于输入前社长的姓名
        JTextField oldleader = new JTextField(20);
        // 设置文本字段的位置
        gbc.gridx = 1;
        gbc.gridy = 0;
        // 将文本字段添加到面板中
        centerPanel.add(oldleader, gbc);

        // 创建一个新的标签，显示"新社长姓名:"
        JLabel text2 = new JLabel("新社长姓名:");
        // 设置标签的位置
        gbc.gridx = 0;
        gbc.gridy = 1;
        // 将标签添加到面板中
        centerPanel.add(text2, gbc);

        // 创建一个新的文本字段，用于输入新社长的姓名
        JTextField newleader = new JTextField(20);
        // 设置文本字段的位置
        gbc.gridx = 1;
        gbc.gridy = 1;
        // 将文本字段添加到面板中
        centerPanel.add(newleader, gbc);

        // 创建一个新的按钮，显示"确定"
        JButton yesButton = new JButton("确定");
        // 设置按钮的位置
        gbc.gridx = 0;
        gbc.gridy = 3;
        // 将按钮添加到面板中
        centerPanel.add(yesButton, gbc);

        // 创建一个新的按钮，显示"返回"
        JButton breakButton = new JButton("返回");
        // 设置按钮的位置
        gbc.gridx = 3;
        gbc.gridy = 3;
        // 将按钮添加到面板中
        centerPanel.add(breakButton, gbc);

        // 为"确定"按钮添加动作监听器
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取前社长姓名文本字段中的文本
                String oldLeaderText = oldleader.getText();
                // 获取新社长姓名文本字段中的文本
                String newLeaderText = new String(newleader.getText());
                // 如果前社长的姓名不是社长，则显示消息"该同学不是社长！"
                if(!teacher.is_leader(oldLeaderText)){
                    JOptionPane.showMessageDialog(frame, "该同学不是社长！");
                }
                // 如果新社长的姓名不存在，则显示消息"账号不存在！"
                else if (!teacher.selectname(newLeaderText)) {
                    JOptionPane.showMessageDialog(frame, "账号不存在！");
                }
                else{
                    // 否则，更改社长，并显示消息"修改成功"
                    teacher.change(oldLeaderText,newLeaderText);
                    JOptionPane.showMessageDialog(frame, "修改成功");
                }
    }});
    // 为"返回"按钮添加动作监听器
    breakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭窗口
                frame.dispose();
            }
        });
        // 设置窗口为可见
        frame.setVisible(true);
    }


    public static void changeallpassword(){
        // 连接到数据库
        Connection con = GetDBConnection.connectDB("team", "root", "123456");
        // 创建一个新的教师对象
        teacher teacher=new teacher();
        // 创建一个新的登录对象
        Login login=new Login();
        // 连接到登录
        login.connect(con);
        // 连接到教师
        teacher.connect(con);
        // 创建一个新的窗口用于修改密码
        frame = new JFrame("修改密码");
        // 设置窗口大小
        frame.setSize(1000, 900);
        // 设置窗口关闭操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 创建一个新的面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        // 将面板添加到窗口中
        frame.add(mainPanel);
        // 创建一个新的网格包布局约束
        GridBagConstraints gbc = new GridBagConstraints();
        // 设置组件之间的间距
        gbc.insets = new Insets(20, 10, 10, 10);
        // 创建一个新的面板，使用网格包布局
        JPanel centerPanel = new JPanel(new GridBagLayout());
        // 将面板添加到主面板中
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 创建一个新的标签，显示"更改账号:"
        JLabel text1 = new JLabel("更改账号:");
        // 设置标签的位置
        gbc.gridx = 0;
        gbc.gridy = 0;
        // 将标签添加到面板中
        centerPanel.add(text1, gbc);

        // 创建一个新的文本字段，用于输入账号
        JTextField id = new JTextField(20);
        // 设置文本字段的位置
        gbc.gridx = 1;
        gbc.gridy = 0;
        // 将文本字段添加到面板中
        centerPanel.add(id, gbc);

        // 创建一个新的标签，显示"新密码:"
        JLabel text2 = new JLabel("新密码:");
        // 设置标签的位置
        gbc.gridx = 0;
        gbc.gridy = 1;
        // 将标签添加到面板中
        centerPanel.add(text2, gbc);

        // 创建一个新的文本字段，用于输入新密码
        JTextField newpassowrd = new JTextField(20);
        // 设置文本字段的位置
        gbc.gridx = 1;
        gbc.gridy = 1;
        // 将文本字段添加到面板中
        centerPanel.add(newpassowrd, gbc);

        // 创建一个新的按钮，显示"确定"
        JButton yesButton = new JButton("确定");
        // 设置按钮的位置
        gbc.gridx = 0;
        gbc.gridy = 3;
        // 将按钮添加到面板中
        centerPanel.add(yesButton, gbc);

        // 创建一个新的按钮，显示"返回"
        JButton breakButton = new JButton("返回");
        // 设置按钮的位置
        gbc.gridx = 3;
        gbc.gridy = 3;
        // 将按钮添加到面板中
        centerPanel.add(breakButton, gbc);

        // 为"确定"按钮添加动作监听器
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取账号文本字段中的文本
                String theid = id.getText();
                // 获取新密码文本字段中的文本
                String password = new String(newpassowrd.getText());
                // 如果账号不存在，则显示消息"账号不存在！"
                if (!login.selectid(theid)) {
                    JOptionPane.showMessageDialog(frame, "账号不存在！");
                }
                else{
                    // 否则，更改密码，并显示消息"修改成功"
                    teacher.changepassword(theid,password);
                    JOptionPane.showMessageDialog(frame, "修改成功");
                }
    }});
    // 为"返回"按钮添加动作监听器
    breakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭窗口
                frame.dispose();
            }
        });
        // 设置窗口为可见
        frame.setVisible(true);
    }


public static void changename(){
        // 连接到数据库
        Connection con = GetDBConnection.connectDB("team", "root", "123456");
        // 创建一个新的教师对象
        teacher teacher=new teacher();
        // 连接到教师
        teacher.connect(con);
        // 创建一个新的窗口用于更改社团名称
        frame = new JFrame("更改社团名称");
        // 设置窗口大小
        frame.setSize(1000, 900);
        // 设置窗口关闭操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 创建一个新的面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        // 将面板添加到窗口中
        frame.add(mainPanel);
        // 创建一个新的网格包布局约束
        GridBagConstraints gbc = new GridBagConstraints();
        // 设置组件之间的间距
        gbc.insets = new Insets(20, 10, 10, 10);
        // 创建一个新的面板，使用网格包布局
        JPanel centerPanel = new JPanel(new GridBagLayout());
        // 将面板添加到主面板中
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 创建一个新的标签，显示"社团新名称为:"
        JLabel text1 = new JLabel("社团新名称为:");
        // 设置标签的位置
        gbc.gridx = 0;
        gbc.gridy = 0;
        // 将标签添加到面板中
        centerPanel.add(text1, gbc);

        // 创建一个新的文本字段，用于输入新的社团名称
        JTextField newname = new JTextField(20);
        // 设置文本字段的位置
        gbc.gridx = 1;
        gbc.gridy = 0;
        // 将文本字段添加到面板中
        centerPanel.add(newname, gbc);

        // 创建一个新的按钮，显示"确定"
        JButton yesButton = new JButton("确定");
        // 设置按钮的位置
        gbc.gridx = 0;
        gbc.gridy = 3;
        // 将按钮添加到面板中
        centerPanel.add(yesButton, gbc);

        // 创建一个新的按钮，显示"返回"
        JButton breakButton = new JButton("返回");
        // 设置按钮的位置
        gbc.gridx = 3;
        gbc.gridy = 3;
        // 将按钮添加到面板中
        centerPanel.add(breakButton, gbc);

        // 为"确定"按钮添加动作监听器
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取文本字段中的文本
                String newnametext = newname.getText();
                // 如果新的社团名称是旧的社团名称，则显示消息"请输入新名称！"
                if(teacher.is_oldname(newnametext)){
                    JOptionPane.showMessageDialog(frame, "请输入新名称！");
                }
                else{
                    // 否则，更改社团名称，并显示消息"修改成功"
                    teacher.change_teamname(newnametext);
                    JOptionPane.showMessageDialog(frame, "修改成功");
                }
    }});
    // 为"返回"按钮添加动作监听器
    breakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭窗口
                frame.dispose();
            }
        });
        // 设置窗口为可见
        frame.setVisible(true);
    }


// 定义一个静态方法，用于招募社员
public static void enlist(){
    // 连接到数据库
    Connection con = GetDBConnection.connectDB("team", "root", "123456");
    // 创建 leader、login 和 teacher 对象
    leader leader=new leader();
    Login login=new Login();
    teacher teacher=new teacher();
    // 连接到数据库
    leader.connect(con);
    login.connect(con);
    teacher.connect(con);

    // 创建一个新的 JFrame 对象，标题为 "招募社员"
    frame = new JFrame("招募社员");
    // 设置窗口的大小
    frame.setSize(1000, 900);
    // 设置窗口关闭时的操作
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 创建一个主面板，并设置布局为 BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());
    // 将主面板添加到窗口中
    frame.add(mainPanel);

    // 创建一个 GridBagConstraints 对象，用于设置组件的显示位置和大小
    GridBagConstraints gbc = new GridBagConstraints();
    // 设置组件之间的间隔
    gbc.insets = new Insets(20, 10, 10, 10);

    // 创建一个中心面板，并设置布局为 GridBagLayout
    JPanel centerPanel = new JPanel(new GridBagLayout());
    // 将中心面板添加到主面板的中心
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    // 创建一个标签，用于提示用户输入账号
    JLabel text1 = new JLabel("账号:");
    // 设置标签的位置
    gbc.gridx = 0;
    gbc.gridy = 0;
    // 将标签添加到中心面板中
    centerPanel.add(text1, gbc);

    // 创建一个文本字段，用于用户输入账号
    JTextField id = new JTextField(20);
    // 设置文本字段的位置
    gbc.gridx = 1;
    gbc.gridy = 0;
    // 将文本字段添加到中心面板中
    centerPanel.add(id, gbc);

    // 创建一个标签，用于提示用户输入姓名
    JLabel text2 = new JLabel("姓名");
    // 设置标签的位置
    gbc.gridx = 0;
    gbc.gridy = 1;
    // 将标签添加到中心面板中
    centerPanel.add(text2, gbc);

    // 创建一个文本字段，用于用户输入姓名
    JTextField name = new JTextField(20);
    // 设置文本字段的位置
    gbc.gridx = 1;
    gbc.gridy = 1;
    // 将文本字段添加到中心面板中
    centerPanel.add(name, gbc);

    // 创建一个按钮，用于确认招募社员
    JButton yesButton = new JButton("确定");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(yesButton, gbc);

    // 创建一个按钮，用于返回
    JButton breakButton = new JButton("返回");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(breakButton, gbc);

    // 为确认按钮添加动作监听器
    yesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取用户输入的账号和姓名
            String theid = id.getText();
            String thename = name.getText();
            // 检查姓名是否已存在
            if(teacher.selectname(thename)){
                // 如果姓名已存在，显示错误消息
                JOptionPane.showMessageDialog(frame, "姓名已存在！");
            }
            // 检查账号是否已存在
            else if(login.selectid(theid)){
                // 如果账号已存在，显示错误消息
                JOptionPane.showMessageDialog(frame, "账号已存在！");
            }
            else{
                // 如果账号和姓名都不存在，招募社员
                leader.enlist(theid,thename);
                // 显示添加成功的消息，初始密码为123456
                JOptionPane.showMessageDialog(frame, "添加成功,初始密码为123456");
            }
        }
    });

    // 为返回按钮添加动作监听器
    breakButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 关闭窗口
            frame.dispose();
        }
    });

    // 设置窗口可见
    frame.setVisible(true);
}

// 定义一个静态方法，用于踢出社员
public static void kick(){
    // 连接到数据库
    Connection con = GetDBConnection.connectDB("team", "root", "123456");
    // 创建 leader 和 login 对象
    leader leader=new leader();
    Login login=new Login();
    // 连接到数据库
    leader.connect(con);
    login.connect(con);

    // 创建一个新的 JFrame 对象，标题为 "踢出社员"
    frame = new JFrame("踢出社员");
    // 设置窗口的大小
    frame.setSize(1000, 900);
    // 设置窗口关闭时的操作
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 创建一个主面板，并设置布局为 BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());
    // 将主面板添加到窗口中
    frame.add(mainPanel);

    // 创建一个 GridBagConstraints 对象，用于设置组件的显示位置和大小
    GridBagConstraints gbc = new GridBagConstraints();
    // 设置组件之间的间隔
    gbc.insets = new Insets(20, 10, 10, 10);

    // 创建一个中心面板，并设置布局为 GridBagLayout
    JPanel centerPanel = new JPanel(new GridBagLayout());
    // 将中心面板添加到主面板的中心
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    // 创建一个标签，用于提示用户输入踢出者账号
    JLabel text1 = new JLabel("踢出者账号:");
    // 设置标签的位置
    gbc.gridx = 0;
    gbc.gridy = 0;
    // 将标签添加到中心面板中
    centerPanel.add(text1, gbc);

    // 创建一个文本字段，用于用户输入踢出者账号
    JTextField id = new JTextField(20);
    // 设置文本字段的位置
    gbc.gridx = 1;
    gbc.gridy = 0;
    // 将文本字段添加到中心面板中
    centerPanel.add(id, gbc);

    // 创建一个按钮，用于确认踢出社员
    JButton yesButton = new JButton("确定");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(yesButton, gbc);

    // 创建一个按钮，用于返回
    JButton breakButton = new JButton("返回");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(breakButton, gbc);

    // 为确认按钮添加动作监听器
    yesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取用户输入的踢出者账号
            String theid = id.getText();
            // 检查账号是否存在
            if(!login.selectid(theid)){
                // 如果账号不存在，显示错误消息
                JOptionPane.showMessageDialog(frame, "账号不存在！");
            }
            // 检查用户是否是社员
            else if(!leader.is_member(theid)){
                // 如果用户不是社员，显示错误消息
                JOptionPane.showMessageDialog(frame, "您无权踢出非社员！");
            }
            else{
                // 如果用户是社员，踢出社员
                leader.kick(theid);
                // 显示踢出成功的消息
                JOptionPane.showMessageDialog(frame, "踢出成功");
            }
        }
    });

    // 为返回按钮添加动作监听器
    breakButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 关闭窗口
            frame.dispose();
        }
    });

    // 设置窗口可见
    frame.setVisible(true);
}


// 定义一个静态方法，用于删除公告
public static void drop(){
    // 连接到数据库
    Connection con = GetDBConnection.connectDB("team", "root", "123456");
    // 创建 leader 对象
    leader leader=new leader();
    // 连接到数据库
    leader.connect(con);

    // 创建一个新的 JFrame 对象，标题为 "踢出社员"
    frame = new JFrame("删除公告");
    // 设置窗口的大小
    frame.setSize(1000, 900);
    // 设置窗口关闭时的操作
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 创建一个主面板，并设置布局为 BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());
    // 将主面板添加到窗口中
    frame.add(mainPanel);

    // 创建一个 GridBagConstraints 对象，用于设置组件的显示位置和大小
    GridBagConstraints gbc = new GridBagConstraints();
    // 设置组件之间的间隔
    gbc.insets = new Insets(20, 10, 10, 10);

    // 创建一个中心面板，并设置布局为 GridBagLayout
    JPanel centerPanel = new JPanel(new GridBagLayout());
    // 将中心面板添加到主面板的中心
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    // 创建一个标签，用于提示用户输入踢出者账号
    JLabel text1 = new JLabel("所删除公告的内容:");
    // 设置标签的位置
    gbc.gridx = 0;
    gbc.gridy = 1;
    // 将标签添加到中心面板中
    centerPanel.add(text1, gbc);

    // 创建一个文本区域，用于用户输入内容
    JTextArea content = new JTextArea(3,20);
    // 设置文本区域的位置
    gbc.gridx = 1;
    gbc.gridy = 1;
    // 将文本区域添加到中心面板中
    centerPanel.add(content, gbc);

    // 创建一个按钮，用于确认踢出社员
    JButton yesButton = new JButton("确定");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(yesButton, gbc);

    // 创建一个按钮，用于返回
    JButton breakButton = new JButton("返回");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(breakButton, gbc);

    // 为确认按钮添加动作监听器
    yesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取用户输入的踢出者账号
            String thecontent = content.getText();
            // 检查公告内容是否存在
            if(!leader.selectcontent(thecontent)){
                // 如果公告内容不存在，显示错误消息
                JOptionPane.showMessageDialog(frame, "公告内容不存在！");
            }
            else{
                leader.drop(thecontent);
                // 显示删除成功的消息
                JOptionPane.showMessageDialog(frame, "删除成功");
            }
        }
    });

    // 为返回按钮添加动作监听器
    breakButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 关闭窗口
            frame.dispose();
        }
    });

    // 设置窗口可见
    frame.setVisible(true);
}

// 定义一个静态方法，用于发布公告
public static void baba(String myid){
    // 连接到数据库
    Connection con = GetDBConnection.connectDB("team", "root", "123456");
    // 创建 leader 和 teacher 对象
    leader leader=new leader();
    teacher teacher=new teacher();
    // 连接到数据库
    leader.connect(con);
    teacher.connect(con);

    // 通过 leader 对象的 selectmyname 方法获取名字
    String myname=leader.selectmyname(myid);

    // 创建一个新的 JFrame 对象，标题为 "发布公告"
    frame = new JFrame("发布公告");
    // 设置窗口的大小
    frame.setSize(1000, 900);
    // 设置窗口关闭时的操作
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 创建一个主面板，并设置布局为 BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());
    // 将主面板添加到窗口中
    frame.add(mainPanel);

    // 创建一个 GridBagConstraints 对象，用于设置组件的显示位置和大小
    GridBagConstraints gbc = new GridBagConstraints();
    // 设置组件之间的间隔
    gbc.insets = new Insets(20, 10, 10, 10);

    // 创建一个中心面板，并设置布局为 GridBagLayout
    JPanel centerPanel = new JPanel(new GridBagLayout());
    // 将中心面板添加到主面板的中心
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    // 创建一个标签，用于提示用户输入发布内容
    JLabel text2 = new JLabel("发布内容:");
    // 设置标签的位置
    gbc.gridx = 0;
    gbc.gridy = 1;
    // 将标签添加到中心面板中
    centerPanel.add(text2, gbc);

    // 创建一个文本区域，用于用户输入发布内容
    JTextArea content = new JTextArea(3,20);
    // 设置文本区域的位置
    gbc.gridx = 1;
    gbc.gridy = 1;
    // 将文本区域添加到中心面板中
    centerPanel.add(content, gbc);

    // 创建一个按钮，用于确认发布公告
    JButton yesButton = new JButton("确定");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(yesButton, gbc);

    // 创建一个按钮，用于返回
    JButton breakButton = new JButton("返回");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(breakButton, gbc);

    // 为确认按钮添加动作监听器
    yesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取用户输入的发布内容
            String thename = myname;
            String thecontent = content.getText();
            // 调用 leader 对象的 baba 方法发布公告
            if(leader.selectcontent(thecontent)){
                // 如果公告内容已存在存在，显示错误消息
                JOptionPane.showMessageDialog(frame, "公告内容重复！");
            }
            else{
            leader.baba(thename,thecontent);
            // 显示发布成功的消息
            JOptionPane.showMessageDialog(frame, "发布成功");
            }
        }
    });

    // 为返回按钮添加动作监听器
    breakButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 关闭窗口
            frame.dispose();
        }
    });

    // 设置窗口可见
    frame.setVisible(true);
}


// 定义一个静态方法，用于修改密码
public static void changepassword(String myid){
    // 连接到数据库
    Connection con = GetDBConnection.connectDB("team", "root", "123456");
    // 创建 teacher 和 login 对象
    teacher teacher=new teacher();
    Login login=new Login();
    // 连接到数据库
    login.connect(con);
    teacher.connect(con);

    // 创建一个新的 JFrame 对象，标题为 "修改密码"
    frame = new JFrame("修改密码");
    // 设置窗口的大小
    frame.setSize(1000, 900);
    // 设置窗口关闭时的操作
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 创建一个主面板，并设置布局为 BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());
    // 将主面板添加到窗口中
    frame.add(mainPanel);

    // 创建一个 GridBagConstraints 对象，用于设置组件的显示位置和大小
    GridBagConstraints gbc = new GridBagConstraints();
    // 设置组件之间的间隔
    gbc.insets = new Insets(20, 10, 10, 10);

    // 创建一个中心面板，并设置布局为 GridBagLayout
    JPanel centerPanel = new JPanel(new GridBagLayout());
    // 将中心面板添加到主面板的中心
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    // 创建一个标签，用于提示用户输入新密码
    JLabel text2 = new JLabel("新密码:");
    // 设置标签的位置
    gbc.gridx = 0;
    gbc.gridy = 1;
    // 将标签添加到中心面板中
    centerPanel.add(text2, gbc);

    // 创建一个文本字段，用于用户输入新密码
    JTextField newpassowrd = new JTextField(20);
    // 设置文本字段的位置
    gbc.gridx = 1;
    gbc.gridy = 1;
    // 将文本字段添加到中心面板中
    centerPanel.add(newpassowrd, gbc);

    // 创建一个按钮，用于确认修改密码
    JButton yesButton = new JButton("确定");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(yesButton, gbc);

    // 创建一个按钮，用于返回
    JButton breakButton = new JButton("返回");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(breakButton, gbc);

    // 为确认按钮添加动作监听器
    yesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取用户输入的新密码
            String password = new String(newpassowrd.getText());
            // 调用 teacher 对象的 changepassword 方法修改密码
            teacher.changepassword(myid,password);
            // 显示修改成功的消息
            JOptionPane.showMessageDialog(frame, "修改成功");
        }
    });

    // 为返回按钮添加动作监听器
    breakButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 关闭窗口
            frame.dispose();
        }
    });

    // 设置窗口可见
    frame.setVisible(true);
}

// 定义一个静态方法，用于从数据库查询结果集中获取数据并显示在表格中
private static void displayTableData(ResultSet resultSet, String title) {
    // 创建主面板，并设置布局为 BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());
    // 创建一个新的 JFrame 对象
    JFrame frame = new JFrame();
    // 将主面板设置为窗口的内容面板
    frame.setContentPane(mainPanel);
    

    // 创建标题面板
    JPanel titlePanel = new JPanel();
    // 创建标题标签，并设置字体和文本
    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("华文行楷", Font.PLAIN, 20));
    // 设置标题面板的边框
    titlePanel.setBorder(new EmptyBorder(30, 10, 30, 10));
    // 将标题标签添加到标题面板中
    titlePanel.add(titleLabel);
    // 将标题面板添加到主面板的北部（上方）
    mainPanel.add(titlePanel, BorderLayout.NORTH);

    // 创建一个新的按钮，用于返回
    JButton breakButton = new JButton("返回");
    // 为按钮添加动作监听器，当点击按钮时，关闭窗口
    breakButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    });
    // 将按钮添加到主面板的南部（下方）
    mainPanel.add(breakButton, BorderLayout.SOUTH);

    try {
        // 获取结果集的元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        // 获取列数
        int columnCount = metaData.getColumnCount();

        // 创建列名和数据数组
        String[] columnNames = new String[columnCount];
        ArrayList<String[]> data = new ArrayList<>();

        // 获取列名
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }

        // 获取数据
        while (resultSet.next()) {
            String[] row = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = resultSet.getString(i);
            }
            data.add(row);
        }

        // 创建表格并添加到滚动面板
        JTable table = new JTable(data.toArray(new String[0][]), columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 设置窗口大小
        frame.setSize(1000, 900);
        // 设置窗口可见
        frame.setVisible(true);
    } catch (SQLException e) {
        // 打印堆栈跟踪以帮助调试
        e.printStackTrace();
        // 显示错误消息
        JOptionPane.showMessageDialog(frame, "数据库处理错误！");
    }
}

// 定义一个静态方法，用于查询并显示社团信息
protected static void findteam() {
    // 连接到数据库
    Connection con = GetDBConnection.connectDB("team", "root", "123456");
    try {
        // 创建 SQL 语句对象
        Statement sql = con.createStatement();
        // 执行 SQL 查询并获取结果集
        ResultSet rs = sql.executeQuery("SELECT * FROM team");
        // 显示查询结果
        displayTableData(rs, "社团信息");
    } catch (SQLException e) {
        // 打印堆栈跟踪以帮助调试
        e.printStackTrace();
        // 显示错误消息
        JOptionPane.showMessageDialog(frame, "数据库查询错误或表不存在！");
    }
}

// 定义一个静态方法，用于查询并显示成员信息
protected static void findmember() {
    // 连接到数据库
    Connection con = GetDBConnection.connectDB("team", "root", "123456");
    try {
        // 创建 SQL 语句对象
        Statement sql = con.createStatement();
        // 执行 SQL 查询并获取结果集
        ResultSet rs = sql.executeQuery("SELECT * FROM member");
        // 显示查询结果
        displayTableData(rs, "成员信息");
    } catch (SQLException e) {
        // 打印堆栈跟踪以帮助调试
        e.printStackTrace();
        // 显示错误消息
        JOptionPane.showMessageDialog(frame, "数据库查询错误或表不存在！");
    }
}

// 定义一个静态方法，用于查询并显示社团公告
protected static void findnotice() {
    // 连接到数据库
    Connection con = GetDBConnection.connectDB("team", "root", "123456");
    try {
        // 创建 SQL 语句对象
        Statement sql = con.createStatement();
        // 执行 SQL 查询并获取结果集
        ResultSet rs = sql.executeQuery("SELECT * FROM notice");
        // 显示查询结果
        displayTableData(rs, "社团公告");
    } catch (SQLException e) {
        // 打印堆栈跟踪以帮助调试
        e.printStackTrace();
        // 显示错误消息
        JOptionPane.showMessageDialog(frame, "数据库查询错误或表不存在！");
    }
}

// 定义一个静态方法，用于显示教师信息
protected static void showteacher() {
    // 创建一个新的 JFrame 对象，标题为 "社团管理系统-教师端"
    JFrame frame = new JFrame("社团管理系统-教师端");
    // 设置窗口的大
    frame.setSize(1000, 900);
    // 设置窗口关闭时的操作
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 创建一个主面板，并设置布局为 BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());
    // 将主面板添加到窗口中
    frame.add(mainPanel);

    // 创建一个标题面板
    JPanel titlePanel = new JPanel();
    // 创建一个标签，并设置字体和文本
    JLabel titleLabel = new JLabel("社团管理系统-教师端");
    titleLabel.setFont(new Font("华文行楷", Font.PLAIN, 40));
    // 设置标题面板的边框
    titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
    // 将标签添加到标题面板中
    titlePanel.add(titleLabel);
    // 将标题面板添加到主面板的北部（上方）
    mainPanel.add(titlePanel, BorderLayout.NORTH);

    // 创建一个中心面板，并设置布局为 GridBagLayout
    JPanel centerPanel = new JPanel(new GridBagLayout());
    // 将中心面板添加到主面板的中心
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    // 创建一个 GridBagConstraints 对象，用于设置组件的显示位置和大小
    GridBagConstraints gbc = new GridBagConstraints();
    // 设置组件之间的间隔
    gbc.insets = new Insets(20, 10, 10, 10);

    // 创建一个按钮，用于查看社团信息
    JButton findteam = new JButton("查看社团信息");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(findteam, gbc);

    // 创建一个按钮，用于查看成员信息
    JButton findmember = new JButton("查看成员信息");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(findmember, gbc);

    // 创建一个按钮，用于更换社长
    JButton changeleader = new JButton("更换社长");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 6;
    // 将按钮添加到中心面板中
    centerPanel.add(changeleader, gbc);

    // 创建一个按钮，用于更改社团名称
    JButton changename = new JButton("更改社团名称");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 0;
    // 将按钮添加到中心面板中
    centerPanel.add(changename, gbc);

    // 创建一个按钮，用于修改密码
    JButton changeallpassword = new JButton("修改密码");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 0;
    // 将按钮添加到中心面板中
    centerPanel.add(changeallpassword, gbc);

    // 创建一个按钮，用于查看社团公告
    JButton findnotice = new JButton("查看社团公告");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 6;
    // 将按钮添加到中心面板中
    centerPanel.add(findnotice, gbc);

    // 创建一个按钮，用于退出登录
    JButton breakButton = new JButton("退出登录");
    // 设置按钮的位置
    gbc.gridx = 6;
    gbc.gridy = 6;
    // 将按钮添加到中心面板中
    centerPanel.add(breakButton, gbc);

    // 为查看社团信息按钮添加点击事件监听器
    findteam.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            findteam();
        }
    });

    // 为查看成员信息按钮添加点击事件监听器
    findmember.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            findmember();
        }
    });

    // 为退出登录按钮添加点击事件监听器
    breakButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    });

    // 为查看社团公告按钮添加点击事件监听器
    findnotice.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            findnotice();
        }
    });

    // 为更换社长按钮添加点击事件监听器
    changeleader.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeleader();
        }
    });

    // 为更改社团名称按钮添加点击事件监听器
    changename.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            changename();
        }
    });

    // 为修改密码按钮添加点击事件监听器
    changeallpassword.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeallpassword();
        }
    });

    // 设置窗口可见
    frame.setVisible(true);
}


// 定义一个静态方法，用于实现干部功能
protected static void showleader(String myid) {
    // 创建一个新的 JFrame 对象，标题为 "社团管理系统-干部端"
    JFrame frame = new JFrame("社团管理系统-干部端");
    // 设置窗口的大小
    frame.setSize(1000, 900);
    // 设置窗口关闭时的操作
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 创建一个主面板，并设置布局为 BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());
    // 将主面板添加到窗口中
    frame.add(mainPanel);

    // 创建一个标题面板
    JPanel titlePanel = new JPanel();
    // 创建一个标签，并设置字体和文本
    JLabel titleLabel = new JLabel("社团管理系统-干部端");
    titleLabel.setFont(new Font("华文行楷", Font.PLAIN, 40));
    // 设置标题面板的边框
    titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
    // 将标签添加到标题面板中
    titlePanel.add(titleLabel);
    // 将标题面板添加到主面板的北部（上方）
    mainPanel.add(titlePanel, BorderLayout.NORTH);

    // 创建一个中心面板，并设置布局为 GridBagLayout
    JPanel centerPanel = new JPanel(new GridBagLayout());
    // 将中心面板添加到主面板的中心
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    // 创建一个 GridBagConstraints 对象，用于设置组件的显示位置和大小
    GridBagConstraints gbc = new GridBagConstraints();
    // 设置组件之间的间隔
    gbc.insets = new Insets(20, 10, 10, 10);

    // 创建一个按钮，用于查看社团信息
    JButton findteam = new JButton("查看社团信息");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 0;
    // 将按钮添加到中心面板中
    centerPanel.add(findteam, gbc);

    // 创建一个按钮，用于查看成员信息
    JButton findmember = new JButton("查看成员信息");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(findmember, gbc);

    // 创建一个按钮，用于招募社员
    JButton enlist = new JButton("招募社员");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 0;
    // 将按钮添加到中心面板中
    centerPanel.add(enlist, gbc);

    // 创建一个按钮，用于踢出社员
    JButton kick = new JButton("踢出社员");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(kick, gbc);

    // 创建一个按钮，用于发布社团公告
    JButton baba = new JButton("发布社团公告");
    // 设置按钮的位置
    gbc.gridx = 6;
    gbc.gridy = 0;
    // 将按钮添加到中心面板中
    centerPanel.add(baba, gbc);

    // 创建一个按钮，用于更改密码
    JButton changepassword = new JButton("更改密码");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 6;
    // 将按钮添加到中心面板中
    centerPanel.add(changepassword, gbc);

    // 创建一个按钮，用于查看社团公告
    JButton findnotice = new JButton("查看社团公告");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 6;
    // 将按钮添加到中心面板中
    centerPanel.add(findnotice, gbc);

    // 创建一个按钮，用于发布社团公告
    JButton drop = new JButton("删除社团公告");
    // 设置按钮的位置
    gbc.gridx = 6;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(drop, gbc);

    // 创建一个按钮，用于退出登录
    JButton breakButton = new JButton("退出登录");
    // 设置按钮的位置
    gbc.gridx = 6;
    gbc.gridy = 6;
    // 将按钮添加到中心面板中
    centerPanel.add(breakButton, gbc);

    // 为查看社团信息按钮添加点击事件监听器
    findteam.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            findteam();
        }
    });

    // 为查看成员信息按钮添加点击事件监听器
    findmember.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            findmember();
        }
    });

    // 为退出登录按钮添加点击事件监听器
    breakButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    });

    // 为查看社团公告按钮添加点击事件监听器
    findnotice.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            findnotice();
        }
    });

    // 为删除公告按钮添加点击事件监听器
    drop.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            drop();
        }
    });

    // 为招募社员按钮添加点击事件监听器
    enlist.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            enlist();
        }
    });

    // 为踢出社员按钮添加点击事件监听器
    kick.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            kick();
        }
    });

    // 为更改密码按钮添加点击事件监听器
    changepassword.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            changepassword(myid);
        }
    });

    // 为发布社团公告按钮添加点击事件监听器
    baba.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            baba(myid);
        }
    });

    // 设置窗口可见
    frame.setVisible(true);
}


// 定义一个静态方法，用于实现社员功能
protected static void showmember(String myid) {
    // 创建一个新的 JFrame 对象，标题为 "社团管理系统-社员端"
    JFrame frame = new JFrame("社团管理系统-社员端");
    // 设置窗口的大小
    frame.setSize(1000, 900);
    // 设置窗口关闭时的操作
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 创建一个主面板，并设置布局为 BorderLayout
    JPanel mainPanel = new JPanel(new BorderLayout());
    // 将主面板添加到窗口中
    frame.add(mainPanel);

    // 创建一个标题面板
    JPanel titlePanel = new JPanel();
    // 创建一个标签，并设置字体和文本
    JLabel titleLabel = new JLabel("社团管理系统-社员端");
    titleLabel.setFont(new Font("华文行楷", Font.PLAIN, 40));
    // 设置标题面板的边框
    titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
    // 将标签添加到标题面板中
    titlePanel.add(titleLabel);
    // 将标题面板添加到主面板的北部（上方）
    mainPanel.add(titlePanel, BorderLayout.NORTH);

    // 创建一个中心面板，并设置布局为 GridBagLayout
    JPanel centerPanel = new JPanel(new GridBagLayout());
    // 将中心面板添加到主面板的中心
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    // 创建一个 GridBagConstraints 对象，用于设置组件的显示位置和大小
    GridBagConstraints gbc = new GridBagConstraints();
    // 设置组件之间的间隔
    gbc.insets = new Insets(20, 10, 10, 10);

    // 创建一个按钮，用于查看社团信息
    JButton findteam = new JButton("查看社团信息");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 0;
    // 将按钮添加到中心面板中
    centerPanel.add(findteam, gbc);

    // 创建一个按钮，用于查看成员信息
    JButton findmember = new JButton("查看成员信息");
    // 设置按钮的位置
    gbc.gridx = 3;
    gbc.gridy = 0;
    // 将按钮添加到中心面板中
    centerPanel.add(findmember, gbc);

    // 创建一个按钮，用于更改密码
    JButton changepassword = new JButton("更改密码");
    // 设置按钮的位置
    gbc.gridx = 0;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(changepassword, gbc);

    // 创建一个按钮，用于查看社团公告
    JButton findnotice = new JButton("查看社团公告");
    // 设置按钮的位置
    gbc.gridx = 6;
    gbc.gridy = 0;
    // 将按钮添加到中心面板中
    centerPanel.add(findnotice, gbc);

    // 创建一个按钮，用于退出登录
    JButton breakButton = new JButton("退出登录");
    // 设置按钮的位置
    gbc.gridx = 6;
    gbc.gridy = 3;
    // 将按钮添加到中心面板中
    centerPanel.add(breakButton, gbc);

    // 为查看社团信息按钮添加点击事件监听器
    findteam.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            findteam();
        }
    });

    // 为查看成员信息按钮添加点击事件监听器
    findmember.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            findmember();
        }
    });

    // 为退出登录按钮添加点击事件监听器
    breakButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    });

    // 为查看社团公告按钮添加点击事件监听器
    findnotice.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            findnotice();
        }
    });

    // 为更改密码按钮添加点击事件监听器
    changepassword.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            changepassword(myid);
        }
    });

    // 设置窗口可见
    frame.setVisible(true);
}
}
