package swing.SocketServer;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.Random;

import swing.SocketServer.*;
import swing.SocketServer.InfoDTO.Info;

import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class ServerHandler extends Thread //처리해주는 곳(소켓에 대한 정보가 담겨있는 곳. 소켓을 처리함)
{
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket socket;
	//private InfoDTO dto;
	///private Info command;
	private List <ServerHandler> list;
	//생성자
	public ServerHandler(Socket socket, List <ServerHandler> list) throws IOException {
		
		this.socket = socket;
		this.list = list;
		writer = new ObjectOutputStream(socket.getOutputStream());
		reader = new ObjectInputStream(socket.getInputStream());
		//순서가 뒤바뀌면 값을 입력받지 못하는 상황이 벌어지기 때문에 반드시 writer부터 생성시켜주어야 함!!!!!!
		
	}
	public void run(){
		InfoDTO dto = null;
		
		
		try{
			while(true){
				dto=(InfoDTO)reader.readObject();
				System.out.println(dto.getRoomId());
				System.out.println(dto.getNickName());
				if(dto.getCommand()==Info.EXIT){
					System.out.println("종료");
					InfoDTO sendDto = new InfoDTO();
					//나가려고 exit를 보낸 클라이언트에게 답변 보내기
					
					sendDto=dto;
					writer.writeObject(sendDto);
					broadcast(sendDto);

					
					//reader.close();
					//writer.close();
					//socket.close();

					list.remove(this);

			
					break;
				} else if(dto.getCommand()==Info.JOIN){
					InfoDTO sendDto = new InfoDTO();
					sendDto=dto;
					sendDto.setCommand(Info.JOIN);
					sendDto.setNickName(dto.getNickName());
					
					System.out.println("조인"+ServerMain.room.get(dto.getRoomId()));
					
				
					if(isMember(dto.getNickName())){
						sendDto.setMessage(dto.getNickName()+"ERR");
						System.out.println("닉중복");
						broadcast(sendDto);
						
						
					}else{

						if(ServerMain.room.get(dto.getRoomId())==null){
							sendDto.setMessage(dto.getRoomId()+"ERR");
							System.out.println("방없음");
							broadcast(sendDto);
							
						}else{//방입장하기
							sendDto.setSeed(ServerMain.room.get(dto.getRoomId()));
							sendDto.setRoomId(dto.getRoomId());

							broadcast(sendDto);
						}
					}


				} else if(dto.getCommand()==Info.SEND){
					InfoDTO sendDto = new InfoDTO();
					sendDto=dto;
					sendDto.setCommand(Info.SEND);
	
				
					broadcast(sendDto);
				}
				else if(dto.getCommand()==Info.MAKE){
					InfoDTO sendDto = new InfoDTO();
					sendDto=dto;
					sendDto.setCommand(Info.MAKE);
					sendDto.setNickName(dto.getNickName());
					
					
				
					if(isMember(dto.getNickName())){
						sendDto.setMessage(dto.getNickName()+"ERR");
						broadcast(sendDto);
						
					}else{//방만들기

						ServerMain.member.add(dto.getNickName());
						int leftLimit = 97; // letter 'a'
						int rightLimit = 122; // letter 'z'
						int targetStringLength = 12;
						Random random = new Random();
						String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();	
						/* 
						int seed=400;
	   					Random rand = new Random();
       					rand.setSeed(seed);
 
						for (int i = 1; i <= 5; i++) {
							System.out.print(rand.nextInt(2) + " ");
				
						}*/
						Random rand = new Random();
						int seed = rand.nextInt(10000);
				
						ServerMain.room.put(generatedString, seed);

						sendDto.setRoomId(generatedString);
						sendDto.setSeed(seed);
						broadcast(sendDto);
						

					}
					
				
				}else if (dto.getCommand()==Info.STATE){
					InfoDTO sendDto = new InfoDTO();
					sendDto=dto;
					sendDto.setCommand(Info.STATE);
					sendDto.setNickName(dto.getNickName());
					sendDto.setMessage(dto.getMessage());
					sendDto.setRoomId(dto.getRoomId());
					broadcast(sendDto);

				}
			}//while

		} catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	
		
	}
	//다른 클라이언트에게 전체 메세지 보내주기
	public void broadcast(InfoDTO sendDto) throws IOException {
		for(ServerHandler handler: list){
			handler.writer.writeObject(sendDto); //핸들러 안의 writer에 값을 보내기
			handler.writer.flush();  //핸들러 안의 writer 값 비워주기
		}
	}
	public boolean isMember(String nick){
		for(int i=0;i<ServerMain.member.size();i++){
			if(nick.equals(ServerMain.member.get(i))){
				return true;
			}
			
		}
		return false;
	}
}