package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextField;

import UI.ChatUI;

public class PortMonitorThread extends Thread {
	private int port;
	private JTextField feedback;
	private String name;

	public PortMonitorThread(int port, JTextField feedback, String name) {
		this.port = port;
		this.feedback = feedback;
		this.name = name;
	}

	// 停止线程方法
	public void MyStop() {
		// 中断自身
		this.interrupt();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("开始监听端口：" + port);
		try {
			ServerSocket ss = new ServerSocket(port);
			while (!Thread.interrupted()) {
				Socket chatSocket = ss.accept();
				//建立输入输出流
				BufferedReader chatReader = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
				PrintWriter chatWriter = new PrintWriter(new DataOutputStream(chatSocket.getOutputStream()));
				//打开聊天页面
				ChatUI chatUI = new ChatUI(chatReader, chatWriter, "", name);
				chatUI.setVisible(true);
			}
		} catch (Exception e) {
			System.out.println("监听端口失败，端口监听线程退出");
			feedback.setText("监听端口"+port+"失败，请退出");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
