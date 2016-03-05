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
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 윤성
 *
 */
public class Ac_ReadyGame extends Activity implements OnClickListener{

	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;
	
	TextView txtView_name;
	TextView txtView_num_total;
	TextView txtView_user_01;
	TextView txtView_user_02;
	TextView txtView_user_03;
	TextView txtView_user_04;
	
	Button btn_start;
	
	int numUsers;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.ReadyGame_btn_start :
			
			Log.i("Ac_ReadyGame", "Start button click");
			String flag="03";
			String[] content=new String[1];
			content[0]="#";
			
			mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));
			break;
		}
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_readygame);
		
		txtView_name=(TextView)findViewById(R.id.ReadyGame_txtview_name);
		txtView_num_total=(TextView)findViewById(R.id.ReadyGame_txtView_num_total);
		txtView_user_01=(TextView)findViewById(R.id.ReadyGame_txtView_user01_name);
		txtView_user_02=(TextView)findViewById(R.id.ReadyGame_txtView_user02_name);
		txtView_user_03=(TextView)findViewById(R.id.ReadyGame_txtView_user03_name);
		txtView_user_04=(TextView)findViewById(R.id.ReadyGame_txtView_user04_name);
		btn_start=(Button)findViewById(R.id.ReadyGame_btn_start);
		
		btn_start.setOnClickListener(this);
		
		mHandler=new MsgHandler(Looper.getMainLooper());
		
		mSock = ClntSock.getInstance(Functions.IP, Functions.PORT);
		mSender=ClntSender.getInstance(mSock);
		mReciever=ClntReciever.getInstance(mSock);
		mReciever.setHandler(mHandler);
		
		Intent intent=getIntent();
		numUsers=Integer.parseInt(intent.getStringExtra("NUM_USERS"));
		txtView_name.setText(intent.getStringExtra("PLAYER_NAME"));
		txtView_num_total.setText(""+numUsers);
		
		String[] tmpStr=new String[numUsers];
		if(numUsers==1){
			tmpStr[0]=intent.getStringExtra("USER_1");
		}else if(numUsers==2){
			tmpStr[0]=intent.getStringExtra("USER_1");
			tmpStr[1]=intent.getStringExtra("USER_2");
		}else if(numUsers==3){
			tmpStr[0]=intent.getStringExtra("USER_1");
			tmpStr[1]=intent.getStringExtra("USER_2");
			tmpStr[2]=intent.getStringExtra("USER_3");
		}else if(numUsers==4){
			tmpStr[0]=intent.getStringExtra("USER_1");
			tmpStr[1]=intent.getStringExtra("USER_2");
			tmpStr[2]=intent.getStringExtra("USER_3");
			tmpStr[3]=intent.getStringExtra("USER_4");
		}
		
		for(int i=0; i<tmpStr.length; i++){
			Log.i("ReadyGame", "tmpStr["+i+"] : "+tmpStr[i]);
		}
		
		allocateUsers(tmpStr);
		
		
	}
	
	private void allocateUsers(String[] userArr){
		
		numUsers=userArr.length;
		
		if(numUsers==1){
			txtView_user_01.setText(userArr[0]);
		}else if(numUsers==2){
			txtView_user_01.setText(userArr[0]);
			txtView_user_02.setText(userArr[1]);
		}else if(numUsers==3){
			txtView_user_01.setText(userArr[0]);
			txtView_user_02.setText(userArr[1]);
			txtView_user_03.setText(userArr[2]);
		}else if(numUsers==4){
			txtView_user_01.setText(userArr[0]);
			txtView_user_02.setText(userArr[1]);
			txtView_user_03.setText(userArr[2]);
			txtView_user_04.setText(userArr[3]);
		}
		
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
			String f04=new String("04");
			Log.i("Ac_ReadyGame handler", ""+flag);
			
			if(Integer.parseInt(flag)==Integer.parseInt(f02)){
				
				/**
				 *  f02 content format :    인원수:유저1:유저2:유저3:유저4
				 */
				String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
				
				String[] tmpStr=new String[contentArr.length-1];
				System.arraycopy(contentArr, 1, tmpStr, 0, tmpStr.length);
				
				allocateUsers(tmpStr);
				
				txtView_num_total.setText(contentArr[0]);
			}
			else if(Integer.parseInt(flag)==Integer.parseInt(f04)){
				/**
				 * 	packet 안에 flag 다음에 4byte로 위치하도록한 int값의 msg content length를 뽑아내는 과정
				 */
				
				
				/**
				 *  activity 전환
				 */
				Intent intent=new Intent(Ac_ReadyGame.this, Ac_PlayingGame.class);
				
				intent.putExtra("PLAYER_NAME", txtView_name.getText().toString());
				intent.putExtra("NUM_USERS", ""+numUsers);
				
				if(txtView_user_01.getText().toString() !=null){
					intent.putExtra("USER_1", txtView_user_01.getText().toString());
				}
				if(txtView_user_02.getText().toString() !=null){
					intent.putExtra("USER_2", txtView_user_02.getText().toString());
				}
				if(txtView_user_03.getText().toString() !=null){
					intent.putExtra("USER_3", txtView_user_03.getText().toString());
				}
				if(txtView_user_04.getText().toString() !=null){
					intent.putExtra("USER_4", txtView_user_04.getText().toString());
				}
				
				startActivity(intent);
				finish();
			}else{
				Toast.makeText(getApplicationContext(), "등록되지 않은 flag : "+flag, Toast.LENGTH_LONG).show();
			}
			
			
		}

	}
}
