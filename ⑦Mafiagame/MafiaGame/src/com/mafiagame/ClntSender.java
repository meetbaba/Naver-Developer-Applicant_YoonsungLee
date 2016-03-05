/**
 * 
 */
package com.mafiagame;

import java.io.DataOutputStream;
import java.io.IOException;

import android.util.Log;

/**
 * @author 星失
 *
 */
public class ClntSender extends Thread{

	private static ClntSender cSender;
	
	

	ClntSock mSock;
	DataOutputStream out;
	private boolean isMsg;
	private byte[] msg;
	
	private ClntSender(ClntSock sock){
		
		this.mSock=sock;
		setIsMsg(false);
		Log.i("Sender Thread", "Sender 持失切 / isMsg: "+getIsMsg());
		Log.i("Sender Thread", "ip: "+mSock.getInetAddress() +"\nport :"+mSock.getPort());
		try{
			out = new DataOutputStream(mSock.getOutputStream());
			Log.i("Sender Thread", "Out Stream 持失");

		}catch(Exception o){
			o.printStackTrace();
		}
	}
	
	public static ClntSender getInstance(ClntSock sock){
		
		if(cSender==null){
			Log.i("Sender Thread", "try 持失 Sender");

			cSender=new ClntSender(sock);
		}
		Log.i("Sender Thread", "return Sender");
		return cSender;
	}
	
	private boolean getIsMsg(){
		return this.isMsg;
	}
	
	private void setIsMsg(boolean setValue){
		this.isMsg=setValue;
		Log.i("Sender", "Setting isMsg : "+this.isMsg);
	}
	
	/***
	 *   	User of this Thread can only use this function except getInstance
	 * @param msg : byte array type what you want to send
	 * 
	 */
	public void sendMsg(byte[] msg){
		this.msg=msg;
		Log.i("Sender", "SendMsg: "+msg.toString());
		setIsMsg(true);
	}
	
	private byte[] getMsg(){
		return this.msg;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(out!=null){
			
			
			if(!mSock.isConnected()){
				try {
					out.close();
					mSock.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			if(getIsMsg()){
				Log.i("Sender", "msg is "+getIsMsg());
				try {
					Log.i("Sender", "write ("+getMsg().length+") : "+getMsg());
					out.write(getMsg());
					Log.i("Sender", "write done");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setIsMsg(false);
			}else{
				//Log.i("Sender", "message do not exist");
			}
		}
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

}
