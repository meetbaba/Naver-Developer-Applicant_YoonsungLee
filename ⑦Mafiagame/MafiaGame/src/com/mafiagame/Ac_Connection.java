package com.mafiagame;


import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ac_Connection extends Activity implements OnClickListener {

	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	
	ConnectServer mConnectAsync;
	
	EditText editTxt_ip;
	EditText editTxt_port;
	
	Button btn_connection;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){

		case R.id.Connection_button_login:
			
//			Log.i("Connection Btn Clicked", ""+editTxt_ip.getText().toString());
//			Log.i("Connection Btn Clicked", ""+Integer.parseInt(editTxt_port.getText().toString()));
//			Functions.IP=editTxt_ip.getText().toString();
//			Functions.PORT=Integer.parseInt(editTxt_port.getText().toString());
//			
//			Log.i("Connection Btn Clicked", ""+Functions.IP);
//			Log.i("Connection Btn Clicked", ""+Functions.PORT);
//			
//			mConnectAsync=new ConnectServer();
//			mConnectAsync.execute(1);
//			
//			try{
//				Thread.sleep(5000);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			
			if(mSock.isConnected()==true){

				Toast.makeText(getApplicationContext(), "Connection Success ", Toast.LENGTH_LONG).show();

				Intent intent=new Intent(Ac_Connection.this, Ac_Register.class);
				startActivity(intent);
				finish();
			}
			
			break;
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection);
		
		Log.e("Start session filter", "start");

		editTxt_ip=(EditText)findViewById(R.id.Connection_inputbox_ip);
		editTxt_port=(EditText)findViewById(R.id.Connection_inputbox_port);
		btn_connection=(Button)findViewById(R.id.Connection_button_login);
		
		btn_connection.setOnClickListener(this);
		
		mConnectAsync=new ConnectServer();
		mConnectAsync.execute(1);
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
	
	private class ConnectServer extends AsyncTask<Integer, Integer, ClntSock>
	{

		@Override
		protected ClntSock doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			Log.i("AsyncTask", "do IN back");
			
			Log.i("AsyncTask", "Instance ¹Þ¾Ò¾î");
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
				
//				mSender.setPriority(SOCKET_PRIORITY);
//				mReciever.setPriority(SOCKET_PRIORITY);
				
				
				Log.i("Connect TCP Server", "Sender Thread:"+mSender.getPriority()+"\nReciever Thread: "+mReciever.getPriority());
				
				/**
				 *  - here
				 */
				
				Log.i("Ac_Connection", "socketThread Run");
				
				mSender.start();
				mReciever.start();
			}else{
				Toast.makeText(getApplicationContext(), "Connection Failed ", Toast.LENGTH_LONG).show();

			}
		}

	}

	
}
