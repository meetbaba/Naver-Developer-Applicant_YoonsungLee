/**
 * release 0.1  : 프로토타입 (Prototype)
 * 
 * 버전 완성 날짜 : 2015-06-12
 * 
 * 개발자 : 이윤성 (Yoonsung Lee)
 *  
 *  본 프로그램의 저작권은 개발자에게 있으며 개발자의 허락없이 무단 복제, 공개, 배포를 불허합니다.
 * 
 * 
 * 관련 사항 : 
 * 아주대학교 시스템공학 수업  프로젝트
 * 담당교수 : 이성주
 * 관계업체 : MSP (수원 소재지)
 * 
 * 
 * 프로그램 설명 :
 * 
 * 본 프로그램은 아주대학교 2015년도 1학기 시스템 공학 수업에서 진행된 최종 프로젝트 과정에서 만들어졌다.
 * 본 프로그램 MSP_Manager는 회사 생산 라인 운영/관리 사항을 모두 수기로 기록 후 엑셀로 따로 저장하는 MSP의 시스템을 개선하기위해 만들어졌다.
 * 이 프로그램을 MSP_Manager를 통해서 관리자 직책의 관계자는 손쉽게 작업량을 관리할 수 있고, 통계 정보를 제공받아 이전부다 편리하게 
 * 작업을 관리하고 목표달성을 위한 전략수립의 용이성, 그리고 리스크 상황에 대한 즉각적인 대처를 도울 수 있다.
 * 
 * 
 */
package com.msp_manager;

import java.io.DataInputStream;
import java.io.IOException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author 윤성
 *
 */
public class ClntReciever extends Thread{

	private static ClntReciever cReciever;
	
	DataInputStream in;
	ClntSock mSock;
	private Handler mHandler;
	
	private ClntReciever(ClntSock sock){
		this.mSock=sock;
		Log.i("Reciever Thread", "Reciever 생성자 ");
		Log.i("Reciever Thread", "ip: "+mSock.getInetAddress() +"\nport :"+mSock.getPort());
		try{
			in=new DataInputStream(mSock.getInputStream());
			Log.i("Reciever Thread", "InputStream 완료 ");

		}catch(Exception o){
			o.printStackTrace();
		}
	}
	
	public static ClntReciever getInstance(ClntSock sock){
		if(cReciever==null){
			Log.i("Reciever Thread", "try 생성 Reciever");

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
