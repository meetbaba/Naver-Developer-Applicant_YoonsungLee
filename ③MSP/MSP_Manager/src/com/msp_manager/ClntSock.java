/**
 * release 0.1  : ������Ÿ�� (Prototype)
 * 
 * ���� �ϼ� ��¥ : 2015-06-12
 * 
 * ������ : ������ (Yoonsung Lee)
 *  
 *  �� ���α׷��� ���۱��� �����ڿ��� ������ �������� ������� ���� ����, ����, ������ �����մϴ�.
 * 
 * 
 * ���� ���� : 
 * ���ִ��б� �ý��۰��� ����  ������Ʈ
 * ��米�� : �̼���
 * �����ü : MSP (���� ������)
 * 
 * 
 * ���α׷� ���� :
 * 
 * �� ���α׷��� ���ִ��б� 2015�⵵ 1�б� �ý��� ���� �������� ����� ���� ������Ʈ �������� ���������.
 * �� ���α׷� MSP_Manager�� ȸ�� ���� ���� �/���� ������ ��� ����� ��� �� ������ ���� �����ϴ� MSP�� �ý����� �����ϱ����� ���������.
 * �� ���α׷��� MSP_Manager�� ���ؼ� ������ ��å�� �����ڴ� �ս��� �۾����� ������ �� �ְ�, ��� ������ �����޾� �����δ� ���ϰ� 
 * �۾��� �����ϰ� ��ǥ�޼��� ���� ���������� ���̼�, �׸��� ����ũ ��Ȳ�� ���� �ﰢ���� ��ó�� ���� �� �ִ�.
 * 
 * 
 */
package com.msp_manager;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;


/**
 * @author ����
 *
 */
public class ClntSock extends Socket{

	private static ClntSock cSocket;
	
	private ClntSock(String IP, int PORT) throws UnknownHostException, IOException{
		
		super(IP, PORT);
		Log.i("ClntSock", "������");
	}
	
	public static ClntSock getInstance(String IP, int PORT){
		
		if(cSocket==null){
			try {
				Log.i("ClntSock", "���ϻ��� try");
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
