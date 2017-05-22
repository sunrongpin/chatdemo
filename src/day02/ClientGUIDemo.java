package day02;

import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
//import java.sql.Date;

import javax.swing.*;

/**
 * @date 第二天
 * @author zhoudan
 *
 */

public class ClientGUIDemo  {
    private Socket s;
    private static OutputStream out;
    private InputStream in;
    static String ipServer ;
    int deterCnt;
    int buttonCnt;

    public static JFrame signInFrame = new JFrame("QQ2035");
    //  public static JFrame waitJFrame = new JFrame("QQ2035");
    public static JFrame ipFrame = new JFrame("QQ2035");
    public static JTextField ipText = new JTextField("127.0.0.1",15);
    public static void main(String[] args) {

        //输入服务器ip窗体
        createIPFrame();
    }

    /**
     * 定义全局用户名框，密码框
     *
     */
    public static JTextField countText = new JTextField("QQ号码/手机/邮箱",15);
    public static JPasswordField pwdText = new JPasswordField(15);


    public static void createIPFrame(){
        //建立输入服务器IP窗体
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        panel2.setLayout( new GridLayout(2,1,8,8) );

        JButton ipButton = new JButton("              连  接              ");

        signInFrame.setLayout( new BorderLayout() );
        panel2.add(ipText);
        panel2.add(ipButton);

        panel.add( createTopLayout(),BorderLayout.NORTH );
        panel.add( panel2,BorderLayout.CENTER );

        ipButton.addActionListener( new ActionListener(){
            //获取主机ip事件监听
            @Override
            public void actionPerformed(ActionEvent e) {
                ipServer = ipText.getText();
                ipFrame.dispose();
                createsignInFrame();
            }

        });

        ipFrame.setContentPane(panel);
        ipFrame.setSize(440,335);
        ipFrame.setVisible(true);
    }

    public static void createsignInFrame(){
        JPanel panel = new JPanel();
        //panel.setLayout( new BorderLayout() );
        signInFrame.setLayout( new BorderLayout() );

        panel.add( createTopLayout(),BorderLayout.NORTH );

        panel.add( createCenterLayout(),BorderLayout.CENTER );

        signInFrame.setContentPane(panel);
        signInFrame.setSize(440,335);
        signInFrame.setVisible(true);
    }

    /*
     * 建立连接
     */
    public void open() throws IOException{
        s = new Socket(ipServer, 8000);

        //客户端的in 连接到服务的out
        in = s.getInputStream();
        //客户端的out连接到服务器的in
        out = s.getOutputStream();

        //向服务器写数据应该不用建立一个新的线程，用点击发送按钮来控制
        //Reader线程负责: 从控制台读取信息写入到服务器端out.
        // new Reader(out).start();


//          发送用户名和密码
//          用户名与密码一起提交
        char[] password = pwdText.getPassword();
        System.out.print(countText.getText() + new String(password)  + "\n");
        out.write( ( countText.getText() + new String(password) + "\n").getBytes() );
        out.flush();
        new Writer(in).start();
    }

    public static void craetJFrameWait() {
        //登录等待窗口
        JFrame waitFrame = new JFrame();
        JPanel waitPanel = new JPanel();
        waitFrame.setLayout( new BorderLayout() );
        waitFrame.setTitle("deng");

        waitPanel.add( createTopLayout(),BorderLayout.NORTH );

        waitPanel.add( new JLabel("dsvfdf"),BorderLayout.CENTER );

        waitFrame.setContentPane(waitPanel);
        waitFrame.setSize(440,335);
        waitFrame.setVisible(true);

        waitFrame.setVisible(false);
    }
    public static JLabel createTopLayout(){
        Icon ic = new ImageIcon("G:\\java\\ico_pngForJava\\QQ.PNG");
        JLabel label1 = new JLabel(ic);
        return label1;
    }

    public static JPanel createCenterLayout(){

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout( new BorderLayout() );
        //icoHead.setBounds(100, 200, 300, 500);;
//      头像
        Icon ic = new ImageIcon("G:\\java\\ico_pngForJava\\SginInHead.PNG");
        JLabel label1 = new JLabel(ic);

        centerPanel.add(label1,BorderLayout.WEST);

        centerPanel.add( createUserPwdOptionLayout(),BorderLayout.CENTER );


        centerPanel.add( createSignUpButtonLayout(),BorderLayout.SOUTH );

        return centerPanel;
    }


    public static JPanel createUserPwdOptionLayout(){
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout( new BorderLayout() );

        panelCenter.add( createUserPwdLayout(),BorderLayout.NORTH);
        panelCenter.add( createOptionLayout(),BorderLayout.CENTER);
        return panelCenter;
    }

