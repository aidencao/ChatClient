package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import UI.ChatUI;
import UI.MainFrameUI;

//登录后接收服务器消息的线程
public class GetServerThread extends Thread {
	private BufferedReader reader;
	private JComboBox<String> userList;
	private JTextField feedback;
	private String name;

	public GetServerThread(BufferedReader reader, JComboBox userList, JTextField feedback, String name) {
		this.reader = reader;
		this.feedback = feedback;
		this.userList = userList;
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
		System.out.println("开始接收服务器消息");
		try {
			while(!Thread.interrupted()) {
				String response = reader.readLine();
				String code = response.substring(0, 2);
				String content = response.substring(2);
				
				//处理返回结果
				if(code.equals("11")) {
					// 获取当前所有用户
					String[] u = content.split(",");
					
					// 改变列表
					userList.removeAllItems();
					for (int j = 0; j < u.length; j++) {
						//不显示本人
						if(!u[j].equals(name)){
							userList.addItem(u[j]);
						}
					}
				}else if(code.equals("22")) {
					//申请与某用户链接失败
					feedback.setText("当前用户已离线");
				}else if(code.equals("21")) {
					//申请获取用户端口号成功
					
					//分割结果
					String[] s = content.split(",");
					String chatName = s[0];
					int port = Integer.parseInt(s[1]);
					//与对方建立链接，并建立输入输出流
					Socket chatSocket = new Socket("localhost", port);
					BufferedReader chatReader = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
					PrintWriter chatWriter = new PrintWriter(new DataOutputStream(chatSocket.getOutputStream()));
					//打开聊天页面
					ChatUI chatUI = new ChatUI(chatReader, chatWriter, chatName);
					chatUI.setVisible(true);
				}
				
			}
		} catch (IOException e) {
			System.out.println("服务器失效，接收消息线程退出");
			feedback.setText("服务器失效，请退出");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
