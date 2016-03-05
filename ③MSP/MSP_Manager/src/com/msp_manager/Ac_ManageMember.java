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
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 윤성
 *
 */
public class Ac_ManageMember extends Activity implements OnClickListener{


	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;
	

	ListView mListView;
	CustomAdapterDaily mArrayAdapter;
	
	TextView txtView_date;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	
	static final int DATE_DIALOG_ID = 0;
	

	int numOfLine;
	int []numOfWorkerOnLine;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String flag;
		String[] content;
		
		switch(v.getId()){
		
		case R.id.WorkGoal_txtView_date :

			showDialog(DATE_DIALOG_ID);
			/**
			 * b1xxx날짜  메세지 전송 (날짜는 년-월-일
			 */
			Log.i("Ac_WorkGoal", "Date pick-up");
			flag="c1";
			
			
			
			content=new String[1];
			content[0]=new String(""+mYear+"-"+mMonth+"-"+mDay);
			
			Log.i("Ac_WorkGoal", "sended msg : "+mYear+"-"+mMonth+"-"+mDay);
			mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));
			
			break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_managemember);
	
		Log.e("Start session filter", "start");


		mHandler=new MsgHandler(Looper.getMainLooper());
		
		mSock = ClntSock.getInstance(Functions.IP, Functions.PORT);
		mSender=ClntSender.getInstance(mSock);
		mReciever=ClntReciever.getInstance(mSock);
		mReciever.setHandler(mHandler);
		
		mArrayAdapter=new CustomAdapterDaily();
		mListView=(ListView)findViewById(R.id.TabDaily_listview);
		
		mListView.setAdapter(mArrayAdapter);
	        
	}
	

	 private void updateDisplay() {
		// main.xml의 레이아웃에 배치된 날짜 입력 TextView에 인식된 날짜 출력
		 
		txtView_date.setText(new StringBuilder()
				// 월은 시스템에서 0~11로 인식하기 때문에 1을 더해줌
				.append(mYear).append("-").append(mMonth + 1).append("-")
				.append(mDay).append(" "));

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}

		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			// 사용자가 지정한 날짜를 출력
			updateDisplay();

		}
	};


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
			
			String fc2=new String("c2");

			Log.i("Ac_PlayingGame handler", ""+flag);
			
			if(flag.equals(fc2)){
				/**
				 *C2xxx날짜:라인수:라인-달성률-오류률:라인의 작업자수:작업자-달성률-오류률:라인-달성률-오류률:작업자-달성률-오류률:작업자-달성률-오류률
				 */
				int indexOfLineNum=0;
				String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
				numOfLine=Integer.parseInt(contentArr[1]);
				numOfWorkerOnLine=new int[numOfLine];
				
				int indexOfCurContentArr=2;
				
				for(int i=0; i<numOfLine; i++){
					String firstView;
					String secondView;
					String thirdView;
					
					String tmpStr;
					String[] tmpArr;
					
					/* 매 : 마다 반복					 */
					tmpStr=contentArr[indexOfCurContentArr];
					indexOfCurContentArr++;
					tmpArr=tmpStr.split("-");
					
					firstView=tmpArr[0];
					secondView=tmpArr[1];
					thirdView=tmpArr[2];
					
					mArrayAdapter.add(new ItemContentDaily(firstView, secondView, thirdView));
					
					Log.i("recieved msg flag c2", "new Addapter : "+firstView+"/"+secondView+"/"+thirdView);
					/*여기까지 반복 */
					
					numOfWorkerOnLine[i]=Integer.parseInt(contentArr[indexOfCurContentArr]);
					indexOfCurContentArr++;
					for(int j=0; j<numOfWorkerOnLine[i]; j++){
						tmpStr=contentArr[indexOfCurContentArr];
						indexOfCurContentArr++;
						tmpArr=tmpStr.split("-");
						
						firstView=tmpArr[0];
						secondView=tmpArr[1];
						thirdView=tmpArr[2];
						
						mArrayAdapter.add(new ItemContentDaily(firstView, secondView, thirdView));
						
						Log.i("recieved msg falg c2", "new Addapter : "+firstView+"/"+secondView+"/"+thirdView);

					}
					
					
				}
				
			}else{
				Toast.makeText(getApplicationContext(), "등록되지 않은 flag : "+flag, Toast.LENGTH_LONG).show();
			}
		}

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
