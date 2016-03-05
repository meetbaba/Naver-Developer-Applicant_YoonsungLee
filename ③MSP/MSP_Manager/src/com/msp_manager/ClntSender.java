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

import java.io.DataOutputStream;
import java.io.IOException;

import android.util.Log;

/**
 * @author 윤성
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
		Log.i("Sender Thread", "Sender 생성자 / isMsg: "+getIsMsg());
		Log.i("Sender Thread", "ip: "+mSock.getInetAddress() +"\nport :"+mSock.getPort());
		try{
			out = new DataOutputStream(mSock.getOutputStream());
			Log.i("Sender Thread", "Out Stream 생성");

		}catch(Exception o){
			o.printStackTrace();
		}
	}
	
	public static ClntSender getInstance(ClntSock sock){
		
		if(cSender==null){
			Log.i("Sender Thread", "try 생성 Sender");

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
