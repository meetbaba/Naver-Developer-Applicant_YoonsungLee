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


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
	
	
	
	@Override
	public void onClick(View v){
		
		switch(v.getId()){
		
		case R.id.button_login:
			Log.i("Ac_Login", "Login button Clicked");
			
			/**
			 * a1XXXID:PW
			 */
			String flag="a1";
			Log.i("Ac_Login", "flag : "+flag);
			String[] msg=new String[2];
			msg[0]=InputBox_Name.getText().toString();
			msg[1]=InputBox_Password.getText().toString();
			mSender.sendMsg(Functions.makeMsgFromAndroid(flag, msg)); 

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
		
		@Override
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
				String fa2=new String("a2");
				
				Log.i("Ac_Login handler", ""+flag+" //fa2: "+fa2);
				
				if(flag.equals(fa2)){
					
					
					/**
					 * 	a2XXXID:LINE:GOAL:현재작업량
					 */
					String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
					
					
					/**
					 *  activity 전환
					 */
					Intent intent=new Intent(Ac_Login.this, Ac_ChoiceMenu.class);
					
					intent.putExtra("MANAGER_NAME", contentArr[0]);
					intent.putExtra("MANAGER_POSITION", contentArr[1]);
					
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(getApplicationContext(), "리시브 :flag아님", Toast.LENGTH_LONG).show();
				}
				
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
