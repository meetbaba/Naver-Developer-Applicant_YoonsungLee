/**
 * 
 */
package com.mafiagame;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;


/**
 * @author 星失
 *
 */
public class ClntSock extends Socket{

	private static ClntSock cSocket;
	
	private ClntSock(String IP, int PORT) throws UnknownHostException, IOException{
		
		super(IP, PORT);
		Log.i("ClntSock", "持失切");
	}
	
	public static ClntSock getInstance(String IP, int PORT){
		
		if(cSocket==null){
			try {
				Log.i("ClntSock", "社掴持失 try");
				cSocket=new ClntSock(IP, PORT);
				Log.i("ClntSock", "ip: "+cSocket.getInetAddress()+"\n port: "+cSocket.getPort());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.i("ClntSock", "return socket");
		return cSocket;
	}
	
	
}
