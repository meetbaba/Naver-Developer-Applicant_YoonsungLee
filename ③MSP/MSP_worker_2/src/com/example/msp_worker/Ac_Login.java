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
 * 본 프로그램 MSP_Worker는 회사 생산 라인에서 일하는 작업자를 위해 만들어 졌으며, 모두 수기로 기록 후 엑셀로 따로 저장하는 MSP의 시스템을 개선하기위해 만들어졌다.
 * 이 프로그램을 MSP_Woker를 통해서 작업자는 손쉽게 작업량을 기록할 수 있고, 현재 작업 진행상황을 파악할 수 있다. 
 * 또한 자신이 작업한 내용에 대한 정확한 정보를 관리자에게 제시할 수 있고  추가적인 문서작업이 필요 없게 된다. 
 * 
 * 
 * 
 */
package com.example.msp_worker;


import java.nio.ByteBuffer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.renderscript.Byte2;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ac_Login extends Activity implements OnClickListener{

	
	
	public static int SOCKET_PRIORITY=10;
	public static int MAINTHREAD_PRIORITY=5;
	
	
	Button btnLogin;			/*Connect Button*/
	EditText InputBox_Name;	/*input text box for getting IP*/
	EditText InputBox_Password;
	
	
	ConnectServer mConnectAsync;
	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;
	
	String msgType_send;
	byte msgType_recieved;
	byte[] msgContent_send;
	byte[] sendMsg;
	
	char divisionSign='|';
	
	
	public boolean checkLoginAuthorization(){
		
		return true;
	}
	
	
	
	public void requestLogin(String strName, String strPassword){
		
//		Log.i("Ac_Login", "function: requestLogin");
		msgType_send="01";
		Log.i("Ac_Login", "flag : "+msgType_send);
		String[] msg=new String[2];
		msg[0]=strName;
		msg[1]=strPassword;
//		
//		Log.i("making msg", "content: "+msg);
//		
//		byte[] forSendBytes_msgType=Functions.charArrayToBytes(msgType_send);
//		Log.i("making msg", "type num of bytes"+forSendBytes_msgType.length);
//		byte[] forSendBytes_msgClength=Functions.intToByteArray(msg.length());
//		Log.i("making msg", "Clength num of bytes"+forSendBytes_msgClength.length);
//	    Log.i("making msg", "int to hex"+Integer.toHexString(msg.length()));
//	    Log.i("making msg", "byte to hex"+Functions.byteArrayToHex(forSendBytes_msgClength));
//
//
//		byte[] forSendBytes_msgContent=msg.getBytes();
//		Log.i("making msg", "Content num of bytes"+forSendBytes_msgContent.length);
//
//
//		ByteBuffer byteBuffer = ByteBuffer.allocate(forSendBytes_msgType.length
//				+ forSendBytes_msgClength.length
//				+ forSendBytes_msgContent.length);
//		byteBuffer.put(forSendBytes_msgType); // message type flag
//		byteBuffer.put(forSendBytes_msgClength); // msg Length
//		byteBuffer.put(forSendBytes_msgContent);
//
//		Log.i("Ac_Login", "byte buffer puts end");
//
//		
//		byte[] bufferArr = byteBuffer.array();
//		
//		Log.i("Ac_Login", "byte bufferArray created");
//		
//		String strSend=new String(bufferArr);
//		
//		Log.i("Ac_Login", "buffer allocate to String ");
//		
//		Log.w("Ac_Login", "maked message :"+strSend);
//		Log.i("Ac_Login", "byte length: "+bufferArr.length);
		mSender.sendMsg(Functions.makeMsgFromAndroid(msgType_send, msg)); 
	}
	
	@Override
	public void onClick(View v){
		
		switch(v.getId()){
		
		case R.id.button_login:
			Log.i("Ac_Login", "Login button Clicked");
			/**
			 * make socket
			 */
			
			/**
			 * Login 요청
			 */
			requestLogin(InputBox_Name.getText().toString(),InputBox_Password.getText().toString());

			/**
			 * 	서버로부터 승인 받으면 핸들러가 다음 액티비티로 acitivy로 넘어가게 함.
			 */
			
			
			//connect to TCP
			break;
		}
	}
	
	private class ConnectServer extends AsyncTask<Integer, Integer, ClntSock>
	{

		@Override
		protected ClntSock doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			Log.i("AsyncTask", "do IN back");
			
			Log.i("AsyncTask", "Instance 받았어");
			return ClntSock.getInstance(Functions.IP, Functions.PORT);
		}
		
		protected void onPostExecute(ClntSock sock){
			
			if(sock!=null){
				
				Toast.makeText(getApplicationContext(), "Connection Success ", Toast.LENGTH_LONG).show();

				/**
				 *  In Every Activity, this process must be executed.
				 *  
				 *  until -
				 */
				
				mSock=sock;
				mSender=ClntSender.getInstance(mSock);
				mReciever=ClntReciever.getInstance(mSock);
				mReciever.setHandler(mHandler);
				
//				mSender.setPriority(SOCKET_PRIORITY);
//				mReciever.setPriority(SOCKET_PRIORITY);
				
				
				Log.i("Connect TCP Server", "Sender Thread:"+mSender.getPriority()+"\nReciever Thread: "+mReciever.getPriority());
				
				/**
				 *  - here
				 */
				
				Log.i("Ac_Login", "socketThread Run");
				
				mSender.start();
				mReciever.start();
			}else{
				Toast.makeText(getApplicationContext(), "Connection Failed ", Toast.LENGTH_LONG).show();

			}
		}

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	
		Log.e("Start session filter", "start");
		
		btnLogin=(Button)findViewById(R.id.button_login);
		btnLogin.setOnClickListener(this);
		
		InputBox_Name=(EditText)findViewById(R.id.inputBox_name);
		InputBox_Password=(EditText)findViewById(R.id.txt_password);
		
		/**
		 * password 별표시
		 */
		PasswordTransformationMethod PassWtm = new PasswordTransformationMethod();
		InputBox_Password.setTransformationMethod(PassWtm);
		
		
		mHandler=new Handler(Looper.getMainLooper()){
			
			
			
			@Override
			public void handleMessage(Message inputMessage) {
				
				int msglength;
				msglength=inputMessage.what;
				byte[] msgContent=new byte[msglength];
				msgContent=(byte[])inputMessage.obj;
				
				String flag=Functions.extractFlag(msgContent);
				String f02=new String("02");
				
				Log.i("Ac_Login handler", ""+flag+" //f01: "+f02);
				
				if(Integer.parseInt(flag)==Integer.parseInt(f02)){
					
					
					/**
					 * 	02XXXID:LINE:GOAL:현재작업량
					 */
					String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
					
					
					/**
					 *  activity 전환
					 */
					Intent intent=new Intent(Ac_Login.this, Ac_Working.class);
					
					intent.putExtra("WORKER_NAME", contentArr[0]);
					intent.putExtra("WORKER_LINE", contentArr[1]);
					intent.putExtra("WORKER_GOAL", contentArr[2]);
					intent.putExtra("WORKER_WORKED", contentArr[3]);
					
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(getApplicationContext(), "리시브 : 0x01아님", Toast.LENGTH_LONG).show();
				}
				
//				switch(inputMessage.what){
//				case 1 : 
//					
//					Log.d("Recieve Handler", "value = 1" );
//
//					
//					Intent intent=new Intent(Ac_Login.this, Ac_Working.class);
//					startActivity(intent);
//					finish();
//					// do something with UI
//					break;
//
//				}
			}    
		};
		
		
		mConnectAsync=new ConnectServer();
		mConnectAsync.execute(1);
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