    public static JPanel createUserPwdLayout(){
        JPanel userPwdLayout = new JPanel();
        userPwdLayout.setLayout( new GridLayout(2,1,3,4) );
        userPwdLayout.add( createUserLayout() );
        userPwdLayout.add( createPwdLayout() );

        return userPwdLayout;
    }

    public static JPanel createUserLayout(){
        JPanel userLayout = new JPanel();
        userLayout.setLayout( new BorderLayout() );

        JLabel singUp = new JLabel("    注册帐号");

        //countText
        userLayout.add(countText,BorderLayout.WEST);
        userLayout.add(singUp,BorderLayout.CENTER);
        return userLayout;
    }

    public static JPanel createPwdLayout(){
        JPanel pwdLayout = new JPanel();
        pwdLayout.setLayout( new BorderLayout() );

        JLabel findPwd = new JLabel("    找回密码");

        pwdLayout.add(pwdText,BorderLayout.WEST);
        pwdLayout.add(findPwd,BorderLayout.CENTER);
        return pwdLayout;
    }
    public static JPanel createOptionLayout(){
        JPanel optionLayout = new JPanel();

        JCheckBox rememberPwd = new JCheckBox("记住密码",false);
        JCheckBox autoSginIn = new JCheckBox("自动登录",true);

        optionLayout.setLayout( new GridLayout(1,2,7,2) );

        optionLayout.add(rememberPwd);
        optionLayout.add(autoSginIn);
        return optionLayout;
    }

