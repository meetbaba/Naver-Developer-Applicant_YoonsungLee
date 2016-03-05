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
			
			Log.i("AsyncTask", "Instance �޾Ҿ�");
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
		 * password ��ǥ��
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
					 * 	a2XXXID:LINE:GOAL:�����۾���
					 */
					String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
					
					
					/**
					 *  activity ��ȯ
					 */
					Intent intent=new Intent(Ac_Login.this, Ac_ChoiceMenu.class);
					
					intent.putExtra("MANAGER_NAME", contentArr[0]);
					intent.putExtra("MANAGER_POSITION", contentArr[1]);
					
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(getApplicationContext(), "���ú� :flag�ƴ�", Toast.LENGTH_LONG).show();
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
