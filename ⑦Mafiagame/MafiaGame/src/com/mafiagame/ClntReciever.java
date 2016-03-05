/**
 * 
 */
package com.mafiagame;

import java.io.DataInputStream;
import java.io.IOException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author 辣己
 *
 */
public class ClntReciever extends Thread{

	private static ClntReciever cReciever;
	
	DataInputStream in;
	ClntSock mSock;
	private Handler mHandler;
	
	private ClntReciever(ClntSock sock){
		this.mSock=sock;
		Log.i("Reciever Thread", "Reciever 积己磊 ");
		Log.i("Reciever Thread", "ip: "+mSock.getInetAddress() +"\nport :"+mSock.getPort());
		try{
			in=new DataInputStream(mSock.getInputStream());
			Log.i("Reciever Thread", "InputStream 肯丰 ");

		}catch(Exception o){
			o.printStackTrace();
		}
	}
	
	public static ClntReciever getInstance(ClntSock sock){
		if(cReciever==null){
			Log.i("Reciever Thread", "try 积己 Reciever");

			cReciever=new ClntReciever(sock);
		}
		Log.i("Reciever Thread", "return Reciever");
		return cReciever;
	}
	
	/**
	 *   In MainThread, the Activity code must call this setHandler Function 
	 *   in onCreate() Method allocating their own Handler.
	 *   
	 * @param handler : each Activity defined handler
	 */
	public void setHandler(Handler handler){
		this.mHandler=handler;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] flagByte=new byte[1024];
		int msglength=-1;
		while(in != null){
			
			if(!mSock.isConnected()){
				try {
					in.close();
					mSock.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			try {
				Log.i("Reciever", "try read");

				msglength=in.read(flagByte);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Log.i("Reciever", "read is over / length: "+msglength);

			
			if(msglength != -1){
				Log.i("Reciever", "SendMsg to UI Thread using Handler");
				Message msg=Message.obtain(mHandler, msglength, flagByte);
				
				mHandler.sendMessage(msg);
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
