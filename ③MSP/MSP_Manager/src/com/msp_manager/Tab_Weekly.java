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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 윤성
 *
 */
public class Tab_Weekly extends Activity implements OnClickListener{

	ClntSock mSock;
	ClntSender mSender;
	ClntReciever mReciever;
	Handler mHandler;
	

	ListView mListView;
	CustomAdapterWeekly mArrayAdapter;
	
	TextView txtView_date;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	private int realMonth;
	
	static final int DATE_DIALOG_ID = 0;
	
	
	int numOfLine;
	int [][]num_list_statistics;
	
	String startDay;
	
	LinearLayout graphLayout;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		
		case R.id.TabWeekly_txtView_date :

			showDialog(DATE_DIALOG_ID);
			
			
			break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_weekly);
	
		Log.e("Start session filter", "start");

		graphLayout = (LinearLayout) findViewById(R.id.TabWeekly_linearlayout_graph);
		
		txtView_date=(TextView)findViewById(R.id.TabWeekly_txtView_date);
		txtView_date.setOnClickListener(this);

		mHandler=new MsgHandler(Looper.getMainLooper());
		
		mSock = ClntSock.getInstance(Functions.IP, Functions.PORT);
		mSender=ClntSender.getInstance(mSock);
		mReciever=ClntReciever.getInstance(mSock);
		mReciever.setHandler(mHandler);
		
		mArrayAdapter=new CustomAdapterWeekly();
		mListView=(ListView)findViewById(R.id.TabWeekly_listview);
		
		mListView.setAdapter(mArrayAdapter);
	        
		final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth= c.get(Calendar.MONTH);
        mDay  = c.get(Calendar.DAY_OF_MONTH);
        // (4) 인식된 날짜를 출력
        updateDisplay();
	
	}
	

	 private void updateDisplay() {
		// main.xml의 레이아웃에 배치된 날짜 입력 TextView에 인식된 날짜 출력
		 
		txtView_date.setText(new StringBuilder()
				// 월은 시스템에서 0~11로 인식하기 때문에 1을 더해줌
				.append(mYear).append("-").append(mMonth+1).append("-")
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
			 * c5xxx날짜  메세지 전송 (날짜는 년-월-일
			 */
			Log.i("Ac_WorkGoal", "Date pick-up");
			flag="c5";
			
			
			
			content=new String[1];
			realMonth=mMonth+1;
			content[0]=new String(""+mYear+"-"+realMonth+"-"+mDay);
			
			Log.i("Ac_WorkGoal", "sended msg : "+mYear+"-"+mMonth+"-"+mDay);
			mSender.sendMsg(Functions.makeMsgFromAndroid(flag, content));
			
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
			
			String fc4=new String("c4");
			String fzz=new String("zz");


			Log.i("Ac_PlayingGame handler", ""+flag);
			
			if(flag.equals(fc4)){
				/**
				 *C4xxx시작날짜:라인수:라인1:당일 달성률:+1일 달성률:+2일 달성률(5개) :라인2:반복..라인수만큼
				 */
				String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
				startDay= contentArr[0].split("-")[2];
				Log.i("Tab Weekly", "start day"+startDay);
				numOfLine=Integer.parseInt(contentArr[1]);
				num_list_statistics=new int[numOfLine][5];
				
				int indexOfCurContentArr=2;
				
				for(int i=0; i<numOfLine; i++){
										
					String strLineName;
					/* 매 : 마다 반복					 */
					strLineName=contentArr[indexOfCurContentArr];
					indexOfCurContentArr++;
					
					mArrayAdapter.add(new ItemContentWeekly(strLineName, this));
					mArrayAdapter.notifyDataSetChanged();

					Log.i("recieved msg flag c4", "new Addapter : "+strLineName);
					/*여기까지 반복 */
					
					
					for(int j=0; j<5; j++){
						num_list_statistics[i][j]=Integer.parseInt(contentArr[indexOfCurContentArr]);
						indexOfCurContentArr++;
						
						Log.i("recieved msg falg c4", strLineName+" _ statistics : "+j+"st day: "+num_list_statistics[i][j]);

					}
					
					
				}
				}else if(flag.equals(fzz)){
					/**
					 * zzxxx클릭된 리스트이름:position
					 */
					String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
					
					String clicked_list_name=contentArr[0];
					int clicked_list_position=Integer.parseInt(contentArr[1]);
					/**
					 * 클린된 list에 해당하는 그래프 띄워주기
					 */
					showUpGraph(clicked_list_name, num_list_statistics[clicked_list_position], mArrayAdapter, mHandler);
					
					
				
				
			}else{
				Toast.makeText(getApplicationContext(), "등록되지 않은 flag : "+flag, Toast.LENGTH_LONG).show();
			}
		}

	}
	
//	
//	class listViewHandler extends Handler{
//		
//	
//		public listViewHandler(Looper mainLooper) {
//			// TODO Auto-generated constructor stub
//			super(mainLooper);
//		}
//
//		@Override
//		public void handleMessage(Message inputMessage) {
//			// TODO Auto-generated method stub
//			
//			int msglength;
//			msglength=inputMessage.what;
//			byte[] msgContent=new byte[msglength];
//			msgContent=(byte[])inputMessage.obj;
//			
//			String flag=Functions.extractFlag(msgContent);
//			
//			String fzz=new String("zz");
//
//			Log.i("Ac_PlayingGame handler", ""+flag);
//			
//			if(flag.equals(fzz)){
//				/**
//				 * zzxxx클릭된 리스트이름:position
//				 */
//				String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
//				
//				String clicked_list_name=contentArr[0];
//				int clicked_list_position=Integer.parseInt(contentArr[1]);
//				/**
//				 * 클린된 list에 해당하는 그래프 띄워주기
//				 */
//				showUpGraph(clicked_list_name, num_list_statistics[clicked_list_position], mArrayAdapter, mHandler);
//				
//				
//			}else{
//				Toast.makeText(getApplicationContext(), "등록되지 않은 flag : "+flag, Toast.LENGTH_LONG).show();
//			}
//		}
//	}
	
	public void showUpGraph(String listName, int[] list_statistics, CustomAdapterWeekly param_adpater, Handler handler){
		// 표시할 수치값
		List<int[]> values = new ArrayList<int[]>();
		values.add(list_statistics);

		
		
		/** 그래프 출력을 위한 그래픽 속성 지정객체 */
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

		// 상단 표시 제목과 글자 크기
		renderer.setChartTitle(listName+" 주간 통계");
		renderer.setChartTitleTextSize(50);

		// 분류에 대한 이름
		String[] titles = new String[] { "일별 달성률" };

		// 항목을 표시하는데 사용될 색상값
		int[] colors = new int[] {Color.YELLOW} ;
//		
//		for(int i=0; i<list_statistics.length ; i++){
//			if(list_statistics[i]<85){
//				colors[i]=Color.YELLOW;
//			}else{
//				colors[i]=Color.RED;
//			}
//		}

		// 분류명 글자 크기 및 각 색상 지정
		renderer.setLegendTextSize(40);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}

		// X,Y축 항목이름과 글자 크기
		renderer.setXTitle("날짜");
		renderer.setYTitle("목표달성률(%)");
		renderer.setAxisTitleTextSize(50);

		// 수치값 글자 크기 / X축 최소,최대값 / Y축 최소,최대값
		renderer.setLabelsTextSize(30);
		renderer.setXAxisMin(-0.5);
		renderer.setXAxisMax(5.5);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(100);

		// X,Y축 라인 색상
		renderer.setAxesColor(Color.WHITE);
		// 상단제목, X,Y축 제목, 수치값의 글자 색상
		renderer.setLabelsColor(Color.CYAN);

		// X축의 표시 간격
		renderer.setXLabels(0);
		
		renderer.addXTextLabel(0, new String(""));
		for(int i=1; i<6; i++){
			int day=Integer.parseInt(startDay)+i-1;
			renderer.addXTextLabel(i, new String(""+day+"일"));
		}
		
		
		// Y축의 표시 간격
		renderer.setYLabels(10);

		// X,Y축 정렬방향
		renderer.setXLabelsAlign(Align.LEFT);
		renderer.setYLabelsAlign(Align.LEFT);
		// X,Y축 스크롤 여부 ON/OFF
		renderer.setPanEnabled(false, false);
		// ZOOM기능 ON/OFF
		renderer.setZoomEnabled(false, false);
		// ZOOM 비율
		renderer.setZoomRate(1.0f);
		// 막대간 간격
		renderer.setBarSpacing(0.5f);

		// 설정 정보 설정
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		for (int i = 0; i < titles.length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			int[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}

		// 그래프 객체 생성
		GraphicalView gv = ChartFactory.getBarChartView(this, dataset,
				renderer, Type.STACKED);

		// 그래프를 LinearLayout에 추가
		graphLayout.removeAllViewsInLayout();
		graphLayout.addView(gv);
//		
//		param_adpater.add(new ItemContentWeekly("제거용", handler));
//		
//		param_adpater.remove(param_adpater.getCount()-1);
//		param_adpater.notifyDataSetChanged();
		
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
