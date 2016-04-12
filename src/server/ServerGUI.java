package server;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerGUI {
	private UDPServer server = null;
	
	private JFrame frame = new JFrame("Configuration");
	private JPanel panel = new JPanel();
	
	private JTextArea filenameArea = new JTextArea("/home/saeval/Documenti/Lab project/Samples/LOGOHD MP4 INFRONT.ts");
	private JButton button = new JButton("Confirm (single)");
	private JTextArea outputArea = new JTextArea("");
	private JButton button2 = new JButton("Confirm (loop)");
	private JTextField sourceAddressField = new JTextField("192.168.1.169");
	
	public ServerGUI() {
		outputArea.setEditable(false);
		outputArea.setAutoscrolls(true);
		filenameArea.setWrapStyleWord(true);
		
		panel.setLayout(new GridLayout(6, 1));
		panel.add(new JLabel("File to send:"));
		panel.add(filenameArea);
		panel.add(new JLabel("IP Address of source (i.e. of this machine: "));
		panel.add(sourceAddressField);
		panel.add(button);
//		panel.add(outputArea);
		panel.add(button2);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					server = new UDPServer(sourceAddressField.getText());
					
					server.setFileToSend(filenameArea.getText());
					
					boolean done =  server.sendFile(server.getServerSocket(), server.getSendData(), 
									server.getBis(), InetAddress.getByName("224.2.2.2"));
					
//					server.sendFileLoop();
					
					if (done)
						server.closeTransfer();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					server = new UDPServer(sourceAddressField.getText());
					
					server.setFileToSend(filenameArea.getText());
					
					server.sendFileLoop();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
			}
		});
		
		frame.getContentPane().add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 400);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		ServerGUI gui = new ServerGUI();
		
	}
	
	

}
