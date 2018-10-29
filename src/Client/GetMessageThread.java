package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import UI.ChatUI;

public class GetMessageThread extends Thread {
	private PrintWriter chatWriter;
	private BufferedReader chatReader;
	private JTextField feedback;
	private JTextArea textArea;
	private String myName;
	private ChatUI UI;
	
	public GetMessageThread(PrintWriter chatWriter, BufferedReader chatReader, JTextField feedback, JTextArea textArea, String myName, ChatUI UI) {
		this.chatWriter = chatWriter;
		this.chatReader = chatReader;
		this.feedback = feedback;
		this.textArea = textArea;
		this.myName = myName;
		this.UI = UI;
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
		try {
			while(!Thread.interrupted()) {
				String request = chatReader.readLine();
				String code = request.substring(0, 2);
				String content = request.substring(2);
				
				if(code.equals("30")) {
					//处理获取用户名请求
					String response = "31"+myName;
					chatWriter.println(response);
					chatWriter.flush();
				}else if(code.equals("31")) {
					//处理获取用户名响应
					UI.setTitle("聊天~"+myName+" to "+content);
				}else if(code.equals("32")) {
					//处理聊天消息
					String[] m = content.split("@");
					textArea.append("FROM "+ m[0] +": "+ m[1] + "\r\n");
				}
			}
		} catch (Exception e) {
			feedback.setText("对方已经断开连接，请退出");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
