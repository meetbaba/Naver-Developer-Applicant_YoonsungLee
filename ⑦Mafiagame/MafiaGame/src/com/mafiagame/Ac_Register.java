/**
 * 
 */
package com.mafiagame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author 윤성
 *
 */
public class Ac_Register extends Activity implements OnClickListener{

	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;
	
	EditText editTxt_name;
	Button btn_register;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.Register_button_register : 
			
			String flag="01";
			String[] content=new String[1];
			content[0]=editTxt_name.getText().toString();
			
			mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));
			
			break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		editTxt_name=(EditText)findViewById(R.id.Register_inputbox_name);
		btn_register=(Button)findViewById(R.id.Register_button_register);
		
		btn_register.setOnClickListener(this);
		
		mHandler=new MsgHandler(Looper.getMainLooper());
		
		mSock = ClntSock.getInstance(Functions.IP, Functions.PORT);
		mSender=ClntSender.getInstance(mSock);
		mReciever=ClntReciever.getInstance(mSock);
		mReciever.setHandler(mHandler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ac__connection, menu);
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

	class MsgHandler extends Handler{

		public MsgHandler(Looper mainLooper) {
			// TODO Auto-generated constructor stub
			super(mainLooper);
		}

		@Override
		public void handleMessage(Message inputMessage) {
			// TODO Auto-generated method stub
			
			int msglength;
			msglength=inputMessage.what;
			byte[] msgContent=new byte[msglength];
			msgContent=(byte[])inputMessage.obj;
			
			String flag=Functions.extractFlag(msgContent);
			String f02=new String("02");
			Log.i("Ac_Register handler", ""+flag);
			
			if(Integer.parseInt(flag)==Integer.parseInt(f02)){
				/**
				 * 	packet 안에 flag 다음에 4byte로 위치하도록한 int값의 msg content length를 뽑아내는 과정
				 */
				String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
				
				
				/**
				 *  activity 전환
				 */
				Intent intent=new Intent(Ac_Register.this, Ac_ReadyGame.class);
				
				intent.putExtra("PLAYER_NAME", editTxt_name.getText().toString());
				intent.putExtra("NUM_USERS", contentArr[0]);
				
				/**
				 * f02 msg content : 인원수 : 유저1 : 유저2 : 유저3 : 유저 4
				 */
				int numUsers=Integer.parseInt(contentArr[0]);
				
				if(numUsers==1){
					intent.putExtra("USER_1", contentArr[1]);
				}else if(numUsers==2){
					intent.putExtra("USER_1", contentArr[1]);
					intent.putExtra("USER_2", contentArr[2]);
				}else if(numUsers==3){
					intent.putExtra("USER_1", contentArr[1]);
					intent.putExtra("USER_2", contentArr[2]);
					intent.putExtra("USER_3", contentArr[3]);
				}else if(numUsers==4){
					intent.putExtra("USER_1", contentArr[1]);
					intent.putExtra("USER_2", contentArr[2]);
					intent.putExtra("USER_3", contentArr[3]);
					intent.putExtra("USER_4", contentArr[4]);
				}
				
				startActivity(intent);
				finish();
			}else{
				Toast.makeText(getApplicationContext(), "등록되지 않은 리시브 flag : "+flag, Toast.LENGTH_LONG).show();
			}
			
			
		}
		
		
	}

}
