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
 * �� ���α׷� MSP_Worker�� ȸ�� ���� ���ο��� ���ϴ� �۾��ڸ� ���� ����� ������, ��� ����� ��� �� ������ ���� �����ϴ� MSP�� �ý����� �����ϱ����� ���������.
 * �� ���α׷��� MSP_Woker�� ���ؼ� �۾��ڴ� �ս��� �۾����� ����� �� �ְ�, ���� �۾� �����Ȳ�� �ľ��� �� �ִ�. 
 * ���� �ڽ��� �۾��� ���뿡 ���� ��Ȯ�� ������ �����ڿ��� ������ �� �ְ�  �߰����� �����۾��� �ʿ� ���� �ȴ�. 
 * 
 * 
 * 
 */
package com.example.msp_worker;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ����
 *
 */
public class Ac_Working extends Activity implements OnClickListener{

	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;
	
	
	TextView txtView_name;
	TextView txtView_line;
	TextView txtView_goal;
	TextView txtView_worked;
	
	EditText editText_success;
	EditText editText_fail;
	
	Button btn_start;
	Button btn_send;
	Button btn_success_minus;
	Button btn_success_plus;
	Button btn_fail_minus;
	Button btn_fail_plus;
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int num=0;
		String tmpStr;
		String flag;
		String[] content;
		switch(v.getId()){
		
			case R.id.working_button_start:
				Log.i("Ac_Working", "Start button Clicked");
				blockStartBtn();
				
				/**
				 * �����۾��� ������Ʈ 
				 */
				
				break;
			
			case R.id.working_button_send:
				Log.i("Ac_Working", "Send button Clicked");

				blockSendBtn();
				
				/**
				 * send msg to Server.
				 * 03XXXID:LINE:������:������
				 */
				flag="03";
				content=new String[4];
				content[0]=txtView_name.getText().toString();
				content[1]=txtView_line.getText().toString();
				content[2]=editText_success.getText().toString();
				content[3]=editText_success.getText().toString();
				
				mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));
				
				break;
				
			case R.id.working_button_success_minus:
				Log.i("Ac_Working", "success minus button Clicked");
				
				Log.i("Ac_Working", ""+editText_success.getText().toString());
				tmpStr=editText_success.getText().toString();
				Log.i("Ac_Working", ""+Integer.parseInt(tmpStr));

				num=(int)(Integer.parseInt(tmpStr))-1;
				Log.i("Ac_Working", ""+num);

				editText_success.setText(""+num);
				break;
			
			case R.id.working_button_success_plus:
				Log.i("Ac_Working", "success plus button Clicked");

				Log.i("Ac_Working", ""+editText_success.getText().toString());
				tmpStr=editText_success.getText().toString();
				Log.i("Ac_Working", ""+Integer.parseInt(tmpStr));

				num=(int)(Integer.parseInt(tmpStr))+1;
				Log.i("Ac_Working", ""+num);
				editText_success.setText(""+num);
				break;
			
			case R.id.working_button_fail_minus:
				Log.i("Ac_Working", "fail minus button Clicked");

				num=(int)(Integer.parseInt(editText_fail.getText().toString()))-1;
				editText_fail.setText(""+num);
				break;
			
			case R.id.working_button_fail_plus:
				Log.i("Ac_Working", "fail plus button Clicked");

				num=(int)(Integer.parseInt(editText_fail.getText().toString()))+1;
				editText_fail.setText(""+num);
				break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_working);
		
		txtView_name=(TextView)findViewById(R.id.working_txtview_name);
		txtView_line=(TextView)findViewById(R.id.working_txtview_line);
		txtView_goal=(TextView)findViewById(R.id.working_txtview_goal);
		txtView_worked=(TextView)findViewById(R.id.working_txtview_worked);
		
		editText_success=(EditText)findViewById(R.id.working_edit_txt_success);
		editText_fail=(EditText)findViewById(R.id.working_edit_txt_fail);
		
		btn_start=(Button)findViewById(R.id.working_button_start);
		btn_send=(Button)findViewById(R.id.working_button_send);
		btn_success_minus=(Button)findViewById(R.id.working_button_success_minus);
		btn_success_plus=(Button)findViewById(R.id.working_button_success_plus);
		btn_fail_minus=(Button)findViewById(R.id.working_button_fail_minus);
		btn_fail_plus=(Button)findViewById(R.id.working_button_fail_plus);
		
		btn_start.setOnClickListener(this);
		btn_send.setOnClickListener(this);
		btn_success_minus.setOnClickListener(this);
		btn_success_plus.setOnClickListener(this);
		btn_fail_minus.setOnClickListener(this);
		btn_fail_plus.setOnClickListener(this);
		
		editText_success.setText("0");
		editText_fail.setText("0");
				
		Intent intent=getIntent();
		String worker_name=intent.getStringExtra("WORKER_NAME");
		String worker_line=intent.getStringExtra("WORKER_LINE");
		String worker_goal=intent.getStringExtra("WORKER_GOAL");
		String worker_worked=intent.getStringExtra("WORKER_WORKED");
		
		/***
		 * As a rule,array[0]=name, [1]=line, [2]=goal
 		 */
		txtView_name.setText(worker_name);
		txtView_line.setText(worker_line);
		txtView_goal.setText(worker_goal);
		txtView_worked.setText(worker_worked);
		
		mHandler=new MsgHandler(Looper.getMainLooper());
		
		mSock=ClntSock.getInstance(Functions.IP, Functions.PORT);
		mSender=ClntSender.getInstance(mSock);
		mReciever=ClntReciever.getInstance(mSock);
		mReciever.setHandler(mHandler);
		
		blockSendBtn();

		
		Log.i("Ac_Working", "onCreate Done");
	}
	
	private void blockSendBtn(){
		btn_send.setEnabled(false);
		btn_send.setTextColor(Color.GRAY);
		btn_success_minus.setEnabled(false);
		btn_success_plus.setEnabled(false);
		btn_fail_minus.setEnabled(false);
		btn_fail_plus.setEnabled(false);
		
		btn_start.setText("����");
		btn_start.setTextColor(Color.YELLOW);
		btn_start.setEnabled(true);
		Log.i("Ac_Working", "block SendButton Done");

	}
	
	@SuppressLint("SimpleDateFormat")
	private void blockStartBtn(){
		
		long now=System.currentTimeMillis();
		Date date=new Date(now);
		
		SimpleDateFormat curTimeFormat=new SimpleDateFormat("HH�� mm�� ss��");
		String curTime=curTimeFormat.format(date);
		
		btn_send.setEnabled(true);
		btn_send.setTextColor(Color.YELLOW);
		btn_success_minus.setEnabled(true);
		btn_success_plus.setEnabled(true);
		btn_fail_minus.setEnabled(true);
		btn_fail_plus.setEnabled(true);
		
		btn_start.setText(curTime);
		btn_start.setTextColor(Color.BLUE);
		btn_start.setEnabled(false);
		
		Log.i("Ac_Working", "block StartButton Done");

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
			
			String f04=new String("04");
			
			Log.i("Ac_PlayingGame handler", ""+flag);
			
			if(Integer.parseInt(flag)==Integer.parseInt(f04)){
				/**
				 *  04XXXID:LINE:GOAL:�����۾���
				 */
				String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
				txtView_name.setText(contentArr[0]);
				txtView_line.setText(contentArr[1]);
				txtView_goal.setText(contentArr[2]);
				txtView_worked.setText(contentArr[3]);
				
			}else{
				Toast.makeText(getApplicationContext(), "��ϵ��� ���� flag : "+flag, Toast.LENGTH_LONG).show();
			}
			
			
			
		}

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

}
