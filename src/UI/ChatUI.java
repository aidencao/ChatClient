package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ChatUI extends JFrame {

	private JPanel contentPane;
	private JTextField feedback;

	/**
	 * Create the frame.
	 */
	public ChatUI(BufferedReader chatReader, PrintWriter chatWriter, String name) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 301);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(14, 13, 404, 160);
		contentPane.add(textArea);
		
		JButton button = new JButton("发送");
		button.setBounds(158, 214, 113, 27);
		contentPane.add(button);
		
		JLabel label = new JLabel("提示信息：");
		label.setBounds(24, 185, 89, 18);
		contentPane.add(label);
		
		feedback = new JTextField();
		feedback.setEnabled(false);
		feedback.setBounds(127, 182, 280, 24);
		contentPane.add(feedback);
		feedback.setColumns(10);
	}
}
