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
import android.widget.TextView;

/**
 * @author 윤성
 *
 */
public class Ac_ChoiceMenu extends Activity implements OnClickListener{
	
	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;

	TextView txtView_name;
	TextView txtView_position;
	
	Button btn_workgoal;
	Button btn_statistics;
	Button btn_manage_member;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.ChoiceMenu_button_workgoal :
			
			Intent intentWorkGoal=new Intent(Ac_ChoiceMenu.this, Ac_WorkGoal.class);
			
			startActivity(intentWorkGoal);
			onPause();
			break;

		case R.id.ChoiceMenu_button_statistics:

			Intent intentStatistics = new Intent(Ac_ChoiceMenu.this, Ac_Statistics.class);

			startActivity(intentStatistics);
			onPause();
			break;

		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choicemenu);
	
		Log.e("Start session filter", "start");
		
		txtView_name=(TextView)findViewById(R.id.ChoiceMenu_txtview_name);
		txtView_position=(TextView)findViewById(R.id.ChoiceMenu_txtview_position);
		
		btn_workgoal=(Button)findViewById(R.id.ChoiceMenu_button_workgoal);
		btn_statistics=(Button)findViewById(R.id.ChoiceMenu_button_statistics);
		
		btn_workgoal.setOnClickListener(this);
		btn_statistics.setOnClickListener(this);

		mHandler=new MsgHandler(Looper.getMainLooper());
		
		mSock = ClntSock.getInstance(Functions.IP, Functions.PORT);
		mSender=ClntSender.getInstance(mSock);
		mReciever=ClntReciever.getInstance(mSock);
		mReciever.setHandler(mHandler);
		
		Intent intent=getIntent();
		
		txtView_name.setText(intent.getStringExtra("MANAGER_NAME"));
		txtView_position.setText(intent.getStringExtra("MANAGER_POSITION"));
		
		
	}

	class MsgHandler extends Handler{

		public MsgHandler(Looper mainLooper) {
			// TODO Auto-generated constructor stub
			super(mainLooper);
		}

		@Override
		public void handleMessage(Message inputMessage) {
			// TODO Auto-generated method stub
			
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
