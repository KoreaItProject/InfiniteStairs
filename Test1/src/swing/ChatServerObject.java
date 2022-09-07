package swing;

import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

class ChatServerObject 
{
	private ServerSocket serverSocket;
	private List <ChatHandlerObject> list;
	public ChatServerObject(){
		try{
			serverSocket= new ServerSocket (9500);
			System.out.println("서버 준비 완료");
			list = new  ArrayList<ChatHandlerObject>();

			int [] result=new int [new Setting().getBlockCount()];
			result[0] = 0;
			for (int i = 1; i < result.length; i++) {
             	   result[i] = (int) (Math.random() * 2);
      	  }
			
			while(true){
				Socket socket = serverSocket.accept();
				ChatHandlerObject handler = new  ChatHandlerObject(socket,list,result);  //스레드를 생성한 것이랑 동일함! 떄문에 시자해주어야 
				handler.start();  //스레드 시작- 스레드 실행
				list.add(handler);  //핸들러를 담음( 이 리스트의 개수가 클라이언트의 갯수!!)
			}//while
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) 
	{
		new ChatServerObject();
	}
    
}