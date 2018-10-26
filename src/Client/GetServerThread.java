package Client;

import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JTextField;

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
				
				//用户列表
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
