package swing.SocketServer;

import java.util.*;
import java.io.*;


public class InfoDTO implements Serializable{
	public enum Info {
		MAKE , JOIN, EXIT, SEND , STATE
	}
	
	private String nickName;
	private String message;
	private Info command;
	private int step;
	private int moveX;
	private int skill;
	private int  seed;
	private String roomId;

	public String getNickName(){
		return nickName;
	}
	public int getSkill() {
		return skill;
	}
	public void setSkill(int skill) {
		this.skill = skill;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public Info getCommand(){
		return command;
	}
	public String getMessage(){
		return message;
	}
	public void setNickName(String nickName){
		this.nickName= nickName;
	}
	public void setCommand(Info command){
		this.command= command;
	}
	public void setMessage(String message){
		this.message= message; 
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getMoveX() {
		return moveX;
	}
	public void setMoveX(int moveX) {
		this.moveX = moveX;
	}
	public int getSeed() {
		return seed;
	}
	public void setSeed(int seed) {
		this.seed = seed;
	}
	
}