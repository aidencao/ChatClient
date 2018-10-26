package Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextField;

public class PortMonitorThread extends Thread {
	private int port;
	private JTextField feedback;

	public PortMonitorThread(int port, JTextField feedback) {
		this.port = port;
		this.feedback = feedback;
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
				Socket chatsocket = ss.accept();
			}
		} catch (IOException e) {
			System.out.println("监听端口失败，端口监听线程退出");
			feedback.setText("监听端口"+port+"失败，请退出");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
