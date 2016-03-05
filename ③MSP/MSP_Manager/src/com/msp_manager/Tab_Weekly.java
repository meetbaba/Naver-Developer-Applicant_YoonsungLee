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
 * @author ����
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
        // (4) �νĵ� ��¥�� ���
        updateDisplay();
	
	}
	

	 private void updateDisplay() {
		// main.xml�� ���̾ƿ��� ��ġ�� ��¥ �Է� TextView�� �νĵ� ��¥ ���
		 
		txtView_date.setText(new StringBuilder()
				// ���� �ý��ۿ��� 0~11�� �ν��ϱ� ������ 1�� ������
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
			// ����ڰ� ������ ��¥�� ���
			
			/**
			 * c5xxx��¥  �޼��� ���� (��¥�� ��-��-��
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
				 *C4xxx���۳�¥:���μ�:����1:���� �޼���:+1�� �޼���:+2�� �޼���(5��) :����2:�ݺ�..���μ���ŭ
				 */
				String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
				startDay= contentArr[0].split("-")[2];
				Log.i("Tab Weekly", "start day"+startDay);
				numOfLine=Integer.parseInt(contentArr[1]);
				num_list_statistics=new int[numOfLine][5];
				
				int indexOfCurContentArr=2;
				
				for(int i=0; i<numOfLine; i++){
										
					String strLineName;
					/* �� : ���� �ݺ�					 */
					strLineName=contentArr[indexOfCurContentArr];
					indexOfCurContentArr++;
					
					mArrayAdapter.add(new ItemContentWeekly(strLineName, this));
					mArrayAdapter.notifyDataSetChanged();

					Log.i("recieved msg flag c4", "new Addapter : "+strLineName);
					/*������� �ݺ� */
					
					
					for(int j=0; j<5; j++){
						num_list_statistics[i][j]=Integer.parseInt(contentArr[indexOfCurContentArr]);
						indexOfCurContentArr++;
						
						Log.i("recieved msg falg c4", strLineName+" _ statistics : "+j+"st day: "+num_list_statistics[i][j]);

					}
					
					
				}
				}else if(flag.equals(fzz)){
					/**
					 * zzxxxŬ���� ����Ʈ�̸�:position
					 */
					String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
					
					String clicked_list_name=contentArr[0];
					int clicked_list_position=Integer.parseInt(contentArr[1]);
					/**
					 * Ŭ���� list�� �ش��ϴ� �׷��� ����ֱ�
					 */
					showUpGraph(clicked_list_name, num_list_statistics[clicked_list_position], mArrayAdapter, mHandler);
					
					
				
				
			}else{
				Toast.makeText(getApplicationContext(), "��ϵ��� ���� flag : "+flag, Toast.LENGTH_LONG).show();
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
//				 * zzxxxŬ���� ����Ʈ�̸�:position
//				 */
//				String[] contentArr=Functions.extractMsgStringArray(msglength, msgContent);
//				
//				String clicked_list_name=contentArr[0];
//				int clicked_list_position=Integer.parseInt(contentArr[1]);
//				/**
//				 * Ŭ���� list�� �ش��ϴ� �׷��� ����ֱ�
//				 */
//				showUpGraph(clicked_list_name, num_list_statistics[clicked_list_position], mArrayAdapter, mHandler);
//				
//				
//			}else{
//				Toast.makeText(getApplicationContext(), "��ϵ��� ���� flag : "+flag, Toast.LENGTH_LONG).show();
//			}
//		}
//	}
	
	public void showUpGraph(String listName, int[] list_statistics, CustomAdapterWeekly param_adpater, Handler handler){
		// ǥ���� ��ġ��
		List<int[]> values = new ArrayList<int[]>();
		values.add(list_statistics);

		
		
		/** �׷��� ����� ���� �׷��� �Ӽ� ������ü */
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

		// ��� ǥ�� ����� ���� ũ��
		renderer.setChartTitle(listName+" �ְ� ���");
		renderer.setChartTitleTextSize(50);

		// �з��� ���� �̸�
		String[] titles = new String[] { "�Ϻ� �޼���" };

		// �׸��� ǥ���ϴµ� ���� ����
		int[] colors = new int[] {Color.YELLOW} ;
//		
//		for(int i=0; i<list_statistics.length ; i++){
//			if(list_statistics[i]<85){
//				colors[i]=Color.YELLOW;
//			}else{
//				colors[i]=Color.RED;
//			}
//		}

		// �з��� ���� ũ�� �� �� ���� ����
		renderer.setLegendTextSize(40);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}

		// X,Y�� �׸��̸��� ���� ũ��
		renderer.setXTitle("��¥");
		renderer.setYTitle("��ǥ�޼���(%)");
		renderer.setAxisTitleTextSize(50);

		// ��ġ�� ���� ũ�� / X�� �ּ�,�ִ밪 / Y�� �ּ�,�ִ밪
		renderer.setLabelsTextSize(30);
		renderer.setXAxisMin(-0.5);
		renderer.setXAxisMax(5.5);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(100);

		// X,Y�� ���� ����
		renderer.setAxesColor(Color.WHITE);
		// �������, X,Y�� ����, ��ġ���� ���� ����
		renderer.setLabelsColor(Color.CYAN);

		// X���� ǥ�� ����
		renderer.setXLabels(0);
		
		renderer.addXTextLabel(0, new String(""));
		for(int i=1; i<6; i++){
			int day=Integer.parseInt(startDay)+i-1;
			renderer.addXTextLabel(i, new String(""+day+"��"));
		}
		
		
		// Y���� ǥ�� ����
		renderer.setYLabels(10);

		// X,Y�� ���Ĺ���
		renderer.setXLabelsAlign(Align.LEFT);
		renderer.setYLabelsAlign(Align.LEFT);
		// X,Y�� ��ũ�� ���� ON/OFF
		renderer.setPanEnabled(false, false);
		// ZOOM��� ON/OFF
		renderer.setZoomEnabled(false, false);
		// ZOOM ����
		renderer.setZoomRate(1.0f);
		// ���밣 ����
		renderer.setBarSpacing(0.5f);

		// ���� ���� ����
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

		// �׷��� ��ü ����
		GraphicalView gv = ChartFactory.getBarChartView(this, dataset,
				renderer, Type.STACKED);

		// �׷����� LinearLayout�� �߰�
		graphLayout.removeAllViewsInLayout();
		graphLayout.addView(gv);
//		
//		param_adpater.add(new ItemContentWeekly("���ſ�", handler));
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
