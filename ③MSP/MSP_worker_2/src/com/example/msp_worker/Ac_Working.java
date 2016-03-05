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
 * @author 윤성
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
				 * 현재작업량 업데이트 
				 */
				
				break;
			
			case R.id.working_button_send:
				Log.i("Ac_Working", "Send button Clicked");

				blockSendBtn();
				
				/**
				 * send msg to Server.
				 * 03XXXID:LINE:성공량:오류량
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
		
		btn_start.setText("시작");
		btn_start.setTextColor(Color.YELLOW);
		btn_start.setEnabled(true);
		Log.i("Ac_Working", "block SendButton Done");

	}
	
	@SuppressLint("SimpleDateFormat")
	private void blockStartBtn(){
		
		long now=System.currentTimeMillis();
		Date date=new Date(now);
		
		SimpleDateFormat curTimeFormat=new SimpleDateFormat("HH시 mm분 ss초");
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
				 *  04XXXID:LINE:GOAL:현재작업량
				 */
				String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
				txtView_name.setText(contentArr[0]);
				txtView_line.setText(contentArr[1]);
				txtView_goal.setText(contentArr[2]);
				txtView_worked.setText(contentArr[3]);
				
			}else{
				Toast.makeText(getApplicationContext(), "등록되지 않은 flag : "+flag, Toast.LENGTH_LONG).show();
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
