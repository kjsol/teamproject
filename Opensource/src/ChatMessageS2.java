import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class ChatMessageS2 extends Frame {
	TextArea display;
	Label info;
	List<ServerThread> list;
	
	public ServerThread SThread;
   
	public ChatMessageS2() {
		super("서버");
		info = new Label();
		add(info, BorderLayout.CENTER);
		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(300,250);
		setVisible(true);
	}
	
	public void runServer() {
		ServerSocket server;
		Socket sock;
		//ServerThread SThread;
		try {
			list = new ArrayList<ServerThread>();
			server = new ServerSocket(5000, 100);
			try {
				while(true) {
					sock = server.accept();
					//SThread = new ServerThread(this, sock, display, info);
					SThread = new ServerThread(this, sock, display);
					SThread.start();
					info.setText(sock.getInetAddress().getHostName() + " 서버는 클라이언트와 연결됨");
				}
			} catch(IOException ioe) {
				server.close();
				ioe.printStackTrace();
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
		
	public static void main(String args[]) {
		ChatMessageS2 s = new ChatMessageS2();
		s.runServer();
	}
		
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) { 
			System.exit(0);
		}
	}
}

class ServerThread extends Thread {
	Socket sock;
	BufferedWriter output;
	BufferedReader input;
	TextArea display;
	Label info;
	TextField text;
	String clientdata;
	//String serverdata = "";
	ChatMessageS2 cs;
	
	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_SENDWORDS = 1021;
	private static final int LOGOUT = 1004;
	
	//public ServerThread(ChatMessageS c, Socket s, TextArea ta, Label l) {
	public ServerThread(ChatMessageS2 c, Socket s, TextArea ta) {
		sock = s;
		display = ta;
		//info = l;
		cs = c;
		try {
			input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public void run() {
		ServerThread SThread = null;
		try {
			while((clientdata = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
				int command = Integer.parseInt(st.nextToken());
				int cnt = cs.list.size();
				
				switch(command) { 
					case REQ_LOGON : { // “1001|아이디”를 수신한 경우
						cs.list.add(this);    
						String ID = st.nextToken();
						String logms = st.nextToken();
						display.append("클라이언트 " + ID);
						display.append(logms + "\r\n");
						for (int i = 0; i < cnt; i++) {
			                  if (cs.list.get(i) != null) {
			                     SThread = (ServerThread)cs.list.get(i);
			                     SThread.output.write(ID + "이(가) 로그인 하였습니다.\r\n");
			                     SThread.output.flush();
			                  }
			               }
						break; 

					}
					case REQ_SENDWORDS : { // “1021|아이디|대화말”를 수신
						String ID = st.nextToken();
						String message = st.nextToken();
						display.append(ID + " : " + message + "\r\n"); //server
						for (int i = 0; i < cnt; i++) { 
							SThread = (ServerThread)cs.list.get(i);
							SThread.output.write(ID + " : " + message + "\r\n"); //client
							SThread.output.flush();
						}  
						break;
					}
					case LOGOUT : {  // 1004|아이디 로그아웃
						cs.list.remove(this);  //로그아웃하면 소켓 해제
						String ID = st.nextToken();
						String logms = st.nextToken();						
						display.append("클라이언트 " + ID);
						display.append(logms + "\r\n");
						
						for (int i = 0; i < cnt; i++) {
			                  if (cs.list.get(i) != null) {
			                     SThread = (ServerThread)cs.list.get(i);
			                     SThread.output.write(ID + "이(가) 로그아웃 되었습니다.\r\n");
			                     SThread.output.flush();
			                  }
			               }
						break; 	   
					}
				}
			}
		} catch(IOException e) {
			//cs.list.add(this);
			e.printStackTrace();
		}
		cs.list.remove(this);
		try {
			sock.close();
		} catch(IOException ea){
			ea.printStackTrace();
		}
	}
}