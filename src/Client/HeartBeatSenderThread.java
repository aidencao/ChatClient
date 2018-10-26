package Client;

import java.io.PrintWriter;

import javax.swing.JTextField;

//用于登录后向服务器发送心跳
public class HeartBeatSenderThread extends Thread {
	private PrintWriter writer;
	private JTextField feedback;

	public HeartBeatSenderThread(PrintWriter writer, JTextField feedback) {
		this.writer = writer;
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
		System.out.println("开始发送心跳消息，每15秒发送一次");
		try {
			while(!Thread.interrupted()) {
				System.out.println("发送心跳");
				writer.println("10");
				writer.flush();
				sleep(15000);
			}
		} catch (InterruptedException e) {
			System.out.println("心跳发送线程退出");
			feedback.setText("服务器失效，请退出");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
