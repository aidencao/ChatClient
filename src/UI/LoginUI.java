package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Client.GetServerThread;
import Client.HeartBeatSenderThread;

import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class LoginUI extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JTextField listeningPort;
	private JTextField feedback;

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Create the frame.
	 */
	public LoginUI() {
		try {
			// 与监听端口连接
			Socket socket = new Socket("localhost", 4001);
			// 创建输入输出流
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(new DataOutputStream(socket.getOutputStream()));

			setTitle("登录");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);

			JLabel label = new JLabel("用户名：");
			label.setBounds(38, 50, 87, 18);

			JLabel label_1 = new JLabel("监听端口：");
			label_1.setBounds(38, 92, 87, 18);

			username = new JTextField();
			username.setBounds(143, 47, 140, 24);
			username.setColumns(10);

			listeningPort = new JTextField();
			listeningPort.setBounds(143, 89, 140, 24);
			listeningPort.setColumns(10);

			JButton button = new JButton("登录");
			// 设置登录按钮效果
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						feedback.setText("");
						// 获取用户名监听端口
						String user = username.getText();
						String port = listeningPort.getText();

						// 输入合法才可登录
						if (!user.equals("") && !port.equals("") && isNumeric(port)) {
							// 发送到服务器
							writer.println("00");// 00作为登录命令
							writer.println(user);
							writer.println(port);
							writer.flush();

							// 接收反馈
							String response = reader.readLine();
							// 判断
							if (response.equals("0")) {
								System.out.println("登录成功");
								//打开主界面，关闭登录界面
								MainFrameUI mainFrame = new MainFrameUI(reader, writer, user, Integer.parseInt(port));
								mainFrame.setVisible(true);
								setVisible(false);
							} else if (response.equals("1")) {
								feedback.setText("用户名被占用");
							} else if (response.equals("2")) {
								feedback.setText("端口被占用");
							}
						} else {
							feedback.setText("输入非法");
						}
					} catch (IOException e) {
						feedback.setText("服务器失效");
						System.out.println("服务器失效");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			button.setBounds(182, 173, 63, 27);

			feedback = new JTextField();
			feedback.setEditable(false);
			feedback.setBounds(143, 131, 140, 24);
			feedback.setEnabled(false);
			feedback.setColumns(10);

			JLabel label_2 = new JLabel("提示信息：");
			label_2.setBounds(38, 134, 75, 18);
			contentPane.setLayout(null);
			contentPane.add(label);
			contentPane.add(label_1);
			contentPane.add(username);
			contentPane.add(listeningPort);
			contentPane.add(button);
			contentPane.add(feedback);
			contentPane.add(label_2);

			// 关闭窗口时调用的方法
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					// 发送停止消息给服务器，让其停止相应线程
					writer.println("01");
					writer.flush();
				}
			});
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
