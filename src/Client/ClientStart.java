package Client;

import java.awt.EventQueue;

import UI.LoginUI;

public class ClientStart {
	//开始登录
	public static void main(String[] args) {
		//打开登录界面
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI frame = new LoginUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
