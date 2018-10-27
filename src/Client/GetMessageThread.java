package Client;

import java.io.BufferedReader;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GetMessageThread extends Thread {
	private BufferedReader chatReader;
	private JTextField feedback;
	private JTextArea textArea;
	
	public GetMessageThread(BufferedReader chatReader, JTextField feedback, JTextArea textArea) {
		this.chatReader = chatReader;
		this.feedback = feedback;
		this.textArea = textArea;
	}

	// 停止线程方法
	public void MyStop() {
		// 中断自身
		this.interrupt();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("开启聊天消息接收线程");
		while(!Thread.interrupted()) {
			
		}
	}
}
