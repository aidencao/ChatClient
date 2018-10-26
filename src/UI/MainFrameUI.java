package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Client.GetServerThread;
import Client.HeartBeatSenderThread;
import Client.PortMonitorThread;

import javax.swing.JMenuBar;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class MainFrameUI extends JFrame {

	private JPanel contentPane;
	private JTextField feedback;

	/**
	 * Create the frame.
	 */
	public MainFrameUI(BufferedReader reader, PrintWriter writer, String name, int port) {
		setTitle(name + "~主界面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox userList = new JComboBox();
		userList.setBounds(116, 52, 226, 24);
		contentPane.add(userList);

		JLabel label = new JLabel("用户列表：");
		label.setBounds(12, 55, 90, 18);
		contentPane.add(label);

		JLabel label_1 = new JLabel("提示信息：");
		label_1.setBounds(14, 116, 90, 18);
		contentPane.add(label_1);

		feedback = new JTextField();
		feedback.setEnabled(false);
		feedback.setBounds(116, 113, 226, 24);
		contentPane.add(feedback);
		feedback.setColumns(10);

		JButton button = new JButton("聊天");
		button.setBounds(158, 183, 113, 27);
		contentPane.add(button);

		// 打开心跳发送线程、接收消息线程和端口监听线程
		PortMonitorThread portMonitorThread = new PortMonitorThread(port, feedback);
		portMonitorThread.start();
		HeartBeatSenderThread heartBeatSenderThread = new HeartBeatSenderThread(writer, feedback);
		heartBeatSenderThread.start();
		GetServerThread getServerThread = new GetServerThread(reader, userList, feedback, name);
		getServerThread.start();

		// 关闭窗口时调用的方法
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// 发送停止消息给服务器，让其停止相应线程
				heartBeatSenderThread.MyStop();
				getServerThread.MyStop();
				portMonitorThread.MyStop();
				writer.println("01");
				writer.flush();
			}
		});
	}
}
