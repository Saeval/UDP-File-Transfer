package server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPServer {
	
//	public final static String FILE_TO_SEND = "/home/saeval/Documenti/Lab project/Samples/LOGOHD MP4.ts";
//	public final static String FILE_TO_SEND = "./LOGOHD MP4 INFRONT.ts";
	public final static int SOURCE_PORT = 4444;
	public final static int DESTINATION_PORT = 8001;
	
	private DatagramSocket serverSocket = null;
	private InetAddress IPAddress = null;
	private byte[] sendData = new byte[1316];
	
    private FileInputStream fis = null;
    private BufferedInputStream bis = null;
    private String fileToSend = "";
    private File myFile = null;
	
	public UDPServer(){		
		
		try {
//			serverSocket = new DatagramSocket(SOURCE_PORT);
			serverSocket = new DatagramSocket(SOURCE_PORT, InetAddress.getByName("10.80.5.3"));
		} catch (SocketException e) {
			System.err.println("Couldn't create serverSocket");
			e.printStackTrace();
		} catch (UnknownHostException e2) {
			System.err.println("Unknown host");
			e2.printStackTrace();
		}
	    
    	try {
			IPAddress = InetAddress.getByName("224.2.2.2");
		} catch (UnknownHostException e) {
			System.err.println("Couldn't initialize IPAddress");
			e.printStackTrace();
		}
	}

	public void closeTransfer() {
		try {
			fis.close();
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Couldn't close something");
			e.printStackTrace();
		}
	}

	public boolean sendFile(DatagramSocket serverSocket, byte[] sendData,
								 BufferedInputStream bis, InetAddress IPAddress) 
								 throws IOException, InterruptedException {
		
		try {
    		myFile = new File(fileToSend);
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't create file");
			e.printStackTrace();
		}
		
		while(bis.read(sendData, 0, sendData.length) != -1){
			
			DatagramPacket sendPacket = new DatagramPacket(
										sendData, sendData.length,
										IPAddress, DESTINATION_PORT);
			serverSocket.send(sendPacket);

//			Thread.sleep(100);
		}
		
		return true;
	}
	
	public void sendFileLoop() throws Exception{
		if ( sendFile(getServerSocket(), getSendData(), getBis(), getIPAddress()) == true)
			sendFileLoop();
	}
	
	public InetAddress getIPAddress() {
		return IPAddress;
	}
	
	public DatagramSocket getServerSocket() {
		return serverSocket;
	}
	
	public byte[] getSendData() {
		return sendData;
	}
	
	public void setFileToSend(String fileToSend) {
		this.fileToSend = fileToSend;
	}
	
	public BufferedInputStream getBis() {
		return bis;
	}

}
