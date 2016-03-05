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

import java.util.Calendar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 윤성
 *
 */
public class Tab_Monthly extends Activity implements OnClickListener {

	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;

	TextView txtView_date;

	private int mYear;
	private int mMonth;
	private int mDay;

	private int realMonth;

	static final int DATE_DIALOG_ID = 0;

	int rate_success;
	int rate_fail;

	AlertDialog.Builder dialog;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {

		case R.id.TabMonthly_txtView_date:

			showDialog(DATE_DIALOG_ID);
			
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_monthly);

		Log.e("Start session filter", "start");

		txtView_date=(TextView)findViewById(R.id.TabMonthly_txtView_date);
		txtView_date.setOnClickListener(this);
		
		mHandler = new MsgHandler(Looper.getMainLooper());

		mSock = ClntSock.getInstance(Functions.IP, Functions.PORT);
		mSender = ClntSender.getInstance(mSock);
		mReciever = ClntReciever.getInstance(mSock);
		mReciever.setHandler(mHandler);

		final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth= c.get(Calendar.MONTH);
        mDay  = c.get(Calendar.DAY_OF_MONTH);
        // (4) 인식된 날짜를 출력
        updateDisplay();
	

		dialog = new AlertDialog.Builder(Tab_Monthly.this);

		dialog.setMessage("오류 발생률이 기준치 이상입니다. 생산 라인을 확인하세요.")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								
							}
						});
	}

	private void updateDisplay() {
		// main.xml의 레이아웃에 배치된 날짜 입력 TextView에 인식된 날짜 출력

		txtView_date.setText(new StringBuilder()
		// 월은 시스템에서 0~11로 인식하기 때문에 1을 더해줌
				.append(mYear).append("-").append(mMonth+1).append(" "));

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

		String flag;
		String[] content;
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			// 사용자가 지정한 날짜를 출력
			

			/**
			 * c3xxx날짜  메세지 전송 (날짜는 년-월-일
			 */
			Log.i("Ac_WorkGoal", "Date pick-up");
			flag="c3";
			
			
			
			content=new String[1];
			realMonth=mMonth+1;
			content[0]=new String(""+mYear+"-"+realMonth+"-"+mDay);
			
			Log.i("Ac_WorkGoal", "sended msg : "+mYear+"-"+mMonth+"-"+mDay);
			mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));
			
			updateDisplay();

		}
	};

	class MsgHandler extends Handler {

		public MsgHandler(Looper mainLooper) {
			// TODO Auto-generated constructor stub
			super(mainLooper);
		}

		@Override
		public void handleMessage(Message inputMessage) {
			// TODO Auto-generated method stub

			int msglength;
			msglength = inputMessage.what;
			byte[] msgContent = new byte[msglength];
			msgContent = (byte[]) inputMessage.obj;

			String flag = Functions.extractFlag(msgContent);

			String fc6 = new String("c6");

			Log.i("Tab_Monthly handler", "" + flag);

			if (flag.equals(fc6)) {
				/**
				 * C6xxx달성률:오류률
				 */

				String[] contentArr = Functions.extractMsgStringArray(
						msglength, msgContent);
				rate_success = Integer.parseInt(contentArr[0]);
				rate_fail = Integer.parseInt(contentArr[1]);

				showGraphSuccess(rate_success);

				showGraphFail(rate_fail);
				
				if(rate_fail>5){
					
					
					dialog.show();
					
				}

			} else {
				Toast.makeText(getApplicationContext(),
						"등록되지 않은 flag : " + flag, Toast.LENGTH_LONG).show();
			}
		}

	}

	public void showGraphSuccess(int value_success) {
		// 그래프 렌더러 생성
		DefaultRenderer renderer = new DefaultRenderer();

		// 상단 표시 제목과 글자 크기
		renderer.setChartTitle("목표 달성률");
		renderer.setChartTitleTextSize(70);


		int tmpValue=100-value_success;
		// 각 항목에 대한 데이터
		int[] values = new int[] { value_success, tmpValue };


		
		// 각 항목별 문구 지정
		CategorySeries series = new CategorySeries("목표 달성률");
		series.add("목표 달성률 : "+value_success+"(%)" , values[0]);
		series.add("달성 미달 : "+tmpValue+"(%)", values[1]);


		// 그래프와 함께 표시되는 항목별 표시 문구의 글자 크기
		renderer.setLabelsTextSize(50);

		// 하단부에 표시되는 문구의 글자 크기
		renderer.setLegendTextSize(40);

		// 각 항목에 연결할 컬러 지정
		int[] colors = new int[] { Color.GREEN, Color.RED };
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}

		// Zoom 버튼 표시 여부
		renderer.setZoomButtonsVisible(true);
		// Zoom 기능 활성화
		renderer.setZoomEnabled(true);

		// 그래프를 View로 얻어온다.
		GraphicalView gv = ChartFactory.getPieChartView(this, series, renderer);

		// 그래프의 화면 출력
		LinearLayout llBody = (LinearLayout) findViewById(R.id.TabMonthly_linearlayout_graph_success);
		llBody.addView(gv);
	}

	public void showGraphFail(int value_fail) {
		// 그래프 렌더러 생성
		DefaultRenderer renderer = new DefaultRenderer();

		// 상단 표시 제목과 글자 크기
		renderer.setChartTitle("오류 발생률");
		renderer.setChartTitleTextSize(70);



		int tmpValue=100-value_fail;
		// 각 항목에 대한 데이터
		int[] values = new int[] { tmpValue, value_fail };


		// 각 항목별 문구 지정
		CategorySeries series = new CategorySeries("오류 발생률");
		series.add("정상품 비율 : "+tmpValue+"(%)", values[0]);
		series.add("오류 발생률 : "+value_fail+"(%)", values[1]);


		// 그래프와 함께 표시되는 항목별 표시 문구의 글자 크기
		renderer.setLabelsTextSize(50);

		// 하단부에 표시되는 문구의 글자 크기
		renderer.setLegendTextSize(40);

		// 각 항목에 연결할 컬러 지정
		int[] colors = new int[] { Color.BLUE, Color.RED };
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}

		// Zoom 버튼 표시 여부
		renderer.setZoomButtonsVisible(true);
		// Zoom 기능 활성화
		renderer.setZoomEnabled(true);

		// 그래프를 View로 얻어온다.
		GraphicalView gv = ChartFactory.getPieChartView(this, series, renderer);

		// 그래프의 화면 출력
		LinearLayout llBody = (LinearLayout) findViewById(R.id.TabMonthly_linearlayout_graph_fail);
		llBody.addView(gv);
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