    public static JPanel createSignUpButtonLayout(){
        JPanel buttonLayout = new JPanel();
        JButton sginInButton = new JButton("              登   录              ");
        buttonLayout.add(sginInButton);

        sginInButton.addActionListener( new ActionListener(){
            //登录事件监听
            @Override
            public void actionPerformed(ActionEvent e) {

//              这种关闭方法只是使得窗口不可见,资源并没有释放，多次调试之后会使得jvm资源大量浪费
//              signInFrame.setVisible(false);

//              用这种方法关闭窗口可以释放资源
                signInFrame.dispose();


//TODO 登录窗体显示两秒  but 没成功 ,登录窗体不能完整显示，有卡死感觉。2s后可以成功跳转至主窗体
//                  craetJFrameWait();
//              //聊天室主窗口
                ClientGUIDemo client = new ClientGUIDemo();
                JFrame chatHouseFrame = chatHouse();
                chatHouseFrame.setTitle("QQ聊天室" + "——" + countText.getText() );

                try {
                    //建立连接
                    client.open();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        return buttonLayout;
    }

    /**
     * 聊天界面
     * @return
     */
    public static JFrame chatHouse(){
        JFrame chatHouseFrame = new JFrame("QQ聊天室");
        JPanel chatMainPanel = new JPanel();
        chatMainPanel.setLayout( new BorderLayout() );

        chatMainPanel.add( craeteChatArea(),BorderLayout.WEST);

        //信息显示区，显示通知，广告，成员信的信息
        chatMainPanel.add( createInfoArea(),BorderLayout.CENTER );

        chatHouseFrame.setContentPane(chatMainPanel);
        chatHouseFrame.setSize(700,505);
        chatHouseFrame.setVisible(true);
        return chatHouseFrame;
    }

    public static JPanel  craeteChatArea(){
        JPanel chatAreaPanel = new JPanel();
        chatAreaPanel.setLayout( new BorderLayout() );

        chatAreaPanel.add( createRecvArea(),BorderLayout.NORTH);
        chatAreaPanel.add( createSendArea(),BorderLayout.CENTER);
        chatAreaPanel.add( createSendButtonArea(),BorderLayout.SOUTH);
        return chatAreaPanel;
    }

    //接收信息显示框
    public static JTextArea recviveArea = new JTextArea(20,40);
    //发送信息显示框
    public static JTextArea sendArea = new JTextArea(5,40);
    //聊天成员信息框
    public static JTextArea infoOfMemberText = new JTextArea("管理员:张特\n"
            + "张三\n"
            + "李四\n"
            + "王五\n"
            + "马六\n"
            + "赵七\n"
            + "钱八\n"
            + "孙九\n",2,2);

    public static JPanel createRecvArea(){
        JPanel recviveAreaPanel = new JPanel();
        JScrollPane recviveScroll = new JScrollPane(recviveArea);

        //接收信息框竖直滚动条自动出现
        recviveScroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        recviveArea.setEditable(false);
        recviveArea.setLineWrap(true); //超出显示长度自动换行

        recviveAreaPanel.add(recviveScroll);
        return recviveAreaPanel;
    }

    public static JPanel createSendArea(){
        JPanel sendAreaPanel = new JPanel();
        JScrollPane sendScroll = new JScrollPane(sendArea);

        //接收信息框竖直滚动条自动出现
        sendScroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sendArea.setLineWrap(true); //超出显示长度自动换行
        sendAreaPanel.setLayout(new BorderLayout());

        sendAreaPanel.add( createAssLabel() ,BorderLayout.CENTER);
        sendAreaPanel.add(sendScroll,BorderLayout.SOUTH);
        return sendAreaPanel;
    }

    public static JLabel createAssLabel(){
        Icon ic = new ImageIcon("G:\\java\\ico_pngForJava\\ass.PNG");
        JLabel assLabel = new JLabel(ic);
        return assLabel;
    }

    public static JPanel createSendButtonArea(){
        JPanel SendButtonAreaPanel = new JPanel();
        SendButtonAreaPanel.setLayout( new BorderLayout() );

        //这一句对应于QQ聊天界面的小广告条
        SendButtonAreaPanel.add( new JLabel("CF老兵豪礼赠送ئۇيغۇرچە"),BorderLayout.WEST );
        SendButtonAreaPanel.add( craetSendButton(),BorderLayout.EAST );
        return SendButtonAreaPanel;
    }

    public static JPanel craetSendButton(){
        //聊天区关闭和发送按钮面板
        JPanel buttonPanel = new JPanel();
        JButton close = new JButton("关闭(C)");
        JButton send = new JButton("发送(S)");
        buttonPanel.add(close);
        buttonPanel.add(send);

        send.addActionListener( new ActionListener(){
            //发送事件监听
            @Override
            public void actionPerformed(ActionEvent e) {


//              String sendContent = sendArea.getText();
//              String sendInit = new String();
//
//              int cntLength = 140 - sendContent.length();
//              for( int i = 0; i < cntLength;i++ ){
//                  sendInit = sendInit + "  ";
//              }
//              sendInit = sendInit + sendContent;

                try {
                    reader();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
//              recviveArea.append(currentDate.format(new Date()) + "\r\n");
//              recviveArea.append( sendArea.getText() + "\r\n" + "\r\n" );
                sendArea.setText(null);
            }

        });

        close.addActionListener( new ActionListener(){
            //关闭事件监听
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });

        return buttonPanel;
    }

    public static JPanel  createInfoArea(){
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout( new GridLayout(2,1) );

        Icon ic = new ImageIcon("G:\\java\\ico_pngForJava\\Advertisement.PNG");
        JLabel label1 = new JLabel(ic);
        infoPanel.add(label1);
        infoPanel.add( createInfoOfMemberArea() );

        return infoPanel;
    }

    public static JPanel createInfoOfMemberArea(){
        JPanel infoOfMemberPanel = new JPanel();
        infoOfMemberPanel.setLayout(new BorderLayout() );

        JScrollPane infoScroll = new JScrollPane(infoOfMemberText);
        infoScroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        infoOfMemberText.setEditable(false);

        infoOfMemberPanel.add( new JLabel("聊天成员"),BorderLayout.NORTH);
        infoOfMemberPanel.add(infoScroll);
        return infoOfMemberPanel;
    }

//  public static JPanel memberArea(){
//
//
//      JScrollPane sendScroll = new JScrollPane(sendArea);
//
//      //接收信息框竖直滚动条自动出现
//      sendScroll.setVerticalScrollBarPolicy(
//                      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//  }


    /**
     * 发送不应该作为一个线程，而是一个函数，通过发送按钮
     * 点击事件来触发，.
     * @throws
     */
//Reader线程负责: 从控制台读取信息写入到服务器端out.
//  private class Reader extends Thread{
//    OutputStream out;
//      //private Scanner console;
//    public Reader(OutputStream out ) {
//      this.out = out;
//    }
//    public void run(){
//     /*   替换为从输入框获取
//      *  console = new Scanner(System.in);
//      */
//      while(true){
//        try{
//          String str = sendArea.getText();
//          out.write((str+"\n").getBytes());
//          out.flush();
//          if(str.startsWith("bye")){
//            break;
//          }
//        }catch(IOException e){
//          e.printStackTrace();
//          break;
//        }
//      }
//    }
//  }

    public static void reader() throws IOException{
//      final SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String str = sendArea.getText();
        out.write((str + "\n").getBytes());
        out.flush();
    }


    //Writer线程负责: 从服务器读in取信息显示到控制台.
    private class Writer extends Thread{
        InputStream in;
        public Writer(InputStream in) {
            this.in = in;
            setDaemon(true);
        }
        public void run(){
            try{
                int b;
                byte[] readByte = new byte[300];
                while(( b = in.read(readByte))!=-1){

                    String str=new String(readByte,0,b,"UTF-8");
                    recviveArea.append( str );
                    System.out.print(str);
                    readByte = new byte[200];//清空数组
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}