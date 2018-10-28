package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Client.GetMessageThread;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ChatUI extends JFrame {

	private JPanel contentPane;
	private JTextField feedback;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public ChatUI(BufferedReader chatReader, PrintWriter chatWriter, String chatName, String myName) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(14, 44, 404, 199);
		contentPane.add(textArea);

		JButton button = new JButton("发送");
		// 监听点击发送事件
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				feedback.setText("");
				// 获取消息内容并发送
				String content = textField.getText();
				if (!content.equals("")) {
					String send = "32" + myName + "@" + content;
					chatWriter.println(send);
					chatWriter.flush();

					// 在聊天框内显示发送，并清空发送内容
					textArea.append("SEND: " + content + "\r\n");
					textField.setText("");
				} else {
					feedback.setText("请输入聊天内容");
				}
			}
		});
		button.setBounds(160, 284, 113, 27);
		contentPane.add(button);

		JLabel label = new JLabel("提示信息：");
		label.setBounds(14, 13, 89, 18);
		contentPane.add(label);

		feedback = new JTextField();
		feedback.setEditable(false);
		feedback.setBounds(117, 10, 280, 24);
		contentPane.add(feedback);
		feedback.setColumns(10);

		// 启动消息接收线程
		GetMessageThread getMessageThread = new GetMessageThread(chatWriter, chatReader, feedback, textArea, myName,
				this);

		textField = new JTextField();
		textField.setBounds(91, 256, 306, 24);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel label_1 = new JLabel("输入：");
		label_1.setBounds(24, 259, 53, 18);
		contentPane.add(label_1);
		getMessageThread.start();

		// 判断是否已经获取对方用户名，并发送获取用户名请求
		if (chatName.equals("")) {
			chatWriter.println("30");
			chatWriter.flush();
		} else {
			setTitle("聊天~" + myName + " to " + chatName);
		}

		// 关闭窗口时调用的方法
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				chatWriter.close();
				// 发送停止消息给对方，让其停止相应线程
				chatWriter.println("03");
				chatWriter.flush();
			}
		});
	}
}
